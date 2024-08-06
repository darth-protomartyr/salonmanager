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

public class JButtonMetalBlu extends JButton{
    Color bluSSt = new Color(4, 97, 80);
    public JButtonMetalBlu() {
        setUI(new MetalButtonUI() {
            protected Color getDisabledTextColor() {
                return Color.WHITE;
            }

            protected Color getDisabledBorderColor() {
                return Color.GRAY; 
            }

            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                AbstractButton button = (AbstractButton) c;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                ButtonModel model = button.getModel();

                if (model.isArmed()) {
                    g2.setColor(new Color(4, 77, 60));
                } else if (model.isRollover()) {
                    g2.setColor(new Color(4, 127, 110));
                } else {
                    g2.setColor(new Color(4, 107, 90));
                }

                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);

                g2.setColor(Color.WHITE);

                FontMetrics fm = g2.getFontMetrics();
                Rectangle textRect = fm.getStringBounds(button.getText(), g2).getBounds();
                int textX = (c.getWidth() - textRect.width) / 2;
                int textY = (c.getHeight() - textRect.height) / 2 + fm.getMaxAscent();

                g2.setColor(Color.WHITE);
                g2.drawString(button.getText(), textX, textY);

                g2.dispose();
            }
        });
        setContentAreaFilled(false);
        setFocusPainted(false);
    }
}
