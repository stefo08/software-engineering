package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.SensorDAO;

public class SensoreController {

    private static final int MONGODB = 0;
    private DAOFactory mongoDBFactory;
    private SensorDAO sensoreDAO;


    public SensoreController(){
        mongoDBFactory = DAOFactory.getDAOFactory(MONGODB);
        sensoreDAO = mongoDBFactory.getSensorDAO();
    }

}
