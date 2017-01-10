package com.fitrainer.upm.fitrainer.Tabs;


    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ExpandableListView;
    import android.widget.GridView;

    import com.fitrainer.upm.fitrainer.AdaptadorDeCategorias;
    import com.fitrainer.upm.fitrainer.Ejercicio;
    import com.fitrainer.upm.fitrainer.ExpandableListAdapter;
    import com.fitrainer.upm.fitrainer.R;
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
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.LinkedHashMap;
    import java.util.List;
    import java.util.Map;

public class TabFragment1 extends Fragment {
    List<String> groupList;
    ArrayList<Ejercicio> childList;
    Map<String, ArrayList<Ejercicio>> laptopCollection;
    ExpandableListView expListView;
    private GridView gridView;
    private AdaptadorDeCategorias adaptador;
    // Inflate the layout for this fragment
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    JSONArray ejercicios;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    String errorString="";

    // Session Manager Class
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);

        // get user data from session
        session = new SessionManagement(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        String id_usuario=user.get(SessionManagement.KEY_ID);
        expListView = (ExpandableListView) view.findViewById(R.id.laptop_list);
        Date d = new Date();
        String str = new SimpleDateFormat("mm-dd-yyyy").format(d);
        Date mDate = null;
        try {
            mDate = new SimpleDateFormat("mm-dd-yyy").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = mDate.getTime();

        createGroupList();

        new AsyncObtenerRutinasDia().execute(String.valueOf(millis),id_usuario);
        if(errorString.equals("False")) {
            createCollection();
            // Lista Expandible
        }
        return view;
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
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                getActivity(), groupList, laptopCollection,1);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition).getNombre();
                //Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }


    private void loadChild(ArrayList<Ejercicio> ejerciciosModelos) {
        childList = new ArrayList<Ejercicio>();
        for (Ejercicio model : ejerciciosModelos)
            childList.add(model);
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();

    }


    private class AsyncObtenerRutinasDia extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread

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
                        .appendQueryParameter("fecha", params[0])
                        .appendQueryParameter("idUsuario", params[1])
                        .appendQueryParameter("accion", "obtenerRutinasDia");
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

                    try {
                        JSONObject jObj = null;
                        jObj = new JSONObject(result.toString());
                        boolean error = jObj.getBoolean("error");
                        if(!error){
                            ejercicios = jObj.getJSONArray("posts");
                            createCollection();
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
            if(result.equals("False"))
            {
                //alert.showAlertDialog(getActivity(), "Insercion Correcta", "Se ha insertado correctamente", true);
            }else if (result.equals("True")){

                alert.showAlertDialog(getActivity(), "No se han encontrado resultados", "No hay ninguna rutina pleaneada en el dia de hoy", false);

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                alert.showAlertDialog(getActivity(), "Error", "Otro Error", false);
            }
        }

    }

}