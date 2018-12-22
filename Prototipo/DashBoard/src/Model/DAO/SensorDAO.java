package Model.DAO;

import Model.VO.Sensor;
import com.mongodb.DBObject;

import java.util.List;

public interface SensorDAO {

    public List<Sensor> getSensoriEdificio(DBObject obj);
}
