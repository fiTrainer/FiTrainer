package com.fitrainer.upm.fitrainer;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MisRutinas extends AppCompatActivity {
    List<String> groupList;
    ArrayList<Ejercicio> childList;
    Map<String, ArrayList<Ejercicio>> laptopCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_rutinas);

        createGroupList();

        createCollection();


        // Lista Expandible
        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection, 3);
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


    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Curl de Pierna");
        groupList.add("Sentadillas");

    }

    private void createCollection() {

        Ejercicio ejercicio1Pierna = new Ejercicio(1, "Curl de Pierna", "Se hace en maquina", "");
        Ejercicio ejercicio2Pierna = new Ejercicio(2, "Sentadillas", "Se hace de pie", "");

        ArrayList<Ejercicio> piernaModel = new ArrayList<Ejercicio>();
        piernaModel.add(ejercicio1Pierna);


        ArrayList<Ejercicio> pechoModel = new ArrayList<Ejercicio>();
        pechoModel.add(ejercicio2Pierna);

        laptopCollection = new LinkedHashMap<String, ArrayList<Ejercicio>>();

        for (String laptop : groupList) {
            if (laptop.equals("Curl de Pierna")) {
                loadChild(piernaModel);
            } else if (laptop.equals("Sentadillas"))
                loadChild(pechoModel);
           /* else if (laptop.equals("Espalda"))
                loadChild(sonyModels);
            else if (laptop.equals("Biceps"))
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