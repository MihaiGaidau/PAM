package com.example.denis.calendarview;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.arch.core.util.Function;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
    int mHour, mMinute;
    EditText startTime, endTime, eTitle, eLocation, eNote;
    Button saveBtn;
    String myDate;
    DatabaseHelper myDb;
    Event myE;
    Intent intent;
    String myId;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        Log.d("misa","a ajuns si in eveniment1");
        myDb = new DatabaseHelper(this);

        eTitle = (EditText) findViewById(R.id.titleText);
        eLocation = (EditText) findViewById(R.id.titleText);
        startTime = (EditText) findViewById(R.id.start_time_text);
        endTime = (EditText) findViewById(R.id.end_time_text);
        eNote = (EditText) findViewById(R.id.note_text);
        saveBtn = (Button) findViewById(R.id.save_btn);

        Log.d("misa","a ajuns si in eveniment2");

        Intent intent= getIntent();
        Log.d("misa","a ajuns si in eveniment5");
        myDate = intent.getStringExtra("selectedDate");
        myId=intent.getStringExtra("selectedId");

        Log.d("misa","a ajuns si in eveniment3");
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePic(startTime);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePic(endTime);
            }
        });
        Log.d("misa","a ajuns si in eveniment4");
        manageView();
        saveBtnClicked();
    }

    private void manageView() {
        if (Integer.parseInt(myId) == -1){
            ;
        }
        else{
            Cursor myEvent = myDb.getById(myId);
            eTitle.setText(myEvent.getString(2));
            eLocation.setText(myEvent.getString(3));
            startTime.setText(myEvent.getString(4));
            endTime.setText(myEvent.getString(5));
            eNote.setText(myEvent.getString(6));
        }
    }

    private void saveBtnClicked() {
        addOneData();
    }

    public void timePic(final EditText mEditText){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEditText.setText(hourOfDay+":"+minute);
            }
        },mHour,mMinute,false);
        timePickerDialog.show();
    }
    public void addOneData(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted;
                if (Integer.parseInt(myId) == -1)
                    isInserted = insertNew();
                else
                    isInserted = editData();

                if (isInserted == true)
                    Toast.makeText(EventActivity.this,"Saved",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EventActivity.this,":(",Toast.LENGTH_LONG).show();

//                Intent intent3 = new Intent(EventActivity.this, MainActivity.class);
////                intent.putExtra("studioData", date);
//                startActivity(intent3);
                finish();
            }
        });
    }
    public boolean editData(){
        return myDb.updateData(myId,myDate,eTitle.getText().toString(),eLocation.getText().toString(),startTime.getText().toString(),endTime.getText().toString(),eNote.getText().toString());
    }
    public boolean insertNew(){
        return  myDb.insertData(myDate,eTitle.getText().toString(),eLocation.getText().toString(),startTime.getText().toString(),endTime.getText().toString(),eNote.getText().toString());
    }

}
