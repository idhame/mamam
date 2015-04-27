package com.ppl.b03.mamam.models;

import java.io.Serializable;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    int idUser;
    double nilai;
    String judul;
    String tanggapan;
    int idKantin;
    public Review(String judul, String tanggapan, int idUser, double nilai, int idKantin) {
        this.judul=judul;
        this.tanggapan=tanggapan;
        this.idUser=idUser;
        this.nilai=nilai;
        this.idKantin=idKantin;
    }

    public int getIdUser() {
        return idUser;
    }

    public double getNilai() {
        return nilai;
    }

    public String getJudul() {
        return judul;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public int getIdKantin() { return idKantin;}
}
