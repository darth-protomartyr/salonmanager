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
import static javax.swing.SwingConstants.CENTER;
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
        panelTitle.setBounds(anchoUnit * 0, altoUnit * 20, anchoFrame, altoUnit * 39);
        panelTitle.setBackground(bluSt);
        panelPpal.add(panelTitle);

        String fontName1 = "Arial";
        String fontName2 = "Tahoma";
        int fontSize1 = altoUnit * 28;
        String colorHex1 = "#FFFFFF"; // Color en formato hexadecimal
        String colorHex2 = "#FCCB05"; // Color en formato hexadecimal

        String text = "<html><span style='font-family: " + fontName1 + "; font-weight: bold; font-size: " + fontSize1 + "px; color: " + colorHex1 + ";'>bar</span>"
                + "<span style='font-family: " + fontName2 + "; font-weight: bold; font-size: " + fontSize1 + "px; color: " + colorHex2 + ";'>IA</span></html>";
        JLabel labelTitleProgram = new JLabel(text);
        labelTitleProgram.setBounds(anchoUnit * 0, altoUnit * 0, anchoFrame, altoUnit * 39);
        panelTitle.add(labelTitleProgram);
        
        JPanel panelSubtitle = new JPanel();
        panelSubtitle.setBounds(anchoUnit * 0, altoUnit * 60, anchoFrame, altoUnit * 8);
        panelSubtitle.setBackground(bluSt);
        panelPpal.add(panelSubtitle);

        JLabel labelSTitleProgram = new JLabel("Software para Locales Gastronómicos");
        Font newFont3 = new Font("Lucida Sans", Font.BOLD, 40);
        labelSTitleProgram.setFont(newFont3);
        labelSTitleProgram.setHorizontalAlignment(CENTER);
        labelSTitleProgram.setForeground(whi);
        labelSTitleProgram.setPreferredSize(new Dimension(anchoFrame, altoUnit * 6));
        panelSubtitle.add(labelSTitleProgram);

        JPanel panelSoft = new JPanel();
        panelSoft.setBounds(anchoUnit * 0, altoUnit * 70, anchoFrame, altoUnit * 5);
        panelSoft.setBackground(bluSt);
        panelPpal.add(panelSoft);

        JLabel labelSTitleSoft = new JLabel("by NAXOFT");
        Font newFont4 = new Font("Arial", Font.BOLD, anchoUnit * 2);
        labelSTitleSoft.setFont(newFont4);
        labelSTitleSoft.setHorizontalAlignment(CENTER);
        labelSTitleSoft.setForeground(whi);
        labelSTitleSoft.setPreferredSize(new Dimension(anchoFrame, altoUnit * 5));
        panelSoft.add(labelSTitleSoft);

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

        JButtonMetalBlu butLog = utiliGraf.button1("Log in", anchoUnit * 90, altoUnit * 13, anchoUnit * 8);
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
