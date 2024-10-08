package salonmanager.entidades.config;
import java.util.ArrayList;

public class ConfigGeneral {
    int totalTable; //table quantity
    ArrayList<Integer> tableNum; // quantity of tables for pane
    ArrayList<String> tablePan; // name pane for pane
    ArrayList<String> tablePanCh; // inicial del nombre de cada uno de los sectores
    ArrayList<String> tableItemCategories;
    boolean activeConfig;
    int tipPc;

    public ConfigGeneral() {
    }

    public ConfigGeneral(int totalTable, ArrayList<Integer> tableNum, ArrayList<String> tablePan, ArrayList<String> tablePanCh, ArrayList<String> tableItemCategories, int tipPc, boolean activeConfig) {
        this.totalTable = totalTable;
        this.tableNum = tableNum;
        this.tablePan = tablePan;
        this.tablePanCh = tablePanCh;
        this.tableItemCategories = tableItemCategories;
        this.tipPc = tipPc;
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
    
    public ArrayList<String> getTableItemCategories() {
        return tableItemCategories;
    }

    public void setTableItemCategories(ArrayList<String> tableItemCategories) {
        this.tableItemCategories = tableItemCategories;
    }
    
    public int getTipPc() {
        return tipPc;
    }

    public void setTipPc(int tipPc) {
        this.tipPc = tipPc;
    }

    public boolean isActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(boolean activeConfig) {
        this.activeConfig = activeConfig;
    } 
    
}