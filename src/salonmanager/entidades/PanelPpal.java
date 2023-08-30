/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Gonzalo
 */
public class PanelPpal extends JPanel {
    Color bluSt = new Color(3, 166, 136);
    public PanelPpal(int anchoPanel, int alturaPanel) {
        setBackground(bluSt);
        setLayout(null);
        setVisible(true);
        setBounds(0,0, anchoPanel, alturaPanel);
    }

}
