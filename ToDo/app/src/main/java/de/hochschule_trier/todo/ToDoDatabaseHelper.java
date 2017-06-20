package de.hochschule_trier.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo.db";
    private static final String CREATE_TABLE = "CREATE TABLE " + ToDoContract.ToDo.TABLE_NAME + "(" + ToDoContract.ToDo.DATE_FIELD_NAME + " " + ToDoContract.ToDo.DATE_FIELD_TYPE
            + ", " + ToDoContract.ToDo.TITLE_FIELD_NAME + " " + ToDoContract.ToDo.TITLE_FIELD_TYPE + ", "
            + ToDoContract.ToDo.DESCR_FIELD_NAME + " " + ToDoContract.ToDo.DESCR_FIELD_TYPE + ")";
    private static String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ToDoContract.ToDo.TABLE_NAME;
    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }
}
