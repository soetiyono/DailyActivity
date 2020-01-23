package com.notur.dailyactivity.model;

public class Data {
    private int id;
    private String katagori;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKatagori() {
        return katagori;
    }

    public void setKatagori(String katagori) {
        this.katagori = katagori;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    private String tgl;
}
