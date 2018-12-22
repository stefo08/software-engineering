package Model.DAO;

public interface GestoreDAO {

    public boolean CorrectLoginData(String username, String password);
    public String getGestoreEdificio(String username);
}
