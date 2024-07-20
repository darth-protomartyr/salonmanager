package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import salonmanager.Admin;
import salonmanager.ConfigItemList;
import salonmanager.ConfigSalonFrame;
import salonmanager.TemplateUser;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServiceAdmin;
import salonmanager.servicios.ServicioTable;

public class UtilidadesGraficasAdmin {
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice().getDefaultConfiguration());
    int taskBarHeight = insets.bottom;
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - taskBarHeight;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    ServicioTable st = new ServicioTable();
    ServiceAdmin sa = new ServiceAdmin();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOConfig daoC = new DAOConfig();
    Color bluLg = new Color(194, 242, 206);
    UtilidadesGraficasAdmin uga = this;

    public JPanel panelUserBacker(Admin admin) {
        JPanel panelUsers = new JPanel();
        panelUsers.setLayout(null);
        panelUsers.setBackground(bluLg);
        panelUsers.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 23, altoUnit * 47);

        JLabel labelUsers = utiliGraf.labelTitleBacker1("Administrar Usuarios");
        labelUsers.setHorizontalAlignment(SwingConstants.CENTER);
        labelUsers.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelUsers.add(labelUsers);

        admin.getComboUsers().setBounds(anchoUnit * 4, altoUnit * 7, anchoUnit * 15, altoUnit * 4);
        admin.getComboUsers().setModel(utili.userComboModelReturnWNull(admin.getUsers()));
        admin.getComboUsers().setSelectedItem("");
        admin.getComboUsers().setFont(admin.getNewFont());
        panelUsers.add(admin.getComboUsers());

        JButtonMetalBlu butSelUsers = utiliGraf.button2("Elegir Usuario", anchoUnit * 5, altoUnit * 13, anchoUnit * 13);
        butSelUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sa.selectUser(admin);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butSelUsers);

        admin.setLabelUserMod(utiliGraf.labelTitleBacker2(""));
        admin.getLabelUserMod().setHorizontalAlignment(SwingConstants.CENTER);
        admin.getLabelUserMod().setBounds(anchoUnit * 0, altoUnit * 17, anchoUnit * 23, altoUnit * 5);
        panelUsers.add(admin.getLabelUserMod());

        JLabel labelAct = utiliGraf.labelTitleBacker2("ESTADO:");
        labelAct.setHorizontalAlignment(SwingConstants.CENTER);
        labelAct.setBounds(anchoUnit * 5, altoUnit * 22, anchoUnit * 6, altoUnit * 4);
        panelUsers.add(labelAct);

        ArrayList<String> state = new ArrayList<>();
        state.add("");
        state.add("alta");
        state.add("baja");

        admin.getComboAct().setBounds(anchoUnit * 13, altoUnit * 22, anchoUnit * 5, altoUnit * 4);
        admin.getComboAct().setModel(utili.categoryComboModelReturn(state));
        admin.getComboAct().setSelectedItem("");
        admin.getComboAct().setFont(admin.getNewFont());
        panelUsers.add(admin.getComboAct());

        JLabel labelRol = utiliGraf.labelTitleBacker2("ROL:");
        labelRol.setHorizontalAlignment(SwingConstants.CENTER);
        labelRol.setBounds(anchoUnit * 5, altoUnit * 28, anchoUnit * 4, altoUnit * 4);
        panelUsers.add(labelRol);

        ArrayList<String> rols = new ArrayList<>();
        rols.add("MANAGER");
        rols.add("CAJERO");
        rols.add("MOZO");
        rols.add("DELIVERY");
        rols.add("");

        admin.getComboRol().setBounds(anchoUnit * 9, altoUnit * 28, anchoUnit * 9, altoUnit * 4);
        admin.getComboRol().setModel(utili.categoryComboModelReturn(rols));
        admin.getComboRol().setFont(admin.getNewFont());
        admin.getComboRol().setSelectedItem("");
        panelUsers.add(admin.getComboRol());

        JButtonMetalBlu butModifyUser = utiliGraf.button1("Confirmar", anchoUnit * 5, altoUnit * 34, anchoUnit * 13);
        butModifyUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (admin.getUserMod() != null) {
                        sa.updateUser(admin, uga);
                    } else {
                        utiliMsg.errorUserSelected();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butModifyUser);

        JButtonMetalBlu butCreateUser = utiliGraf.button3("Crear Usuario", anchoUnit, altoUnit * 43, anchoUnit * 8);
        butCreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new TemplateUser(null, admin);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butCreateUser);

        JButtonMetalBlu butUpdateUser = utiliGraf.button3("Actualizar Usuario", anchoUnit * 11, altoUnit * 43, anchoUnit * 11);
        butUpdateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (admin.getUserMod() != null) {
                        new TemplateUser(admin.getUserMod(), admin);
                    } else {
                        utiliMsg.errorUserSelected();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butUpdateUser);
        return panelUsers;
    }

    public void reset(Admin admin) {
        admin.getComboUsers().setSelectedItem("");
        admin.getComboAct().setSelectedItem("");
        admin.getComboRol().setSelectedItem("");
        admin.setUserMod(null);
        admin.getLabelUserMod().setText("");
        admin.getComboTabs().setSelectedItem("");
        admin.setTabAux(null);
    }

    public JPanel panelTableBacker(Admin adm) {
        JPanel panelTables = new JPanel();
        panelTables.setLayout(null);
        panelTables.setBackground(bluLg);
        panelTables.setBounds(anchoUnit * 26, altoUnit * 10, anchoUnit * 23, altoUnit * 23);

        adm.setDefer1(adm.getCfgAct().getArrayDeferWs());
        adm.setDefer2(utili.tabsEasyReader(adm.getDefer1()));

        JLabel labelTables = utiliGraf.labelTitleBacker1("Administrar Mesas de Error");
        labelTables.setHorizontalAlignment(SwingConstants.CENTER);
        labelTables.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelTables.add(labelTables);

        adm.getComboTabs().setBounds(anchoUnit * 5, altoUnit * 8, anchoUnit * 13, altoUnit * 4);
        adm.getComboTabs().setModel(utili.categoryComboModelReturn(adm.getDefer2()));
        adm.getComboTabs().setSelectedItem("");
        adm.getComboTabs().setFont(adm.getNewFont());
        panelTables.add(adm.getComboTabs());

        JButtonMetalBlu butSelTabs = utiliGraf.button2("Elegir Mesa", anchoUnit * 5, altoUnit * 15, anchoUnit * 13);
        butSelTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sa.selectTab(adm);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTables.add(butSelTabs);
        return panelTables;
    }

    public JPanel panelWsBacker(Admin adm) throws Exception {
        JPanel panelWs = new JPanel();
        panelWs.setLayout(null);
        panelWs.setBackground(bluLg);
        panelWs.setBounds(anchoUnit * 26, altoUnit * 34, anchoUnit * 23, altoUnit * 23);

        adm.setDefer1Ws(daoW.listarErrorId());
        adm.setDefer2Ws(utili.wsEasyReader(adm.getDefer1Ws()));

        JLabel labelWs = utiliGraf.labelTitleBacker1("Administrar Turnos erróneos");
        labelWs.setHorizontalAlignment(SwingConstants.CENTER);
        labelWs.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelWs.add(labelWs);

        adm.getComboWs().setBounds(anchoUnit * 5, altoUnit * 8, anchoUnit * 13, altoUnit * 4);
        adm.getComboWs().setModel(utili.categoryComboModelReturn(adm.getDefer2Ws()));
        adm.getComboWs().setSelectedItem("");
        adm.getComboWs().setFont(adm.getNewFont());
        panelWs.add(adm.getComboWs());

        JButtonMetalBlu butSelWs = utiliGraf.button2("Elegir Turno", anchoUnit * 5, altoUnit * 15, anchoUnit * 13);
        butSelWs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sa.selectWs(adm);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelWs.add(butSelWs);
        return panelWs;
    }

    public JPanel panelItemsBacker(Admin adm) {
        JPanel panelItems = panelCreator("Administrar Items", 59, 15);
        JButtonMetalBlu butCfgItems = utiliGraf.button1("Modificar Lista", anchoUnit * 15, altoUnit * 6, anchoUnit * 17);
        butCfgItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new ConfigItemList(adm.getManager());
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItems.add(butCfgItems);

        return panelItems;
    }
    
        public JPanel panelConfigBacker(Admin adm) {
        JPanel panelConfig = panelCreator("Administrar Configuración", 76, 15);
        JButtonMetalBlu butCfgSalon = utiliGraf.button1("Configurar salón", anchoUnit * 15, altoUnit * 6, anchoUnit * 17);
        butCfgSalon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarConfigSalon();
                if (confirmation) {
                    try {
                        if (!adm.getCfgAct().isOpenWs()) {
                            if (adm.getUser().getRol().equals("ADMIN")) {
                                new ConfigSalonFrame(adm.getUser());
                            }
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

        return panelConfig;
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

    public void enabledTrue(int mod, Admin adm) throws Exception {
        if (mod == 1) {
            adm.setCfgAct(daoC.askConfigActual());
            adm.setDefer1(adm.getCfgAct().getArrayDeferWs());
            adm.setDefer2(utili.tabsEasyReader(adm.getDefer1()));
            adm.getComboTabs().setModel(utili.categoryComboModelReturn(adm.getDefer2()));
            adm.getComboTabs().setSelectedItem("");
        } else if (mod == 2) {
            adm.setUsers(daoU.listarUsersCompleto());
            adm.getComboUsers().setModel(utili.userComboModelReturnWNull(adm.getUsers()));
            adm.getComboUsers().setSelectedItem("");
        }
        reset(adm);
        adm.setEnabled(true);
    }
    
    public void setErrorCombo(Admin adm) throws Exception {
        adm.setDefer1Ws(daoW.listarErrorId());
        adm.setDefer2Ws(utili.wsEasyReader(adm.getDefer1Ws()));
        adm.getComboWs().setModel(utili.categoryComboModelReturn(adm.getDefer2Ws()));
        adm.getComboWs().setSelectedItem("");        
    }
    
    
}
