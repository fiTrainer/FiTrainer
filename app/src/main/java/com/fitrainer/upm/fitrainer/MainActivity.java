package com.fitrainer.upm.fitrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;




import com.github.clans.fab.FloatingActionMenu;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    List<String> groupList;
    ArrayList<Ejercicio> childList;
    Map<String, ArrayList<Ejercicio>> laptopCollection;
    ExpandableListView expListView;
    private GridView gridView;
    private AdaptadorDeCategorias adaptador;
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createGroupList();

        createCollection();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Lista Expandible
        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection,0);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition).getNombre();
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });



        //Floating Button
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent intent = new Intent(getApplicationContext(),ListadoCategorias.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                //Intent intent = new Intent(getApplicationContext(),Calendario.class);
                //startActivity(intent);

            }
        });

    }



    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Piernas");
        groupList.add("Pecho");
        groupList.add("Espalda");
        groupList.add("Biceps");
        groupList.add("Triceps");
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
            if (laptop.equals("Piernas")) {
                loadChild(piernaModel);
            } else if (laptop.equals("Pecho"))
                loadChild(pechoModel);
            else if (laptop.equals("Espalda"))
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mi_perfil) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),RegistroModificacion.class);
            startActivity(intent);

        } else if (id == R.id.nav_mis_rutinas) {

            Intent intent = new Intent(getApplicationContext(),MisRutinas.class);
            startActivity(intent);

        } else if (id == R.id.nav_mis_dietas) {

        } else if (id == R.id.nav_asignar_usuario) {

        } else if (id == R.id.nav_inicio_sesion) {
            Intent intent = new Intent(getApplicationContext(),InicioSesion.class);
            startActivity(intent);

        } else if (id == R.id.nav_desconectar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
