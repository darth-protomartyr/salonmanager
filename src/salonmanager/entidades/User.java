package salonmanager.entidades;

import salonmanager.utilidades.Utilidades;

public class User {

    Utilidades utili = new Utilidades();
    String id;
    String nombre;
    String apellido;
    String mail;
    String rol;
    String routeImage;
    String nameImage;
    String password;
    boolean alta;

    public User() {

    }

    public User(String nombre, String apellido, String mail, String routeImage, String nameImage, String password) {
        this.id = utili.idRandom();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.rol = "CAJERO";
        this.routeImage = routeImage;
        this.nameImage = nameImage;
        this.password = password;
        this.alta = true;
    }

    public User(String id, String nombre, String apellido, String mail, String rol, String routeImage, String nameImage, String password, boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.rol = rol;
        this.routeImage = routeImage;
        this.nameImage = nameImage;
        this.password = password;
        this.alta = true;
    }

    public User(String id, String nombre, String apellido, String mail, boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.rol = "MOZO";
        this.alta = alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRouteImage() {
        return routeImage;
    }

    public void setRouteImage(String routeImage) {
        this.routeImage = routeImage;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
}
