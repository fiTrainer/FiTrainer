package com.fitrainer.upm.fitrainer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ListadoCategorias extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int year_x,month_x,day_x;
    private GridView gridView;
    private AdaptadorDeCategorias adaptador;
    ProgressDialog prgDialog;

    static final int DIALOG_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_categorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btn = new Button(ListadoCategorias.this);
        btn.setText("Guardar Rutina");

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Por favor espere...");
        prgDialog.setCancelable(false);

        final Calendar cal = Calendar.getInstance();
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        year_x = cal.get(Calendar.YEAR);


        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Locale locale = new Locale("es","ES");
                Locale.setDefault(locale);
                showDialog(DIALOG_ID);
            }
        });
        toolbar.addView(btn);
        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new AdaptadorDeCategorias(this);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria item = (Categoria) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, DetalleListEjerc.class);
        intent.putExtra("tipoEjercicio", item.getNombre());
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID){
            return new DatePickerDialog(this, dpickerListener, year_x, month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            year_x = year;
            month_x = month + 1;
            day_x = day;
            SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyy");
            String date = month_x+"-"+day_x+"-"+year_x;
            try{
                Date mDate = sdf.parse(date);
                long fecha = mDate.getTime();
                RequestParams params = new RequestParams();
                params.put("fecha",fecha);
                params.put("accion","agregarRutina");
                params.put("userid",1);
                if(!RutinaSingleton.getInstance().isEmpty()) {
                    ArrayList<Ejercicio> ejercicios = RutinaSingleton.getInstance().getEjercicios();
                    String rutina = "[";
                    for (Ejercicio ejer : ejercicios) {
                        rutina += "{\"idejercicio\":" + ejer.getIdEjercicio() + ",\"rep\":" + ejer.getRep() + ",\"series\":" + ejer.getSeries() + ",\"peso\":" + ejer.getPeso() + "},";
                    }
                    rutina = rutina.substring(0, rutina.length() - 1) + "]";
                    params.put("ejercicios", rutina);
                    System.out.println(rutina);
                    invokeWS(params );
                    RutinaSingleton.getInstance().cleanRutina();
                    //Toast.makeText(ListadoCategorias.this, ""+fecha,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ListadoCategorias.this,"No hay ejercicios en tu rutina",Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ListadoCategorias.super.onBackPressed();


        }
    };

    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.serverURL),params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    System.out.println(new String(response));
                    JSONObject obj = new JSONObject(new String(response));
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(),obj.getString("Error") , Toast.LENGTH_LONG).show();
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
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
