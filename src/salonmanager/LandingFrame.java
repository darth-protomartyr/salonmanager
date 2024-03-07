package salonmanager;

import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.FrameGeneral;
import salonmanager.entidades.graphics.JButtonMetalBlu;

public class LandingFrame extends FrameGeneral {

    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();

    public LandingFrame() {
        setBounds(0, 0, anchoFrame, alturaFrame);
        setTitle("Ingreso");
        JPanel panelPpal = new JPanel();
        panelPpal.setBounds(0, 0, anchoFrame, alturaFrame);
        panelPpal.setBackground(bluSt);
        panelPpal.setLayout(null);
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

        JButtonMetalBlu butSign = utiliGraf.button1("Sign In", anchoFrame - 140, 20, 100);
        butSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    new CreateAccount();
                } catch (Exception ex) {
                    Logger.getLogger(LandingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSign);

        JButtonMetalBlu butLog = utiliGraf.button1("Login", anchoFrame - 140, 80, 100);
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

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(anchoFrame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
        
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setVisible(true);

        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public JFrame getJFrame() {
        return this;
    }
}