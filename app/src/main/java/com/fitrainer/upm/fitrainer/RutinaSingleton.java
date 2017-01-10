package com.fitrainer.upm.fitrainer;

import java.util.ArrayList;

/**
 * Created by La russa on 1/10/2017.
 */

public class RutinaSingleton {
    private ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

    public ArrayList<Ejercicio> getEjercicios(){return this.ejercicios;}
    public void add(Ejercicio ejercicio){ejercicios.add(ejercicio);}
    public void cleanRutina(){this.ejercicios = new ArrayList<Ejercicio>();}
    private static final RutinaSingleton rutina = new RutinaSingleton();
    public static RutinaSingleton getInstance(){return rutina;}
    public boolean isEmpty(){ return ejercicios.isEmpty();}
}
