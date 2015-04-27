package com.ppl.b03.mamam.models;

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

    public Faculty(int id, String nama ) {
        this.nama=nama;
        this.id=id;
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

}
