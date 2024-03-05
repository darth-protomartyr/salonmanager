package salonmanager.entidades.graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FrameGeneral extends JFrame {

    static protected int anchoFrame;
    static protected int alturaFrame;
    static protected int anchoUnit;
    static protected int altoUnit;
    protected JFrame frame = null;

    public FrameGeneral() {
        frame = this;
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = pantalla.getScreenSize();
        anchoFrame = tamanioPantalla.width;
        alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
        anchoUnit = anchoFrame / 100;
        altoUnit = alturaFrame / 100;
        ImageIcon icono = new ImageIcon("menu.png");
        setIconImage(icono.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
    }

    public int getAnchoFrame() {
        return anchoFrame;
    }

    public void setAnchoFrame(int anchoFrame) {
        this.anchoFrame = anchoFrame;
    }

    public int getAlturaFrame() {
        return alturaFrame;
    }

    public void setAlturaFrame(int alturaFrame) {
        this.alturaFrame = alturaFrame;
    }
}
