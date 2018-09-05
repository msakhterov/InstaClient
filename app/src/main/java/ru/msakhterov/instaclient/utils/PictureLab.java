package ru.msakhterov.instaclient.utils;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;

public class PictureLab {

    private static final String TAG = "PictureLab";

    private Activity mActivity;
    private final String favouritesFileName = "favourites.txt";
    private final String favouritesDirName = "favourites";

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
            if (file.isDirectory()) continue;
            pictures.add(new Picture(file));
        }
//        for (String favourite : getFavourites()){
//            Log.d(TAG, favourite);
//        }
        return pictures;
    }

    public void setFavourites(Picture picture, boolean add) {
        picture.setFavorite(true);
        File favouritesDir = mActivity.getApplicationContext().getFilesDir();
        favouritesDir = new File(favouritesDir.getAbsolutePath() + "/" + favouritesDirName);
        if (!favouritesDir.exists())
            favouritesDir.mkdirs();
        File favouritesFile = new File(favouritesDir, favouritesFileName);
        if (add) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(favouritesFile, true));
                bw.write(picture.getPath().getName() + "\n");
                bw.flush();
                bw.close();
                Log.d(TAG, "Произведена запись файл :" + picture.getPath().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }


    }

    private List<String> getFavourites() {
        List<String> favourites = new ArrayList<>();
        File favouritesDir = mActivity.getApplicationContext().getFilesDir();
        favouritesDir = new File(favouritesDir.getAbsolutePath() + "/" + favouritesDirName);
        File favouritesFile = new File(favouritesDir, favouritesFileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(favouritesFile));
            String str;
            while ((str = br.readLine()) != null) {
                Log.d(TAG, str);
                favourites.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return favourites;

    }

    public int getImagePreviewSize(int countSpan){
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x/countSpan;
    }
}
