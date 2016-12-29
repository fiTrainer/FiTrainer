package com.fitrainer.upm.fitrainer;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fitrainer.upm.fitrainer.ListadoRutinas.ListViewAdapterRutinas;
import com.fitrainer.upm.fitrainer.ListadoDietas.Menu;

import java.util.ArrayList;
import java.util.List;

public class MisRutinas extends Activity {

    // Declare Variables
    ListView list;
    ListViewAdapterRutinas listviewadapter;
    List<Menu> arrayMenus = new ArrayList<Menu>();
    String[] id;
    String[] nombre;
    String[] descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.activity_listado_menus);

        // Generate sample data into string arrays
        id = new String[] { "1","2","3" };

        nombre = new String[] { "Rutina1", "Rutina2", "Rutina3"};

        descripcion = new String[] { "Para adelgazar \nPara adelgazar\nPara adelgazar", "Para adelgazar en 1 mes",
                "Para perder 5KG en una semana"};
/*
        flag = new int[] { R.drawable.china, R.drawable.india,
                R.drawable.unitedstates, R.drawable.indonesia,
                R.drawable.brazil, R.drawable.pakistan, R.drawable.nigeria,
                R.drawable.bangladesh, R.drawable.russia, R.drawable.japan };*/

        for (int i = 0; i < id.length; i++) {
            Menu listadoMenu = new Menu(id[i], nombre[i], descripcion[i]);
            arrayMenus.add(listadoMenu);
        }


        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.ListView_listadoMenu);

        // Pass results to ListViewAdapterRutinas Class
        listviewadapter = new ListViewAdapterRutinas(this, R.layout.entrada,
                arrayMenus);

        // Binds the Adapter to the ListView
        list.setAdapter(listviewadapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapterRutinas Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapterRutinas Class
                        SparseBooleanArray selected = listviewadapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Menu selecteditem = listviewadapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                listviewadapter.remove(selecteditem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                mode.getMenuInflater().inflate(R.menu.listado_menus, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listviewadapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
}