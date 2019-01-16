package Model.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

// import java.util.Collection;

public class MongoDBGestoreDAOimpl implements GestoreDAO {

    private String COLLECTION = "User";
    private MongoDBDAOFactory factory = new MongoDBDAOFactory();
    private DBCollection edificioCollection = factory.createConnection().getCollection(COLLECTION);
    private DBCollection gestoreCollection = factory.createConnection().getCollection(COLLECTION);

    @Override
    public boolean CorrectLoginData(String username, String password) {
    BasicDBObject allQuery = new BasicDBObject();
    DBObject query = new BasicDBObject().append("Username", username);
    	    	/*BasicDBObject fields = new BasicDBObject();
    	    	fields.put("username", username);
    	    	fields.put("password", password );*/
    	    	DBCursor cursor = gestoreCollection.find(query);

    	    	while (cursor.hasNext()) {
    	    		DBObject user = cursor.next();
    	    		if (((String) user.get("Password")).equals(password)) return true;
    	    	}
                                    return false;


    	}
    @Override
    public String getGestoreEdificio(String user){

        edificioCollection = factory.createConnection().getCollection(COLLECTION);
        DBObject edif = edificioCollection.findOne(new BasicDBObject().append("Username", "Angeloo"));
        return (String) edif.get("Edificio");

    }


}
