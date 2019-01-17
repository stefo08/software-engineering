package Model.DAO;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.List;

public interface SensorDAO {

    public DBCursor getSensoriEdificio(String id);
    public void updateRangeSensoreMin(String id, int min);
    public void updateRangeSensoreMax(String id, int min);
}
