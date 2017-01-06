        package com.fitrainer.upm.fitrainer;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import com.fitrainer.upm.fitrainer.Sesion.AlertDialogManager;
        import com.fitrainer.upm.fitrainer.Sesion.SessionManagement;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;


        public class InicioSesion extends Activity {
            public static final int CONNECTION_TIMEOUT=10000;
            public static final int READ_TIMEOUT=15000;

            ProgressDialog prgDialog;

            // Email, password edittext
            EditText txtUsername, txtPassword;

            // login button
            Button btnLogin;

            // Alert Dialog Manager
            AlertDialogManager alert = new AlertDialogManager();

            // Session Manager Class
            SessionManagement session;

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // Session Manager
        session = new SessionManagement(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);


        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

            //Para el boton de Registrarse

            Button button = (Button)findViewById(R.id.btnRegistrarse);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent intent = new Intent(getApplicationContext(),RegistroModificacion.class);
                    intent.putExtra("VIENE_DE_LOGIN",true);
                    startActivity(intent);
                }
            });

        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // Initialize  AsyncLogin() class with email and password
                    new AsyncLogin().execute(username,password);

                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(InicioSesion.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }

            private class AsyncLogin extends AsyncTask<String, String, String>
            {
                ProgressDialog pdLoading = new ProgressDialog(InicioSesion.this);
                JSONObject jObj = null;
                HttpURLConnection conn;
                URL url = null;
                Usuario usuario = new Usuario();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();

                }
                @Override
                protected String doInBackground(String... params) {
                    try {

                        // Enter URL address where your php file resides
                        url = new URL(getString(R.string.serverURL));

                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return "exception";
                    }
                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        conn = (HttpURLConnection)url.openConnection();
                        conn.setReadTimeout(READ_TIMEOUT);
                        conn.setConnectTimeout(CONNECTION_TIMEOUT);
                        conn.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("nickname", params[0])
                                .appendQueryParameter("password", params[1])
                                .appendQueryParameter("accion", "loginUser");
                        String query = builder.build().getEncodedQuery();

                        // Open connection for sending data
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();
                        conn.connect();

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        return "exception";
                    }

                    try {
                        int response_code = conn.getResponseCode();

                        // Check if successful connection made
                        if (response_code == HttpURLConnection.HTTP_OK) {

                            // Read data sent from server
                            InputStream input = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                            StringBuilder result = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                            String errorString="";
                            try {
                                jObj = new JSONObject(result.toString());
                                boolean error = jObj.getBoolean("error");
                                if(!error){
                                    errorString="False";
                                    JSONObject user = jObj.getJSONObject("usuario");
                                    usuario.setNickname(user.getString("nickname"));
                                    usuario.setNombre(user.getString("nombre"));
                                    usuario.setIdUsuario(user.getInt("id"));
                                    usuario.setAltura(user.getDouble("altura"));
                                    usuario.setPeso(user.getDouble("peso"));
                                    usuario.setEdad(user.getInt("edad"));
                                    usuario.setEmail(user.getString("email"));
                                    if(user.getInt("sexo")==0){
                                        usuario.setSexo(true);
                                    }else{
                                        usuario.setSexo(false);
                                    }
                                    if(user.getInt("entrenador")==0){
                                        usuario.setEsEntrenador(false);
                                    }else{
                                        usuario.setSexo(true);
                                    }
                                }else{
                                    errorString="True";
                                }
                                // Pass data to onPostExecute method

                            } catch (JSONException e) {
                                Log.e("JSON Parser", "Error parsing data " + e.toString());
                            }
                            return(errorString);
                        }else{
                            return("unsuccessful");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "exception";
                    } finally {
                        conn.disconnect();
                    }
                }

                @Override
                protected void onPostExecute(String result) {

                    //this method will be running on UI thread

                    pdLoading.dismiss();

                    if(result.equals("False"))
                    {
                        /* Here launching another activity when login successful. If you persist login state
                        use sharedPreferences of Android. and logout button to clear sharedPreferences.
                         */

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(usuario);
                        String str=String.valueOf(session.isLoggedIn());
                        System.out.println(str);
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }else if (result.equals("True")){

                        // If username and password does not match display a error message
                        //Toast.makeText(InicioSesion.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                        // username / password doesn't match
                        alert.showAlertDialog(InicioSesion.this, "Login failed..", "Username/Password is incorrect", false);

                    } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                        //Toast.makeText(InicioSesion.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                        alert.showAlertDialog(InicioSesion.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }

            }
}


