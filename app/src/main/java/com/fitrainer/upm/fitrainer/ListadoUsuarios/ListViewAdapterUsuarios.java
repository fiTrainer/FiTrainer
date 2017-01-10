package com.fitrainer.upm.fitrainer.ListadoUsuarios;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.fitrainer.upm.fitrainer.R;
import com.fitrainer.upm.fitrainer.Sesion.AlertDialogManager;
import com.fitrainer.upm.fitrainer.Usuario;
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
import java.util.List;

public class ListViewAdapterUsuarios extends ArrayAdapter<Usuario> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<Usuario> arrayUsers;
    int id_entrenador;


    private SparseBooleanArray mSelectedItemsIds;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    public ListViewAdapterUsuarios(Context context, int resourceId,
                                  List<Usuario> arrayUsuarios,int idEntrenador) {
        super(context, resourceId, arrayUsuarios);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.arrayUsers = arrayUsuarios;
        inflater = LayoutInflater.from(context);
        this.id_entrenador=idEntrenador;
    }

    private class ViewHolder {
        // TextView id;
        TextView nombre;
        TextView nickname;
        TextView edad;
        TextView email;
        Button boton;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final com.fitrainer.upm.fitrainer.ListadoUsuarios.ListViewAdapterUsuarios.ViewHolder holder;
        final int pos=position;
        if (view == null) {
            holder = new com.fitrainer.upm.fitrainer.ListadoUsuarios.ListViewAdapterUsuarios.ViewHolder();
            view = inflater.inflate(R.layout.entrada_usuario, null);
            holder.nickname = (TextView) view.findViewById(R.id.nickname);
            holder.nombre = (TextView) view.findViewById(R.id.nombre);
            holder.edad = (TextView) view.findViewById(R.id.edad);
            holder.email = (TextView) view.findViewById(R.id.email);

            holder.boton=(Button) view.findViewById(R.id.botonDetalle);
            view.setTag(holder);
        } else {
            holder = (com.fitrainer.upm.fitrainer.ListadoUsuarios.ListViewAdapterUsuarios.ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        // holder.id.setText(arrayMenus.get(position).getId());
        holder.nombre.setText("Nombre: "+arrayUsers.get(position).getNombre());
        holder.nickname.setText(arrayUsers.get(position).getNickname());
        holder.edad.setText("Edad: "+String.valueOf(arrayUsers.get(position).getEdad()));
        holder.email.setText("Email: "+arrayUsers.get(position).getEmail());


        //FUNCIONA

        Button boton = (Button) view.findViewById(R.id.botonDetalle);
        boton.setText("Agregar");
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                int id_usuario =arrayUsers.get(pos).getIdUsuario();
                int entrenador=id_entrenador;
                new AsyncEntenadorAsignaUsuario().execute(String.valueOf(entrenador),String.valueOf(id_usuario));
                remove(arrayUsers.get(pos));
            }
        });
        return view;
    }

    @Override
    public void remove(Usuario object) {
        arrayUsers.remove(object);
        notifyDataSetChanged();
    }

    public List<Usuario> getWorldPopulation() {
        return arrayUsers;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }



    private class AsyncEntenadorAsignaUsuario extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(context);
        HttpURLConnection conn;
        URL url = null;

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
                url = new URL(context.getString(R.string.serverURL));
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
                        .appendQueryParameter("idEntrenador", params[0])
                        .appendQueryParameter("idUsuario", params[1])
                        .appendQueryParameter("accion", "asignarUsuarioAEntrenador");
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
                alert.showAlertDialog(context, "Insercion Correcta", "Se ha insertado correctamente", true);
            }else if (result.equals("True")){

                alert.showAlertDialog(context, "Insercion Incorrecta", "Ha habido un problema en la insercion", false);

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                alert.showAlertDialog(context, "Error", "Otro Error", false);
            }
        }

    }
}