package com.fitrainer.upm.fitrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.fitrainer.upm.fitrainer.Sesion.AlertDialogManager;
import com.fitrainer.upm.fitrainer.Sesion.SessionManagement;
import com.fitrainer.upm.fitrainer.Tabs.PagerAdapter;
import com.github.clans.fab.FloatingActionMenu;
import android.support.v4.view.ViewPager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2,floatingActionButton3;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isLoggedIn(),
                Toast.LENGTH_LONG).show();

        /**************BLOQUEAMOS LOS ITEMS DEL LISTADO SI NO ESTAMOS LOGUEADOS*******************/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        if(!session.isLoggedIn()){
            MenuItem nav_perfil = menu.findItem(R.id.nav_mi_perfil);
            nav_perfil.setVisible(false);

            MenuItem nav_desconectar = menu.findItem(R.id.nav_desconectar);
            nav_desconectar.setVisible(false);
        }else{
            MenuItem nav_inicio_sesion = menu.findItem(R.id.nav_inicio_sesion);
            nav_inicio_sesion.setVisible(false);
        }

        /*********************************/

        /*********************************/
        /*Tabs del principal*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabRutinaHoy));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabMenuHoy));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /* FIN Tabs del principal*/
        /*********************************/

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //Floating Button
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);


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
                Intent intent = new Intent(getApplicationContext(),CrearModificarMenu.class);
                intent.putExtra("ORIGEN_PAGINA", "3");
                startActivity(intent);

            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent calendarIntent = new Intent(getApplicationContext(),CompactCalendar.class);
                startActivity(calendarIntent);
            }
        });

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
        if (id == R.id.action_calendar) {
            Intent calendarIntent = new Intent(getApplicationContext(),CompactCalendar.class);
            startActivity(calendarIntent);
            //return true;
        }
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
            intent.putExtra("VIENE_DE_LOGIN",false);
            startActivity(intent);

        } else if (id == R.id.nav_mis_rutinas) {

            Intent intent = new Intent(getApplicationContext(),MisRutinas.class);
            startActivity(intent);

        } else if (id == R.id.nav_mis_dietas) {
            Intent intent = new Intent(getApplicationContext(),MisMenus.class);
            startActivity(intent);

        } else if (id == R.id.nav_asignar_usuario) {
            Intent intent = new Intent(getApplicationContext(),AsignarUsuario.class);
            startActivity(intent);

        } else if (id == R.id.nav_inicio_sesion) {
            Intent intent = new Intent(getApplicationContext(),InicioSesion.class);
            startActivity(intent);

        } else if (id == R.id.nav_desconectar) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
