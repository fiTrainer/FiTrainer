package com.fitrainer.upm.fitrainer.ListadoUsuarios;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.fitrainer.upm.fitrainer.R;
import com.fitrainer.upm.fitrainer.Usuario;

import java.util.List;

public class ListViewAdapterUsuarios extends ArrayAdapter<Usuario> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<Usuario> arrayUsers;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapterUsuarios(Context context, int resourceId,
                                  List<Usuario> arrayUsuarios) {
        super(context, resourceId, arrayUsuarios);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.arrayUsers = arrayUsuarios;
        inflater = LayoutInflater.from(context);
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
            // Locate the TextViews in listview_item.xml
            //holder.id = (TextView) view.findViewById(R.id.id);
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
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                //Intent intent = new Intent(context,DetalleRutina.class);
                //intent.putExtra("NOMBRE_RUTINA", arrayUsers.get(pos).getNombre());
                //context.startActivity(intent);
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
}