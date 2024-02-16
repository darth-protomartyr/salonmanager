package salonmanager.entidades.config;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ConfigActual {
    boolean openWs;
    int openIdWs;
    boolean openSession;
    int openIdSession;
    Timestamp lastSessionOpen;
    ArrayList<Integer> arrayDeferWs;

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

    public ArrayList<Integer> getArrayDeferWs() {
        return arrayDeferWs;
    }

    public void setArrayDeferWs(ArrayList<Integer> arrayDeferWs) {
        this.arrayDeferWs = arrayDeferWs;
    }
    
    
}