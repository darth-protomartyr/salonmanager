package salonmanager.entidades.graphics;

import salonmanager.entidades.bussiness.Table;
import javax.swing.JButton;
import salonmanager.entidades.bussiness.Delivery;

public class JButtonDelivery extends JButton {

    String pos;
    int num;
    Table table;
    String text;
    boolean openJBD;
    Delivery delivery;
    

    public JButtonDelivery() {
    }

    public JButtonDelivery(int num, Delivery delivery) {
        this.pos = "delivery";
        this.num = num;
        this.table = null;
        this.text = pos + " " + num;
        this.openJBD = false;
        this.delivery = delivery;
    }

    public JButtonDelivery(String pos, int num, Table table, String text, boolean openJBB, Delivery delivery) {
        this.pos = pos;
        this.num = num;
        this.table = table;
        this.text = text;
        this.openJBD = openJBB;
        this.delivery = delivery;
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
    
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

}
