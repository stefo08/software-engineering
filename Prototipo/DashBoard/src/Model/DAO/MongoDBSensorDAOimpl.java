package Model.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class MongoDBSensorDAOimpl implements SensorDAO {

    private String COLLECTION = "Sensor";
    private MongoDBDAOFactory factory = new MongoDBDAOFactory();
    private DBCollection sensorCollection = factory.createConnection().getCollection(COLLECTION);

    @Override
    public DBObject getSensoriEdificio(String id){

        DBObject sens = sensorCollection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));
        return sens;

    }
}
