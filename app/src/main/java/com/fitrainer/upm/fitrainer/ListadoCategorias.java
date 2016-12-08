package com.fitrainer.upm.fitrainer;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ListadoCategorias extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private AdaptadorDeCategorias adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_categorias);

        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new AdaptadorDeCategorias(this);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria item = (Categoria) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, DetalleCategoria.class);
        intent.putExtra("tipoEjercicio", item.getNombre());
        startActivity(intent);
    }
}
