package ru.msakhterov.instaclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.util.Log;
import android.view.MenuItem;

public class ChoiceThemeColorActivity extends AppCompatActivity implements ChoiceThemeColorFragment.ThemeColorListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String CHOICE_THEME_COLOR_FRAGMENT_TAG = "choice_theme_color_fragment_tag";
    private static final String TAG = "ChoiceThemeColorActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt("THEME", R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_theme_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ChoiceThemeColorFragment choiceThemeColorFragment = new ChoiceThemeColorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, choiceThemeColorFragment, CHOICE_THEME_COLOR_FRAGMENT_TAG);
        transaction.commit();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onColorSelected(Integer color) {
        Log.d(TAG, "onColorSelected" + color);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme;
        switch (color) {
            case Color.RED:
                theme = R.style.RedTheme;
                break;
            case Color.GREEN:
                theme = R.style.GreenTheme;
                break;
            case Color.BLUE:
                theme = R.style.BlueTheme;
                break;
            default:
                theme = R.style.AppTheme;
                break;
        }
        sp.edit().putInt("THEME", theme).apply();
        this.recreate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case R.id.picture_gallery_drawer:
                selectPictureGallery();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.change_theme_drawer:
                recreate();
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectPictureGallery(){
        Intent intent = new Intent(this, PictureGalleryActivity.class);
        startActivity(intent);
    }
}
