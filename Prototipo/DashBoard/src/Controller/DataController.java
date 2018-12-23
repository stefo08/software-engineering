package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.DataDAO;
import Model.VO.Sensor;

public class DataController {

    private static final int MONGODB = 0;
    private DAOFactory mongoDBFactory;
    private DataDAO dataDAO;

    public DataController(){
        mongoDBFactory = DAOFactory.getDAOFactory(MONGODB);
        dataDAO = mongoDBFactory.getDataDAO();
    }

    public void getLastData(Sensor s){

    }

}
