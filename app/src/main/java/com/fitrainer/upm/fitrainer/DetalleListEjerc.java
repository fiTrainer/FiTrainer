package com.fitrainer.upm.fitrainer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class DetalleListEjerc extends AppCompatActivity {
    List<String> groupList;
    ArrayList<Ejercicio> childList;
    Map<String, ArrayList<Ejercicio>> laptopCollection;
    ExpandableListView expListView;
    private Categoria itemDetallado;
    ProgressDialog prgDialog;
    JSONArray ejercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_list_ejerc);

        TextView tipoEjercicio = (TextView) findViewById(R.id.tvNomRut);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Por favor espere...");
        prgDialog.setCancelable(false);
        Bundle bundle = getIntent().getExtras();
        tipoEjercicio.setText(bundle.getString("tipoEjercicio"));




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createGroupList();
        RequestParams params = new RequestParams();
        params.put("accion","ejercicios");
        params.put("categoria",bundle.getString("tipoEjercicio"));
        System.out.println(bundle.getString("tipoEjercicio"));
        //createCollection();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        childList = new ArrayList<Ejercicio>();
        invokeWS(params);
    }


    private void createGroupList() {
        groupList = new ArrayList<String>();
        //groupList.add("Curl de Pierna");
        //groupList.add("Sentadillas");

    }

    private void createCollection() {
        laptopCollection = new LinkedHashMap<String, ArrayList<Ejercicio>>();
        try {

            for (int i = 0; i < ejercicios.length(); i++) {
                ArrayList<Ejercicio> model = new ArrayList<Ejercicio>();
                JSONObject ejerObj = (JSONObject) ejercicios.get(i);
                String imagen = ejerObj.isNull("foto")? null : ejerObj.getString("foto");
                Ejercicio ejercicio1 = new Ejercicio(ejerObj.getInt("idejercicio"),ejerObj.getString("nombre"),ejerObj.getString("descripcion"),imagen);
                model.add(ejercicio1);
                groupList.add(ejercicio1.getNombre());
                loadChild(model);
                laptopCollection.put(ejercicio1.getNombre(),childList);
            }
        }catch (JSONException e) {
                e.printStackTrace();
        }

        // Lista Expandible
        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection,1);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition).getNombre();
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void loadChild(ArrayList<Ejercicio> ejerciciosModelos) {
        childList = new ArrayList<Ejercicio>();
        for (Ejercicio model : ejerciciosModelos)
            childList.add(model);
    }
    public void invokeWS(RequestParams params) {
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.serverURL), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    System.out.println(new String(response));
                    JSONObject obj = new JSONObject(new String(response));
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        // Set Default Values for Edit View controls
                        ejercicios = obj.getJSONArray("ejercicios");
                        // Display successfully registered message using Toast
                        createCollection();
                        //Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else {
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("Error"), Toast.LENGTH_LONG).show();
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
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
