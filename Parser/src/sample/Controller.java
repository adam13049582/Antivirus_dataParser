package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Controller{
    @FXML private TextField link;
    @FXML private TextField program;
    @FXML private Label exist;
@FXML private Button search;
@FXML private Button Edit;
@FXML private Button versions;

  PreparedStatement ps,stmt,ver,id;
  String a;
  Date date;
  String dates;
int spr=0;

    String[] ochrona = {"Firewall", "Parental Control", "Browsing", "Backup", "ransomware", "real-time", "malware",
            "Wi-FI security", "HTTP Monitor", "Data Loss Prevention", "Anti-spam", "password"};
    List<String>subweb;
    List<String>pdfsite;

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

  public String createrow(){
      int idd;
      int id1=0;
    try {
     ps=Database.conn.prepareStatement("SELECT name FROM Antivirus_software WHERE name ILIKE '"+program.getText()+"'");
      ResultSet rs=ps.executeQuery();
 a=null;

        date=new Date();
        SimpleDateFormat dateformat=new SimpleDateFormat("dd.MM.yyyy");
        dates=dateformat.format(date);

        if(rs.next()) {
          a = rs.getString(1);
      }
 if(a==null) {
      System.out.println(a);

     id=Database.conn.prepareStatement("SELECT MAX(id) FROM Antivirus_software");
     ResultSet result_id=id.executeQuery();

     if(result_id.next()){
         idd=result_id.getInt(1);
         id1=idd+1;
     }
      stmt=Database.conn.prepareStatement("INSERT INTO Antivirus_software(id,name,version_of_data,date,firewall,malware,"
              +"\"parental control\""+",backup,"+"\"http monitor\""+",ransomware,"+"\"browsing\""+","+"\"real-time\""
              +","+"\"anti-spam\""+",password,"+"\"wi-fi security\""+","+"\"data loss prevention\""+") VALUES ('"+id1+"','" + program.getText() + "','1',?,'NA','NA','NA','NA','NA','NA','NA','NA','NA','NA','NA','NA')");
  stmt.setString(1, dates);
 stmt.executeUpdate();

        }else {
     Platform.runLater(new Runnable() {
         @Override
         public void run() {
             exist.setText("Data about this product there are in database");
         }

     });
 }
 } catch (SQLException e) {
      e.printStackTrace();
    }
return  a;
  }

    static void sleep (){
        long waittime;
        Random time = new Random();
        waittime = time.nextInt(10)+5;
        try{
            Thread.sleep(waittime*100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

  public  void htmlscrapping() throws IOException {
      Elements answerers;
      String url = link.getText();
      Document document;
      String[] tagi = {"tr", "span", "title", "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "p"};

      if (isValid(url)) {
          createrow();


          if (a == null) {
              if(pdfsite.size()>0) {
              PDFparser();
          }
              subwebsites(url);
              subweb.add(url);

              for (int elem = 0; elem < subweb.size(); elem++) {
                  try {
                      document = Jsoup.connect(subweb.get(elem)).get();
                      sleep();
                      for (int i = 0; i < tagi.length; i++) {
                          for (int j = 0; j < ochrona.length; j++) {
                              answerers = document.getElementsByTag(tagi[i]);

                              for (Element answerer : answerers) {

                                  if (answerer.text().toLowerCase().contains(ochrona[j].toLowerCase()) == true) {
                                      ps = Database.conn.prepareStatement("Update antivirus_software SET" + "\"" + ochrona[j].toLowerCase() + "\"" + "='T' WHERE name ILIKE ?");
                                      ps.setString(1, program.getText());
                                      ps.executeUpdate();
                                  }

                              }
                          }
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }


              }
              spr = 1;
              System.out.println("Koniec");
          }
      }else {
              JOptionPane.showMessageDialog(null, "This string cannot be a website adress", "Error adress", JOptionPane.ERROR_MESSAGE);
          }

  }

public List<String> subwebsites(String url){
    Document document;
    Elements subs;
    subweb= new ArrayList<String>();
    pdfsite= new ArrayList<String>();
    try {
        document = Jsoup.connect(url).get();
        subs = document.select("a[href]");

        for(Element sub:subs){
            if((sub.attr("href").startsWith("http")||sub.attr("href").startsWith("https"))&&!sub.attr("href").endsWith(".pdf")&&!sub.attr("href").contains("linkedin")) {
                subweb.add(sub.attr("href"));
            }
            else if(sub.attr("href").endsWith(".pdf")){
                pdfsite.add(sub.attr("href"));
            }
        }
    }catch(IOException e){
e.printStackTrace();
    }
    return subweb;
}

    @FXML public void search() {


        Runnable task = new Runnable() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        exist.setText("");
                        exist.setWrapText(true);
                       search.setDisable(true);
                    }
                });
                Database.connect_with_database();
                try {
                    htmlscrapping();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (spr==1) {
                    JOptionPane.showMessageDialog(null, "Search results have been saved to the database", "Saved result", JOptionPane.INFORMATION_MESSAGE);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        search.setDisable(false);
                    }
                });
            }
        };
        Thread thr=new Thread(task);
        thr.start();


    }

    @FXML public void get_information() throws IOException {


                Main.fxml = "sample.fxml";
                Scene scene;
                Parent root = null;
                root = FXMLLoader.load(getClass().getResource(Main.fxml));
                scene = new Scene(root, 700, 450);
                Main.stage.setScene(scene);

  }

    @FXML public void edit_information() throws IOException {
        JOptionPane.showMessageDialog(null,"Remember that you could edit only the newest version of choose software","Remember",JOptionPane.INFORMATION_MESSAGE);

        Main.fxml = "edit.fxml";
        Scene scene;
        Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
        scene=new Scene(root, 700, 450);
        Main.stage.setScene(scene);
    }

    @FXML public void Edit() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        exist.setText("");
                        exist.setWrapText(true);
                        Edit.setDisable(true);
                    }
                });


                Database.connect_with_database();
                try {
                    toEdit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run () {
                        Edit.setDisable(false);
                        }
                        });}
            };

            Thread thread = new Thread(task);
        thread.start();


    }
    @FXML public void search_information() throws IOException {
        Main.fxml = "use_information.fxml";
        Scene scene;
        Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
        scene=new Scene(root, 1600, 550);
        Main.stage.setScene(scene);
    }

    @FXML public void back() throws IOException{
        Main.fxml = "menu.fxml";
        Scene scene;
        Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
        scene=new Scene(root, 500, 450);
        Main.stage.setScene(scene);
    }

    @FXML public void new_version() throws IOException {
        Main.fxml = "version.fxml";
        Scene scene;
        Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
        scene=new Scene(root, 700, 450);
        Main.stage.setScene(scene);
    }

    @FXML public void versions(){
        Runnable task=new Runnable() {
            @Override
            public void run() {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
              exist.setText("");
              exist.setWrapText(true);
              versions.setDisable(true);
          }
      });


                Database.connect_with_database();
                toVersion();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        versions.setDisable(false);
                    }
                });
            }

        };
Thread thread=new Thread(task);
thread.start();

    }


public void toEdit()throws SQLException {


    a = null;
    int version = 0;


    ps = Database.conn.prepareStatement("SELECT Name FROM Antivirus_software WHERE name=?");
    ps.setString(1, program.getText());
    ResultSet rs = ps.executeQuery();
    a = null;
    if (rs.next()) {
        a = rs.getString(1);
    }

    if (a != null) {
        Elements answerers;
        String url = link.getText();
        subwebsites(url);
        subweb.add(url);

        Document document;
        String[] tagi = {"tr", "span", "title", "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "p"};

        ver = Database.conn.prepareStatement("SELECT MAX(version_of_data) FROM Antivirus_software WHERE name=?");
        ver.setString(1, program.getText());
        ResultSet result_version = ver.executeQuery();

        if (result_version.next()) {
            version = result_version.getInt(1);
        }

        if (isValid(url)) {

                for (int elem = 0; elem < subweb.size(); elem++) {
                    try {
                        document = Jsoup.connect(subweb.get(elem)).get();
                        sleep();
                    for (int i = 0; i < tagi.length; i++) {
                        for (int j = 0; j < ochrona.length; j++) {
                            answerers = document.getElementsByTag(tagi[i]);

                            for (Element answerer : answerers) {
                                if (answerer.text().toLowerCase().contains(ochrona[j].toLowerCase()) == true) {
                                    ps = Database.conn.prepareStatement("Update antivirus_software SET" + "\"" + ochrona[j].toLowerCase() + "\"" + "='H' WHERE name ILIKE ? AND version_of_data=?");
                                    ps.setString(1, program.getText());
                                    ps.setInt(2, version);
                                    ps.executeUpdate();
                                }
                            }
                        }
                    }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(null, "Information have been updated", "Updated information", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Koniec");

        } else {
            JOptionPane.showMessageDialog(null, "This string cannot be a website adress", "Error adress", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                exist.setText("The database doesn't contain information about this software if you want add new information go to other overlap");
            }
        });
    }
}

public void toVersion(){
    int idd;
    int id1=0;
    int version=0;
    try {
        ps=Database.conn.prepareStatement("SELECT Name FROM Antivirus_software WHERE name=?");
        ps.setString(1,program.getText());
        ResultSet rs=ps.executeQuery();
        a=null;

        date=new Date();
        SimpleDateFormat dateformat=new SimpleDateFormat("dd.MM.yyyy");
        dates=dateformat.format(date);

        if(rs.next()) {
            a = rs.getString(1);
        }
        if(a!=null) {
            System.out.println(a);

            id=Database.conn.prepareStatement("SELECT MAX(id) FROM Antivirus_software");
            ResultSet result_id=id.executeQuery();

            if(result_id.next()){
                idd=result_id.getInt(1);
                id1=idd+1;
            }
            ver=Database.conn.prepareStatement("SELECT MAX(version_of_data) FROM Antivirus_software WHERE name=?");
            ver.setString(1,program.getText());
            ResultSet result_version=ver.executeQuery();

            if(result_version.next()){
                version=result_version.getInt(1);
            }

            stmt=Database.conn.prepareStatement("INSERT INTO Antivirus_software(id,name,version_of_data,date,firewall,malware,"
                    +"\"parental control\""+",backup,"+"\"http monitor\""+",ransomware,"+"\"browsing\""+","+"\"real-time\""
                    +","+"\"anti-spam\""+",password,"+"\"wi-fi security\""+","+"\"data loss prevention\""+") VALUES ('"+id1+"','" + program.getText() + "','"+(version+1)+"',?,'NA','NA','NA','NA','NA','NA','NA','NA','NA','NA','NA','NA')");
            stmt.setString(1,dates);
            stmt.executeUpdate();

        }else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    exist.setText("In database there aren't information about this software. If you want to get new information go to other overlap.");
                }
            });
    }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    if(a!=null) {
        Elements answerers = null;
        String url = link.getText();
        Document document;
        String []tagi={"tr","span","title","h1","h2","h3","h4","h5","h6","h7","h8","p"};

        if(isValid(url)) {
            if(a!=null) {
                subwebsites(url);
                subweb.add(url);

                    for (int elem = 0; elem < subweb.size(); elem++) {
                        try {
                            document = Jsoup.connect(subweb.get(elem)).get();
                            sleep();
                        for (int i = 0; i < tagi.length; i++) {
                            for (int j = 0; j < ochrona.length; j++) {
                                answerers = document.getElementsByTag(tagi[i]);

                                for (Element answerer : answerers) {

                                    if (answerer.text().toLowerCase().contains(ochrona[j].toLowerCase()) == true) {
                                        ps = Database.conn.prepareStatement("Update antivirus_software SET" + "\"" + ochrona[j].toLowerCase() + "\"" + "='T' WHERE name ILIKE ?");
                                        ps.setString(1, program.getText());
                                        ps.executeUpdate();
                                    }

                                }
                            }
                        }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    spr = 1;
                    System.out.println("Koniec");
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"This string cannot be a website adress","Error adress",JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "New version information of this software is already.", "Saved result", JOptionPane.INFORMATION_MESSAGE);
    }
}

    public void PDFparser() throws IOException {
        PDFTextStripper pdfStripper;
        PDDocument pdDoc = null;
        PreparedStatement pdfq;
        for(int i=0;i<pdfsite.size();i++) {
            URL url = new URL(pdfsite.get(i));
            try {
                pdDoc = PDDocument.load(url.openStream());
                pdfStripper = new PDFTextStripper();
                String parsedText = pdfStripper.getText(pdDoc);
                System.out.println(parsedText);
                for(int j=0;j<ochrona.length;j++){
                if(parsedText.toLowerCase().contains(ochrona[j].toLowerCase())){
                    pdfq = Database.conn.prepareStatement("Update antivirus_software SET" + "\"" + ochrona[j].toLowerCase() + "\"" + "='T' WHERE name ILIKE ?");
                    pdfq.setString(1, program.getText());
                    pdfq.executeUpdate();
                }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pdDoc.close();
            }
        }
    }
}