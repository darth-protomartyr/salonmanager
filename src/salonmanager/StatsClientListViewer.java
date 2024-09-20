/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.StaticsManager;
import salonmanager.entidades.bussiness.Itemcard;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

/**
 *
 * @author Gonzalo
 */
public class StatsClientListViewer extends FrameThird {
    DAODeliveryClient daoDC = new DAODeliveryClient();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Color bluLg = new Color(194, 242, 206);
    Color narUlg = new Color(255, 255, 176);
    SalonManager sm = new SalonManager();
    Itemcard itemAux = null;

    
    
    public StatsClientListViewer(StaticsManager statsM, HashMap<Integer, Double> countClientBuys) throws Exception {
        ArrayList<Integer> clientsBuys1 = new ArrayList<>(countClientBuys.keySet());
        ArrayList<String> clientsBuys2 = new ArrayList<>();
        ArrayList<Double> amounts3 = new ArrayList<>(countClientBuys.values());
        ArrayList<Double> amounts4 = new ArrayList<>();
        
        sm.addFrame(this);
        setTitle("Clientes Por Volumen de Venta");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Lista de Clientes por");
        labelTit.setBounds(anchoUnit * 2, altoUnit * 1, anchoUnit * 28, altoUnit * 4);
        panelPpal.add(labelTit);
        
        JLabel labelTit2 = utiliGraf.labelTitleBackerA4W("Volumen de Venta");
        labelTit2.setBounds(anchoUnit * 2, altoUnit * 5, anchoUnit * 28, altoUnit * 4);
        panelPpal.add(labelTit2);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelB = new JPanel();
        panelB.setLayout(null);
        int heightP = countClientBuys.size() * 5;
        if (heightP < 81) {
            heightP = 80;
        }
        panelB.setPreferredSize(new Dimension (anchoUnit * 24, altoUnit * 80));
        panelB.setBackground(bluLg);

        int size = clientsBuys1.size();
        if (size < 15) {
            size = 15;
        }
        

        for (int i = 0; i < size; i++) {
            String name = "--";
            double mount = 00;
            if (i < clientsBuys1.size()) {
                name = daoDC.getClientNameById(clientsBuys1.get(i));
                mount = amounts3.get(i);
            } else {
                clientsBuys2.add("--");
                amounts4.add(0.0);
            }
            
            String st = i + 1 + "- " + name + " $: " + mount;                      
            JLabel label = utiliGraf.labelTitleBacker1(st);
            label.setBounds(anchoUnit * 4, altoUnit * ((i * 5) + 1) , anchoUnit * 24, altoUnit * 4);
            panelB.add(label);
        }         

        JScrollPane scrollPane = new JScrollPane(panelB);
        scrollPane.setBounds(anchoUnit * 2, altoUnit * 11, anchoUnit * 30, altoUnit * 80);
        scrollPane.setBackground(narUlg);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
