package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.PanelPpalCustom;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Manager extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioSalon ss = new ServicioSalon();
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
    Salon salon = null;
    int f1 = anchoUnit * 3;
    Font font1 = new Font("Arial", Font.BOLD, f1);
    int f2 = (int) Math.round(anchoUnit * 2.3);
    Font font2 = new Font("Arial", Font.BOLD, f2);
    int f3 = (int) Math.round(anchoUnit * 1.6);
    Font font3 = new Font("Arial", Font.BOLD, f3);
    int f4 = (int) Math.round(anchoUnit * 1.2);
    Font font4 = new Font("Arial", Font.BOLD, f4);
    int f5 = (int) Math.round(anchoUnit * 1.1);
    Font font5 = new Font("Arial", Font.BOLD, f5);

    public Manager(User userIn, String passIn) throws Exception {
        sm.addFrame(this);
        user = userIn;
        pass = passIn;
        setTitle("Salón Manager");
        setLayout(null);
        PanelPpalCustom panelPpal = new PanelPpalCustom(frame, 4);
        add(panelPpal);

        JMenuBar menuBar = utiliGraf.navegador(userIn, passIn, this);
        setJMenuBar(menuBar);

        JLabel labelLegal = utiliGraf.labelLegal(anchoFrame, alturaFrame, 1, 10);
        panelPpal.add(labelLegal);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(null);
        panelUser.setBounds(anchoUnit * 85, altoUnit * 2, anchoUnit * 19, altoUnit * 18);
        panelUser.setBackground(narSt);
        panelPpal.add(panelUser);

        String route = utili.barrReplaceInverse(userIn.getRouteImage());
        ImageIcon imageIcon = new ImageIcon(route);
        JLabel labelImage = new JLabel(imageIcon);
        labelImage.setBounds(anchoUnit, altoUnit * 2, anchoUnit * 8, altoUnit * 14);
        panelUser.add(labelImage);

        JLabel labelName = utiliGraf.labelTitleBacker2(userIn.getName());
        labelName.setBounds(anchoUnit * 10, altoUnit * 3, anchoUnit * 8, altoUnit * 3);

        panelUser.add(labelName);

        JLabel labelRol = utiliGraf.labelTitleBacker2(userIn.getRol());
        labelRol.setBounds(anchoUnit * 10, altoUnit * 9, anchoUnit * 8, altoUnit * 3);
        panelUser.add(labelRol);

        JPanel panelWorkshift = new JPanel();
        panelWorkshift.setLayout(null);
        panelWorkshift.setBackground(bluLg);
        panelWorkshift.setBounds(anchoUnit * 54, altoUnit * 22, anchoUnit * 50, altoUnit * 65);
        panelPpal.add(panelWorkshift);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir2(frame, 4);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirm = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirm) {
                    sm.frameCloser();
                    System.exit(0);
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    sm.frameCloser();
                    System.exit(0);
                }
            }
        });
    }

    public void salonFrameManager() throws Exception {
        Manager man = this;
        if (salon == null) {
            ConfigActual cfgAct = daoC.askConfigActual();
            User userWs = null;
            if (cfgAct.isOpenWs()) {
                userWs = daoU.getCashierByWorkshift(cfgAct.getOpenIdWs());
            }
    
            if (userWs != null) {
                if (!userWs.getId().equals(user.getId())) {
                    ss.endWorkshift(null, man, true);
                } else {
                    salon = new Salon(man, cfgAct);                
                }
            } else {
                salon = new Salon(man, cfgAct);
            }
            
        } else {
            salon.setVisible(true);
            salon.toFront();
            salon.setExtendedState(JFrame.MAXIMIZED_BOTH);
            salon.requestFocus();
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

    public void nullSalon() {
        this.salon = null;
        this.pass = "27949874";

    }

    public Font getFont1() {
        return font1;
    }

    public void setFont1(Font font1) {
        this.font1 = font1;
    }

    public Font getFont2() {
        return font2;
    }

    public void setFont2(Font font2) {
        this.font2 = font2;
    }

    public Font getFont3() {
        return font3;
    }

    public void setFont3(Font font3) {
        this.font3 = font3;
    }

    public Font getFont4() {
        return font4;
    }

    public void setFont4(Font font4) {
        this.font4 = font4;
    }

    public Font getFont5() {
        return font5;
    }

    public void setFont5(Font font5) {
        this.font5 = font5;
    }
    
    
}
