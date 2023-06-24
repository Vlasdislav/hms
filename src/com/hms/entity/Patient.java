package com.hms.entity;

import java.util.Random;

public class Patient {
    private int id;
    private String name;
    private String address;
    private String tel;
    private int age;
    private String gender;
    private String diagnosis;

    public Patient(int id, String name, String address, String tel, int age, String gender, String diagnosis) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
    }

    public Patient(String name, String address, String tel, int age, String gender, String diagnosis) {
        int min = 1000, max = 10000;
        this.id = new Random().nextInt(max - min) + min;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
