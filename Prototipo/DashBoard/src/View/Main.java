package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            AnchorPane root = FXMLLoader.load( getClass().getResource("loginPage.fxml"));
            primaryStage.setTitle("Dashboard - Home");
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image("View/icon.png"));
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {e.printStackTrace();}

    }


    public static void main(String[] args) {
        launch(args);
    }
}
