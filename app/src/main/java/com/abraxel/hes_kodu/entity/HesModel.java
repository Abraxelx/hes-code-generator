package com.abraxel.hes_kodu.entity;

public class HesModel {
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String name;
    private String hesCode;
    private String qrCode;

    public HesModel(String name, String hesCode, String qrCode) {
        this.name = name;
        this.hesCode = hesCode;
        this.qrCode = qrCode;
    }

    public HesModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHesCode() {
        return hesCode;
    }

    public void setHesCode(String hesCode) {
        this.hesCode = hesCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
