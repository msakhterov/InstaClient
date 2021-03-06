package ru.msakhterov.instaclient.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;

import ru.msakhterov.instaclient.R;
import ru.msakhterov.instaclient.model.Picture;
import ru.msakhterov.instaclient.presentation.presenter.MainPresenter;
import ru.msakhterov.instaclient.presentation.view.MainView;
import ru.msakhterov.instaclient.ui.fragment.PicturesGalleryFragment;
import ru.msakhterov.instaclient.ui.fragment.UpdatableFragment;
import ru.msakhterov.instaclient.utils.Constants;
import ru.msakhterov.instaclient.utils.PictureFragmentPagerAdapter;
import ru.msakhterov.instaclient.utils.PictureLab;

public class MainActivity extends MvpAppCompatActivity implements MainView, PicturesGalleryFragment.PictureGalleryListener,
        NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter presenter;

    private static final String TAG = "PictureGalleryAct";
    private static final int REQUEST_PHOTO = 0;
    private FloatingActionButton fab;
    private ViewPager mViewPager;
    private PictureFragmentPagerAdapter mPictureFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt(Constants.THEME_CONSTANT, R.style.AppTheme));

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

        mPictureFragmentPagerAdapter = new PictureFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.fragment_container);
        mViewPager.setAdapter(mPictureFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addNewPhoto();
            }
        });
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(new PictureLab(this));
        Log.d(TAG, "Presenter created");
        return presenter;
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
                presenter.showChoiceThemeActivity();
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
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.change_theme_drawer:
                presenter.showChoiceThemeActivity();
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showChoiceThemeActivity() {
        Intent intent = new Intent(this, ChoiceThemeColorActivity.class);
        startActivity(intent);
    }

    @Override
    public void addNewPhoto(File photoFile) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(this,
                        getString(R.string.file_provider_authorities),
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

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
                        getString(R.string.file_provider_authorities),
                        presenter.getPhotoFile());
                revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                presenter.saveNewPhoto();
                Snackbar.make(findViewById(R.id.coordinator), R.string.photo_added, Snackbar.LENGTH_LONG).show();
                updatePicturesList();
            }
        }
    }

    public void updatePicturesList() {
        UpdatableFragment fragment = (UpdatableFragment) mPictureFragmentPagerAdapter.getCurrentFragment();
        fragment.updateUI();
    }

    public void updateFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
            ((UpdatableFragment) fragment).updateUI();
    }

}
