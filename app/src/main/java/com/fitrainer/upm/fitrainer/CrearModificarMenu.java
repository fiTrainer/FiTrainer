package com.fitrainer.upm.fitrainer;

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

import org.w3c.dom.Text;

public class CrearModificarMenu extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_menu);
        Bundle bundle = getIntent().getExtras();


        TextView tituloMenu = (TextView) findViewById(R.id.tituloMenu);
        ArrayList<ListaEntrada> datos = new ArrayList<ListaEntrada>();

        datos.add(new ListaEntrada(R.drawable.bmw_serie6_cabrio_2015, "DESAYUNO", "Búho es el nombre común de aves de la familia Strigidae, del orden de las estrigiformes o aves rapaces nocturnas. Habitualmente designa especies que, a diferencia de las lechuzas, tienen plumas alzadas que parecen orejas."));
        datos.add(new ListaEntrada(R.drawable.ford_mondeo, "ALMUERZO", "Los troquilinos (Trochilinae) son una subfamilia de aves apodiformes de la familia Trochilidae, conocidas vulgarmente como colibríes, quindes, tucusitos, picaflores, chupamirtos, chuparrosas, huichichiquis (idioma nahuatl), mainumby (idioma guaraní) o guanumby. Conjuntamente con las ermitas, que pertenecen a la subfamilia Phaethornithinae, conforman la familia Trochilidae que, en la sistemática de Charles Sibley, se clasifica en un orden propio: Trochiliformes, independiente de los vencejos del orden Apodiformes. La subfamilia Trochilinae incluye más de 100 géneros que comprenden un total de 330 a 340 especies."));
        datos.add(new ListaEntrada(R.drawable.jaguar_xe, "COMIDA", "El cuervo común (Corvus corax) es una especie de ave paseriforme de la familia de los córvidos (Corvidae). Presente en todo el hemisferio septentrional, es la especie de córvido con la mayor superficie de distribución. Con el cuervo de pico grueso, es el mayor de los córvidos y probablemente la paseriforme más pesada; en su madurez, el cuervo común mide entre 52 y 69 centímetros de longitud y su peso varía de 0,69 a 1,7 kilogramos. Los cuervos comunes viven generalmente de 10 a 15 años pero algunos individuos han vivido 40 años. Los juveniles pueden desplazarse en grupos pero las parejas ya formadas permanecen juntas toda su vida, cada pareja defendiendo un territorio. Existen 8 subespecies conocidas que se diferencian muy poco aparentemente, aunque estudios recientes hayan demostrado diferencias genéticas significativas entre las poblaciones de distintas regiones."));
        datos.add(new ListaEntrada(R.drawable.ford_mondeo, "MERIENDA", "Los troquilinos (Trochilinae) son una subfamilia de aves apodiformes de la familia Trochilidae, conocidas vulgarmente como colibríes, quindes, tucusitos, picaflores, chupamirtos, chuparrosas, huichichiquis (idioma nahuatl), mainumby (idioma guaraní) o guanumby. Conjuntamente con las ermitas, que pertenecen a la subfamilia Phaethornithinae, conforman la familia Trochilidae que, en la sistemática de Charles Sibley, se clasifica en un orden propio: Trochiliformes, independiente de los vencejos del orden Apodiformes. La subfamilia Trochilinae incluye más de 100 géneros que comprenden un total de 330 a 340 especies."));
        datos.add(new ListaEntrada(R.drawable.jaguar_xe, "CENA", "Los fenicopteriformes (Phoenicopteriformes), los cuales reciben el nombre vulgar de flamencos, son un orden de aves neognatas, con un único género viviente: Phoenicopterus. Son aves que se distribuyen tanto por el hemisferio occidental como por el hemisferio oriental: existen cuatro especies en América y dos en el Viejo Mundo. Tienen cráneo desmognato holorrino, con 16 a 20 vértebras cervicales y pies anisodáctilos."));
        lista = (ListView) findViewById(R.id.ListView_listado);

        //Si origen pagina vale 1 significa que viene del listado de los Menus y nos encontramos en el detalle
        if(bundle.getString("ORIGEN_PAGINA").equals("1")){
            tituloMenu.setText(bundle.getString("NOMBRE_MENU"));
            lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_menu, datos){
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                        if (texto_superior_entrada != null)
                            texto_superior_entrada.setText(((ListaEntrada) entrada).get_textoEncima());

                        TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.edit_text_desc);
                        texto_inferior_entrada.setTextIsSelectable(true);
                        texto_inferior_entrada.setRawInputType(InputType.TYPE_CLASS_TEXT);
                        if (texto_inferior_entrada != null)
                            texto_inferior_entrada.setFocusable(false);
                            texto_inferior_entrada.setClickable(false);
                            texto_inferior_entrada.setText(((ListaEntrada) entrada).get_textoDebajo());

                    }
                }
            });

            lista.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    ListaEntrada elegido = (ListaEntrada) pariente.getItemAtPosition(posicion);

                    CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                    Toast toast = Toast.makeText(CrearModificarMenu.this, texto, Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }else if(bundle.getString("ORIGEN_PAGINA").equals("2")){ //Significa que viene de modificar menus
            // TODO
        }else{
            tituloMenu.setVisibility(View.INVISIBLE); //Significa que viene de modificar menus
            lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_menu, datos){
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                        if (texto_superior_entrada != null)
                            texto_superior_entrada.setText(((ListaEntrada) entrada).get_textoEncima());

                        TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.edit_text_desc);
                        texto_inferior_entrada.setTextIsSelectable(true);
                        texto_inferior_entrada.setRawInputType(InputType.TYPE_CLASS_TEXT);
                        if (texto_inferior_entrada != null)
                            texto_inferior_entrada.setText(((ListaEntrada) entrada).get_textoDebajo(), TextView.BufferType.EDITABLE);

                    }
                }
            });

            lista.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    ListaEntrada elegido = (ListaEntrada) pariente.getItemAtPosition(posicion);

                    CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                    Toast toast = Toast.makeText(CrearModificarMenu.this, texto, Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }




    }

}
