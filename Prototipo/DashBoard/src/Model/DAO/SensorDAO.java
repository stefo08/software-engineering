package Model.DAO;

import com.mongodb.DBObject;

import java.util.List;

public interface SensorDAO {

    public DBObject getSensoriEdificio(String id);
}
