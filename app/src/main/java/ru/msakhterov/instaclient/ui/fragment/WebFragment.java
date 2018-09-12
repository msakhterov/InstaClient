package ru.msakhterov.instaclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.msakhterov.instaclient.R;

public class WebFragment extends Fragment implements UpdatableFragment {

    private static final String TAG = "WebFragment";

    public WebFragment() {
        Log.i(TAG, "WebFragment");
    }

    public static WebFragment newInstance() {
        return new WebFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "WebFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        return view;
    }

    public void updateUI() {

    }
}
