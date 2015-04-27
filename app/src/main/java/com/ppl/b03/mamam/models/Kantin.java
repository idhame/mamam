package com.ppl.b03.mamam.models;

import java.io.Serializable;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class Kantin implements Serializable {
    private static final long serialVersionUID = 1L;
    String nama;
    String penjual;
    String menu;
    String deskripsi;
    int pricerange;
    int kategori;
    int idKantin;
    String foto;
    public Kantin(String nama, String penjual, String menu, String deskripsi, int pricerange, int kategori, int idKantin, String foto) {
        this.nama=nama;
        this.menu=menu;
        this.pricerange=pricerange;
        this.deskripsi=deskripsi;
        this.penjual=penjual;
        this.menu=menu;
        this.deskripsi=deskripsi;
        this.kategori=kategori;
        this.idKantin=idKantin;
        this.foto=foto;

    }

    public String getNama() {
        return nama;
    }
    public String getDeskripsi() {return deskripsi;}
    public String getMenu() {return menu;}
    public int getId() {return idKantin;}
}
