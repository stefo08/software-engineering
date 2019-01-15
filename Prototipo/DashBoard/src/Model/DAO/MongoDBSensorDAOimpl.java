package Model.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class MongoDBSensorDAOimpl implements SensorDAO {

    private String COLLECTION = "Sensor";
    private MongoDBDAOFactory factory = new MongoDBDAOFactory();
    private DBCollection sensorCollection = factory.createConnection().getCollection(COLLECTION);

    @Override
    public DBCursor getSensoriEdificio(String Owner){

        DBCursor sens = sensorCollection.find(new BasicDBObject().append("Owner", Owner));
        return sens;

    }
}
