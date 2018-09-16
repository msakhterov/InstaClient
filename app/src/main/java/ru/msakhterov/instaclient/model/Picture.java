package ru.msakhterov.instaclient.model;

import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

import ru.msakhterov.instaclient.utils.Constants;

public class Picture {

    private String dateFormat = "yyyyMMdd_HHmmss";
    private File path;
    private int isFavorite;

    public Picture() {
    }

    public Picture(File path) {
        this.path = path;
        isFavorite = Constants.IS_NOT_FAVORITE;
    }

    public String getPhotoFilename() {
        return "IMG_" + DateFormat.format(dateFormat, new Date()) + ".jpg";
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }
}
