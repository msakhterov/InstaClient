package ru.msakhterov.instaclient.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.PicturesGalleryFragment;

public class PictureFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FragmentPagerAdapter";
    private Fragment mCurrentFragment;
    private List<PicturesGalleryFragment> fragments = new ArrayList<>();

    public PictureFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    public List<PicturesGalleryFragment> getFragments() {
        return fragments;
    }

    @Override

    public Fragment getItem(int position) {
        PicturesGalleryFragment fragment;
        switch (position) {
            case 0:
                fragment = PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE);
                fragments.add(fragment);
                return fragment;
//                return PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE);
            case 1:
                fragment = PicturesGalleryFragment.newInstance(Constants.FAVORITES_FRAGMENT_TYPE);
                fragments.add(fragment);
                return fragment;
//                return PicturesGalleryFragment.newInstance(Constants.FAVORITES_FRAGMENT_TYPE);
            default:
                throw new IllegalArgumentException("Could not create fragment for position " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constants.ALL_FRAGMENT_TITLE;
            case 1:
                return Constants.FAVORITES_FRAGMENT_TITLE;
            default:
                throw new IllegalArgumentException("Could not show fragment title for position " + position);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
