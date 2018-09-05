package ru.msakhterov.instaclient.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.io.File;

import ru.msakhterov.instaclient.model.Picture;

public class PictureCursorWrapper extends CursorWrapper {

    public PictureCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Picture getPicture() {
        String path = getString(getColumnIndex(DataBaseSchema.PicturesTable.Colons.PATH));
        int isFavorite = getInt(getColumnIndex(DataBaseSchema.PicturesTable.Colons.IS_FAVOURITE));

        Picture picture = new Picture(new File(path));
        picture.setFavorite(isFavorite);

        return picture;
    }
}
