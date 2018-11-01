package com.example.denis.calendarview;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.arch.core.util.Function;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;

public class EventActivity extends AppCompatActivity {
    int mHour, mMinute;
    EditText startTime, endTime, eTitle, eLocation, eNote;
    Button saveBtn;
    String myDate;
    Event myE;
    Intent intent;
    String myId;
    Realm realm;
    boolean isInserted;
    String evTitle;
    String evLocation;
    Switch allDaySw;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);


        //Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        eTitle = (EditText) findViewById(R.id.titleText);
        eLocation = (EditText) findViewById(R.id.location_text);
        startTime = (EditText) findViewById(R.id.start_time_text);
        endTime = (EditText) findViewById(R.id.end_time_text);
        eNote = (EditText) findViewById(R.id.note_text);
        saveBtn = (Button) findViewById(R.id.save_btn);
        allDaySw = (Switch) findViewById(R.id.switch1);


        Intent intent= getIntent();
        myDate = intent.getStringExtra("selectedDate");
        myId=intent.getStringExtra("selectedId");

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
        manageView();
        addOneData();
    }
    public void manageView(){
        if (myId.equals("-1"))
            ;
        else{
            ViewEvent();

        }
    }

    public void ViewEvent(){
        evTitle = myId.split("[|]")[0];
        evLocation = myId.split("[|]")[1];
        Log.d("misa","Title "+evTitle+ " Location "+ evLocation);
        realm.beginTransaction();
        Event event = realm.where(Event.class).equalTo("eDate",myDate).equalTo("eTitle",evTitle).equalTo("eLocation",evLocation).findFirst();
        eTitle.setText(event.getTitle());
        eLocation.setText(event.getLocation());
        eNote.setText(event.getNote());
        startTime.setText(event.getStartTime());
        endTime.setText(event.getEndTime());
        if (event.isAllDay())
            allDaySw.setChecked(true);
        realm.commitTransaction();


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

                if (myId.equals("-1"))
                    isInserted = insertNew();
                else
                    isInserted = editData();

                finish();
            }
        });
    }
    public boolean editData(){
//something has to be here
        Log.d("misa","curind se va edita elementul");

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Event mevent = realm.where(Event.class).equalTo("eDate",myDate).equalTo("eTitle",evTitle).equalTo("eLocation",evLocation).findFirst();
                Log.d("misa","new event created");
                mevent.setDate(myDate);
                mevent.setEndTime(endTime.getText().toString().trim());
                mevent.setLocation(eLocation.getText().toString().trim());
                mevent.setTitle(eTitle.getText().toString().trim());
                mevent.setNote(eNote.getText().toString().trim());
                mevent.setStartTime(startTime.getText().toString().trim());
                if (!startTime.getText().toString().equals("") || !endTime.getText().toString().equals(""))
                    mevent.setAllDay(false);
                else
                    mevent.setAllDay(false);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(EventActivity.this,"Saved",Toast.LENGTH_LONG).show();
                isInserted = true;

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(EventActivity.this,":(",Toast.LENGTH_LONG).show();
                isInserted = false;
            }
        });

        return true;
    }
    public boolean insertNew(){
        Log.d("misa","curind se va adauga un nou element");
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Event event = bgRealm.createObject(Event.class);
                Log.d("misa","new event created");
                event.setDate(myDate);
                event.setEndTime(endTime.getText().toString().trim());
                event.setLocation(eLocation.getText().toString().trim());
                event.setTitle(eTitle.getText().toString().trim());
                event.setNote(eNote.getText().toString().trim());
                event.setStartTime(startTime.getText().toString().trim());
                if (startTime.getText().toString().equals("") || endTime.getText().toString().equals(""))
                {
                    event.setAllDay(false);
                    Log.d("misa","all day false");
                }
                else {
                    event.setAllDay(false);
                    Log.d("misa","all day true");
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(EventActivity.this,"Saved",Toast.LENGTH_LONG).show();
                isInserted = true;

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(EventActivity.this,":(",Toast.LENGTH_LONG).show();
               isInserted = false;
            }
        });

    return true;
    }

}
