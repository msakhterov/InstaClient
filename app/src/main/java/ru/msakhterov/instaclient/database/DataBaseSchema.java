package ru.msakhterov.instaclient.database;

public class DataBaseSchema {
    public static final class PicturesTable {
        public static final String NAME = "pictures";

        public static final class Colons {
            public static final String ID = "_id";
            public static final String PATH = "path";
            public static final String IS_FAVOURITE = "is_favourite";
        }
    }
}

