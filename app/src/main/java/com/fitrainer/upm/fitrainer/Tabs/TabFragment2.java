package com.fitrainer.upm.fitrainer.Tabs;

/**
 * Created by abel on 10/12/16.
 */
/**
 * Created by abel on 10/12/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitrainer.upm.fitrainer.AdaptadorDeCategorias;
import com.fitrainer.upm.fitrainer.ConfigurarEjercicios;
import com.fitrainer.upm.fitrainer.Ejercicio;
import com.fitrainer.upm.fitrainer.ExpandableListAdapter;
import com.fitrainer.upm.fitrainer.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TabFragment2 extends Fragment {
    List<String> groupList;
    ArrayList<Ejercicio> childList;
    Map<String, ArrayList<Ejercicio>> laptopCollection;
    ExpandableListView expListView;
    private GridView gridView;
    private AdaptadorDeCategorias adaptador;
    // Inflate the layout for this fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        createGroupList();
        createCollection();
        // Lista Expandible
        expListView = (ExpandableListView) view.findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this.getActivity(), groupList, laptopCollection,0);
        expListView.setAdapter(expListAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition).getNombre();
                //Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG).show();

                return true;
            }
        });
        return view;
    }


    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("DESAYUNO");
        groupList.add("APERITIVO");
        groupList.add("COMIDA");
        groupList.add("MERIENDA");
        groupList.add("CENA");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        Ejercicio ejercicio1Pierna = new Ejercicio(1,"Curl de Pierna", "Se hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquinaSe hace en maquina","");
        Ejercicio ejercicio2Pierna = new Ejercicio(2,"Sentadillas", "Se hace de pie","");

        ArrayList<Ejercicio> piernaModel = new ArrayList<Ejercicio>();
        piernaModel.add(ejercicio1Pierna);
        piernaModel.add(ejercicio2Pierna);

        Ejercicio ejercicio1Pecho = new Ejercicio(3,"Press de Banca", "Se hace en maquina","");
        ArrayList<Ejercicio> pechoModel = new ArrayList<Ejercicio>();
        pechoModel.add(ejercicio1Pecho);

        Ejercicio ejercicio1Espalda = new Ejercicio(3,"Dominadas", "No hace falta Maquina","");
        ArrayList<Ejercicio> espaldaModel = new ArrayList<Ejercicio>();
        espaldaModel.add(ejercicio1Espalda);


        laptopCollection = new LinkedHashMap<String, ArrayList<Ejercicio>>();

        for (String laptop : groupList) {
            if (laptop.equals("DESAYUNO")) {
                loadChild(piernaModel);
            } else if (laptop.equals("APERITIVO"))
                loadChild(pechoModel);
            else if (laptop.equals("COMIDA"))
                loadChild(espaldaModel);
            /*else if (laptop.equals("Biceps"))
                loadChild(hclModels);
            else if (laptop.equals("Triceps"))
                loadChild(samsungModels);*/

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(ArrayList<Ejercicio> ejerciciosModelos) {
        childList = new ArrayList<Ejercicio>();
        for (Ejercicio model : ejerciciosModelos)
            childList.add(model);
    }

}