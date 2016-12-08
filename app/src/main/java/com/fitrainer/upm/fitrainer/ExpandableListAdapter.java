package com.fitrainer.upm.fitrainer;

/**
 * Created by abel on 22/11/16.
 */


        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseExpandableListAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.view.View.OnLongClickListener;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, ArrayList<Ejercicio>> laptopCollections;
    private List<String> laptops;
    private int origenActividad;

    /* OrigenActividad
     0 --> Si viene de Main
     1 --> Si viene de Listado Categorias
     2 --> Si viene de Mis Rutinas */


    //ABEL de andres gomez

    public ExpandableListAdapter(Activity context, List<String> laptops,
                                 Map<String, ArrayList<Ejercicio>> laptopCollections, int vieneDe) {
        this.context = context;
        this.laptopCollections = laptopCollections;
        this.laptops = laptops;
        this.origenActividad=vieneDe;
    }

    public Ejercicio getChild(int groupPosition, int childPosition) {
        Ejercicio ejercicio=laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Ejercicio ej = getChild(groupPosition, childPosition);
        final int idEjercicio=ej.getIdEjercicio();
        final String nombreEjercicio = ej.getNombre();
        final String descripcionEjercicio = ej.getDescripcion();
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView titulo = (TextView) convertView.findViewById(R.id.laptop);
        TextView descripcion = (TextView) convertView.findViewById(R.id.detCatTxvDesc);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        final Button confButton = (Button) convertView.findViewById(R.id.detCatBtnConfi);

        if(this.origenActividad==0){
            confButton.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }else if(this.origenActividad==1){
            //titulo.setVisibility(View.INVISIBLE);
            confButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    Intent intent = new Intent(context,ConfigurarEjercicios.class);
                    intent.putExtra("ID_EJERCICIO", idEjercicio);
                    intent.putExtra("NOMBRE_EJERCICIO", nombreEjercicio);
                    context.startActivity(intent);

                }
            });

            delete.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to remove?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ArrayList<Ejercicio> child =
                                            laptopCollections.get(laptops.get(groupPosition));
                                    child.remove(childPosition);
                                    laptops.remove(groupPosition);//Hacer pruebas bien
                                    notifyDataSetChanged();
                                }
                            });
                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }
        else if(this.origenActividad==3){
            descripcion.setVisibility(View.INVISIBLE);
            //titulo.setText(laptops.get(groupPosition)); --Obtiene el nombre del string en el que esta
            confButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    Intent intent = new Intent(context,ConfigurarEjercicios.class);
                    intent.putExtra("ID_EJERCICIO", idEjercicio);
                    intent.putExtra("NOMBRE_EJERCICIO", nombreEjercicio);
                    context.startActivity(intent);

                }
            });


            delete.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to remove?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ArrayList<Ejercicio> child =
                                            laptopCollections.get(laptops.get(groupPosition));
                                    child.remove(childPosition);
                                    notifyDataSetChanged();
                                }
                            });
                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }

        titulo.setText(nombreEjercicio);

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return laptops.get(groupPosition);
    }

    public int getGroupCount() {
        return laptops.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
