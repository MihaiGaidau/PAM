package com.example.denis.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;

public class SearchActivity extends AppCompatActivity {
    ListView myListView;
    Realm realm;
    RealmResults<Event> events;
    String myQuerry;
    List<String> eventsShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myListView = (ListView)findViewById(R.id.myresultsList);
        myQuerry = getIntent().getStringExtra("searchText");
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        fillList();
    }

    private void fillList() {
        events = realm.where(Event.class).contains("eTitle",myQuerry).findAll();
        eventsShow = new ArrayList<String>();
        if (events.size() >0) {
            for (Event event : events) {
                eventsShow.add(event.getDate() + "|" + event.toString());
            }
        }
        events = realm.where(Event.class).contains("eLocation",myQuerry).findAll();
        if (events.size() >0) {
            for (Event event : events) {
                eventsShow.add(event.getDate() + "|" + event.toString());
            }
        }
        events = realm.where(Event.class).contains("eNote",myQuerry).findAll();
        if (events.size() >0) {
            for (Event event : events) {
                eventsShow.add(event.getDate() + "|" + event.toString());
            }
        }
        eventsShow.stream().distinct();
        if (eventsShow.size()>0) {
            ArrayAdapter<String> eventsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsShow);
            myListView.setAdapter(eventsAdapter);
        }

    }
}
