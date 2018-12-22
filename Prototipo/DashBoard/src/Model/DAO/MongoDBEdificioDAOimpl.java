package Model.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoDBEdificioDAOimpl implements EdificioDAO {

    private String COLLECTION = "edificio";
    private MongoDBDAOFactory factory;
    private DBCollection edificioCollection = null;

    @Override
    public String getEdificioGestore(String username) {

        edificioCollection = factory.createConnection().getCollection(COLLECTION);
        DBObject obj = edificioCollection.findOne(new BasicDBObject().append("Username", "Angeloo"));
        return (String) obj.get("Edifcio");

    }

}
