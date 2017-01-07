package com.fitrainer.upm.fitrainer;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.fitrainer.upm.fitrainer.ListadoUsuarios.ListViewAdapterUsuarios;
import com.fitrainer.upm.fitrainer.Sesion.AlertDialogManager;
import com.fitrainer.upm.fitrainer.Sesion.SessionManagement;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AsignarUsuario extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;


    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;

    //ID ENTRENADOR
    int id_entrenador;

    ProgressDialog prgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_usuario);

        //SESION
        // get user data from session
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        if(!session.checkLogin()){
            HashMap<String, String> user = session.getUserDetails();
            id_entrenador=Integer.parseInt(user.get(SessionManagement.KEY_ID));
        }

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Por favor espere...");
        prgDialog.setCancelable(false);

        final EditText textBusqueda = (EditText) findViewById(R.id.etBuscar);



        Button btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(!textBusqueda.getText().toString().equals("")){
                    String textoBusqueda=textBusqueda.getText().toString();
                    new AsyncUserAsign().execute(textoBusqueda);
                }
            }
        });
    }


    private class AsyncUserAsign extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(AsignarUsuario.this);
        HttpURLConnection conn;
        URL url = null;

        //LISTADO USUARIOS
        ListView list;
        ListViewAdapterUsuarios listviewadapter;
        List<Usuario> arrayUsuarios = new ArrayList<Usuario>();

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
                        .appendQueryParameter("accion", "obtenerUsuariosPorNick");
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
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
                        JSONObject jObj = null;
                        jObj = new JSONObject(result.toString());
                        boolean error = jObj.getBoolean("error");
                        if(!error){
                            JSONArray jsonarray = jObj.getJSONArray("posts");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                Usuario usuario = new Usuario();
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                usuario.setIdUsuario(Integer.parseInt(jsonobject.getString("idusuario")));
                                usuario.setNickname(jsonobject.getString("nickname"));
                                usuario.setNombre(jsonobject.getString("nombre"));
                                usuario.setEmail(jsonobject.getString("email"));
                                usuario.setEdad(Integer.parseInt(jsonobject.getString("edad")));
                                arrayUsuarios.add(usuario);
                        }
                            errorString="False";
                        }else{
                            errorString="True";
                        }
                        // Pass data to onPostExecute method
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                        return "exception";
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
                // Locate the ListView in listview_main.xml
                list = (ListView) findViewById(R.id.ListView_listadoUserAsign);
                list.setVisibility(View.VISIBLE);
                // Pass results to ListViewAdapterMenu Class
                listviewadapter = new ListViewAdapterUsuarios(AsignarUsuario.this, R.layout.entrada_usuario,
                        arrayUsuarios,id_entrenador);

                // Binds the Adapter to the ListView
                list.setAdapter(listviewadapter);
            }else if (result.equals("True")){
                list = (ListView) findViewById(R.id.ListView_listadoUserAsign);
                list.setVisibility(View.INVISIBLE);
                alert.showAlertDialog(AsignarUsuario.this, "La busqueda no ha encontrado resultados", "0 resultados encontrados", false);

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                alert.showAlertDialog(AsignarUsuario.this, "Error", "Otro Error", false);
            }
        }

    }
}
