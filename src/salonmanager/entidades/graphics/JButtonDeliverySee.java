package salonmanager.entidades.graphics;

import java.awt.Color;
import javax.swing.JButton;
import salonmanager.entidades.bussiness.Delivery;

public class JButtonDeliverySee extends JButton {
    Color bluStBarr = new Color(2, 82, 67);
    int numDeli;
    Delivery delivery;
    boolean openJBD;

    public JButtonDeliverySee() {
        super();
        initialize();
    }

    public JButtonDeliverySee(int num, Delivery delivery) {
        this.numDeli = num;
        this.delivery = delivery;
        this.openJBD = true;
        initialize();
    }
    
    private void initialize() {
        this.setForeground(bluStBarr); // Establece el color de texto por defecto
//        this.setText(this.text); // Asegura que el texto se establece en el botón
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
