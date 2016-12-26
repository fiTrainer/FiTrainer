package com.fitrainer.upm.fitrainer;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.MessageDigest;
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

       final Spinner spinner = (Spinner)findViewById(R.id.spinnerEdad);
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
            spinner.setSelection(user.getEdad());
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
                boolean validar=true;
                if(etNombre.getText().toString().matches("")){
                    etNombre.setError("Debe ingresar un Nombre");
                    validar=false;
                }else{
                    //Aqui de momento no se hace nada mas

                }
                if(etEmail.getText().toString().matches("")){
                    etEmail.setError("Debe ingresar un Email");
                    validar=false;
                }else{
                    if (!etEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
                    {
                        etEmail.setError("Email no valido");
                        validar=false;
                    }
                }
                if(etContrasenia.getText().toString().matches("")){
                    etContrasenia.setError("Debe ingresar una contraseña");
                    validar=false;
                }else{
                    //Aqui de momento no se hace nada mas

                }
                if(etRepContrasenia.getText().toString().matches("")){
                    etRepContrasenia.setError("Debe repetir la contraseña");
                    validar=false;
                }else{
                    //Aqui de momento no se hace nada mas
                }
                if(!etRepContrasenia.getText().toString().matches("")&&!etContrasenia.getText().toString().matches("")){
                    if(!etContrasenia.getText().toString().matches(etRepContrasenia.getText().toString())){
                        validar=false;
                        etRepContrasenia.setError("Las contraseñas no concuerda");
                    }
                }
                if(etAltura.getText().toString().matches("")){
                    etAltura.setError("Debe insertar su altura");
                    validar=false;
                }else{
                    //Aqui de momento no se hace nada mas
                }
                if(etPeso.getText().toString().matches("")){
                    etPeso.setError("Debe insertar su peso");
                    validar=false;
                }else{
                    //Aqui de momento no se hace nada mas
                }
                if(spinner.getSelectedItemPosition()==0){
                    View selectedView = spinner.getSelectedView();
                    TextView selectedTextView = (TextView) selectedView;
                    selectedTextView.setError("Debe seleccionar una edad");
                    validar=false;
                }
                if(!rbMujer.isChecked()&&!rbHombre.isChecked()){
                    rbMujer.setError("Debe seleccionar un sexo");
                    rbHombre.setError("Debe seleccionar un sexo");
                    validar=false;
                }else{
                    rbMujer.setError(null);
                    rbHombre.setError(null);
                }
                if(extras.getBoolean("VIENE_DE_LOGIN")){
                    if(etNickname.getText().toString().matches("")){
                        etNickname.setError("Debe ingresar un Nickname");
                        validar=false;
                    }else{
                        //Se debe buscar en la base de datos si existe ese nickname
                    }
                    //Si valido hago insert
                    if(validar){
                        //Si se ha validado, entonces insertamos en BBDD

                    }
                }else{
                    //Si se valida, entonces hago update
                    if(validar){
                        //Si se ha validado, entonces update en BBDD

                    }
                    RegistroModificacion.super.onBackPressed();
                }


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
                    spinner.setSelection(0);
                    rbHombre.setChecked(false);
                    rbMujer.setChecked(false);
                }else{
                    etNickname.setEnabled(false);
                    etNickname.setText(user.getNickname());
                    etNombre.setText(user.getNombre());
                    etEmail.setText(user.getEmail());
                    etContrasenia.setText(user.getContrasenia());
                    etRepContrasenia.setText(user.getContrasenia());
                    spinner.setSelection(user.getEdad());
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
    //Obtener SHA-256 de un String
    private String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
