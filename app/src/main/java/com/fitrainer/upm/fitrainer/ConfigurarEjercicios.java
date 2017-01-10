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
    NumberPicker nbSeries;
    NumberPicker nbRep;
    NumberPicker nbPeso;
    String nombre;
    int idEjer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_ejercicios);
        TextView nombreEjercicio = (TextView) findViewById(R.id.confTitulo);
        Bundle bundle = getIntent().getExtras();
        nombre = bundle.getString("NOMBRE_EJERCICIO");
        idEjer = bundle.getInt("ID_EJERCICIO");
        nombreEjercicio.setText(nombre);


        /*Botones*/
        Button botonMenosSeries = (Button) findViewById(R.id.botonMenosSeries);
        Button botonMasSeries = (Button) findViewById(R.id.botonMasSeries);
        Button botonMenosRep = (Button) findViewById(R.id.botonMenosRep);
        Button botonMasRep = (Button) findViewById(R.id.botonMasRep);
        Button botonMenosPeso = (Button) findViewById(R.id.botonMenosPeso);
        Button botonMasPeso = (Button) findViewById(R.id.botonMasPeso);
        EditText Series = (EditText) findViewById(R.id.etSeries);
        EditText Reps = (EditText) findViewById(R.id.etRep);
        EditText Peso = (EditText) findViewById(R.id.etPeso);

        nbSeries = new NumberPicker(botonMasSeries, botonMenosSeries, Series);
        nbRep = new NumberPicker(botonMasRep, botonMenosRep, Reps);
        nbPeso = new NumberPicker(botonMasPeso, botonMenosPeso, Peso);

        // Series.setText(nbSeries.getValue());


        //Para el boton de Guardar
        Button botonGuardar = (Button) findViewById(R.id.confBtnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                int series = nbSeries.getValue();
                int rep = nbRep.getValue();
                int peso = nbPeso.getValue();
                if(series == 0||rep==0||peso==0){
                    Toast.makeText(getApplicationContext(),"debes llenar los campos!",Toast.LENGTH_LONG).show();
                }else{
                    Ejercicio ej = new Ejercicio(idEjer,nombre,"","");
                    ej.setPeso(peso);
                    ej.setRep(rep);
                    ej.setSeries(series);
                    RutinaSingleton.getInstance().add(ej);
                    ConfigurarEjercicios.super.onBackPressed();
                }
            }
        });

    }

}
