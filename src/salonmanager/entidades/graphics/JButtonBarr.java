package salonmanager.entidades.graphics;

import salonmanager.entidades.bussiness.Table;
import javax.swing.JButton;

public class JButtonBarr extends JButton {
    String pos;
    int num;
    Table table;
    String text;
    boolean openJBB;

    public JButtonBarr() {
    }

    public JButtonBarr(int num) {
        this.pos = "barra";
        this.num = num;
        this.table = null;
        this.text = pos + " " + num;
        this.openJBB = false;
    }

    public JButtonBarr(String pos, int num, Table table, String text, boolean openJBB) {
        this.pos = pos;
        this.num = num;
        this.table = table;
        this.text = text;
        this.openJBB = openJBB;
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

    public boolean isOpenJBB() {
        return openJBB;
    }

    public void setOpenJBB(boolean openJBB) {
        this.openJBB = openJBB;
    }
}
