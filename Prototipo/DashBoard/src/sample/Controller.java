package sample;

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
import org.bson.types.ObjectId;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView Table;
    @FXML
    private TableColumn IDColumn, NumberColumn, ValueColumn;

    private MongoClient client;
    private DB database;
    private DBCollection GestoreCollection;
    private DBCollection EdificioCollection;
    private DBCollection SensoriCollection;
    private DBCollection dataCollection;
    private List<Sensor> listasensori;

    @Override
    public void initialize(URL location, ResourceBundle resource){
        try {
            client = new MongoClient("localhost", 27018);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        listasensori = new ArrayList<Sensor>();
        database = client.getDB("progetto");
        GestoreCollection = database.getCollection("User");
        EdificioCollection = database.getCollection("Edificio");
        SensoriCollection = database.getCollection("Sensor");
        dataCollection = database.getCollection("Data");
        Run();

    }

    private void Run(){

        DBObject obj = GestoreCollection.findOne(new BasicDBObject().append("Username", "Angeloo"));
        BasicDBObject query = new BasicDBObject().append("_id", new ObjectId((String) obj.get("Edificio")));
        DBObject objedificio = EdificioCollection.findOne(query);
        DBObject obj12 = (DBObject) objedificio.get("Sensori");
        for (String key : obj12.keySet()){
            DBObject sens = SensoriCollection.findOne(new BasicDBObject().append("_id", new ObjectId((String) obj12.get(key))));
                System.out.println(sens.toString());
                Sensor s = new Sensor();
                s.setNumSensore((int) sens.get("Number"));
                s.setMaxRange((int) sens.get("MaxRange"));
                s.setMinRange((int) sens.get("MinRange"));
                s.setID(sens.get("_id").toString());
                listasensori.add(s);
        }

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
                    setStyle("");
                } else {
                    int value = item.getValue(), max = item.getMaxRange(), min = item.getMinRange();
                    if (value > max + 3 || min < min - 3) setStyle("-fx-background-color: #9e0911;");
                    if ((value > max  && value <= max + 3 ) || (value < min && value >= min - 3))
                        setStyle("-fx-background-color: #de8101;");
                    if (value >= min && value <= max) setStyle("-fx-background-color: #007000;");
                }
            }
        });

        Thread f = new Thread(){
            @Override
            public void run() {
                while (true) {
                    for (Sensor s : listasensori) {
                        DBCursor livesen = dataCollection.find(new BasicDBObject().append("IDSensore", s.getID()))
                                .sort(new BasicDBObject("_id", -1)).limit(1);
                        DBObject sen = livesen.next();
                        for (Object items : Table.getItems()) {
                            Sensor temp = (Sensor) items;
                            if (temp.getNumSensore() == ((int) sen.get("Number"))) {
                                temp.setValue((int) sen.get("Temp"));
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
            }
        }; f.start();

    }
}
