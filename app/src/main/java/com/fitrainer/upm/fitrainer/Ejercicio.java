package com.fitrainer.upm.fitrainer;

/**
 * Created by abel on 3/12/16.
 */

public class Ejercicio {
    private int idEjercicio;
    private String nombre;
    private String descripcion;
    private String foto;

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ejercicio(int idEjercicio, String nombre, String descripcion, String foto) {
        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }
}
