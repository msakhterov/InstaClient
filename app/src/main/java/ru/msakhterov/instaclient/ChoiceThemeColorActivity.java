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

import ru.msakhterov.instaclient.utils.Constants;

public class ChoiceThemeColorActivity extends AppCompatActivity implements ChoiceThemeColorFragment.ThemeColorListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String CHOICE_THEME_COLOR_FRAGMENT_TAG = "choice_theme_color_fragment_tag";
    private static final String TAG = "ChoiceThemeActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt(Constants.THEME_CONSTANT, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_theme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ChoiceThemeColorFragment choiceThemeColorFragment = new ChoiceThemeColorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, choiceThemeColorFragment, CHOICE_THEME_COLOR_FRAGMENT_TAG);
        transaction.commit();
    }

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
        sp.edit().putInt(Constants.THEME_CONSTANT, theme).apply();
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
