package com.fitrainer.upm.fitrainer.ListadoRutinas;



import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fitrainer.upm.fitrainer.CrearModificarMenu;
import com.fitrainer.upm.fitrainer.DetalleRutina;
import com.fitrainer.upm.fitrainer.ListadoDietas.Menu;
import com.fitrainer.upm.fitrainer.R;

import java.util.List;

public class ListViewAdapterRutinas extends ArrayAdapter<Menu> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<Menu> arrayRutinas;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapterRutinas(Context context, int resourceId,
                                  List<Menu> arrayMenus) {
        super(context, resourceId, arrayMenus);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.arrayRutinas = arrayMenus;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
       // TextView id;
        TextView nombre;
        TextView descripcion;
        Button boton;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final int pos=position;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.entrada, null);
            // Locate the TextViews in listview_item.xml
            //holder.id = (TextView) view.findViewById(R.id.id);
            holder.nombre = (TextView) view.findViewById(R.id.nombre);
            holder.descripcion = (TextView) view.findViewById(R.id.descripcion);
            holder.boton=(Button) view.findViewById(R.id.botonDetalle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
       // holder.id.setText(arrayMenus.get(position).getId());
        holder.nombre.setText(arrayRutinas.get(position).getNombre());
        holder.descripcion.setText(arrayRutinas.get(position).getDescripcion());


        //FUNCIONA

        Button boton = (Button) view.findViewById(R.id.botonDetalle);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    Intent intent = new Intent(context,DetalleRutina.class);
                    intent.putExtra("NOMBRE_RUTINA", arrayRutinas.get(pos).getNombre());
                    context.startActivity(intent);
                }
            });

        return view;
    }

    @Override
    public void remove(Menu object) {
        arrayRutinas.remove(object);
        notifyDataSetChanged();
    }

    public List<Menu> getWorldPopulation() {
        return arrayRutinas;
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