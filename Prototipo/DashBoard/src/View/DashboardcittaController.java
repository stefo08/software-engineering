package View;

/**
 * Software Engineering Project: Dashboard Ambientale
 */

import Controller.DataController;
import Controller.EdificioController;
import Controller.GestoreController;
import Controller.SensoreController;
import Model.VO.Edificio;
import Model.VO.Sensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    private TableView Table;
    @FXML
    private TableColumn NameColumn, ZoneColumn, numColumn;

    private List<Edificio> listaedifici;
    private GestoreController controllerGestore;
    private SensoreController controllerSensore;
    private EdificioController controllerEdificio;
    private DataController controllerData;
    private DateFormat format;

    /**
     * Il metodo initialize inizializza i Controller (Gestore, Sensore e Edificio)
     * Inizializza la lista che servirà a riempire la Table View
     */

    @Override
    public void initialize(URL location, ResourceBundle resource){

        controllerGestore = new GestoreController();
        controllerSensore = new SensoreController();
        controllerEdificio = new EdificioController();
        controllerData = new DataController();
        listaedifici = new ArrayList<Edificio>();
        format = new SimpleDateFormat("HH:mm:ss");
        Run();

    }

    /**
     * Il metodo Run, passa al Controller Gestore l'User di chi ha effettuato l'accesso al sistema. I Controller recuperano, l'User, l'Edificio
     * in suo possesso e i relativi sensori con i valori [min e max]. Viene inizializzata la Table View che servirà a mostrare i dati live al cliente
     */

    private void Run(){


        ObservableList<Edificio> values = FXCollections.
                observableArrayList();
        NameColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("Nome"));
        ZoneColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("Zona"));
        numColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("numeroSensori"));

        for (Edificio e : listaedifici){
            values.add(e);
        }

        Table.setItems(values);


        Table.setRowFactory(tv -> new TableRow<Sensor>() {
            @Override
            public void updateItem(Sensor item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("-fx-background-color: #ffffff;");
                } else {
                    //Algoritmo % dei Sensori in Errore
                }
            }
        });

        /**
         * Il thread è necessario per permettere la renderizzazione dei valori nella TableView in quanto l'aggiornamento sarebbe troppo veloce e non
         * darebbe tempo a FX di listare i valori. Il thread attraverso un While(true) è in continua richiesta di nuovi valori dal server, Viene
         * effettuato un controllo per ogni sensore presente, se il dato che arriva aggiorna un sensore, solleva il metodo sopra setRowFactory.
         */

        Thread f = new Thread(() -> {
            while (true) {
                //Codice che intercetta i nuovi valori dei Sensori
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        f.start();


    }
}
