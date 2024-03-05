/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager;

import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.entidades.graphics.PanelPpal;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import salonmanager.SalonManager;
import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class IndicationsShower extends FrameWindow{
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOItemcard daoIC = new DAOItemcard();
    ServicioUser si = new ServicioUser();
    SalonManager sm = new SalonManager();
    Color narSt = new Color(217, 103, 4);
    Color bluSt = new Color(1, 64, 64);
    Color narMed = new Color(255, 172, 13);
    Color bluLg = new Color(3, 166, 136);
    Color narUlg = new Color(255, 255, 176);

    
    public IndicationsShower(ItemMonitor im) {
//            public IndicationsShower() {

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluLg);
        panelLabel.setBounds(0, 10, 390, 40);
        panelPpal.add(panelLabel);
        
        JLabel labelTit = utiliGraf.labelTitleBacker1W("Indicaciones del Cliente");
        labelTit.setBounds(10, 10, 200, 20);
        panelLabel.add(labelTit);
     
        JPanel panelText = new JPanel(new GridBagLayout());
        panelText.setBackground(narUlg);

        JLabel labelIndications = new JLabel();
        labelIndications.setText(utili.stringMsgFrd(im.getIndications(), 25, 2));
        Font nuevaFuente = new Font("Arial", Font.BOLD, 14);
        labelIndications.setFont(nuevaFuente);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelText.add(labelIndications, gbc);

        JScrollPane scrollPane = new JScrollPane(panelText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 65, 340, 190);
        panelPpal.add(scrollPane);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
