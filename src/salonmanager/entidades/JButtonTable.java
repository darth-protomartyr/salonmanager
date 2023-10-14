package salonmanager.entidades;

import javax.swing.JButton;

public class JButtonTable extends JButton {    
    String pos;
    int num;
    int width;
    int height;
    int marginW;
    int marginH;
    Table table;
    String text;
    boolean openJBT;

    public JButtonTable() {
    }

    public JButtonTable(String pos, int num, int marginW, int marginH, int width, int height) {
        this.pos = pos;
        this.num = num;
        this.marginW = marginW;
        this.marginH = marginH;
        this.width = width;
        this.height = height;
        this.table = null;
        this.text = pos + num;
        this.openJBT = false;
    }

    public JButtonTable(String pos, int num, int marginW, int marginH, int width, int height, Table table, String text, boolean openJBT) {
        this.pos = pos;
        this.num = num;
        this.marginW = marginW;
        this.marginH = marginH;
        this.width = width;
        this.height = height;
        this.table = table;
        this.text = text;
        this.openJBT = openJBT;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMarginW() {
        return marginW;
    }

    public void setMarginW(int marginW) {
        this.marginW = marginW;
    }

    public int getMarginH() {
        return marginH;
    }

    public void setMarginH(int marginH) {
        this.marginH = marginH;
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
    
    public boolean isOpenJBT() {
        return openJBT;
    }

    public void setOpenJBT (boolean ojbt) {
        this.openJBT = ojbt;
    }
    
    
    
    

//    public JButtonTable uttonActionPerformed(JButtonTable jbtAux, User waiter) {
//        JButtonTable jbt = jbtAux;
//        Table table = new Table(jbtAux.getNum(), jbtAux.getPos(), waiter);
//        jbtAux.setTable(table);
//        return jbt;
//    }
}
