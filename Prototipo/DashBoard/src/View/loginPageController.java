package View;


import Controller.GestoreController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
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