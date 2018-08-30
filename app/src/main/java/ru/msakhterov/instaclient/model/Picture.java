package ru.msakhterov.instaclient.model;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

import java.util.Date;

public class Picture {

    private String dateFormat = "yyyyMMdd_HHmmss";
    private Bitmap picture;

    public Picture() {
    }

    public Picture(Bitmap picture) {
        this.picture = picture;
    }

    public String getPhotoFilename() {
        return "IMG_" + DateFormat.format(dateFormat, new Date()) + ".jpg";
    }

    public Bitmap getPicture() {
        return picture;
    }

}
