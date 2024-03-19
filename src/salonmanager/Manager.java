package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Manager extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User user = null;
    String pass;
    Workshift actualWs = null;
    DAOConfig daoC = new DAOConfig();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOUser daoU = new DAOUser();
    ConfigActual cfgAct = new ConfigActual();
    Salon salon = null;

    public Manager(User userIn, String passIn) throws Exception {
        sm.addFrame(this);
        cfgAct = daoC.askConfigActual();
        if (cfgAct.isOpenWs()) {
            actualWs = daoW.askWorshiftById(cfgAct.getOpenIdWs());
            actualWs.setCashierWs(daoU.getCashierByWorkshift(actualWs.getId()));
        }

        user = userIn;
        pass = passIn;
        setTitle("Salón Manager");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JMenuBar menuBar = utiliGraf.navegador(userIn, passIn, this);
        setJMenuBar(menuBar);

        JLabel labelLegal = utiliGraf.labelLegal(anchoFrame, alturaFrame, 1, 70);
        panelPpal.add(labelLegal);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(null);
        panelUser.setBounds(anchoUnit * 87, altoUnit, anchoUnit * 17, altoUnit * 17);
        panelUser.setBackground(narSt);
        panelPpal.add(panelUser);

        String route = utili.barrReplaceInverse(userIn.getRouteImage());
        ImageIcon imageIcon = new ImageIcon(route);
        JLabel labelImage = new JLabel(imageIcon);
        labelImage.setBounds(altoUnit, altoUnit, anchoUnit * 8, anchoUnit * 8);
        panelUser.add(labelImage);

        JLabel labelName = utiliGraf.labelTitleBacker2(userIn.getName());
        labelName.setBounds(130, 20, 120, 40);
        panelUser.add(labelName);

        JLabel labelRol = utiliGraf.labelTitleBacker2(userIn.getRol());
        labelRol.setBounds(130, 20, 120, 120);
        panelUser.add(labelRol);

        JPanel panelWorkshift = new JPanel();
        panelWorkshift.setLayout(null);
        panelWorkshift.setBackground(bluLg);
        panelWorkshift.setBounds(anchoUnit * 54, altoUnit * 21, anchoUnit * 50, altoUnit * 65);
        panelPpal.add(panelWorkshift);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir2(frame, 3);
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
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    sm.frameCloser();
                    dispose();
                }
            }
        });
    }

    public void salonFrameManager(ArrayList<Table> tabs, ConfigActual cfgAct) throws Exception {
        Manager man = this;
        if (salon == null) {
            salon = new Salon(tabs, man, cfgAct);
        } else {
            salon.setVisible(true);
            salon.toFront();
        }
    }

    public Workshift getActualWorkShift() {
        return actualWs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userAux) {
        this.user = userAux;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String passAux) {
        this.pass = passAux;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
