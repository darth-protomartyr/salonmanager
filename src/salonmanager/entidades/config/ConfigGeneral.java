package salonmanager.entidades.config;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ConfigGeneral {
    int totalTable; //table quantity
    ArrayList<Integer> tableNum; // quantity of tables for pane
    ArrayList<String> tablePan; // name pane for pane
    ArrayList<String> tableItemCaptions;
    ArrayList<String> tablePanCh; // inicial del nombre de cada uno de los sectores  
    boolean activeConfig;

    public ConfigGeneral() {
    }

    public ConfigGeneral(int totalTable, ArrayList<Integer> tableNum, ArrayList<String> tablePan, ArrayList<String> tableItemCaptions, ArrayList<String> tablePanCh, boolean activeConfig) {
        this.totalTable = totalTable;
        this.tableNum = tableNum;
        this.tablePan = tablePan;
        this.tableItemCaptions = tableItemCaptions;
        this.tablePanCh = tablePanCh;
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

    public ArrayList<String> getTableItemCaptions() {
        return tableItemCaptions;
    }

    public void setTableItemCaptions(ArrayList<String> tableItemCaptions) {
        this.tableItemCaptions = tableItemCaptions;
    }

    public ArrayList<String> getTablePanCh() {
        return tablePanCh;
    }

    public void setTablePanCh(ArrayList<String> tablePanCh) {
        this.tablePanCh = tablePanCh;
    }

    public boolean isActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(boolean activeConfig) {
        this.activeConfig = activeConfig;
    }
}