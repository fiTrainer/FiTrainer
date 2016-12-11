package com.fitrainer.upm.fitrainer.ListadoDietas;

/**
 * Created by abel on 10/12/16.
 */

public class ListaEntrada {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
    private Boolean marcado=false;

    public ListaEntrada(int idImagen, String textoEncima, String textoDebajo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public ListaEntrada(Boolean marcado, String textoEncima, String textoDebajo) {
        this.marcado = marcado;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }

    public boolean get_marcado(){
        return this.marcado;
    }
}
