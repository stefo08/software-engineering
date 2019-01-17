package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.SensorDAO;
import Model.VO.Sensor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SensoreController {

    private static final int MONGODB = 0;
    private DAOFactory mongoDBFactory;
    private SensorDAO sensoreDAO;


    public SensoreController(){

        mongoDBFactory = DAOFactory.getDAOFactory(MONGODB);
        sensoreDAO = mongoDBFactory.getSensorDAO();

    }

    public List<Sensor> getSensoriEdificio(String Edificio){

        List<Sensor> sensori = new ArrayList<Sensor>();
        DBCursor sens = sensoreDAO.getSensoriEdificio(Edificio);
        while(sens.hasNext()) {
            DBObject temp = sens.next();
            Sensor s = new Sensor();
            s.setNumSensore((int) temp.get("Number"));
            s.setMaxRange((int) temp.get("MaxRange"));
            s.setMinRange((int) temp.get("MinRange"));
            s.setID(temp.get("_id").toString());
            sensori.add(s);
        }

        return sensori;
    }

    public void updateRangeSensoreMax(String id, int max){
        sensoreDAO.updateRangeSensoreMax(id, max);
    }

    public void updateRangeSensoreMin(String id, int min){
        sensoreDAO.updateRangeSensoreMax(id, min);
    }
}
