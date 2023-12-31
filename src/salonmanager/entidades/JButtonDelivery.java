package salonmanager.entidades;

import javax.swing.JButton;

public class JButtonDelivery extends JButton {

    String pos;
    int num;
    Table table;
    String text;
    boolean openJBD;

    public JButtonDelivery() {
    }

    public JButtonDelivery(int num) {
        this.pos = "delivery";
        this.num = num;
        this.table = null;
        this.text = pos + " " + num;
        this.openJBD = false;
    }

    public JButtonDelivery(String pos, int num, Table table, String text, boolean openJBB) {
        this.pos = pos;
        this.num = num;
        this.table = table;
        this.text = text;
        this.openJBD = openJBB;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOpenJBD() {
        return openJBD;
    }

    public void setOpenJBD(boolean openJBB) {
        this.openJBD = openJBB;
    }

    
    
}
