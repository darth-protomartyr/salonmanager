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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasAdmin;
import salonmanager.utilidades.UtilidadesMensajes;

public class Admin extends FrameHalf {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    ServicioTable st = new ServicioTable();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasAdmin utiliGrafAdm = new UtilidadesGraficasAdmin();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOConfig daoC = new DAOConfig();
    Color bluLg = new Color(194, 242, 206);
    User user = null;
    ConfigActual cfgAct = new ConfigActual();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> defer1 = new ArrayList<>();
    ArrayList<String> defer2 = new ArrayList<>();
    ArrayList<Workshift> defer1Ws = new ArrayList<>();
    ArrayList<String> defer2Ws = new ArrayList<>();
    Table tabAux = null;
    User userMod = null;
    JComboBox comboUsers = new JComboBox();
    JComboBox comboAct = new JComboBox();
    JComboBox comboRol = new JComboBox();
    JComboBox comboTabs = new JComboBox();
    JComboBox comboWs = new JComboBox();
    JLabel labelUserMod = null;
    Admin adm = null;
    Manager manager = null;
    Font newFont = new Font("Arial", Font.BOLD, 17);


    public Admin(Manager man) throws Exception {
        manager = man;
        adm = this;
        user = manager.getUser();
        setTitle("Administrador");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        cfgAct = daoC.askConfigActual();
        users = daoU.listarUsersCompleto();

        JLabel labelTit = utiliGraf.labelTitleBackerA3W("Administrar");
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelTit);

//----------------Panel Users-------------------------------------------------
//----------------Panel Users-------------------------------------------------
        JPanel panelUsers = utiliGrafAdm.panelUserBacker(adm);
        panelPpal.add(panelUsers);

//----------------Panel Tables------------------------------------------------
//----------------Panel Tables------------------------------------------------
        JPanel panelTable = utiliGrafAdm.panelTableBacker(adm);
        panelPpal.add(panelTable);

//----------------Panel Workshifts -------------------------------------------
//----------------Panel Workshifts -------------------------------------------
        JPanel panelWs = utiliGrafAdm.panelWsBacker(adm);;
        panelPpal.add(panelWs);

//----------------Panel Items-------------------------------------------------
//----------------Panel Items-------------------------------------------------
        JPanel panelItems = utiliGrafAdm.panelItemsBacker(adm);
        panelPpal.add(panelItems);

//----------------Panel Config------------------------------------------------
//----------------Panel Config------------------------------------------------

        JPanel panelConfig = utiliGrafAdm.panelConfigBacker(adm);
        panelPpal.add(panelConfig);

//----------------Extras------------------------------------------------------
//----------------Extras------------------------------------------------------       

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

    public ServicioTable getSt() {
        return st;
    }

    public void setSt(ServicioTable st) {
        this.st = st;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<String> getDefer1() {
        return defer1;
    }

    public void setDefer1(ArrayList<String> defer1) {
        this.defer1 = defer1;
    }

    public ArrayList<String> getDefer2() {
        return defer2;
    }

    public void setDefer2(ArrayList<String> defer2) {
        this.defer2 = defer2;
    }

    public ArrayList<Workshift> getDefer1Ws() {
        return defer1Ws;
    }

    public void setDefer1Ws(ArrayList<Workshift> defer1Ws) {
        this.defer1Ws = defer1Ws;
    }

    public ArrayList<String> getDefer2Ws() {
        return defer2Ws;
    }

    public void setDefer2Ws(ArrayList<String> defer2Ws) {
        this.defer2Ws = defer2Ws;
    }

    public Table getTabAux() {
        return tabAux;
    }

    public void setTabAux(Table tabAux) {
        this.tabAux = tabAux;
    }

    public User getUserMod() {
        return userMod;
    }

    public void setUserMod(User userMod) {
        this.userMod = userMod;
    }

    public JComboBox getComboUsers() {
        return comboUsers;
    }

    public void setComboUsers(JComboBox comboUsers) {
        this.comboUsers = comboUsers;
    }

    public JComboBox getComboAct() {
        return comboAct;
    }

    public void setComboAct(JComboBox comboAct) {
        this.comboAct = comboAct;
    }

    public JComboBox getComboRol() {
        return comboRol;
    }

    public void setComboRol(JComboBox comboRol) {
        this.comboRol = comboRol;
    }

    public JComboBox getComboTabs() {
        return comboTabs;
    }

    public void setComboTabs(JComboBox comboTabs) {
        this.comboTabs = comboTabs;
    }

    public JComboBox getComboWs() {
        return comboWs;
    }

    public void setComboWs(JComboBox comboWs) {
        this.comboWs = comboWs;
    }

    public JLabel getLabelUserMod() {
        return labelUserMod;
    }

    public void setLabelUserMod(JLabel labelUserMod) {
        this.labelUserMod = labelUserMod;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Font getNewFont() {
        return newFont;
    }

    public void setNewFont(Font newFont) {
        this.newFont = newFont;
    }   

    public ConfigActual getCfgAct() {
        return cfgAct;
    }

    public void setCfgAct(ConfigActual cfgAct) {
        this.cfgAct = cfgAct;
    }    
}
