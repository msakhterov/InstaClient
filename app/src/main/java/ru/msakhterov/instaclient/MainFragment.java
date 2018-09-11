package ru.msakhterov.instaclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ru.msakhterov.instaclient.utils.Constants;


public class MainFragment extends Fragment implements UpdatableFragment {

    public static final String TAG_ALL = "all";
    public static final String TAG_DB = "db";
    public static final String TAG_WEB = "web";

    private static final String TAG = "MainFragment";
    FrameLayout containerFrame;
    FragmentManager mFragmentManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        containerFrame = view.findViewById(R.id.containerFrame);
        mFragmentManager = getChildFragmentManager();
        addAllFragment();

        BottomNavigationView bnv = view.findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction;
                        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.containerFrame);
                        switch (item.getItemId()) {
                            case R.id.action_all:
                                if (currentFragment instanceof PicturesGalleryFragment) {
                                    fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerFrame, PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE), TAG_ALL);
                                    fragmentTransaction.commit();
                                } else {
                                    addAllFragment();
                                }
                                return true;
                            case R.id.action_db:
                                if (currentFragment instanceof DbFragment) {
                                    fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerFrame, DbFragment.newInstance(), TAG_DB);
                                    fragmentTransaction.commit();
                                } else {
                                    Fragment dbFragment = mFragmentManager.findFragmentByTag(TAG_DB);
                                    if (dbFragment == null) {
                                        dbFragment = DbFragment.newInstance();
                                    }
                                    fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerFrame, dbFragment, TAG_DB);
                                    fragmentTransaction.commit();
                                }
                                return true;
                            case R.id.action_web:
                                if (currentFragment instanceof WebFragment) {
                                    fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerFrame, WebFragment.newInstance(), TAG_WEB);
                                    fragmentTransaction.commit();
                                } else {
                                    Fragment webFragment = mFragmentManager.findFragmentByTag(TAG_WEB);
                                    if (webFragment == null) {
                                        webFragment = WebFragment.newInstance();
                                    }
                                    fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerFrame, webFragment, TAG_WEB);
                                    fragmentTransaction.commit();
                                }
                                return true;
                        }
                        return false;
                    }
                });
        return view;
    }

    private void addAllFragment() {
        Fragment allFragment = mFragmentManager.findFragmentByTag(TAG_ALL);
        if (allFragment == null) {
            allFragment = PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE);
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFrame, allFragment, TAG_ALL);
        fragmentTransaction.commit();
    }

    public void updateUI() {
        UpdatableFragment fragment = (UpdatableFragment) mFragmentManager.findFragmentById(R.id.containerFrame);
        fragment.updateUI();
    }
}
