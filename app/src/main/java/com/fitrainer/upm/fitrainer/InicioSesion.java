        package com.fitrainer.upm.fitrainer;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.fitrainer.upm.fitrainer.R;
        import com.fitrainer.upm.fitrainer.Sesion.AlertDialogManager;
        import com.fitrainer.upm.fitrainer.Sesion.SessionManagement;
        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import org.json.JSONException;
        import org.json.JSONObject;

        import cz.msebera.android.httpclient.Header;


        public class InicioSesion extends Activity {

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
                    // username = test
                    // password = test
                    if(username.equals("test") && password.equals("test")){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com","PEPE");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(InicioSesion.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(InicioSesion.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }


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
                        //When user is already registered
                        else if(statusCode == 306){
                            Toast.makeText(getApplicationContext(), "El usuario ya se encuentra registrado", Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else{
                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
}


