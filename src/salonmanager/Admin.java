package salonmanager;

import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Admin extends FrameHalf {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    ServicioTable st = new ServicioTable();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOConfig daoC = new DAOConfig();
    Color bluLg = new Color(194, 242, 206);
    User user = null;
    ConfigActual cfgAct = new ConfigActual();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> defer1 = new ArrayList<>();
    ArrayList<String> defer2 = new ArrayList<>();
    Table tabAux = null;
    User userMod = null;
    JComboBox comboUsers = new JComboBox();
    JComboBox comboAct = new JComboBox();
    JComboBox comboRol = new JComboBox();
    JComboBox comboTabs = new JComboBox();
    JLabel labelUserMod = null;
    Admin adm = null;
    Manager manager = null;

    public Admin(Manager man) throws Exception {
        manager = man;
        adm = this;
        user = manager.getUser();
        setTitle("Administrador");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        cfgAct = daoC.askConfigActual();
        users = daoU.listarUsersCompleto();
        Font newFont = new Font("Arial", Font.BOLD, 17);

        JLabel labelTit = utiliGraf.labelTitleBackerA3W("Administrar");
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelTit);

//----------------Panel Users-------------------------------------------------
//----------------Panel Users-------------------------------------------------
        JPanel panelUsers = new JPanel();
        panelUsers.setLayout(null);
        panelUsers.setBackground(bluLg);
        panelUsers.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 23, altoUnit * 47);
        panelPpal.add(panelUsers);

        JLabel labelUsers = utiliGraf.labelTitleBacker1("Administrar Usuarios");
        labelUsers.setHorizontalAlignment(SwingConstants.CENTER);
        labelUsers.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelUsers.add(labelUsers);

        comboUsers.setBounds(anchoUnit * 4, altoUnit * 7, anchoUnit * 15, altoUnit * 4);
        comboUsers.setModel(utili.userComboModelReturnWNull(users));
        comboUsers.setSelectedItem("");
        comboUsers.setFont(newFont);
        panelUsers.add(comboUsers);

        JButtonMetalBlu butSelUsers = utiliGraf.button2("Elegir Usuario", anchoUnit * 5, altoUnit * 13, anchoUnit * 13);
        butSelUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    selectUser();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butSelUsers);

        labelUserMod = utiliGraf.labelTitleBacker2("");
        labelUserMod.setHorizontalAlignment(SwingConstants.CENTER);
        labelUserMod.setBounds(anchoUnit * 0, altoUnit * 17, anchoUnit * 23, altoUnit * 5);
        panelUsers.add(labelUserMod);

        JLabel labelAct = utiliGraf.labelTitleBacker2("ESTADO:");
        labelAct.setHorizontalAlignment(SwingConstants.CENTER);
        labelAct.setBounds(anchoUnit * 5, altoUnit * 22, anchoUnit * 6, altoUnit * 4);
        panelUsers.add(labelAct);

        ArrayList<String> state = new ArrayList<>();
        state.add("");
        state.add("alta");
        state.add("baja");

        comboAct.setBounds(anchoUnit * 13, altoUnit * 22, anchoUnit * 5, altoUnit * 4);
        comboAct.setModel(utili.categoryComboModelReturn(state));
        comboAct.setSelectedItem("");
        comboAct.setFont(newFont);
        panelUsers.add(comboAct);

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

        comboRol.setBounds(anchoUnit * 9, altoUnit * 28, anchoUnit * 9, altoUnit * 4);
        comboRol.setModel(utili.categoryComboModelReturn(rols));
        comboRol.setFont(newFont);
        comboRol.setSelectedItem("");
        panelUsers.add(comboRol);

        JButtonMetalBlu butModifyUser = utiliGraf.button1("Confirmar", anchoUnit * 5, altoUnit * 34, anchoUnit * 13);
        butModifyUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (userMod != null) {
                        updateUser();
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
                    new TemplateUser(null, adm);
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
                    if (userMod != null) {
                        new TemplateUser(userMod, adm);
                    } else {
                        utiliMsg.errorUserSelected();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelUsers.add(butUpdateUser);

//----------------Panel Tables------------------------------------------------
//----------------Panel Tables------------------------------------------------
        JPanel panelTables = new JPanel();
        panelTables.setLayout(null);
        panelTables.setBackground(bluLg);
        panelTables.setBounds(anchoUnit * 26, altoUnit * 10, anchoUnit * 23, altoUnit * 47);
        panelPpal.add(panelTables);

        defer1 = cfgAct.getArrayDeferWs();
        defer2 = utili.tabsEasyReader(defer1);

        JLabel labelTables = utiliGraf.labelTitleBacker1("Administrar Mesas de Error");
        labelTables.setHorizontalAlignment(SwingConstants.CENTER);
        labelTables.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelTables.add(labelTables);

        comboTabs.setBounds(anchoUnit * 5, altoUnit * 7, anchoUnit * 13, altoUnit * 4);
        comboTabs.setModel(utili.categoryComboModelReturn(defer2));
        comboTabs.setSelectedItem("");
        comboTabs.setFont(newFont);
        panelTables.add(comboTabs);

        JButtonMetalBlu butSelTabs = utiliGraf.button2("Elegir Mesa", anchoUnit * 5, altoUnit * 13, anchoUnit * 13);
        butSelTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    selectTab();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTables.add(butSelTabs);

//----------------Panel Items-------------------------------------------------
//----------------Panel Items-------------------------------------------------
        JPanel panelItems = panelCreator("Administrar Items", 59, 15);
        panelPpal.add(panelItems);

        JButtonMetalBlu butCfgItems = utiliGraf.button1("Modificar Lista", anchoUnit * 15, altoUnit * 6, anchoUnit * 17);
        butCfgItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new ConfigItemList(manager);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItems.add(butCfgItems);

//----------------Panel Config------------------------------------------------
//----------------Panel Config------------------------------------------------
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

    private void selectUser() {
        String selection = (String) comboUsers.getSelectedItem();
        if (!selection.equals("")) {
            userMod = utili.userSelReturn(selection, users);
            comboRol.setSelectedItem(userMod.getRol());
            String state = "";
            if (userMod.isActiveUser()) {
                state = "alta";
            } else {
                state = "baja";
            }
            comboAct.setSelectedItem(state);
            labelUserMod.setText("Usuario: " + userMod.getName() + " " + userMod.getLastName());
        }
    }

    private void updateUser() throws Exception {
        String userId = userMod.getId();
        boolean act = false;
        String rol = (String) comboRol.getSelectedItem();
        String active = (String) comboAct.getSelectedItem();
        if (!userId.equals("") && !active.equals("") && !rol.equals("")) {
            if (active.equals("alta")) {
                act = true;
            } else {
                act = false;
            }
            daoU.updateActUser(userId, act);
            daoU.updateRolUser(userId, rol);
            utiliMsg.cargaUserUpdate();
            reset();
        } else {
            utiliMsg.errorDataNull();
        }
    }  
    
    
    private void reset() {
        comboUsers.setSelectedItem("");
        comboAct.setSelectedItem("");
        comboRol.setSelectedItem("");
        userMod = null;
        labelUserMod.setText("");
        
        comboTabs.setSelectedItem("");
        tabAux = null;
    }

    private void selectTab() throws Exception {
        String id1 = (String) comboTabs.getSelectedItem();
        String id2 = "";
        for (int i = 0; i < defer2.size(); i++) {
            if (id1.equals(defer2.get(i))) {
                id2 = defer1.get(i);
            }
        }
        tabAux = st.getCompleteTableById(id2);
//        new TableResumePanel(null, tabAux, 1, this);
        new TableResumePanel(tabAux);

        setEnabled(false);
    }

    void enabledTrue(int mod) throws Exception {
        if (mod == 1) {
            cfgAct = daoC.askConfigActual();
            defer1 = cfgAct.getArrayDeferWs();
            defer2 = utili.tabsEasyReader(defer1);
            comboTabs.setModel(utili.categoryComboModelReturn(defer2));
            comboTabs.setSelectedItem("");
        } else if (mod == 2) {
            users = daoU.listarUsersCompleto();
            comboUsers.setModel(utili.userComboModelReturnWNull(users));
            comboUsers.setSelectedItem("");
        }
        reset();
        setEnabled(true);
    }
}
