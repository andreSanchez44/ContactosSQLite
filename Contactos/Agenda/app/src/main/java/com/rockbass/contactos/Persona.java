package com.rockbass.contactos;

public class Persona {

    public int id;
    public String nombre;
    public String apellidoPaterno, apellidoMaterno;
    public int edad;
    public String telefono;
    public String email;
    public String contactoConfianza;

    public Persona(String nombre,String apellidoPaterno,String apellidoMaterno,int edad, String telefono,String email, String contactoConfianza) {
        this.nombre = nombre;
        this.apellidoMaterno=apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.contactoConfianza=contactoConfianza;
    }

    public Persona(){

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactoConfianza() {
        return contactoConfianza;
    }

    public void setContactoConfianza(String contactoConfianza) {
        this.contactoConfianza = contactoConfianza;
    }
}
