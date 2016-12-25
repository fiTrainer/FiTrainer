package com.fitrainer.upm.fitrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RegistroModificacion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_modificacion);
        final Bundle extras = getIntent().getExtras();


        //Rellenamos el spinner
        List age = new ArrayList();
        age.add("Elija una edad");
        for (int i = 1; i <= 100; i++) {
            age.add(i);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        Spinner spinner = (Spinner)findViewById(R.id.spinnerEdad);
        spinner.setAdapter(spinnerArrayAdapter);


        //Inicializacion EditableText
        final EditText etNickname = (EditText) findViewById(R.id.etNickname);
        final EditText etNombre = (EditText) findViewById(R.id.etNombre);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etContrasenia = (EditText) findViewById(R.id.etContrasenia);
        final EditText etRepContrasenia = (EditText) findViewById(R.id.etRepContrasenia);
        final EditText etAltura = (EditText) findViewById(R.id.etAltura);
        final EditText etPeso = (EditText) findViewById(R.id.etPeso);
        Button btnModReg = (Button)findViewById(R.id.btnModReg);
        final RadioButton rbMujer = (RadioButton) findViewById(R.id.rbtMujer);
        final RadioButton rbHombre= (RadioButton) findViewById(R.id.rbtHombre);
        Button btnReset = (Button)findViewById(R.id.btnReset);
        final Usuario user= new Usuario(0, "pepe1993", "Pepe", "pepe@pepe.com", "contraseña", 23, 65.3, 1.73,true, false);

        if(extras.getBoolean("VIENE_DE_LOGIN")){
            btnModReg.setText("Registrarse");
        }else{
            etNickname.setEnabled(false);
            etNickname.setText(user.getNickname());
            etNombre.setText(user.getNombre());
            etEmail.setText(user.getEmail());
            etContrasenia.setText(user.getContrasenia());
            etRepContrasenia.setText(user.getContrasenia());
            etAltura.setText( new Double(user.getAltura()).toString());
            etPeso.setText( new Double(user.getPeso()).toString());
            if(user.getSexo()){
                rbHombre.setChecked(true);
            }else{
                rbMujer.setChecked(true);
            }
            btnModReg.setText("Modificar");

        }
        //Para el boton de Registrarse o Modificar
        btnModReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                RegistroModificacion.super.onBackPressed();
            }
        });

        //Para el boton de Reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(extras.getBoolean("VIENE_DE_LOGIN")){
                    etNickname.setText("");
                    etNombre.setText("");
                    etEmail.setText("");
                    etContrasenia.setText("");
                    etRepContrasenia.setText("");
                    etAltura.setText("");
                    etPeso.setText("");
                }else{
                    etNickname.setEnabled(false);
                    etNickname.setText(user.getNickname());
                    etNombre.setText(user.getNombre());
                    etEmail.setText(user.getEmail());
                    etContrasenia.setText(user.getContrasenia());
                    etRepContrasenia.setText(user.getContrasenia());
                    etAltura.setText( new Double(user.getAltura()).toString());
                    etPeso.setText( new Double(user.getPeso()).toString());
                    if(user.getSexo()){
                        rbHombre.setChecked(true);
                        rbMujer.setChecked(false);
                    }else{
                        rbMujer.setChecked(true);
                        rbHombre.setChecked(false);
                    }
                }
            }
        });
    }


    public void onRadioButtonClicked(View v)
    {



        boolean  checked = ((RadioButton) v).isChecked();
        RadioButton rbMujer = (RadioButton) findViewById(R.id.rbtMujer);
        RadioButton rbHombre= (RadioButton) findViewById(R.id.rbtHombre);

        switch(v.getId()){

            case R.id.rbtMujer:
                if(checked)
                    rbHombre.setChecked(false);
                break;

            case R.id.rbtHombre:
                if(checked)
                    rbMujer.setChecked(false);
                break;


        }
    }
}
