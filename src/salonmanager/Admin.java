package salonmanager;

import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesUpdate;

public class Admin extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesUpdate utiliUpd = new UtilidadesUpdate();
    DAOConfig daoC = new DAOConfig();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User user = null;
    ConfigActual cfgAct = new ConfigActual();

    public Admin(User u) throws Exception {
        user = u;
        setTitle("Administrador");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        cfgAct = daoC.askConfigActual();
        

        JLabel labelStatics = utiliGraf.labelTitleBackerA3W("Administrar");
        labelStatics.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelStatics);

        JPanel panelTable = panelCreator("Administrar Mesas", 10, 25);
        panelPpal.add(panelTable);

        JPanel panelUser = panelCreator("Administrar Usuarios", 37, 25);
        panelPpal.add(panelUser);

        JPanel panelConfig = panelCreator("Administrar Configuración", 76, 15);
        panelPpal.add(panelConfig);

        JButtonMetalBlu butCfgSalon = utiliGraf.button1("Configurar salón", anchoUnit * 15, altoUnit * 6, anchoUnit * 17);
        butCfgSalon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarConfigSalon();
                if (confirmation) {
                    try {
                        if (!cfgAct.isOpenWs()) {
                            configSalonOpener();
                        } else {
                            utiliMsg.errorWsPendient();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        panelConfig.add(butCfgSalon);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
    }

    private JPanel panelCreator(String tit, int i, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bluLg);
        panel.setBounds(anchoUnit * 2, altoUnit * i, anchoUnit * 47, altoUnit * y);
        JLabel labelStatics = utiliGraf.labelTitleBacker1(tit);
        labelStatics.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatics.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 47, altoUnit * 5);
        panel.add(labelStatics);

        return panel;
    }

    private void configSalonOpener() throws Exception {
        if (user.getRol().equals("ADMIN")) {
            new ConfigSalonFrame(user);
        }
    }
}