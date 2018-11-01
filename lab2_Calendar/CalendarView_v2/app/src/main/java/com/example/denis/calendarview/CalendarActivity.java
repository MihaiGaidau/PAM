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

import io.realm.Realm;
import io.realm.RealmResults;

public class CalendarActivity extends AppCompatActivity {
    Intent intent;
    Intent intent1;
    Intent sendItem;
    String myDate;
    Button addBtn;
    ListView listEvents;
    String myId = "-1";
    Realm realm;
    RealmResults<Event> events;
    //Integer number;
    List<String> eventsShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        intent = getIntent();
        myDate = intent.getStringExtra("studioData");
        addBtn = (Button)findViewById(R.id.add_btn);
        listEvents = (ListView) findViewById(R.id.activity_list);

        Log.d("misa","a ajuns");
        readDate();
        initializeDisplayContent();
        itemListEventsClick();
        addBtnClick();
    }

    private void itemListEventsClick() {
        listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendItem = new Intent (CalendarActivity.this,EventActivity.class);
                sendItem.putExtra("selectedDate",myDate);
                sendItem.putExtra("selectedId",eventsShow.get((int)id));
                Log.d("misa","el se apasa singur");
                Log.d("misa",eventsShow.get((int)id));
                startActivity(sendItem);
            }
        });
    }


    private void addBtnClick(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(CalendarActivity.this, EventActivity.class);
                intent1.putExtra("selectedDate",myDate);
                intent1.putExtra("selectedId",myId);
                startActivity(intent1);
                Log.d("misa","sa apasat butonul");
                finish();
            }
        });
    }


    private void readDate(){
        Log.d("misa","nu se primes");
         events = realm.where(Event.class).equalTo("eDate",myDate).findAll();
//        events = realm.where(Event.class).findAll();
         Log.d("misa","marime: "+ Integer.toString(events.size()));
    }
    private void initializeDisplayContent() {
        if (events.size() >0) {
        eventsShow = new ArrayList<String>();
        for (Event event : events) {
            eventsShow.add(event.toString());

        }
        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsShow);
        listEvents.setAdapter(eventsAdapter);
    }
}

}
