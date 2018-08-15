package ru.msakhterov.instaclient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.msakhterov.instaclient.model.Picture;

public class PictureGalleryActivity extends AppCompatActivity implements PicturesGalleryFragment.PictureGalleryListener {

    private static final String PICTURE_GALLERY_FRAGMENT_TAG = "picture_gallery_fragment_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt("THEME", R.style.AppTheme));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_gallery);

        PicturesGalleryFragment picturesGalleryFragment = new PicturesGalleryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, picturesGalleryFragment, PICTURE_GALLERY_FRAGMENT_TAG);
        transaction.commit();
    }


    @Override
    public void onPictureSelected(Picture picture) {

    }
}
