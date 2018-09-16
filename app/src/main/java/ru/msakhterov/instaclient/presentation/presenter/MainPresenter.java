package ru.msakhterov.instaclient.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import ru.msakhterov.instaclient.model.Picture;
import ru.msakhterov.instaclient.presentation.view.MainView;
import ru.msakhterov.instaclient.utils.PictureProcessing;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private PictureProcessing pictureLab;
    private Picture currentPicture;

    public MainPresenter(PictureProcessing pictureProcessing) {
        pictureLab = pictureProcessing;
    }

    public void addNewPhoto() {
        currentPicture = new Picture();
        currentPicture.setPath(pictureLab.getPhotoFile(currentPicture));
        getViewState().addNewPhoto(currentPicture.getPath());
    }

    public void saveNewPhoto() {
        pictureLab.addPhotoToDb(currentPicture.getPath());
    }

    public File getPhotoFile() {
        return currentPicture.getPath();
    }

    public void showChoiceThemeActivity() {
        getViewState().showChoiceThemeActivity();
    }


}
