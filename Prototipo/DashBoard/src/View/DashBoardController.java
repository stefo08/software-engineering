package View;

/**
 * Software Engineering Project: Dashboard Ambientale
 */

import Controller.DataController;
import Controller.EdificioController;
import Controller.GestoreController;
import Controller.SensoreController;
import Model.VO.Sensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class DashBoardController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button modifica;
    @FXML
    private TableView Table;
    @FXML
    private TableColumn IDColumn, NumberColumn, ValueColumn, DataColumn;

    private List<Sensor> listasensori;
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
        listasensori = new ArrayList<Sensor>();
        format = new SimpleDateFormat("HH:mm:ss");
        Run();

    }

    /**
     * Il metodo Run, passa al Controller Gestore l'User di chi ha effettuato l'accesso al sistema. I Controller recuperano, l'User, l'Edificio
     * in suo possesso e i relativi sensori con i valori [min e max]. Viene inizializzata la Table View che servirà a mostrare i dati live al cliente
     */

    private void Run(){

        //String idEdificio = controllerGestore.getGestoreEdificio("Angelo");
        //DBObject sensori = controllerEdificio.getSensoriEdificio(idEdificio);
        //System.out.println(sensori);
        listasensori = controllerSensore.getSensoriEdificio("Alessandro");
        System.out.println(listasensori);
        ObservableList<Sensor> values = FXCollections.
                observableArrayList();
        IDColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("ID"));
        NumberColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("numSensore"));
        ValueColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("value"));
        DataColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("Time"));

            for (Sensor s : listasensori){
                values.add(s);
            }

        Table.setItems(values);

        /**
         * Il Metodo setRowFactory è un metodo di libreria di FX ed è necessario per modificare le proprietà gradice della TableView sotto determinate
         * condizioni. Il metodo, ogni volta che un nuovo sensore viene aggiornato nella lista, intercetta la modifica (item) e verifica le condizioni
         * in modo da mostrare i "colori" in base al dato che viene inviato dal sensore. (Rosso: Sensore fuori range di molto, Arancione: Fuori range limite,
         * Verde: tutto ok
         */

        Table.setRowFactory(tv -> new TableRow<Sensor>() {
            @Override
            public void updateItem(Sensor item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("-fx-background-color: #ffffff;");
                } else {
                    int value = item.getValue(), max = item.getMaxRange(), min = item.getMinRange();
                    if (value > max + 3 || min < min - 3) setStyle("-fx-background-color: #9e0911;");
                    if ((value > max  && value <= max + 3 ) || (value < min && value >= min - 3))
                        setStyle("-fx-background-color: #de8101;");
                    if (value >= min && value <= max) setStyle("-fx-background-color: #007000;");
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
                for (Sensor s : listasensori) {
                    Sensor temp = controllerData.getLastData(s.getID());
                    for (Object items : Table.getItems()) {
                        Sensor temptable = (Sensor) items;
                        if (temptable.getNumSensore() == temp.getNumSensore()){
                            temptable.setValue(temp.getValue());
                            temptable.setTime(temp.getTime());
                        }
                        Table.refresh();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        f.start();


        Thread ControlTime = new Thread(() -> {

        while(true) {
            for (Object item : Table.getItems()) {
                Sensor tempor = (Sensor) item;
                Date data = new Date();
                String time = format.format(data);
                String Currmin = String.valueOf(time.charAt(3)) +
                        time.charAt(4);
                String Currh = String.valueOf(time.charAt(0)) +
                        time.charAt(1);
                String Currsec = String.valueOf(time.charAt(6)) +
                        time.charAt(7);
                String time2 = tempor.getTime();
                if(time2 != null) {
                    String Datah = String.valueOf(time2.charAt(0)) +
                            time2.charAt(1);
                    String datacur = String.valueOf(time2.charAt(3)) +
                    time2.charAt(4);
                    String Datasec = String.valueOf(time.charAt(6)) +
                            time.charAt(7);
                    if ((((parseInt(Currh) - parseInt(Datah)) > 0 ) && (parseInt(Currsec) - parseInt(Datasec)) >= 0)||
                            (((parseInt(Currmin) - parseInt(datacur)) > 0) && (parseInt(Currsec) - parseInt(Datasec)) >= 0)) {
                        System.out.println("Errore");
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        });
        ControlTime.start();

    }

    // al click del bottone carica la view della modifica dei valori
    @FXML
    private void caricaModificaValori(ActionEvent Event) throws IOException {
        AnchorPane pane = FXMLLoader.load( getClass().getResource("ModificaValori.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
