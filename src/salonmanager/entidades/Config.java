package salonmanager.entidades;
import java.util.ArrayList;

public class Config {
    int totalTable;
    ArrayList<Integer> tableNum;
    ArrayList<String> tablePan;
    ArrayList<String> tablePanCh;

    public Config() {
    }
   
    public Config(int totalTable, ArrayList<Integer> tableNum, ArrayList<String> tablePan, ArrayList<String>tablePanCh) {
        this.totalTable = totalTable;
        this.tableNum = tableNum;
        this.tablePan = tablePan;
        this.tablePanCh = tablePanCh;
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
}
