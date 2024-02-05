package salonmanager.entidades.config;

import java.sql.Timestamp;

public class ConfigActual {
    boolean openWs; //
    int openIdWs;
    boolean openSession;
    int openIdSession;
    Timestamp lastSessionOpen;

    public boolean isOpenWs() {
        return openWs;
    }

    public void setOpenWs(boolean openWs) {
        this.openWs = openWs;
    }

    public int getOpenIdWs() {
        return openIdWs;
    }

    public void setOpenIdWs(int openIdWs) {
        this.openIdWs = openIdWs;
    }

    public boolean isOpenSession() {
        return openSession;
    }

    public void setOpenSession(boolean openSession) {
        this.openSession = openSession;
    }

    public int getOpenIdSession() {
        return openIdSession;
    }

    public void setOpenIdSession(int openIdSession) {
        this.openIdSession = openIdSession;
    }

    public Timestamp getLastSessionOpen() {
        return lastSessionOpen;
    }

    public void setLastSessionOpen(Timestamp lastSessionOpen) {
        this.lastSessionOpen = lastSessionOpen;
    }
}