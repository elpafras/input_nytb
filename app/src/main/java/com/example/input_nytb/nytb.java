package com.example.input_nytb;

public class nytb {
    String key, title, bait, koor, nada, no;

    public nytb() {
    }

    public nytb(String key, String title, String bait, String koor, String nada, String no) {
        this.key = key;
        this.title = title;
        this.bait = bait;
        this.koor = koor;
        this.nada = nada;
        this.no = no;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getKoor() {
        return koor;
    }

    public void setKoor(String koor) {
        this.koor = koor;
    }

    public String getNada() {
        return nada;
    }

    public void setNada(String nada) {
        this.nada = nada;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
