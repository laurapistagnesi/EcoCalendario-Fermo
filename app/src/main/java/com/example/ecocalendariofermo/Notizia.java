package com.example.ecocalendariofermo;

public class Notizia {
    private String title;
    private String img;
    private String pdf;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Notizia(String title, String img, String pdf) {
        this.title = title;
        this.img = img;
        this.pdf = pdf;
    }

    public Notizia(){

    }
}
