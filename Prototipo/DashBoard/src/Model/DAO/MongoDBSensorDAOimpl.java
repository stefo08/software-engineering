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
    public DBCursor getSensoriEdificio(String Edificio){

        DBCursor sens = sensorCollection.find(new BasicDBObject().append("Edificio", Edificio));
        return sens;

    }

    public void updateRangeSensoreMax(String id, int max){
        DBObject test = sensorCollection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));
        sensorCollection.update(test, new BasicDBObject("MinRange", max));
    }

    public void updateRangeSensoreMin(String id, int min){
        DBObject test = sensorCollection.findOne(new BasicDBObject().append("_id", new ObjectId(id)));
        sensorCollection.update(test, new BasicDBObject("MinRange", min));
    }
}
