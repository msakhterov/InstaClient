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

        if (mFragmentManager.findFragmentByTag(TAG_ALL) == null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.containerFrame, PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE), TAG_ALL);
            fragmentTransaction.commit();
        }

        BottomNavigationView bnv = view.findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction;
                        switch (item.getItemId()) {
                            case R.id.action_all:
                                Fragment allFragment = mFragmentManager.findFragmentByTag(TAG_ALL);
                                if (allFragment == null) {
                                    allFragment = PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE);
                                }
                                fragmentTransaction = mFragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerFrame, allFragment, TAG_ALL);
                                fragmentTransaction.commit();
                                return true;
                            case R.id.action_db:
                                fragmentTransaction = mFragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerFrame, DbFragment.newInstance(), TAG_DB);
                                fragmentTransaction.commit();
                                return true;
                            case R.id.action_web:
                                fragmentTransaction = mFragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerFrame, WebFragment.newInstance(), TAG_WEB);
                                fragmentTransaction.commit();
                                return true;
                        }
                        return false;
                    }
                });
        return view;
    }

    public void updateUI() {
        UpdatableFragment fragment = (UpdatableFragment) mFragmentManager.findFragmentById(R.id.containerFrame);
        fragment.updateUI();
    }
}
