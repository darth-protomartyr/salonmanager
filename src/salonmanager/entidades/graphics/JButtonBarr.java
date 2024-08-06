package salonmanager.entidades.graphics;

import java.awt.Color;
import salonmanager.entidades.bussiness.Table;
import javax.swing.JButton;

public class JButtonBarr extends JButton {
    String pos;
    int num;
    Table table;
    String text;
    boolean openJBB;
    Color bluStBarr = new Color(2, 82, 67);

    public JButtonBarr() {
        super();
        initialize();
    }

    public JButtonBarr(int num) {
        this.pos = "barra";
        this.num = num;
        this.table = null;
        this.text = pos + " " + num;
        this.openJBB = false;
        initialize();
    }

    public JButtonBarr(String pos, int num, Table table, String text, boolean openJBB) {
        this.pos = pos;
        this.num = num;
        this.table = table;
        this.text = text;
        this.openJBB = openJBB;
        initialize();
    }
    
    private void initialize() {
        this.setForeground(bluStBarr); // Establece el color de texto por defecto
        this.setText(this.text); // Asegura que el texto se establece en el botón
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
