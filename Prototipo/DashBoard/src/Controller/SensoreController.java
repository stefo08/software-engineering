package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.SensorDAO;
import Model.VO.Sensor;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class SensoreController {

    private static final int MONGODB = 0;
    private DAOFactory mongoDBFactory;
    private SensorDAO sensoreDAO;


    public SensoreController(){

        mongoDBFactory = DAOFactory.getDAOFactory(MONGODB);
        sensoreDAO = mongoDBFactory.getSensorDAO();

    }

    public List<Sensor> getSensoriEdificio(String Owner){

        List<Sensor> sensori = new ArrayList<Sensor>();
        DBCursor sens = sensoreDAO.getSensoriEdificio(Owner);
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

}
