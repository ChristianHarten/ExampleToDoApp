package de.hochschule_trier.todo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // DatePicker and Listener
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
    private ToDoDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleInput = (EditText) findViewById(R.id.todotitle);
        mDescrInput = (EditText) findViewById(R.id.tododescription);
        mDateInput = (EditText) findViewById(R.id.tododate);

        db = new ToDoDatabaseHelper(this);
    }

    public void showAllEntries(View v) {

    }

    public void countEntries(View v) {
        int entryCount = db.getToDoCount();
        if (entryCount == -1) {
            Toast.makeText(this, "Fehler beim Zählen der Einträge.", Toast.LENGTH_SHORT).show();
        }
        else {
            String count = String.valueOf(entryCount);
            Toast.makeText(this, count , Toast.LENGTH_SHORT).show();
        }
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
        if (db.addToDoEntry(title, descr, Long.valueOf(date))) {
            Toast.makeText(this, "Eintrag erfolgreich hinzugefügt.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Fehler beim Hinzufügen des Eintrags.", Toast.LENGTH_SHORT).show();
        }
        resetInputs();
    }

    public void deleteEntries(View v) {
        if (db.deleteAll()) {
            Toast.makeText(this, "Alle Einträge gelöscht.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Fehler beim Löschen der Einträge.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetInputs() {
        mTitleInput.setText("");
        mDescrInput.setText("");
        mDateInput.setText("");
    }

    public void chooseDate(View v) {
        new DatePickerDialog(this, dateSetListener, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH)).show();
    }
}
