package ru.msakhterov.instaclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DbFragment extends Fragment implements UpdatableFragment {

    private static final String TAG = "DbFragment";

    public DbFragment() {
        Log.i(TAG, "DbFragment");
    }

    public static DbFragment newInstance() {
        return new DbFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "DbFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_db, container, false);
        return view;
    }

    public void updateUI() {

    }
}
