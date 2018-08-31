package ru.msakhterov.instaclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;
import ru.msakhterov.instaclient.utils.Constants;
import ru.msakhterov.instaclient.utils.PictureLab;

public class PictureGalleryActivity extends AppCompatActivity implements PicturesGalleryFragment.PictureGalleryListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String PICTURE_GALLERY_FRAGMENT_TAG = "picture_gallery_fragment_tag";
    private static final String TAG = "PictureGalleryActTag";
    private static final int REQUEST_PHOTO = 0;
    private File photoFile;
    private PictureLab mPictureLab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt(Constants.THEME_CONSTANT, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_gallery_drawer);
        mPictureLab = new PictureLab(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPhoto();
            }
        });

        PicturesGalleryFragment picturesGalleryFragment = new PicturesGalleryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, picturesGalleryFragment, PICTURE_GALLERY_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void onPictureSelected(Picture picture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_picture_gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_theme:
                selectChoiceThemeColor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case R.id.picture_gallery_drawer:
                recreate();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.change_theme_drawer:
                selectChoiceThemeColor();
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectChoiceThemeColor() {
        Intent intent = new Intent(this, ChoiceThemeColorActivity.class);
        startActivity(intent);
    }

    private void addNewPhoto() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Picture picture = new Picture();
            photoFile = mPictureLab.getPhotoFile(picture);
            Log.d(TAG, photoFile.getPath());
            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.msakhterov.instaclient.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getPackageManager().queryIntentActivities(cameraIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(cameraIntent, REQUEST_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PHOTO) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.msakhterov.instaclient.fileprovider",
                        photoFile);
                revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Snackbar.make(findViewById(R.id.coordinator), R.string.photo_added, Snackbar.LENGTH_LONG).show();
                updatePicturesList();
            }
        }
    }

    public void updatePicturesList() {
        PicturesGalleryFragment picturesGalleryFragment = (PicturesGalleryFragment) getSupportFragmentManager().findFragmentByTag(PICTURE_GALLERY_FRAGMENT_TAG);
        picturesGalleryFragment.updateUI();
    }
}
