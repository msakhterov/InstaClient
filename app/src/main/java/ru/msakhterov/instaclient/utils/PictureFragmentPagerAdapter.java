package ru.msakhterov.instaclient.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import ru.msakhterov.instaclient.PicturesGalleryFragment;

public class PictureFragmentPagerAdapter extends FragmentPagerAdapter {


    public PictureFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PicturesGalleryFragment.newInstance(Constants.ALL_FRAGMENT_TYPE);
            case 1:
                return PicturesGalleryFragment.newInstance(Constants.FAVORITES_FRAGMENT_TYPE);
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

}
