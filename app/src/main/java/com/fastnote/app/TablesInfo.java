package com.fastnote.app;

import android.provider.BaseColumns;

public class TablesInfo {

    public static final class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_ID = "note_id";
        public static final String COLUMN_NOTE = "note_text";
        public static final String COLUMN_TITLE = "note_title";
        public static final String COLUMN_CREATE_DATE = "create_data";
    }

}