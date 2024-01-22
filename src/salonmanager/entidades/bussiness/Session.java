/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.bussiness;

import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.bussiness.User;
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
    Timestamp openSession;
    Timestamp closeSession;
    boolean stateSession;
    ArrayList<Workshift> sessionWorkshifts;
    double totalShift;
    double errorShift;
    boolean activeSession;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOpener() {
        return opener;
    }

    public void setOpener(User opener) {
        this.opener = opener;
    }

    public User getCloser() {
        return closer;
    }

    public void setCloser(User closer) {
        this.closer = closer;
    }

    public Timestamp getOpenSession() {
        return openSession;
    }

    public void setOpenSession(Timestamp openSession) {
        this.openSession = openSession;
    }

    public Timestamp getCloseSession() {
        return closeSession;
    }

    public void setCloseSession(Timestamp closeSession) {
        this.closeSession = closeSession;
    }

    public boolean isStateSession() {
        return stateSession;
    }

    public void setStateSession(boolean stateSession) {
        this.stateSession = stateSession;
    }

    public ArrayList<Workshift> getSessionWorkshifts() {
        return sessionWorkshifts;
    }

    public void setSessionWorkshifts(ArrayList<Workshift> sessionWorkshifts) {
        this.sessionWorkshifts = sessionWorkshifts;
    }

    public double getTotalShift() {
        return totalShift;
    }

    public void setTotalShift(double totalShift) {
        this.totalShift = totalShift;
    }

    public double getErrorShift() {
        return errorShift;
    }

    public void setErrorShift(double errorShift) {
        this.errorShift = errorShift;
    }

    public boolean isActiveSession() {
        return activeSession;
    }

    public void setActiveSession(boolean activeSession) {
        this.activeSession = activeSession;
    }

}
