package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.SensorDAO;
import Model.VO.Sensor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

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

    public List<Sensor> getSensoriEdificio(DBObject obj){

        List<Sensor> sensori = new ArrayList<Sensor>();

        for (String key : obj.keySet()){

            DBObject sens = sensoreDAO.getSensoriEdificio((String) obj.get(key));
            System.out.println(sens.toString());
                Sensor s = new Sensor();
                    s.setNumSensore((int) sens.get("Number"));
                    s.setMaxRange((int) sens.get("MaxRange"));
                    s.setMinRange((int) sens.get("MinRange"));
                    s.setID(sens.get("_id").toString());
            sensori.add(s);

        }

        return sensori;
    }

}
