package com.hms.entity;

import java.util.Random;

public class Doctor {
    private int id;
    private String name;
    private String pass;
    private String speciality;
    private int exp;
    private String tel;

    public Doctor(int id, String name, String pass, String speciality, int exp, String tel) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.speciality = speciality;
        this.exp = exp;
        this.tel = tel;
    }

    public Doctor(String name, String pass, String speciality, int exp, String tel) {
        int min = 1000, max = 10000;
        this.id = new Random().nextInt(max - min) + min;
        this.name = name;
        this.pass = pass;
        this.speciality = speciality;
        this.exp = exp;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
