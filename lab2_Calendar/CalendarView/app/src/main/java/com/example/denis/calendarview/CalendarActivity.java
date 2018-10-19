package com.example.denis.calendarview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class CalendarActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    Intent intent;
    Intent intent1;
    Intent sendItem;
    String myDate;
    Button addBtn;
    ListView listEvents;
    Integer myId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

       myDb = new DatabaseHelper(this);
        intent = getIntent();
        myDate = intent.getStringExtra("studioData");
        addBtn = (Button)findViewById(R.id.add_btn);

        Log.d("misa","a ajuns");
        initializeDisplayContent();
        addBtnClick();
        itemListEventsClick();
    }

    private void itemListEventsClick() {
        listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendItem = new Intent (CalendarActivity.this,EventActivity.class);
                sendItem.putExtra("selectedDate",myDate);
                sendItem.putExtra("selectedId",Long.toString(id+myId));
                Log.d("misa","el se apasa singur");
                startActivity(sendItem);
            }
        });
    }
//    private void initializeDisplayContent(){
//        final ListView listEvents = (ListView) findViewById(R.id.activity_list);
////        CursorAdapter adapterEvents = new CursorAdapter() {
////            @Override
////            public View newView(Context context, Cursor cursor, ViewGroup parent) {
////                return null;
////            }
////
////            @Override
////            public void bindView(View view, Context context, Cursor cursor) {
////
////            }
////        };
//    }

    private void addBtnClick(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(CalendarActivity.this, EventActivity.class);
                intent1.putExtra("selectedDate",myDate);
                intent1.putExtra("selectedId",Integer.toString(myId));
                startActivity(intent1);
                Log.d("misa","sa apasat butonul");
                finish();
            }
        });
    }

    private void initializeDisplayContent(){
        listEvents = (ListView) findViewById(R.id.activity_list);
        Log.d("misa",myDate);
        Cursor myEvents = myDb.getAllDay(myDate);
//        Cursor myEvents = myDb.getAllData();
        Log.d("misa","a ajuns si aici");
        if (myEvents != null && myEvents.getCount() >0) {
            Log.d("misa","s-a gasit elemente");
            Log.d("misa",myEvents.getString(0));
            Log.d("misa","6");
            myId = Integer.parseInt(myEvents.getString(0));
            Log.d("misa","1");
            List<String> events = new ArrayList<String>();
            Log.d("misa","2");

            while (myEvents.moveToNext()) {
                events.add(myEvents.getString(2));
            }
            Log.d("misa","3");
            ArrayAdapter<String> eventsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
            Log.d("misa","4");
            listEvents.setAdapter(eventsAdapter);
            Log.d("misa","5");
        }
        else{
            Log.d("misa","nu este numic");
        }
    }
}
