package Model.DAO;

import com.mongodb.DBCollection;

public class MongoDBEdificioDAOimpl implements EdificioDAO {

    private String COLLECTION = "edificio";
    private MongoDBDAOFactory factory;
    private DBCollection edificioCollection = null;

    @Override
    public String getEdificioGestore(String username) {

        edificioCollection = factory.createConnection().getCollection(COLLECTION);
        return null;

    }

}
