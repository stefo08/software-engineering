package View;


import Controller.DataController;
import Controller.EdificioController;
import Controller.GestoreController;
import Controller.SensoreController;
import Model.VO.Sensor;
import com.mongodb.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Controller.GestoreController;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class loginPageController {


    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label getin;
    @FXML

    private GestoreController gestore = new GestoreController();

    @FXML
    public void login(ActionEvent actionEvent) {
        if (gestore.getin(username.getText(), password.getText())) {
            try {

                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = (Parent) fxmlLoader.load();


                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root));
                stage1.show();


            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        else {
            getin.setText("errore di accesso, usename o password errato/i");
        }

    }
}