package ru.msakhterov.instaclient.utils;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;

public class PictureLab {
    private static PictureLab sPictureLab;
    private Activity mActivity;

    public static PictureLab get(Activity activity) {
        if (sPictureLab == null) {
            sPictureLab = new PictureLab(activity);
        }

        return sPictureLab;
    }

    private PictureLab(Activity activity) {
        mActivity = activity;
    }

    public File getPhotoFile(Picture picture) {
        File filesDir = mActivity.getApplicationContext().getFilesDir();
        return new File(filesDir, picture.getPhotoFilename());
    }

    public List<Picture> getPicturesList() {
        List<Picture> pictures = new ArrayList<>();
        for (File file : getFilesList()) {
            pictures.add(new Picture(PictureUtils.getScaledBitmap(
                    file.getPath(), mActivity)));
        }
        return pictures;
    }

    public ArrayList<File> getFilesList() {
        ArrayList<File> files = new ArrayList<>();
        File picturesDir = mActivity.getApplicationContext().getFilesDir();
        for (File file : picturesDir.listFiles()) {
            if (file.isDirectory())
                files.addAll(getFilesList());
            else
                files.add(file);
            Log.d("TestTag", file.getName());
        }

        return files;
    }
}
