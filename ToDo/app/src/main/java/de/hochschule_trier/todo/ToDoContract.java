package de.hochschule_trier.todo;

import android.provider.BaseColumns;

public final class ToDoContract {
    private ToDoContract(){}
    public static class ToDo implements BaseColumns {
        public static String TABLE_NAME = "todo";
        public static final String ID_FIELD_NAME = "_id";
        public static final String ID_FIELD_TYPE = "INTEGER PRIMARY KEY";
        public static String DATE_FIELD_NAME = "date";
        public static String DATE_FIELD_TYPE = "INTEGER";
        public static String TITLE_FIELD_NAME = "title";
        public static String TITLE_FIELD_TYPE = "TEXT";
        public static String DESCR_FIELD_NAME = "description";
        public static String DESCR_FIELD_TYPE = "TEXT";
    }
}
