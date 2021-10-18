package com.example.ecocalendariofermo;

public class Rifiuto {

    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;

    public Rifiuto(String name, String type, String info) {
        this.name = name;
        this.type = type;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rifiuto(){

    }
}
