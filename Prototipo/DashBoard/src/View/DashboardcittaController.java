package View;

/**
 * Software Engineering Project: Dashboard Ambientale
 */

import Controller.*;
import Model.VO.Edificio;
import Model.VO.Gestore;
import Model.VO.Sensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class DashboardcittaController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private TableView Table;
    @FXML
    private TableColumn NameColumn, ZoneColumn, numColumn, GestoreColumn;

    private List<Edificio> listaedifici;
    private GestoreController controllerGestore;
    private SensoreController controllerSensore;
    private EdificioController controllerEdificio;
    private ZonaController controllerZona;
    private DataController controllerData;
    private DateFormat format;

    @Override
    public void initialize(URL location, ResourceBundle resource){

        controllerGestore = new GestoreController();
        controllerSensore = new SensoreController();
        controllerEdificio = new EdificioController();
        controllerZona = new ZonaController();
        controllerData = new DataController();
        listaedifici = new ArrayList<Edificio>();
        format = new SimpleDateFormat("HH:mm:ss");
        Run();

    }

    private void Run(){

        Gestore logged = controllerGestore.getLoggedGestore();
        List<Edificio> lista = controllerZona.getEdificiCIttà(logged.getUser());
        ObservableList<Edificio> values = FXCollections.
                observableArrayList();
        NameColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Nome"));
        ZoneColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Zona"));
        GestoreColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Owner"));
        numColumn.setCellValueFactory(new PropertyValueFactory<Edificio, Integer>("numSensori"));

        for (Edificio e : lista){
            int count = 0;
            List<Sensor> listasensori= controllerSensore.getSensoriEdificio(e.getNome());
            e.setList(listasensori);
            for (Sensor s : listasensori){
                count++;
            }
            System.out.println(count);
            e.setNumSensori(count);
            values.add(e);
        }


        Table.setItems(values);


        Thread f = new Thread(() -> {
            while (true) {
                while (true) {
                    /*for (Sensor s : listasensori) {
                        Sensor temp = controllerData.getLastData(s.getID());
                        for (Object items : Table.getItems()) {
                            Sensor temptable = (Sensor) items;
                            if (temptable.getNumSensore() == temp.getNumSensore()) {
                                temptable.setValue(temp.getValue());
                                temptable.setTime(temp.getTime());
                            }
                            Table.refresh();
                        }
                    }*/
                    for(Edificio e : listaedifici){
                        for(Sensor s : e.getList()){
                            Sensor temp = controllerData.getLastData(s.getID());
                            for (Object items : Table.getItems()) {
                                Edificio ed = (Edificio) items;
                                List<Sensor> ls = ed.getList();
                                for(Sensor s2 : ls){
                                    if (s.getNumSensore() == s2.getNumSensore()) {
                                        
                                    }
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        f.start();

    }

    @FXML
    private void logout(){
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("loginPage.fxml"));
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
