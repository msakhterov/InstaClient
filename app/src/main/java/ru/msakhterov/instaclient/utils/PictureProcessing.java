package ru.msakhterov.instaclient.utils;

import java.io.File;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;

public interface PictureProcessing {

    File getPhotoFile(Picture picture);

    void addPhotoToDb(File photoFile);

    List<Picture> getPicturesList();

    List<Picture> getFavouritesPicturesList();

    void setFavourites(Picture picture);

    int getImagePreviewSize(int countSpan);

}
