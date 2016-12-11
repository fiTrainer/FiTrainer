package com.fitrainer.upm.fitrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.fitrainer.upm.fitrainer.NumberPicker;

import android.widget.EditText;
import android.widget.TextView;

public class ConfigurarEjercicios extends AppCompatActivity {

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
        Button botonGuardar = (Button)findViewById(R.id.confBtnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ConfigurarEjercicios.super.onBackPressed();
            }
        });

    }


}
