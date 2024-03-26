package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {
    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color narUlgX = new Color(255, 255, 210);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);
    
    
    private Color tabAreaBackground = bluSt;
    private Color selectedTabColor = narLg;
    private Font tabFont = new Font("Arial", Font.BOLD, 18); // Fuente personalizada
    private int tabShape = 0; // Forma predeterminada de las pestañas

    public static ComponentUI createUI(JComponent c) {
        return new CustomTabbedPaneUI();
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        // Cambiar el color del área de las pestañas
        tabPane.setBackground(tabAreaBackground);
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Cambiar el color de la pestaña seleccionada
        if (isSelected) {
            g.setColor(selectedTabColor);
            g.fillRect(x, y, w, h);
        }
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        // Cambiar la fuente del texto en las pestañas
        tabPane.setFont(tabFont);
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Cambiar la forma de las pestañas
        if (tabShape == 1) {
            g.setColor(narLg);
            g.drawLine(x, y, x + w, y); // Línea superior
            g.drawLine(x, y, x, y + h); // Línea izquierda
            g.drawLine(x + w, y, x + w, y + h); // Línea derecha
        } else {
            super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
        }
    }

    public void setTabFont(Font font) {
        this.tabFont = font;
        if (tabPane != null) {
            tabPane.setFont(font);
        }
    }

    public void setTabAreaBackground(Color color) {
        this.tabAreaBackground = color;
        if (tabPane != null) {
            tabPane.setBackground(color);
        }
    }

    public void setSelectedTabColor(Color color) {
        this.selectedTabColor = color;
        if (tabPane != null) {
            tabPane.repaint();
        }
    }

    public void setTabShape(int shape) {
        this.tabShape = shape;
        if (tabPane != null) {
            tabPane.repaint();
        }
    }
}

