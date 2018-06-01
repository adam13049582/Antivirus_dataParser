package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Statistic {
    @FXML private CheckBox firewall,malware,parental,http,ransomware,backup,browsing,real,anti,password,wifi,data;
    String zapytanie;
    PreparedStatement ps;
    List<Antivirus> listtocsv;
    @FXML Button use;
    @FXML AnchorPane tlo;
    @FXML private TableView<Antivirus> result;
    @FXML private TableColumn<Antivirus,String> cname;
    @FXML private TableColumn<Antivirus,String> cversion;
    @FXML private TableColumn<Antivirus,String> cdate;
    @FXML private TableColumn<Antivirus,String> cfirewall;
    @FXML private TableColumn<Antivirus,String> cmalware;
    @FXML private TableColumn<Antivirus,String> cparental;
    @FXML private TableColumn<Antivirus,String> chttp;
    @FXML private TableColumn<Antivirus,String> cransomware;
    @FXML private TableColumn<Antivirus,String> cbackup;
    @FXML private TableColumn<Antivirus,String> cbrowser;
    @FXML private TableColumn<Antivirus,String> creal;
    @FXML private TableColumn<Antivirus,String> canti;
    @FXML private TableColumn<Antivirus,String> cpassword;
    @FXML private TableColumn<Antivirus,String> cwifi;
    @FXML private TableColumn<Antivirus,String> cdata;
    @FXML
    public void back() throws IOException {
        Main.fxml = "menu.fxml";
        Scene scene;
        Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
        scene=new Scene(root, 500, 450);
        Main.stage.setScene(scene);
    }


    public void use(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result.getItems().clear();
            }
        });
        Runnable task=new Runnable() {
            @Override
            public void run() {
                Database.connect_with_database();
                try {
                    toUse();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread thread=new Thread(task);
        thread.start();
    }

    public void toUse() throws  SQLException{
        final CheckBox[] chkname = {firewall, malware, parental, http, ransomware, backup, browsing, real, anti, password, wifi, data};
        List<String>selectedcheck = new ArrayList<String>();

        Antivirus anti;
        cname.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("name"));
        cversion.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("version"));
        cdate.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("date"));
        cfirewall.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("firewall"));
        cmalware.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("malware"));
        cparental.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("parental"));
        chttp.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("http"));
        cransomware.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("ransomware"));
        cbackup.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("backup"));
        cbrowser.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("browsing"));
        creal.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("real"));
        canti.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("anti"));
        cpassword.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("password"));
        cwifi.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("wifi"));
        cdata.setCellValueFactory(new PropertyValueFactory<Antivirus, String>("data"));

        zapytanie = "SELECT * FROM Antivirus_software WHERE ";

        for (int i = 0; i < chkname.length; i++) {
            if (chkname[i].isSelected() == true) {
                selectedcheck.add(chkname[i].getText());
            }
        }

        if (selectedcheck.size() > 1) {
            for(int k=0;k<selectedcheck.size();k++) {
                if (k < selectedcheck.size() - 1) {
                    zapytanie += "\"" + selectedcheck.get(k) + "\"" + "='T' AND ";
                }
                if (k == selectedcheck.size() - 1) {
                    zapytanie += "\"" + selectedcheck.get(k) + "\"" + "='T'";
                }
            }
        }else{
            zapytanie += "\"" + selectedcheck.get(0) + "\"" + "='T'";
        }
listtocsv=new ArrayList<Antivirus>();
        System.out.println(zapytanie);

        ps = Database.conn.prepareStatement(zapytanie);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            anti = new Antivirus(rs.getString("name"), rs.getString("version_of_data"), rs.getString("date"), rs.getString("firewall"), rs.getString("malware"), rs.getString(7),
                    rs.getString(8), rs.getString("ransomware"), rs.getString(10), rs.getString("backup"), rs.getString(12), rs.getString(13), rs.getString("password"), rs.getString(15), rs.getString(16));
listtocsv.add(anti);
            final Antivirus finalAnti = anti;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    result.getItems().add(finalAnti);
                }
            });
        }

    }

    @FXML
    public void export() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

            Writer writer = null;
            try {
                File file = fileChooser.showSaveDialog(Main.stage);
                if (file != null) {
                    writer = new BufferedWriter(new FileWriter(file));
                    String title="name,version_of_data,date,firewall, malware, parental control, http monitor, ransomware, backup, browsing, real-time, anti-spam, password, wi-fi security, data loss protection"+ "\n";
                    writer.write(title);
                    for (Antivirus anty : listtocsv) {

                        String text = anty.getName() + "," + anty.getVersion() + "," + anty.getDate() +","+ anty.getFirewall() + "," + anty.getMalware() + "," + anty.getParental() +
                                ","+anty.getHttp() + "," + anty.getRansomware() + "," + anty.getBackup() + "," + anty.getBrowser() + "," + anty.getReal() + "," + anty.getAnti() +
                                "," + anty.getPassword() + "," + anty.getWifi() + "," + anty.getData() + "\n";

                        writer.write(text);
                    }
                }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            finally{

                    writer.flush();
                    writer.close();
                }

        }

}
