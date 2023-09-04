/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import salonmanager.utilidades.Utilidades;
import java.awt.Image;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Gonzalo
 */
public class Register {

    int id;
    Timestamp ejecution;
    String user;
    String userModify;
    String operation;
    String object;
    String modification;

    public Register() {
    }

    //Completo consulta
    public Register(int id, Timestamp ejecution, String user, String userModify, String operation, String object, String modification) {
        this.id = id;
        this.ejecution = ejecution;
        this.user = user;
        this.userModify = userModify;
        this.operation = operation;
        this.object = object;
        this.modification = modification;
    }

//    //Dar inicio en los daos
//    public Register(String userModify, String operation, String object, String modification) {
//        this.ejecution = new Timestamp(new Date().getTime());
//        this.userModify = userModify;
//        this.userModify = userModify;
//        this.operation = operation;
//        this.object = object;
//        this.modification = modification;
//    }
    public Register(String user, String operation, String object, String modification) {
        this.ejecution = new Timestamp(new Date().getTime());;
        this.user = user;
        this.userModify = "";
        this.operation = operation;
        this.object = object;
        this.modification = modification;
    }

    //Agregar User en DAORegister
    public Register(String user, String userModify, String operation, String object, String modification) {
        this.ejecution = new Timestamp(new Date().getTime());;
        this.user = user;
        this.userModify = userModify;
        this.operation = operation;
        this.object = object;
        this.modification = modification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getEjecution() {
        return ejecution;
    }

    public void setEjecution(Timestamp ejecution) {
        this.ejecution = ejecution;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserModify() {
        return userModify;
    }

    public void setUserModify(String userModify) {
        this.userModify = userModify;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

}
