package com.example.ecocalendariofermo;

public class Memo {
    String Title;
    String Subtitle;
    int Image;

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public Memo(String title, String subtitle, int image) {
        Title = title;
        Subtitle = subtitle;
        Image = image;
    }
}
