package salonmanager.entidades;

import java.awt.Color;
import javax.swing.JPanel;

public class PanelPpal extends JPanel {
    Color bluSt = new Color(3, 166, 136);

    public PanelPpal(int anchoPanel, int alturaPanel) {
        setBackground(bluSt);
        setLayout(null);
        setVisible(true);
        setBounds(0,0, anchoPanel, alturaPanel);
    }
}
