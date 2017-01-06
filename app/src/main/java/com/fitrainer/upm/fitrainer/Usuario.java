package com.fitrainer.upm.fitrainer;

/**
 * Created by abel on 25/12/16.
 */

public class Usuario {
    private int idUsuario;
    private String nickname;
    private String nombre;
    private String email;
    private String contrasenia;
    private int edad;
    private double peso;
    private double altura;
    private boolean sexo;
    private boolean esEntrenador;

    public Usuario(int idUsuario, String nickname, String nombre, String email, String contrasenia, int edad, double peso, double altura, Boolean sexo, boolean esEntrenador) {
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.esEntrenador = esEntrenador;
    }

    public Usuario(){}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public boolean isEsEntrenador() {
        return esEntrenador;
    }

    public void setEsEntrenador(boolean esEntrenador) {
        this.esEntrenador = esEntrenador;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }
}
