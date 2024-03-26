package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelPpalCustom extends JPanel {
    Color bluSt = new Color(3, 166, 136);
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    
    public PanelPpalCustom(JFrame frame, int hLess) {
        setBackground(bluSt);
        setLayout(null);
        setBounds(0, 0, frame.getWidth(), frame.getHeight() - altoUnit * hLess);
    }
}
