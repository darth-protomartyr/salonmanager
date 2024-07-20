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

    Color whi = new Color(255, 255, 255);
    Color bluSt = new Color(3, 166, 136);
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
        panelTitle.setBounds(anchoUnit * 0, altoUnit * 40, anchoUnit * 110, altoUnit * 16);
        panelTitle.setBackground(bluSt);
        panelPpal.add(panelTitle);

        JLabel labelTitleProgram = new JLabel("SALON MANAGER");
        Font newFont = new Font("Arial", Font.BOLD, 120);
        labelTitleProgram.setFont(newFont);
        labelTitleProgram.setForeground(whi);
        labelTitleProgram.setPreferredSize(new Dimension(anchoUnit * 85, altoUnit * 16));
        panelTitle.add(labelTitleProgram);

        JButtonMetalBlu butSign = utiliGraf.button1("Sign In", anchoUnit * 90, altoUnit * 5, anchoUnit * 8);
        butSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    new TemplateUser(null, null);
                } catch (Exception ex) {
                    Logger.getLogger(LandingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSign);

        JButtonMetalBlu butLog = utiliGraf.button1("Log in",anchoUnit * 90, altoUnit * 13, anchoUnit * 8);
        butLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    new Inn(getJFrame());
                } catch (Exception ex) {
                    Logger.getLogger(LandingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butLog);
        
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

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        panelPpal.add(butSalir);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public JFrame getJFrame() {
        return this;
    }
}