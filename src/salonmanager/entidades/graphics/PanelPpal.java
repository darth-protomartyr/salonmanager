package salonmanager.entidades.graphics;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelPpal extends JPanel {
    Color bluSt = new Color(3, 166, 136);

    
    public PanelPpal(JFrame frame) {
        setBackground(bluSt);
        setLayout(null);
        setSize(frame.getWidth(), frame.getHeight());
    }
}
