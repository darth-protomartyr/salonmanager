package salonmanager.entidades;

import java.awt.Dimension;
import java.awt.Toolkit;
import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrameFull extends JFrame {
    
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    protected int anchoFrame = tamanioPantalla.width;
    protected int alturaFrame = tamanioPantalla.height - tamanioPantalla.height/14;
    
    public FrameFull() {
        setBounds(3, 3, anchoFrame, alturaFrame);
        ImageIcon icono = new ImageIcon("menu.png");
        setIconImage(icono.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(FrameFull.this, "¿Estás seguro de que quieres cerrar la ventana?");
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {
                setState(JFrame.NORMAL);
            }
        });
    }
}