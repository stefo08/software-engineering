package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = FXMLLoader.load( getClass().getResource("Dashboard.fxml"));
            primaryStage.setTitle("Dashboard - Home");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
        } catch (Exception e) {e.printStackTrace();}
    }


    public static void main(String[] args) {
        launch(args);
    }
}
