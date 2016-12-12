package com.fitrainer.upm.fitrainer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.fitrainer.upm.fitrainer.NumberPicker;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class ConfigurarEjercicios extends AppCompatActivity {
    int year_x,month_x,day_x;

    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_ejercicios);
        TextView nombreEjercicio = (TextView) findViewById(R.id.confTitulo);
        Bundle bundle = getIntent().getExtras();
        nombreEjercicio.setText(bundle.getString("NOMBRE_EJERCICIO"));


        /*Botones*/
        Button botonMenosSeries = (Button)findViewById(R.id.botonMenosSeries);
        Button botonMasSeries = (Button)findViewById(R.id.botonMasSeries);
        Button botonMenosRep = (Button)findViewById(R.id.botonMenosRep);
        Button botonMasRep = (Button)findViewById(R.id.botonMasRep);
        Button botonMenosPeso = (Button)findViewById(R.id.botonMenosPeso);
        Button botonMasPeso= (Button)findViewById(R.id.botonMasPeso);
        EditText Series = (EditText) findViewById(R.id.etSeries);
        EditText Reps = (EditText) findViewById(R.id.etRep);
        EditText Peso = (EditText) findViewById(R.id.etPeso);

        NumberPicker nbSeries= new NumberPicker(botonMasSeries,botonMenosSeries,Series);
        NumberPicker nbRep= new NumberPicker(botonMasRep,botonMenosRep,Reps);
        NumberPicker nbPeso= new NumberPicker(botonMasPeso,botonMenosPeso,Peso);

       // Series.setText(nbSeries.getValue());



        //Para el boton de Guardar
        final Calendar cal = Calendar.getInstance();
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        year_x = cal.get(Calendar.YEAR);
        Button botonGuardar = (Button)findViewById(R.id.confBtnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Locale locale = new Locale("es","ES");
                Locale.setDefault(locale);
                showDialog(DIALOG_ID);
                //ConfigurarEjercicios.super.onBackPressed();
            }
        });

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
            ConfigurarEjercicios.super.onBackPressed();
            Toast.makeText(ConfigurarEjercicios.this, "Rutina Agregada",Toast.LENGTH_LONG).show();

        }
    };

}
