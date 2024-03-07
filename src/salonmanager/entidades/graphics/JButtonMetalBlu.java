/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 *
 * @author Gonzalo
 */
public class JButtonMetalBlu extends JButton{
    Color bluSSt = new Color(1, 54, 62);
    public JButtonMetalBlu () {
        setUI(new MetalButtonUI() {
            protected Color getDisabledTextColor() {
                return Color.WHITE; // Cambia el color del texto cuando el botón está deshabilitado
            }

            protected Color getDisabledBorderColor() {
                return Color.GRAY; // Cambia el color del borde cuando el botón está deshabilitado
            }

            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                AbstractButton button = (AbstractButton) c;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                ButtonModel model = button.getModel();

                if (model.isArmed()) {
                    g2.setColor(new Color(4, 225, 179)); // Color cuando se presiona el botón
                } else if (model.isRollover()) {
                    g2.setColor(new Color(7, 255, 239)); // Color cuando el ratón pasa por encima del botón
                } else {
                    g2.setColor(new Color(5, 255, 209)); // Color normal del botón
                }

                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10); // Dibuja el botón redondeado

//                g2.setColor(Color.WHITE);
//                g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 10, 10); // Dibuja el borde del botón

                FontMetrics fm = g2.getFontMetrics();
                Rectangle textRect = fm.getStringBounds(button.getText(), g2).getBounds();
                int textX = (c.getWidth() - textRect.width) / 2;
                int textY = (c.getHeight() - textRect.height) / 2 + fm.getMaxAscent();

                g2.setColor(bluSSt);
                g2.drawString(button.getText(), textX, textY); // Dibuja el texto centrado en el botón

                g2.dispose();
            }
        });
        setContentAreaFilled(false); // Hace que el área interior del botón sea transparente
        setForeground(Color.WHITE); // Cambia el color del texto del botón
        setFocusPainted(false); // Elimina el resaltado del botón cuando tiene el foco
    }
}
