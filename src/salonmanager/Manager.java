package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import salonmanager.entidades.FrameFull;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class Manager extends FrameFull {
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User userIn = null; 
    
    public Manager (User userIn, String passIn) throws Exception {
        sm.setUserIn(userIn);
        sm.setPassIn(passIn);
        setTitle("Salón Manager");
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);    
        
        JMenuBar menuBar = utiliGraf.navegador(userIn, sm.getPassIn());
        setJMenuBar(menuBar);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(null);
        panelUser.setBounds((anchoFrame - 30 - (anchoFrame / 5)), 15, (anchoFrame - 30) / 5, (alturaFrame - 30) / 6);
        panelUser.setBackground(narLg);
        panelPpal.add(panelUser);

        String route = utili.barrReplaceInverse(userIn.getRouteImage());
        ImageIcon imageIcon = new ImageIcon(route);
        JLabel labelImage = new JLabel(imageIcon);
        int fotM = ((alturaFrame - 30) / 6) - 20;
        labelImage.setBounds(10, 10, fotM, fotM);
        panelUser.add(labelImage);

        JLabel labelName = utiliGraf.labelTitleBacker2(userIn.getNombre());
        labelName.setBounds(130, 20, 120, 40);
        panelUser.add(labelName);

        JLabel labelRol = utiliGraf.labelTitleBacker2(userIn.getRol());
        labelRol.setBounds(130, 20, 120, 120);
        panelUser.add(labelRol);

        JButton butSalir = utiliGraf.buttonSalir(anchoFrame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
    }
}
