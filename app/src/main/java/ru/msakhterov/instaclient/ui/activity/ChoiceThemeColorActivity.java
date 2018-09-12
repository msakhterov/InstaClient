package ru.msakhterov.instaclient.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ru.msakhterov.instaclient.R;
import ru.msakhterov.instaclient.ui.fragment.ChoiceThemeColorFragment;
import ru.msakhterov.instaclient.utils.Constants;

public class ChoiceThemeColorActivity extends AppCompatActivity implements ChoiceThemeColorFragment.ThemeColorListener {

    private static final String CHOICE_THEME_COLOR_FRAGMENT_TAG = "choice_theme_color_fragment_tag";
    private static final String TAG = "ChoiceThemeActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getInt(Constants.THEME_CONSTANT, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_theme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ChoiceThemeColorFragment choiceThemeColorFragment = new ChoiceThemeColorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, choiceThemeColorFragment, CHOICE_THEME_COLOR_FRAGMENT_TAG);
        transaction.commit();
    }

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
        sp.edit().putInt(Constants.THEME_CONSTANT, theme).apply();
        this.recreate();
    }
}
