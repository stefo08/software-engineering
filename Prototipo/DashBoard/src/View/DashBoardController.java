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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private TableView Table;
    @FXML
    private TableColumn IDColumn, NumberColumn, ValueColumn, DataColumn;

    private List<Sensor> listasensori;
    private GestoreController controllerGestore;
    private SensoreController controllerSensore;
    private EdificioController controllerEdificio;
    private DataController controllerData;

    @Override
    public void initialize(URL location, ResourceBundle resource){

        controllerGestore = new GestoreController();    //crea un controller di gestore
        controllerSensore = new SensoreController();    //crea un controller di sensore
        controllerEdificio = new EdificioController();  //crea un controller di edificio
        controllerData = new DataController();          //crea un controller dei dati
        listasensori = new ArrayList<Sensor>();         //crea una ArrayList di sensori
        Run();                                          //lancia il metodo run

    }

    private void Run(){

        String idEdificio = controllerGestore.getGestoreEdificio("Angelo");      //richiama il controller interroga mongo sull'id di edificio
        DBObject sensori = controllerEdificio.getSensoriEdificio(idEdificio);    //richiama il controller di edificio gli passa l'id dell'edificio calcolato alla scorsa linea e ritorna l'object di tutti i sensori di quell'edificio
        System.out.println(sensori);                                             //stampa i sensori appena calcolati
        listasensori = controllerSensore.getSensoriEdificio(sensori);            //interroga il controller e restituisce i sensori di edificio e li mette in un arraylist
        System.out.println(listasensori);                                        //stampa la lista dei sensori dall'arraylist
        ObservableList<Sensor> values = FXCollections.
                observableArrayList();                                           //observable per javafx (suppongo per mostrare i dati)
        IDColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("ID")); //setta la cella ID dell'fxml con l'id corrispondente
        NumberColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("numSensore")); //setta la cella NUMERO dell'fxml con il numero corrispondente
        ValueColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("value"));  //setta la cella VALORE dell'fxml con il valore corrispondente
        DataColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("Time"));  //setta la cella DATA dell'fxml con la data di ricezione del dato(suppongo)

            for (Sensor s : listasensori){          /*RIEMPIE LA OBSERVABLE LIST CHIAMATA values CICLANDO SUI SENSORI PRESI PRIMA E MESSI IN listsasensori*/
                values.add(s);
            }

        Table.setItems(values);                     /*riempie la table view di javafx (id=Table) con i vari valori appena presi nel ciclo sopra*/


        Table.setRowFactory(tv -> new TableRow<Sensor>() {          //metodo che immagino crei una nuova riga nella table
            @Override                                               //override di updateItem
            public void updateItem(Sensor item, boolean empty) {    //Passa l'oggetto sensore e un booleano
                super.updateItem(item, empty);                      //richiama il metodo originario
                if (item == null) {                                 //se l'oggetto sensore è nullo allora
                    setStyle("-fx-background-color: #ffffff;");     //cambia colore in ROSSO su quella riga (sensore ROTTO??)
                } else {
                    int value = item.getValue(), max = item.getMaxRange(), min = item.getMinRange();        //prenditi il dato del sensore e i rispettivi massimali e minimali
                    if (value > max + 3 || min < min - 3) setStyle("-fx-background-color: #9e0911;");       //se value è maggiore del massimale + 3 oppure è più piccolo di minimale - 3 allora cambia colore in ROSSO SCURO
                    if ((value > max  && value <= max + 3 ) || (value < min && value >= min - 3))
                        setStyle("-fx-background-color: #de8101;");                                         //se il value è compreso tra max e max+3 oppure ragionamento analogo al contrario per il minimale allora setta il colore GIALLO
                    if (value >= min && value <= max) setStyle("-fx-background-color: #007000;");           //se il valore è compreso tra il massimale e minimale colora di VERDE
                }
            }
        });

        Thread f = new Thread(() -> {               //CREA IL THREAD f
            while (true) {                          //while true
                for (Sensor s : listasensori) {     //per ogni sensore in lista sensori
                    Sensor temp = controllerData.getLastData(s.getID());    //crea un temporaneo prenditi l'ultimo dato del sensore s
                    for (Object items : Table.getItems()) {                 //Table.getItems si prende gli items dalla dashboard fino a quel momento
                        Sensor temptable = (Sensor) items;                  //oggetto temporaneo di tipo sensore
                        if (temptable.getNumSensore() == temp.getNumSensore()){     //se il sensore è lo stesso
                            temptable.setValue(temp.getValue());                    //allora setta il valore nuovo del dato ricevuto (quello nuovo credo)
                            temptable.setTime(temp.getTime());                      //stessa solfa ma con la data nuova
                            items = (Object) temp;
                        }
                        Table.refresh();                                            //refresha la tableview
                    }
                }
                try {                                                               //il thread va in sleep per 10 millisecondi( detto da Di Matteo che non capisce un cazzo )
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        f.start();

    }
}
