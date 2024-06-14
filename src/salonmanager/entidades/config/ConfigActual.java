package salonmanager.entidades.config;
import java.util.ArrayList;

public class ConfigActual {
    boolean openWs;
    int openIdWs;
    ArrayList<String> arrayDeferWs;
    ArrayList<String> arrayUnModTabs;

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
    
    public ArrayList<String> getArrayDeferWs() {
        return arrayDeferWs;
    }

    public void setArrayDeferWs(ArrayList<String> arrayDeferWs) {
        this.arrayDeferWs = arrayDeferWs;
    }

    public ArrayList<String> getArrayUnModTabs() {
        return arrayUnModTabs;
    }

    public void setArrayUnModTabs(ArrayList<String> arrayUnModTabs) {
        this.arrayUnModTabs = arrayUnModTabs;
    }
}