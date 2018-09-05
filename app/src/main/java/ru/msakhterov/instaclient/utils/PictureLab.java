package ru.msakhterov.instaclient.utils;

import android.app.Activity;
import android.graphics.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.database.DataBaseManager;
import ru.msakhterov.instaclient.model.Picture;

public class PictureLab {

    private static final String TAG = "PictureLab";

    private Activity mActivity;

    public PictureLab(Activity activity) {
        mActivity = activity;
    }

    public File getPhotoFile(Picture picture) {
        File filesDir = mActivity.getApplicationContext().getFilesDir();
        return new File(filesDir, picture.getPhotoFilename());
    }

    public void addPhotoToDb(File photoFile) {
        DataBaseManager.getDataBaseManager(mActivity).addPicture(new Picture(photoFile));
    }

    public List<Picture> getPicturesList() {
        return DataBaseManager.getDataBaseManager(mActivity).getPicturesList();
    }

    public List<Picture> getFavouritesPicturesList() {
        List<Picture> favouritesPictures = new ArrayList<>();
        for (Picture picture : DataBaseManager.getDataBaseManager(mActivity).getPicturesList()) {
            if (picture.isFavorite() == Constants.IS_FAVORITE) favouritesPictures.add(picture);
        }
        return favouritesPictures;
    }

    public void setFavourites(Picture picture) {
        DataBaseManager.getDataBaseManager(mActivity).updatePicture(picture);
    }

    public int getImagePreviewSize(int countSpan){
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x/countSpan;
    }
}
