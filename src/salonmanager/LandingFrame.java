package salonmanager;

import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salonmanager.entidades.FrameFull;
import salonmanager.entidades.PanelPpal;

public class LandingFrame extends FrameFull {

    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    SalonManager sm = new SalonManager();
    private int anchoFrame = 0;
    private int alturaFrame = 0;

    public LandingFrame() throws Exception {
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = pantalla.getScreenSize();
        anchoFrame = tamanioPantalla.width;
        alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
        setTitle("Ingreso");
        anchoFrame = anchoFrame;
        alturaFrame = alturaFrame;
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
            public void windowIconified(WindowEvent e) {
                setState(JFrame.NORMAL);
            }
        });

        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);

        JPanel panelTitle = new JPanel();
        panelTitle.setBounds(0, 250, anchoFrame, 200);
        panelTitle.setBackground(bluSt);
        panelTitle.setLayout(null);
        panelPpal.add(panelTitle);

        JLabel labelTitleProgram = new JLabel("SALON MANAGER");
        Font font = labelTitleProgram.getFont();
        Font newFont = font.deriveFont(100f);
        labelTitleProgram.setFont(newFont);
        labelTitleProgram.setBounds(230, 50, anchoFrame - 160, 100);
        panelTitle.add(labelTitleProgram);

        JButton butSign = utiliGraf.button1("Sign In", anchoFrame - 140, 20, 100);
        butSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    new CrearCuenta();
                } catch (Exception ex) {
                    Logger.getLogger(LandingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSign);

        JButton butLog = utiliGraf.button1("Login", anchoFrame - 140, 80, 100);
        butLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    new Ingresar(getJFrame());
                } catch (Exception ex) {
                    Logger.getLogger(LandingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butLog);

        JButton butSalir = utiliGraf.buttonSalir(anchoFrame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
    }

    public JFrame getJFrame() {
        return this;
    }
}
