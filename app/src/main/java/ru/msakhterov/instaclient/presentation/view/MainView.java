package ru.msakhterov.instaclient.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {

    void showChoiceThemeActivity();

    void addNewPhoto(File photoFile);


}
