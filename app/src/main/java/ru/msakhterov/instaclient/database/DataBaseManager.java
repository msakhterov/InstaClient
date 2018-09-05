package ru.msakhterov.instaclient.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;

public class DataBaseManager {

    private static final String TAG = "TasksManager";
    private static DataBaseManager dataBaseManager;
    private Context context;
    private SQLiteDatabase database;

    public DataBaseManager(Context context) {
        this.context = context;
        database = new DataBaseHelper(context).getWritableDatabase();
    }

    public static DataBaseManager getDataBaseManager(Context context) {
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager(context);
        } else {
            dataBaseManager.context = context;
        }
        return dataBaseManager;
    }

    private static ContentValues getPictureContentValues(Picture picture) {
        ContentValues values = new ContentValues();
        values.put(DataBaseSchema.PicturesTable.Colons.PATH, picture.getPath().toString());
        values.put(DataBaseSchema.PicturesTable.Colons.IS_FAVOURITE, picture.isFavorite());
        return values;
    }

    public void addPicture(Picture picture) {
        new AddPictureAsyncTask().execute(picture);
    }

    public List<Picture> getPicturesList() {
        return new GetPicturesList().loadInBackground();
    }

    public void updatePurpose(Picture picture) {
        new UpdatePictureAsyncTask().execute(picture);

    }

    public void deletePicture(Picture picture) {
        new DeletePictureAsyncTask().execute(picture);

    }

    private PictureCursorWrapper queryPictures(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                DataBaseSchema.PicturesTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new PictureCursorWrapper(cursor);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddPictureAsyncTask extends AsyncTask<Picture, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Picture... pictures) {
            Picture picture = pictures[0];
            ContentValues pictureValues = getPictureContentValues(picture);
            database.insert(DataBaseSchema.PicturesTable.NAME, null, pictureValues);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetPicturesList extends AsyncTaskLoader {
        List<Picture> pictures;

        public GetPicturesList() {
            super(context);
            pictures = new ArrayList<>();
        }

        @Override
        public List<Picture> loadInBackground() {
            PictureCursorWrapper cursor = queryPictures(null, null);
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    pictures.add(cursor.getPicture());
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return pictures;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdatePictureAsyncTask extends AsyncTask<Picture, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Picture... pictures) {
            Picture picture = pictures[0];
            String path = picture.getPath().toString();
            ContentValues pictureContentValues = getPictureContentValues(picture);
            database.update(DataBaseSchema.PicturesTable.NAME, pictureContentValues, DataBaseSchema.PicturesTable.Colons.PATH + " = ?", new String[]{path});
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeletePictureAsyncTask extends AsyncTask<Picture, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Picture... pictures) {
            Picture picture = pictures[0];
            String path = picture.getPath().toString();
            ;
            database.delete(DataBaseSchema.PicturesTable.NAME, DataBaseSchema.PicturesTable.Colons.PATH + " = ?", new String[]{path});
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
