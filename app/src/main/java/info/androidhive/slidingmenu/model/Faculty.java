package info.androidhive.slidingmenu.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class Faculty implements Serializable{
    private static final long serialVersionUID = 1L;
    int id;
    String nama;
    ArrayList<Kantin> listKantin;
    double latitude;
    double longitude;
    public Faculty(int id, String nama, double longitude, double latitude) {
        this.nama=nama;
        this.id=id;
        this.longitude=longitude;
        this.latitude=latitude;
        listKantin=new ArrayList<Kantin>();
    }
    public String getNama() {
        return nama;
    }
    public ArrayList<Kantin> getListKantin() {
        return listKantin;
    }
    public void addKantin(Kantin kantin) {
        listKantin.add(kantin);
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setListKantin(ArrayList<Kantin> ar) {
        this.listKantin = ar;
    }

}
