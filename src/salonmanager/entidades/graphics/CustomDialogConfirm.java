/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;


public class CustomDialogConfirm extends JDialog {    
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    boolean confirm = false;
    ImageIcon icono = new ImageIcon("menu.png");

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color redLg = new Color(243, 103, 91);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);

    Component component = null;

    public CustomDialogConfirm(String message) {
        setIconImage(icono.getImage());
        setModal(true);
        setTitle("CONFIRME LA ACCIÓN");

        setSize(anchoUnit * 30, altoUnit * 40);
        setLocationRelativeTo(null); // Centrar en la pantalla

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(viol);
        setContentPane(contentPane);

        JPanel panelText = new JPanel();
        panelText.setLayout(new GridBagLayout());
        panelText.setBounds(anchoUnit, altoUnit, anchoUnit * 27, altoUnit * 26);
        Border bordeInterno = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        panelText.setBorder(bordeInterno);
        panelText.setBackground(viol);
        contentPane.add(panelText);
        
        JLabel labelText = new JLabel();
        labelText.setText(utili.stringMsgFrd(message, 25, 2));
        Font nuevaFuente = new Font("Arial", Font.BOLD, 18);
        labelText.setFont(nuevaFuente);
        labelText.setHorizontalAlignment(SwingConstants.CENTER); // Alinea el texto al centro
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        panelText.add(labelText, constraints);

        JButtonMetalBlu rejectButton = utiliGraf.button2("Rechazar", anchoUnit * 3, altoUnit * 28, anchoUnit * 10);
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    confirm = false;
                    dispose();
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        contentPane.add(rejectButton);

        JButtonMetalBlu acceptButton = utiliGraf.button2("Aceptar", anchoUnit * 16, altoUnit * 28, anchoUnit * 10);
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    confirm = true;
                    dispose();
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        contentPane.add(acceptButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        }
        );
    }

    public boolean getConfirm() {
        return confirm;
    }
}
