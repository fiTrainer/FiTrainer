package com.fitrainer.upm.fitrainer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CompactCalendar extends AppCompatActivity {
    CompactCalendarView compactCalendar;
    final Locale localeES = new Locale("ES");
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM  yyyy", localeES);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compact_calendar);

        final TextView showPreviousMonthBut = (TextView)findViewById(R.id.prevMonth);
        final TextView showNextMonthBut = (TextView)findViewById(R.id.nextMonth);
        final List<String> mutableEvents = new ArrayList<>();
        final ListView eventListView = (ListView)findViewById(R.id.event_listview);

        final TextView monthTextView = (TextView)findViewById(R.id.monthTextView);

        final ArrayAdapter adapter = new ArrayAdapter<String>(CompactCalendar.this, android.R.layout.simple_list_item_1, mutableEvents);
        TextView listTitle = new TextView(CompactCalendar.this);
        listTitle.setText("Ejercicios del día");
        listTitle.setTextSize(16);
        eventListView.addHeaderView(listTitle);
        eventListView.setAdapter(adapter);

        //final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.setLocale(TimeZone.getDefault(),localeES);

        Event ev1 = new Event(Color.BLACK, 1481738042000L, "Hombros");
        Event ev2 = new Event(Color.RED, 1481738042000L, "Espalda");
        Event ev3 = new Event(Color.YELLOW, 1481738042000L, "Tripceps");
        Event ev4 = new Event(Color.GREEN, 1481738042000L, "Cardio");
        compactCalendar.addEvent(ev1,true);
        compactCalendar.addEvent(ev2,true);
        compactCalendar.addEvent(ev3,true);
        compactCalendar.addEvent(ev4,true);


        monthTextView.setText(dateFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
                public void onDayClick(Date dateClicked) {
                List<Event> eventsFromMap = compactCalendar.getEvents(dateClicked);
                monthTextView.setText(dateFormatMonth.format(dateClicked));
                if (eventsFromMap != null && eventsFromMap.size() >0) {
                    mutableEvents.clear();
                    for (Event event : eventsFromMap) {
                        mutableEvents.add((String) event.getData());
                    }
                    adapter.notifyDataSetChanged();
                }else if (eventsFromMap != null && eventsFromMap.size() == 0){
                    mutableEvents.clear();
                    mutableEvents.add("Ningún ejercicio para este día");
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.showPreviousMonth();
                mutableEvents.clear();
                adapter.notifyDataSetChanged();

            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.showNextMonth();
                mutableEvents.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
