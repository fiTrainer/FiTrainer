package com.fitrainer.upm.fitrainer;

/**
 * Clase que representa la existencia de un Coche
 */
public class Categoria {
    private String nombre;
    private int idDrawable;


    public Categoria(String nombre, int idDrawable) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
    }



    public static Categoria[] ITEMS = {
            new Categoria("Pierna", R.drawable.jaguar_f_type_2015),
            new Categoria("Espalda", R.drawable.mercedes_benz_amg_gt),
            new Categoria("Pecho", R.drawable.mazda_mx5_2015),
            new Categoria("Hombros", R.drawable.porsche_911_gts),
            new Categoria("Biceps", R.drawable.bmw_serie6_cabrio_2015),
            new Categoria("Triceps", R.drawable.ford_mondeo),
            new Categoria("Abdomen", R.drawable.volvo_v60_crosscountry),
            new Categoria("Antebrazo", R.drawable.jaguar_xe),
            new Categoria("Cardio", R.drawable.volvo_v60_crosscountry),

    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */
    public static Categoria getItem(int id) {
        for (Categoria item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}