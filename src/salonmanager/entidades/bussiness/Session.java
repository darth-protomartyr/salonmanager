package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Session {
    int id;
    User opener;
    User closer;
    Timestamp openSession;
    Timestamp closeSession;
    boolean stateSession;
    ArrayList<Workshift> sessionWss;
    double totalSession;
    double errorSession;
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
        this.sessionWss = null;
        this.totalSession = 0;
        this.errorSession = 0;
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

    public ArrayList<Workshift> getSessionWss() {
        return sessionWss;
    }

    public void setSessionWss(ArrayList<Workshift> sessionWss) {
        this.sessionWss = sessionWss;
    }

    public double getTotalSession() {
        return totalSession;
    }

    public void setTotalSession(double totalSession) {
        this.totalSession = totalSession;
    }

    public double getErrorSession() {
        return errorSession;
    }

    public void setErrorSession(double errorSession) {
        this.errorSession = errorSession;
    }

    public boolean isActiveSession() {
        return activeSession;
    }

    public void setActiveSession(boolean activeSession) {
        this.activeSession = activeSession;
    }
}
