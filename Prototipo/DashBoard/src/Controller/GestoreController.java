package Controller;

import Model.DAO.DAOFactory;
import Model.DAO.GestoreDAO;

public class GestoreController {

    private static final int MONGODB = 0;
    private DAOFactory mongoDBFactory;
    private GestoreDAO gestoreDAO;

    public GestoreController(){
        mongoDBFactory = DAOFactory.getDAOFactory(MONGODB);
        gestoreDAO = mongoDBFactory.getGestoreDAO();
    }
}