package salonmanager.entidades.config;

import java.util.ArrayList;

public class ConfigActual {
    boolean openWs;
    int openIdWs;
    ArrayList<Integer> arrayDeferWs;

    public ConfigActual() {
    }
    
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
    

    public ArrayList<Integer> getArrayDeferWs() {
        return arrayDeferWs;
    }

    public void setArrayDeferWs(ArrayList<Integer> arrayDeferWs) {
        this.arrayDeferWs = arrayDeferWs;
    }
}