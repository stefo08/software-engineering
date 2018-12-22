package Model.DAO;

import Model.VO.Sensor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoDBDataDAOimpl implements DataDAO {

    private String COLLECTION = "Data";
    private MongoDBDAOFactory factory;
    private DBCollection dataCollection = null;


    @Override
    public Sensor getData(String idSensore) {

        dataCollection = factory.createConnection().getCollection(COLLECTION);
        DBCursor livesen = dataCollection.find(new BasicDBObject().append("IDSensore", idSensore))
                .sort(new BasicDBObject("_id", -1)).limit(1);
            DBObject sen = livesen.next();
            Sensor s = new Sensor();
            s.setNumSensore((int) sen.get("Number"));
            s.setValue((int) sen.get("Temp"));
        return s;

    }
}
