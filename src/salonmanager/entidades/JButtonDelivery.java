package salonmanager.entidades;

import javax.swing.JButton;

public class JButtonDelivery extends JButton {
    int width;
    int height;
    int marginW;
    int marginH;
    Barr barr;
    String text;
    boolean openButBarr;

    public JButtonDelivery() {
        this.barr = null;
        this.text = "Barra";
        this.openButBarr = false;
    }

    
}
