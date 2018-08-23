package ru.msakhterov.instaclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ChoiceThemeColorActivity extends AppCompatActivity implements ChoiceThemeColorFragment.ThemeColorListener {

    private static final String CHOICE_THEME_COLOR_FRAGMENT_TAG = "choice_theme_color_fragment_tag";
    private static final String TAG = "ChoiceThemeColorActivityTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt("THEME", R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        ChoiceThemeColorFragment choiceThemeColorFragment = new ChoiceThemeColorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, choiceThemeColorFragment, CHOICE_THEME_COLOR_FRAGMENT_TAG);
        transaction.commit();
    }

    @SuppressLint("LongLogTag")
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

        sp.edit().putInt("THEME", theme).apply();
//        Intent intent = new Intent(this, this.getClass());
        this.recreate();
    }
}
