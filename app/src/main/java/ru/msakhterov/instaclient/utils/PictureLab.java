package ru.msakhterov.instaclient.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;

public class PictureLab {

    private Activity mActivity;

    public PictureLab(Activity activity) {
        mActivity = activity;
    }

    public File getPhotoFile(Picture picture) {
        File filesDir = mActivity.getApplicationContext().getFilesDir();
        return new File(filesDir, picture.getPhotoFilename());
    }

    public List<Picture> getPicturesList() {
        List<Picture> pictures = new ArrayList<>();
        File picturesDir = mActivity.getApplicationContext().getFilesDir();
        for (File file : picturesDir.listFiles()) {
            pictures.add(new Picture(file));
        }
        return pictures;
    }

    public int getImagePreviewSize(int countSpan){
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x/countSpan;
    }
}
