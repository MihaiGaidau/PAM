package com.example.denis.calendarview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SearchView mySearch;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = findViewById(R.id.calendarView);
        mySearch = (SearchView)findViewById(R.id.searchView);
        searchBtn = (Button)findViewById(R.id.SearchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mySearch.getQuery().toString() == null && mySearch.getQuery().toString().isEmpty())){
                    Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                    myIntent.putExtra("searchText",mySearch.getQuery().toString());
                    startActivity(myIntent);
                }

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
               Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                intent.putExtra("studioData", date);
                startActivity(intent);
            }
        });



    }
}
