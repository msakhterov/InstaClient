package ru.msakhterov.instaclient.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import ru.msakhterov.instaclient.ui.fragment.MainFragment;
import ru.msakhterov.instaclient.ui.fragment.PicturesGalleryFragment;

public class PictureFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FragmentPagerAdapter";
    private Fragment mCurrentFragment;

    public PictureFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance();
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
