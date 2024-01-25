/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.config.Config;
import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.servicios.ServicioSession;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class WorkshiftSession extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    ServicioSession sses = new ServicioSession();
    SalonManager sm = new SalonManager();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    Manager manager = null;
    Config cfg = new Config();

    User user = new User();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

//    Salon salon = null;
    public WorkshiftSession(Manager man) throws Exception {
        manager = man;
        sm.addFrame(this);
        user = man.getUser();

        cfg = sm.getConfig();
        setTitle("Administrar Turnos Y Sesiones");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        panelPpal.setBackground(bluSt);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);

        JLabel labelSalon = utiliGraf.labelTitleBackerA4("Administrador del Salón");
        panelLabel.add(labelSalon);

        JPanel panelSession = new JPanel();
        panelSession.setLayout(null);
        panelSession.setBackground(bluLg);
        panelSession.setBounds(10, 50, 390 - 27, 210);
        panelPpal.add(panelSession);

        JLabel labelSession = utiliGraf.labelTitleBackerA4("SESIÓN");
        labelSession.setBounds(15, 15, 100, 25);
        panelSession.add(labelSession);

        JLabel labelOpenSession = utiliGraf.labelTitleBacker2("");
        labelOpenSession.setBounds(15, 60, 350, 25);
        panelSession.add(labelOpenSession);

        JButton butSession = utiliGraf.button1("", 110, 110, 150);
        butSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    inSalon();
                } catch (Exception ex) {
                    Logger.getLogger(WorkshiftSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSession.add(butSession);

        if (cfg.isOpenSession()) {
            labelOpenSession.setText("Hay una sesión abierta el " + cfg.getLastSession());
            butSession.setText("Cerrar Sesión");
        } else {
            labelOpenSession.setText("No hay una sesión abierta actualmente.");
            butSession.setText("Abrir Sesión");
        }

        JLabel labelOpenWs = utiliGraf.labelTitleBacker2("");
        labelOpenWs.setBounds(15, 175, 350, 25);
        panelSession.add(labelOpenWs);

        labelOpenWs.setText("TURNO: Hay un turno abierto.");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void inSalon() throws Exception {
        if (cfg.isOpenSession() && cfg.isOpenWs()) {
            int wsId = cfg.getOpenIdWs();
            Workshift ws = daoW.consultarTurnoById(wsId);
            ArrayList<Table> tabs = daoT.listarTablesByTimestamp(ws);
            new Salon(tabs, manager);
            dispose();
        } else {
            sses.crearSession(user);
            new Salon(null, manager);
            dispose();
        }
    }
}
