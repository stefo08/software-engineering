package Model.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoDBGestoreDAOimpl implements GestoreDAO {

    private String COLLECTION = "User";
    private MongoDBDAOFactory factory = new MongoDBDAOFactory();
    private DBCollection edificioCollection = factory.createConnection().getCollection(COLLECTION);

    @Override
    public boolean CorrectLoginData(String username, String password) {
        return false;
    }

    @Override
    public String getGestoreEdificio(String user){

        edificioCollection = factory.createConnection().getCollection(COLLECTION);
        DBObject edif = edificioCollection.findOne(new BasicDBObject().append("Username", "Angeloo"));
        return (String) edif.get("Edificio");

    }


}
