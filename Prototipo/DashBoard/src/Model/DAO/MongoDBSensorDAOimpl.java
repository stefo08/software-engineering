package Model.DAO;

import Model.VO.Sensor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBSensorDAOimpl implements SensorDAO {

    private String COLLECTION = "Sensor";
    private MongoDBDAOFactory factory;
    private DBCollection sensorCollection = null;

    @Override
    public List<Sensor> getSensoriEdificio(DBObject obj) {

        sensorCollection = factory.createConnection().getCollection(COLLECTION);
        List<Sensor> sensori = new ArrayList<Sensor>();
        for (String key : obj.keySet()){
            DBObject sens = sensorCollection.findOne(new BasicDBObject().append("_id", new ObjectId((String) obj.get(key))));
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
