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
    private TableColumn IDColumn, NumberColumn, ValueColumn;

    private List<Sensor> listasensori;
    private GestoreController controllerGestore;
    private SensoreController controllerSensore;
    private EdificioController controllerEdificio;
    private DataController controllerData;

    @Override
    public void initialize(URL location, ResourceBundle resource){

        controllerGestore = new GestoreController();
        controllerSensore = new SensoreController();
        controllerEdificio = new EdificioController();
        controllerData = new DataController();
        listasensori = new ArrayList<Sensor>();
        Run();

    }

    private void Run(){

        String idEdificio = controllerGestore.getGestoreEdificio("Angelo");
        DBObject sensori = controllerEdificio.getSensoriEdificio(idEdificio);
        System.out.println(sensori);
        listasensori = controllerSensore.getSensoriEdificio(sensori);
        System.out.println(listasensori);
        ObservableList<Sensor> values = FXCollections.
                observableArrayList();
        IDColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("ID"));
        NumberColumn.setCellValueFactory(new PropertyValueFactory<Sensor, Integer>("numSensore"));
        ValueColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("value"));

            for (Sensor s : listasensori){
                values.add(s);
            }

        Table.setItems(values);


        Table.setRowFactory(tv -> new TableRow<Sensor>() {
            @Override
            public void updateItem(Sensor item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("-fx-background-color: #fffff;");
                } else {
                    int value = item.getValue(), max = item.getMaxRange(), min = item.getMinRange();
                    if (value > max + 3 || min < min - 3) setStyle("-fx-background-color: #9e0911;");
                    if ((value > max  && value <= max + 3 ) || (value < min && value >= min - 3))
                        setStyle("-fx-background-color: #de8101;");
                    if (value >= min && value <= max) setStyle("-fx-background-color: #007000;");
                }
            }
        });

        Thread f = new Thread(() -> {
            while (true) {
                for (Sensor s : listasensori) {
                    Sensor temp = controllerData.getLastData(s.getID());
                    for (Object items : Table.getItems()) {
                        Sensor temptable = (Sensor) items;
                        if (temptable.getNumSensore() == temp.getNumSensore()){
                            temptable.setValue((int) temp.getValue());
                            items = (Object) temp;
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
        }); f.start();

    }
}
