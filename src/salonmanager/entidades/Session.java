/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gonzalo
 */
public class Session {
    int id;
    User opener;
    User closer;
    boolean stateSession;
    Timestamp openSession;
    Timestamp closeSession;
    ArrayList<Workshift> sessionWorkshifts;
    double totalShift;
    double errorShift;

    public Session() {
    }

    //Crear session
    public Session(User opener) {
        this.opener = opener;
        this.closer = null;
        this.openSession = new Timestamp(new Date().getTime());
        this.closeSession = null;
        this.stateSession = true;
        this.sessionWorkshifts = null;
        this.totalShift = 0;
        this.errorShift = 0;
    }
}
