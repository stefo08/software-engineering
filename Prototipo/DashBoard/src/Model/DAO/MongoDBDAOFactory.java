package Model.DAO;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import javafx.scene.control.Alert;

import java.net.UnknownHostException;

public class MongoDBDAOFactory extends DAOFactory {

    public static String DRIVER = "localhost";
    public static int PORT = 27018;
    public static String DATABASE = "progetto";
    private MongoClient client;
    private DB database;

    public DB createConnection(){

        try {
            client = new MongoClient(DRIVER, PORT);
        } catch (UnknownHostException e)
            {e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Errore accesso al Database");
                alert.setHeaderText(null);
                alert.setContentText("Connection problem: " +e.getMessage() +"//localhost:" +PORT
                        + ":  Database Off-line");
                alert.showAndWait();
            }
            database = client.getDB(DATABASE);
        return database;
    }

    @Override
    public GestoreDAO getGestoreDAO() {
        return new MongoDBGestoreDAOimpl();
    }

    @Override
    public SensorDAO getSensorDAO() {
        return new MongoDBSensorDAOimpl();
    }

    @Override
    public EdificioDAO getEdificioDAO() {
        return new MongoDBEdificioDAOimpl();
    }

    @Override
    public ZonaDAO getZonaDAO() {
        return new MongoDBZonaDAOimpl();
    }

}
