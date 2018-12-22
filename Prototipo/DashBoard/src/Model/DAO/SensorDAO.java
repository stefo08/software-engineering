package Model.DAO;

import com.mongodb.DBObject;

public interface SensorDAO {

    public DBObject getSensoriEdificio(String edifcioID);
}
