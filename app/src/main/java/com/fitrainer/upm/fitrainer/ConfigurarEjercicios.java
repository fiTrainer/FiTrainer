package com.fitrainer.upm.fitrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfigurarEjercicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_ejercicios);
        TextView nombreEjercicio = (TextView) findViewById(R.id.confTitulo);
        Bundle bundle = getIntent().getExtras();
        nombreEjercicio.setText(bundle.getString("NOMBRE_EJERCICIO"));


        //Para el boton de Guardar
        Button botonGuardar = (Button)findViewById(R.id.confBtnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ConfigurarEjercicios.super.onBackPressed();
            }
        });

    }


}
