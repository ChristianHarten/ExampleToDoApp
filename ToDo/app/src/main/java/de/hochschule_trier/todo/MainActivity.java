package de.hochschule_trier.todo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //DatePicker and Listener
    private final Calendar mCal = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCal.set(Calendar.YEAR, year);
            mCal.set(Calendar.MONTH, month);
            mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        }
    };
    private void updateDateLabel() {
        String format = "dd.MM.yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.GERMAN);
        mDateInput.setText(sdf.format(mCal.getTime()));
    }
    private EditText mTitleInput, mDescrInput, mDateInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleInput = (EditText) findViewById(R.id.todotitle);
        mDescrInput = (EditText) findViewById(R.id.tododescription);
        mDateInput = (EditText) findViewById(R.id.tododate);
    }

    public void showAllEntries(View v) {

    }

    public void getTableCount(View v) {
        SQLiteDatabase db = new ToDoDatabaseHelper(this).getReadableDatabase();
    }

    public void saveEntry(View v) {
        String title = mTitleInput.getText().toString();
        String descr = mDescrInput.getText().toString();
        String date = mDateInput.getText().toString();

        if (title.equals("") || descr.equals("")) {
            Toast.makeText(this, "Titel und Beschreibung angeben.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.equals("")) {
            date = String.valueOf(System.currentTimeMillis());

        }
        else {
            String[] dates = date.split("\\.");
            StringBuffer sb = new StringBuffer();
            for (String d : dates) {
                sb.append(d);
            }
            date = sb.toString();
        }
        doInsert(title, descr, Long.valueOf(date));
    }

    private void doInsert(String title, String descr, Long date) {
        SQLiteDatabase db = new ToDoDatabaseHelper(this).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(ToDoContract.ToDo.DATE_FIELD_NAME, new Date(date).toString());
        vals.put(ToDoContract.ToDo.TITLE_FIELD_NAME, title);
        vals.put(ToDoContract.ToDo.DESCR_FIELD_NAME, descr);
        db.insert(ToDoContract.ToDo.TABLE_NAME, null, vals);
        db.close();
    }

    public void chooseDate(View v) {
        new DatePickerDialog(this, dateSetListener, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH)).show();
    }
}
