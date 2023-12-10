package salonmanager.entidades;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Config {
    int totalTable; //table quantity
    ArrayList<Integer> tableNum; // quantity of tables for pane
    ArrayList<String> tablePan; // name pane for pane
    ArrayList<String> tablePanCh; // inicial del nombre de cada uno de los sectores  
    boolean openWs; //
    int openIdWs;
    boolean openSession;
    int openIdSession;
    Timestamp lastSession; 
    boolean activeConfig;

    public Config() {
    }

    public Config(int totalTable, ArrayList<Integer> tableNum, ArrayList<String> tablePan, ArrayList<String> tablePanCh, boolean openWs, int openIdWs, boolean openSession, int openIdSession, Timestamp lastSession, boolean activeConfig) {
        this.totalTable = totalTable;
        this.tableNum = tableNum;
        this.tablePan = tablePan;
        this.tablePanCh = tablePanCh;
        this.openWs = false;
        this.openIdWs = 0;
        this.openSession = false;
        this.openIdSession = 0;
        this.lastSession = null;
        this.activeConfig = true;
    }

    public int getTotalTable() {
        return totalTable;
    }

    public void setTotalTable(int totalTable) {
        this.totalTable = totalTable;
    }

    public ArrayList<Integer> getTableNum() {
        return tableNum;
    }

    public void setTableNum(ArrayList<Integer> tableNum) {
        this.tableNum = tableNum;
    }

    public ArrayList<String> getTablePan() {
        return tablePan;
    }

    public void setTablePan(ArrayList<String> tablePan) {
        this.tablePan = tablePan;
    }

    public ArrayList<String> getTablePanCh() {
        return tablePanCh;
    }

    public void setTablePanCh(ArrayList<String> tablePanCh) {
        this.tablePanCh = tablePanCh;
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

    public Timestamp getLastSession() {
        return lastSession;
    }

    public void setLastSession(Timestamp lastSession) {
        this.lastSession = lastSession;
    }

    public boolean isActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(boolean activeConfig) {
        this.activeConfig = activeConfig;
    }
    
    
    


}