package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import salonmanager.entidades.FrameFullManager;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.entidades.Workshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class Manager extends FrameFullManager {
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User userIn = null;
    Workshift actualShift = null;
    
    public Manager (User userIn, String passIn) throws Exception {
        sm.addFrame(this);
        sm.setUserIn(userIn);
        sm.setPassIn(passIn);
        setTitle("Salón Manager");
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);
        
        JMenuBar menuBar = utiliGraf.navegador(userIn, sm.getPassIn(), this);
        setJMenuBar(menuBar);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(null);
        panelUser.setBounds(anchoUnit * 87, altoUnit, anchoUnit * 17, altoUnit * 17);
        panelUser.setBackground(narSt);
        panelPpal.add(panelUser);

        String route = utili.barrReplaceInverse(userIn.getRouteImage());
        ImageIcon imageIcon = new ImageIcon(route);
        JLabel labelImage = new JLabel(imageIcon);
        labelImage.setBounds(altoUnit, altoUnit , anchoUnit *8, anchoUnit * 8);
        panelUser.add(labelImage);

        JLabel labelName = utiliGraf.labelTitleBacker2(userIn.getName());
        labelName.setBounds(130, 20, 120, 40);
        panelUser.add(labelName);

        JLabel labelRol = utiliGraf.labelTitleBacker2(userIn.getRol());
        labelRol.setBounds(130, 20, 120, 120);
        panelUser.add(labelRol);
        
        JPanel panelSession = new JPanel();
        panelSession.setLayout(null);
        panelSession.setBackground(bluLg);
        panelSession.setBounds(anchoUnit * 54, altoUnit * 21, anchoUnit * 50, altoUnit * 65);
        panelPpal.add(panelSession);

        JLabel labelActualSession = utiliGraf.labelTitleBackerA4("Session Actual");
        labelActualSession.setBounds(altoUnit, altoUnit, anchoUnit * 25, altoUnit * 10);
        panelSession.add(labelActualSession);
        
        JLabel labelActualShift = utiliGraf.labelTitleBacker1("");
        labelActualShift.setBounds(altoUnit, altoUnit * 14, anchoUnit * 25, altoUnit * 50);
        if (actualShift != null) {
            labelActualShift.setText("Te encuentras con una sesión activa \ny un turno abierto a tu nombre.");
        } else {
            labelActualShift.setText("No hay ningún turno activo.");
        }
        panelSession.add(labelActualShift);

        JButton butSalir = utiliGraf.buttonSalir2(anchoFrame, alturaFrame - 90);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sm.frameCloser();
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog( Manager.this ,"¿Estás seguro de que quieres cerrar el programa?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    sm.frameCloser();
                    dispose();   
                }
            }
        });
    }
    
    public Workshift getActualWorkShift() {
        return actualShift;
    }
}