package com.fitrainer.upm.fitrainer;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitrainer.upm.fitrainer.Sesion.SessionManagement;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class CompactCalendar extends AppCompatActivity {
    CompactCalendarView compactCalendar;
    final Locale localeES = new Locale("ES");
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM  yyyy", localeES);
    ProgressDialog prgDialog;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compact_calendar);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Por favor espere...");
        prgDialog.setCancelable(false);

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
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        RequestParams params = new RequestParams();
        params.put("accion","rutinas");
        params.put("userid",user.get(SessionManagement.KEY_ID));
        invokeWS(params);

        /*Event ev1 = new Event(Color.BLACK, 1481738042000L, "Hombros");
        Event ev2 = new Event(Color.RED, 1481738042000L, "Espalda");
        Event ev3 = new Event(Color.YELLOW, 1481738042000L, "Tripceps");
        Event ev4 = new Event(Color.GREEN, 1481738042000L, "Cardio");
        compactCalendar.addEvent(ev1,true);
        compactCalendar.addEvent(ev2,true);
        compactCalendar.addEvent(ev3,true);
        compactCalendar.addEvent(ev4,true);*/


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
    public void invokeWS(RequestParams params) {
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.serverURL), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    System.out.println(new String(response));
                    JSONObject obj = new JSONObject(new String(response));
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        // Set Default Values for Edit View controls
                        JSONArray fechas = obj.getJSONArray("fechas");
                        int len = fechas.length();
                        for(int i =0; i<len;i++){
                            JSONObject ejer = (JSONObject) fechas.get(i);
                            Event ev = new Event(Color.BLACK,ejer.getLong("fecha"),ejer.getString("categoria"));
                            compactCalendar.addEvent(ev,true);
                        }
                        // Display successfully registered message using Toast
                        //Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else {
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("Error"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
