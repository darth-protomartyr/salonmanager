package salonmanager.entidades;

import salonmanager.utilidades.Utilidades;

public class User {

    Utilidades utili = new Utilidades();
    String id;
    String name;
    String lastName;
    String mail;
    String rol;
    String routeImage;
    String nameImage;
    String password;
    boolean activeUser;

    public User() {
    }

    public User(String name, String lastName, String mail, String routeImage, String nameImage, String password) {
        this.id = utili.idRandom();
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.rol = "CAJERO";
        this.routeImage = routeImage;
        this.nameImage = nameImage;
        this.password = password;
        this.activeUser = true;
    }

    public User(String id, String name, String lastName, String mail, String rol, String routeImage, String nameImage, String password, boolean activeUser) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.rol = rol;
        this.routeImage = routeImage;
        this.nameImage = nameImage;
        this.password = password;
        this.activeUser = true;
    }

    public User(String id, String name, String lastName, String mail, boolean activeUser) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.rol = "MOZO";
        this.activeUser = activeUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }
}
