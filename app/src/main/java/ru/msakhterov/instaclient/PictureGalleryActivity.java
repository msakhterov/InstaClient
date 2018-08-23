package ru.msakhterov.instaclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.msakhterov.instaclient.model.Picture;

public class PictureGalleryActivity extends AppCompatActivity implements PicturesGalleryFragment.PictureGalleryListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String PICTURE_GALLERY_FRAGMENT_TAG = "picture_gallery_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt("THEME", R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_gallery_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    private void selectChoiceThemeColor(){
        Intent intent = new Intent(this, ChoiceThemeColorActivity.class);
        startActivity(intent);
    }

}
