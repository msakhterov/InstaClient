package ru.msakhterov.instaclient.model;

import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

public class Picture {

    private String dateFormat = "yyyyMMdd_HHmmss";
    private File path;
    private int isFavorite;

    public Picture() {
    }

    public Picture(File path) {
        this.path = path;
    }

    public String getPhotoFilename() {
        return "IMG_" + DateFormat.format(dateFormat, new Date()) + ".jpg";
    }

    public File getPath() {
        return path;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }
}
