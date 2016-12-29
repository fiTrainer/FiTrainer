package com.fitrainer.upm.fitrainer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class ListadoCategorias extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int year_x,month_x,day_x;
    private GridView gridView;
    private AdaptadorDeCategorias adaptador;

    static final int DIALOG_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_categorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btn = new Button(ListadoCategorias.this);
        btn.setText("Guardar Rutina");

        final Calendar cal = Calendar.getInstance();
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        year_x = cal.get(Calendar.YEAR);


        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Locale locale = new Locale("es","ES");
                Locale.setDefault(locale);
                showDialog(DIALOG_ID);
            }
        });
        toolbar.addView(btn);
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

        Intent intent = new Intent(this, DetalleListEjerc.class);
        intent.putExtra("tipoEjercicio", item.getNombre());
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID){
            return new DatePickerDialog(this, dpickerListener, year_x, month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            year_x = year;
            month_x = month + 1;
            day_x = day;
            ListadoCategorias.super.onBackPressed();
            Toast.makeText(ListadoCategorias.this, "Rutina Agregada",Toast.LENGTH_LONG).show();

        }
    };
}
