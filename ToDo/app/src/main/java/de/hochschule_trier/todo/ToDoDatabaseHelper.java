package de.hochschule_trier.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public int getToDoCount() {
        String query = "SELECT * FROM " + ToDoContract.ToDo.TABLE_NAME;
        int result;
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery(query, null)) {
                result = cursor.getCount();
            } catch (Exception e) {
                result = - 1;
            }
        }
        catch (Exception e) {
            result = -1;
        }

        return result;
    }
    public boolean deleteAll() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(ToDoContract.ToDo.TABLE_NAME, null, null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean addToDoEntry(String title, String descr, long date) {

        ContentValues vals = new ContentValues();
        vals.put(ToDoContract.ToDo.TITLE_FIELD_NAME, title);
        vals.put(ToDoContract.ToDo.DESCR_FIELD_NAME, descr);
        vals.put(ToDoContract.ToDo.DATE_FIELD_NAME, date);

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.insert(ToDoContract.ToDo.TABLE_NAME, null, vals);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
