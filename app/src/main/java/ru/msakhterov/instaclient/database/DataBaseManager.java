package ru.msakhterov.instaclient.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.PictureGalleryActivity;
import ru.msakhterov.instaclient.model.Picture;

public class DataBaseManager {

    private static final String TAG = "DataBaseManager";
    private static DataBaseManager dataBaseManager;
    private SQLiteDatabase database;

    private DataBaseManager(Context context) {
        database = new DataBaseHelper(context).getWritableDatabase();
    }

    public static DataBaseManager getDataBaseManager(Context context) {
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager(context);
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

    public List<Picture> getPicturesList(Context context) {
        return new GetPicturesList(context).loadInBackground();
    }

    public void updatePicture(Picture picture, Context context) {
        new UpdatePictureAsyncTask(context).execute(picture);
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
    private class GetPicturesList extends AsyncTaskLoader {
        List<Picture> pictures;

        public GetPicturesList(Context context) {
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
    private class DeletePictureAsyncTask extends AsyncTask<Picture, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Picture... pictures) {
            Picture picture = pictures[0];
            String path = picture.getPath().toString();
            database.delete(DataBaseSchema.PicturesTable.NAME, DataBaseSchema.PicturesTable.Colons.PATH + " = ?", new String[]{path});
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdatePictureAsyncTask extends AsyncTask<Picture, Void, Boolean> {

        private Context context;

        public UpdatePictureAsyncTask(Context context) {
            this.context = context;
        }

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
            Log.d(TAG, "UpdatePictureAsyncTask");
            ((PictureGalleryActivity) context).updateFragments();
        }
    }
}
