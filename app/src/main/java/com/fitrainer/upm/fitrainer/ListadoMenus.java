package com.fitrainer.upm.fitrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitrainer.upm.fitrainer.ListadoDietas.ListaEntrada;
import com.fitrainer.upm.fitrainer.ListadoDietas.Lista_adaptador;

public class ListadoMenus extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_menus);

        ArrayList<ListaEntrada> datos = new ArrayList<ListaEntrada>();

        datos.add(new ListaEntrada(false, "Menu Dia 1", "Menu del dia 1"));
        datos.add(new ListaEntrada(false, "Menu Dia 2", "Menu del dia 1"));
        datos.add(new ListaEntrada(false, "Menu Dia 3", "Menu del dia 1"));
        datos.add(new ListaEntrada(false, "Menu Dia 4", "Menu del dia 1"));
        datos.add(new ListaEntrada(false, "Menu Dia 5", "Menu del dia 1"));

        lista = (ListView) findViewById(R.id.ListView_listadoMenu);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_list_menu, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((ListaEntrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    texto_inferior_entrada.setTextIsSelectable(true);
                    texto_inferior_entrada.setRawInputType(InputType.TYPE_CLASS_TEXT);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((ListaEntrada) entrada).get_textoDebajo());

                    /*ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((ListaEntrada) entrada_menu).get_idImagen());*/
                }
            }
        });

        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                ListaEntrada elegido = (ListaEntrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                Toast toast = Toast.makeText(ListadoMenus.this, texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

}