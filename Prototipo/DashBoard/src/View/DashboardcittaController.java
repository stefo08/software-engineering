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
import java.util.List;
import java.util.ResourceBundle;

public class DashboardcittaController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane rootPane;
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
        listaedifici = controllerZona.getEdificiCIttà(logged.getUser());
        ObservableList<Edificio> values = FXCollections.
                observableArrayList();
        NameColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Nome"));
        ZoneColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Zona"));
        GestoreColumn.setCellValueFactory(new PropertyValueFactory<Edificio, String>("Owner"));
        numColumn.setCellValueFactory(new PropertyValueFactory<Edificio, Integer>("numSensori"));

        for (Edificio e : listaedifici){
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

        Table.setRowFactory(tv -> new TableRow<Edificio>() {
            @Override
            public void updateItem(Edificio item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("-fx-background-color: #ffffff;");
                } else {
                    if ( item.getLevelerror() == 1 ){
                        setStyle("-fx-background-color: #007000;");
                    }
                    if(item.getLevelerror() == 2 ) setStyle("-fx-background-color: #c97101;");
                    if(item.getLevelerror() == 3 ) setStyle("-fx-background-color: #840910;");
                }
            }
        });


        Thread f = new Thread(() -> {
            while (true) {
                    for(Edificio e : listaedifici){
                        for(Sensor s : e.getList()){
                            Sensor temp = controllerData.getLastData(s.getID());
                            for (Object items : Table.getItems()) {
                                Edificio ed = (Edificio) items;
                                List<Sensor> ls = ed.getList();
                                for(Sensor s2 : ls){
                                    if (temp.getNumSensore() == s2.getNumSensore()) {
                                        s2.setValue(temp.getValue());
                                        s2.setTime(temp.getTime());
                                    }
                                }
                                Table.refresh();
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        f.start();


        Thread controlError = new Thread(() -> {
            while (true) {
                for(Object itemtab : Table.getItems()){
                    Edificio e = (Edificio) itemtab;
                    int count = e.getNumSensori();
                    int err = 0;
                    for (Sensor s : e.getList()){
                        int value = s.getValue(), max = s.getMaxRange(), min = s.getMinRange();
                        if (value > max + 2 || value < min - 3) err += 1;
                    }
                    if ((err/count) >= 0.75) {e.setLevelerror(3);System.out.println("0.75");}
                    if (((err/count) >= 0.5) && ((err/count) <= 0.75))  {e.setLevelerror(2);System.out.println("0.5 >");}
                    if ((err/count) < 0.5) {e.setLevelerror(1); System.out.println("0.5 <");}
                    Table.refresh();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        controlError.start();
    }

    @FXML
    private void logout(ActionEvent Event) throws IOException {
        AnchorPane pane = FXMLLoader.load( getClass().getResource("loginPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
