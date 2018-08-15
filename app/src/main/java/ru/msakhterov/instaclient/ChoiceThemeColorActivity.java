package ru.msakhterov.instaclient;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ChoiceThemeColorActivity extends AppCompatActivity implements ChoiceThemeColorFragment.ThemeColorListener {

    private static final String CHOICE_THEME_COLOR_FRAGMENT_TAG = "choice_theme_color_fragment_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        ChoiceThemeColorFragment choiceThemeColorFragment = new ChoiceThemeColorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, choiceThemeColorFragment, CHOICE_THEME_COLOR_FRAGMENT_TAG);
        transaction.commit();
    }


    @Override
    public void onColorSelected(Integer color) {
    }
}
