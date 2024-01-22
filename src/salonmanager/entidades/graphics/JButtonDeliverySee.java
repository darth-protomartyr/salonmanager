package salonmanager.entidades.graphics;

import javax.swing.JButton;
import salonmanager.entidades.bussiness.Delivery;

public class JButtonDeliverySee extends JButton {

    int numDeli;
    Delivery delivery;
    boolean openJBD;

    public JButtonDeliverySee() {
    }

    public JButtonDeliverySee(int num, Delivery delivery) {
        this.numDeli = num;
        this.delivery = delivery;
        this.openJBD = true;
    }

    public int getNumDeli() {
        return numDeli;
    }

    public void setNumDeli(int numDeli) {
        this.numDeli = numDeli;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public boolean isOpenJBD() {
        return openJBD;
    }

    public void setOpenJBD(boolean openJBD) {
        this.openJBD = openJBD;
    }
}
