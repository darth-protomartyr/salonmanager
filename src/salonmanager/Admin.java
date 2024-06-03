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
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Admin extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOConfig daoC = new DAOConfig();
    Color bluLg = new Color(194, 242, 206);
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

        JPanel panelItems = panelCreator("Administrar Items", 37, 25);
        panelPpal.add(panelItems);

        JButtonMetalBlu butCfgItems = utiliGraf.button1("Modificar Lista", anchoUnit * 15, altoUnit * 6, anchoUnit * 17);
        butCfgItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new ConfigItemList();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItems.add(butCfgItems);

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
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
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
