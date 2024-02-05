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
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.Session;
import salonmanager.entidades.graphics.FrameFullManager;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOSession;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Manager extends FrameFullManager {

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
    Session actualSess = null;
    Workshift actualWs = null;
    DAOConfig daoC = new DAOConfig();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOSession daoS = new DAOSession();
    DAOUser daoU = new DAOUser();
    ConfigActual cfgAct = new ConfigActual();

    public Manager(User userIn, String passIn) throws Exception {
        sm.addFrame(this);
        cfgAct = daoC.askConfigActual();
        if (cfgAct.isOpenSession()) {
            actualSess = daoS.askSessionById(cfgAct.getOpenIdSession());
            if (cfgAct.isOpenWs()) {
                actualWs = daoW.askWorshiftById(cfgAct.getOpenIdWs());
                actualWs.setWsCashier(daoU.getCashierByWorkshift(actualWs.getWsId()));
            }
        }
        user = userIn;
        pass = passIn;
        setTitle("Salón Manager");
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);

        JMenuBar menuBar = utiliGraf.navegador(userIn, passIn, this);
        setJMenuBar(menuBar);

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
        if (actualSess != null) {
            if (actualWs != null) {
                if (user.getId().equals(actualWs.getWsCashier().getId())) {
                    labelActualShift.setText("Te encuentras con una sesión activa \ny un turno abierto a tu nombre.");
                } else {
                    labelActualShift.setText("Te encuentras con una sesión activa \ny hay un turno abierto a nombre de " + actualWs.getWsCashier().getName() + " " +actualWs.getWsCashier().getLastName() +".");
                }
            } else {
                labelActualShift.setText("Te encuentras con una sesión activa.");
            }
        } else {
            labelActualShift.setText("No hay una sesión activa.");
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
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    sm.frameCloser();
                    dispose();
                }
            }
        });
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
}
