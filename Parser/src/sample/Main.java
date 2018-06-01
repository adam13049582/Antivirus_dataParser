package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{
   static String fxml="menu.fxml";
   static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

         stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.setTitle("Parser");
        primaryStage.setScene(new Scene(root, 500, 450, Color.RED));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

}
