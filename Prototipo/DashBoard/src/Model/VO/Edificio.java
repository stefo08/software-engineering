package Model.VO;

import java.util.List;

public class Edificio {

    private String Address;
    private int Civic;
    private List<Sensor> list;

    public Edificio(){

    }

    public Edificio(String address, int civic, List<Sensor> list) {
        this.Address = address;
        this.Civic = civic;
        this.list = list;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getCivic() {
        return Civic;
    }

    public void setCivic(int civic) {
        Civic = civic;
    }

    public List<Sensor> getList() {
        return list;
    }

    public void setList(List<Sensor> list) {
        this.list = list;
    }
}
