package com.fitrainer.upm.fitrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegistroModificacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_modificacion);

        //Rellenamos el spinner
        List age = new ArrayList();
        age.add("Elija una edad");
        for (int i = 1; i <= 100; i++) {
            age.add(i);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(spinnerArrayAdapter);


        //Para el boton de Registrarse
        Button button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                RegistroModificacion.super.onBackPressed();
            }
        });
    }


    public void onRadioButtonClicked(View v)
    {
        RadioButton rb1 = (RadioButton) findViewById(R.id.rbtMujer);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rbtHombre);


        boolean  checked = ((RadioButton) v).isChecked();


        switch(v.getId()){

            case R.id.rbtMujer:
                if(checked)
                    rb2.setChecked(false);
                break;

            case R.id.rbtHombre:
                if(checked)
                    rb1.setChecked(false);
                break;


        }
    }
}
