package com.hhp.funstory.model;

import android.graphics.Bitmap;

public class Topic {
    private String name;
    private Bitmap image;

    public Topic(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }
}
