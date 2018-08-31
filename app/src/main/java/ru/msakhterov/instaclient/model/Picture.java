package ru.msakhterov.instaclient.model;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

public class Picture {

    private String dateFormat = "yyyyMMdd_HHmmss";
    private File path;

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
}
