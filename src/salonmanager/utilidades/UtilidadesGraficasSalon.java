/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.BillDiscounter;
import salonmanager.CorrectorItem;
import salonmanager.DeliveryTemplate;
import salonmanager.ErrorTableCount;
import salonmanager.GiftSelector;
import salonmanager.ItemSelector;
import salonmanager.MoneyType;
import salonmanager.Monitor;
import salonmanager.PartialPayer;
import salonmanager.Salon;
import salonmanager.SalonManager;
import salonmanager.WaiterSelector;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.JButtonBarr;
import salonmanager.entidades.graphics.JButtonDelivery;
import salonmanager.entidades.graphics.JButtonDeliverySee;
import salonmanager.entidades.graphics.JButtonTable;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOSession;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioItemMonitor;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

/**
 *
 * @author Gonzalo
 */
public class UtilidadesGraficasSalon {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    SalonManager sm = new SalonManager();
    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();
    DAOItemcard daoI = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAODelivery daoD = new DAODelivery();
    DAOSession daoS = new DAOSession();
    ServicioSalon ss = new ServicioSalon();
    ServicioTable st = new ServicioTable();
    ServicioItemMonitor sim = new ServicioItemMonitor();

    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color narUlgX = new Color(255, 255, 210);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);

//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------
//HEADER---------------------------------------------------------------------------------------------------------------        
//PANEL ACTUAL.........................................................................................................
//PANEL ACTUAL.........................................................................................................
    public JPanel panelActualBacker(Salon salon) throws Exception {
        ConfigActual cfgAct = daoC.askConfigActual();
        salon.setSessionNow(daoS.askSessionById(cfgAct.getOpenIdSession()));
        if (cfgAct.isOpenWs()) {
            salon.setWorkshiftNow(daoW.askWorshiftById(cfgAct.getOpenIdWs()));
            salon.getWorkshiftNow().setWsCashier(daoU.getCashierByWorkshift(salon.getWorkshiftNow().getWsId()));
        }

        JPanel panelActual = new JPanel();
        panelActual.setBounds(anchoUnit * 11, altoUnit * 3, anchoUnit * 17, altoUnit * 14);
        panelActual.setBackground(bluLg);
        panelActual.setLayout(null);

        JLabel labelUser = utiliGraf.labelTitleBacker3("Usuario: " + salon.getUser().getName().toUpperCase() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
        labelUser.setBounds(altoUnit, altoUnit, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelUser);

        salon.setLabelSession(utiliGraf.labelTitleBacker3("Inicio Sesion: " + utili.friendlyDate(salon.getSessionNow().getOpenSession())));

        salon.getLabelSession().setBounds(altoUnit, altoUnit * 4, anchoUnit * 17, altoUnit * 2);
        panelActual.add(salon.getLabelSession());

        salon.setLabelWorkshift(utiliGraf.labelTitleBacker3(""));
        if (salon.getWorkshiftNow() != null) {
            Timestamp timeInitWork = salon.getWorkshiftNow().getWsOpen();
            salon.getLabelWorkshift().setText("Inicio Turno: " + utili.friendlyDate(timeInitWork));
        } else {
            salon.getLabelWorkshift().setText("Turno no iniciado.");
        }
        salon.getLabelWorkshift().setBounds(altoUnit, altoUnit * 7, anchoUnit * 17, altoUnit * 2);
        panelActual.add(salon.getLabelWorkshift());

        salon.setButInitWorkshift(utiliGraf.button2("ABRIR TURNO", anchoUnit * 3, altoUnit * 9, anchoUnit * 11));
        if (cfgAct.isOpenWs()) {
            salon.getButInitWorkshift().setText("CERRAR TURNO");
        }
        salon.getButInitWorkshift().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getWorkshiftNow() == null) {
                        boolean confirm1 = utiliMsg.cargaConfirmarInicioTurno(salon.getUser().getName(), salon.getUser().getLastName());
                        if (confirm1 == true) {
                            salon.setWorkshiftNow(new Workshift(salon.getUser()));
                            daoW.saveWorkshift(salon.getWorkshiftNow());
                            salon.getWorkshiftNow().setWsId(daoW.findLastWsID());
                            salon.getWorkshiftNow().setWsCashier(salon.getUser());
                            daoU.saveCashierWorkshift(salon.getWorkshiftNow());
                            sm.workshiftBacker(salon.getWorkshiftNow());
                            salon.getLabelWorkshift().setText("Inicio Turno: " + utili.friendlyDate(salon.getWorkshiftNow().getWsOpen()));
                            salon.getButInitWorkshift().setText("CERRAR TURNO");
                            daoC.updateCfgActOpenWs(true);
                            int id = daoW.findLastWsID();
                            daoC.updateCfgActOpenIdWs(id);
                        }
                    } else {
                        ss.endWorkshift(salon, false);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelActual.add(salon.getButInitWorkshift());
        return panelActual;
    }

//PANEL BARRDELI.......................................................................................................
//PANEL BARRDELI.......................................................................................................
    public JPanel panelBarrDeliBacker(Salon salon) {
        JPanel panelBarrDeli = new JPanel();
        panelBarrDeli.setBounds(anchoUnit * 52, altoUnit * 3, anchoUnit * 25, altoUnit * 14);
        panelBarrDeli.setBackground(narLg);
        panelBarrDeli.setLayout(null);

        salon.setButBarrDeli(new JButton());
        salon.getButBarrDeli().setBackground(narUlg);
        salon.getButBarrDeli().setBounds(anchoUnit * 1, altoUnit * 2, anchoUnit * 23, altoUnit * 10);
        salon.getButBarrDeli().setBorder(null);
        salon.getButBarrDeli().setFont(salon.getFont1());
        salon.getButBarrDeli().setText("Barra - Delivery");
        salon.getButBarrDeli().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getWorkshiftNow() != null) {
                        butPanelTurn(salon);
                    } else {
                        utiliMsg.errorWorkshift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        panelBarrDeli.add(salon.getButBarrDeli());
        return panelBarrDeli;
    }

//PANEL MONITOR........................................................................................................
//PANEL MONITOR........................................................................................................
    public JPanel panelMonitor(Salon salon) {
        JPanel panelMonitor = new JPanel();
        panelMonitor.setBounds(anchoUnit * 29, altoUnit * 3, anchoUnit * 22, altoUnit * 14);
        panelMonitor.setBackground(narLg);
        panelMonitor.setLayout(null);

        JButton butMonitor = new JButton();
        butMonitor.setBackground(narUlg);
        butMonitor.setBounds(anchoUnit * 1, altoUnit * 2, anchoUnit * 20, altoUnit * 10);
        butMonitor.setBorder(null);
        butMonitor.setFont(salon.getFont1());
        butMonitor.setText("Seguimiento");
        butMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getWorkshiftNow() != null) {
                        new Monitor(salon);
                    } else {
                        utiliMsg.errorWorkshift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelMonitor.add(butMonitor);
        return panelMonitor;
    }

    private void butPanelTurn(Salon salon) throws Exception {
        salon.setTableAux(null);
        if (salon.isTabsBut() == true) {
            if (salon.getJbtAux() != null) {
                salon.getJbtAux().setBorder(null);
                salon.setJbtAux(null);
            }
            resetTableValues(salon);
            salon.getTabbedPane().setVisible(false);
            salon.getPanelBDButtons().setVisible(true);
            salon.getPanelPartial().setBackground(narLg);
            salon.getButPartialPay().setEnabled(false);
            salon.getLabelPartialPay().setText("DESHABILITADO");
            salon.getButBarrDeli().setText("Mesas");
            salon.setTabsBut(false);
        } else {
            if (salon.getJbbAux() != null) {
                salon.getJbbAux().setBorder(new LineBorder(narLg, 8));
                salon.setTableAux(null);
                salon.setJbbAux(null);
            }

            if (salon.getJbdAux() != null) {
                salon.getJbdAux().setBorder(new LineBorder(narLg, 8));
                salon.getJbdSAux().setBorder(new LineBorder(narLg, 8));
                salon.setTableAux(null);
                salon.setJbdAux(null);
                salon.setJbdSAux(null);
            }

            salon.getTabbedPane().setVisible(true);
            salon.getPanelBDButtons().setVisible(false);
            salon.getPanelPartial().setBackground(bluLg);
            salon.getButPartialPay().setEnabled(true);
            resetTableValues(salon);
            salon.getButBarrDeli().setText("Barra - Delivery");
            salon.setTabsBut(true);

        }
    }

//PANEL BUTTONS--------------------------------------------------------------------------------------------------------        
//PANEL BUTTONS--------------------------------------------------------------------------------------------------------        
//PANEL BUTTONS--------------------------------------------------------------------------------------------------------        
//PANEL BUTTONS--------------------------------------------------------------------------------------------------------        
//PANEL TABLE BUTTONS..................................................................................................
//PANEL TABLE BUTTONS..................................................................................................    
    public void returnTabbedPanes(Salon salon) {
        for (int i = 0; i < salon.getTableNum().size(); i++) {
            JPanel panelB = new JPanel();
            panelB.setBackground(narLg);
            panelB.setLayout(null);
            ArrayList<Integer> configValues = ss.salonConfigValues(salon.getTableNum().get(i), salon.getAnchoPane(), salon.getAlturaPane());
            salon.setFontSizeTable(configValues.get(0));
            salon.setWUnit(configValues.get(1));
            salon.setHUnit(configValues.get(2));
            salon.setRowsButtons(configValues.get(3));
            salon.setColButtons(configValues.get(4));

            for (int y = 0; y < salon.getRowsButtons(); y++) {
                for (int z = 0; z < salon.getColButtons(); z++) {
                    JButtonTable jbt = new JButtonTable(salon.getTablePanCh().get(i), salon.getNumBut(), salon.getWUnit() * (5 * z + 1), salon.getHUnit() * (5 * y + 1), (salon.getWUnit() * 4), (salon.getHUnit() * 4));
                    salon.setFont1(new Font("Arial", Font.BOLD, salon.getFontSizeTable()));
                    jbt.setFont(salon.getFont1());
                    jbt.setText(jbt.getText());
                    jbt.setBackground(narUlg);
                    jbt.setBorder(new EmptyBorder(10, 10, 10, 10));
                    jbt.setBounds(jbt.getMarginW(), jbt.getMarginH(), jbt.getWidth(), jbt.getHeight());
                    salon.setNumBut(salon.getNumBut() + 1);
                    salon.getTableButtons().add(jbt);
                    panelB.add(jbt);
                }
            }
            panelB.setBounds(salon.getWUnit(), salon.getHUnit(), salon.getAnchoPane(), salon.getAlturaPane() + salon.getHUnit());
            salon.getPanelsPane().add(panelB);
        }

        for (int i = 0; i < salon.getPanelsPane().size(); i++) {
            salon.getTabbedPane().addTab(salon.getTablePan().get(i), salon.getPanelsPane().get(i));
        }
        salon.getTabbedPane().setBounds(anchoUnit, anchoUnit, salon.getAnchoPane(), (salon.getAlturaPane() + altoUnit * 3));
        salon.getPanelA().add(salon.getTabbedPane());

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (salon.getWorkshiftNow() != null) {
                    if (salon.getJbtAux() != null) {
                        salon.getJbtAux().setBorder(null);
//                        if (salon.getJbtAux().isOpenJBT() == true) {
//                            try {
//                                resetTableValues(salon);
//                            } catch (Exception ex) {
//                                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
                    }
                    JButtonTable butClicked = (JButtonTable) e.getSource();
                    for (int i = 0; i < salon.getTableButtons().size(); i++) {
                        if (salon.getTableButtons().get(i).getNum() == butClicked.getNum()) {
                            salon.setJbtAux(salon.getTableButtons().get(i));
                        }
                    }
                    try {
                        resetTableValues(salon);
                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    salon.getJbtAux().setBorder(new LineBorder(bluSt, 8));

                    if (salon.getJbtAux().isOpenJBT() == false) {
                        try {
                            setWaiter(0, salon);
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        salon.setTableAux(new Table(salon.getJbtAux().getNum(), salon.getJbtAux().getPos(), salon.getWaiterAux()));
                        salon.getTableAux().setOpen(true);
                        String nameT = salon.getTableAux().getPos() + salon.getTableAux().getNum();
                        salon.getLabelOrder().setText("MESA:" + nameT);
                        salon.getTableAux().setOrder(new ArrayList<Itemcard>());
                        try {
                            jButExtSetter(salon);
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        salon.setTableAux(salon.getJbtAux().getTable());
                        tableFullerProp(salon);
                    }
                } else {
                    utiliMsg.errorWorkshift();
                }
            }
        };

        for (int i = 0; i < salon.getTableButtons().size(); i++) {
            salon.setJbtAux(salon.getTableButtons().get(i));
            salon.getJbtAux().addActionListener(actionListener);
        }
    }

    public void setWaiter(int i, Salon salon) throws Exception {
        WaiterSelector ws = new WaiterSelector(salon, i);
        ws.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ws.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void waiterBacker(User waiter, Salon salon) {
        salon.setWaiterAux(waiter);
        salon.getLabelWaiter().setText("Mozo: " + salon.getWaiterAux().getName() + " " + utili.strShorter(salon.getWaiterAux().getLastName(), 2).toUpperCase());
        salon.getTableAux().setWaiter(salon.getWaiterAux());
        salon.setEnabled(true);
    }

//PANEL BARR BUTTONS...................................................................................................
//PANEL BARR BUTTONS...................................................................................................    
    public JPanel returnPanelBarr(Salon salon) {
        JPanel panelBarr = new JPanel();
        panelBarr.setLayout(null);
        panelBarr.setBackground(bluLg);
        panelBarr.setBounds(anchoUnit * 2, altoUnit, (salon.getAnchoPane() / 2) - anchoUnit * 3, salon.getAlturaPane());

        JLabel labelBP = utiliGraf.labelTitleBackerA4("Barra");
        labelBP.setBounds(anchoUnit, altoUnit, anchoUnit * 12, altoUnit * 4);
        panelBarr.add(labelBP);

        JButton butCreateBarr = utiliGraf.button1("Crear pedido Barra", anchoUnit * 7, altoUnit * 6, anchoUnit * 20);
        butCreateBarr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createBarr(salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBarr.add(butCreateBarr);

        salon.getPanelBarrBut().setBackground(narLg);
        salon.getPanelBarrBut().setLayout(new GridLayout(0, 1, anchoUnit * 5, altoUnit * 5));
        salon.setScrPaneBarr(new JScrollPane(salon.getPanelBarrBut()));
        salon.getScrPaneBarr().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        salon.getScrPaneBarr().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        salon.getScrPaneBarr().setBounds(anchoUnit, altoUnit * 15, anchoUnit * 32, altoUnit * 55);
        panelBarr.add(salon.getScrPaneBarr());

        return panelBarr;
    }

    private void createBarr(Salon salon) throws Exception {
        boolean testN = false;
        boolean emptyButton = false;
        if (salon.getBarrButtons().size() > 0) {
            testN = true;
        }

        if (testN) {
            Table tab = salon.getBarrButtons().get(0).getTable();
            if (salon.getBarrButtons().get(0).isOpenJBB() == false && tab.isBill() == false) {
                emptyButton = true;
                utiliMsg.erroNotNewButton();
            }
        }

        if (emptyButton == false) {
            int num = salon.getBarrButtons().size() + 1;
            JButtonBarr newJBB = new JButtonBarr(num);
            Table newTable = new Table(newJBB.getNum(), newJBB.getPos(), salon.getUser());
            newJBB.setTable(newTable);
            salon.getBarrButtons().add(0, newJBB);
            barrButUpdater(salon);
            resetTableValues(salon);
        }
    }

    public void barrButUpdater(Salon salon) {
        for (int i = 0; i < salon.getBarrButtons().size(); i++) {
            JButtonBarr butSelBarr = salon.getBarrButtons().get(i);
            butSelBarr.setBackground(narUlg);
            butSelBarr.setBorder(new LineBorder(narLg, 8));

            butSelBarr.setFont(salon.getFont2());
            butSelBarr.setText("Barra pedido " + butSelBarr.getNum());
            butSelBarr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {

                        if (salon.getScheduler() != null) {
                            salon.getScheduler().shutdown();
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        } else {
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        }

                        if (!salon.isLoopBreaker()) {
                            selectBarr(ae, salon);
                            salon.setLoopBreaker(true);
                            Runnable duty = () -> {
                                salon.setLoopBreaker(false);
                            };
                            long timeWait = 1000; // en segundos
                            salon.getScheduler().schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            if (salon.getBarrButtons().get(i).isOpenJBB()) {
                salon.getBarrButtons().get(i).setBackground(green);
            }

            if (salon.getBarrButtons().get(i).getTable().isBill() == true) {
                salon.getBarrButtons().get(i).setBackground(red);
            }

            if (salon.getBarrButtons().get(i).isOpenJBB() == false && salon.getBarrButtons().get(i).getTable().isBill() == true) {
                salon.getBarrButtons().get(i).setBackground(narUlgX);
                salon.getBarrButtons().get(i).setEnabled(false);
                salon.getBarrButtons().get(i).setText("Barra " + "P" + salon.getJbbAux().getTable().getNum() + " Cerrado");
            }

            salon.getPanelBarrBut().add(butSelBarr);
        }
        salon.revalidate();
        salon.repaint();
    }

    private void selectBarr(ActionEvent ae, Salon salon) {
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBorder(null);
            salon.setJbtAux(null);
        }

        if (salon.getJbdAux() != null) {
            salon.getJbdAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdAux(null);
            salon.getJbdSAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdSAux(null);
        }

        if (salon.getJbbAux() != null) {
            salon.getJbbAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbbAux(null);
        }

        JButtonBarr butClicked = (JButtonBarr) ae.getSource();
        for (int i = 0; i < salon.getBarrButtons().size(); i++) {
            if (salon.getBarrButtons().get(i).getNum() == butClicked.getNum()) {
                salon.setJbbAux(salon.getBarrButtons().get(i));
            }
        }
        try {
            resetTableValues(salon);
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        salon.getJbbAux().setBorder(new LineBorder(bluSt, 8));

        if (salon.getJbbAux().isOpenJBB() == false) {
            salon.setTableAux(salon.getJbbAux().getTable());
            salon.setWaiterAux(salon.getUser());
            salon.getTableAux().setWaiter(salon.getUser());
            salon.getTableAux().setOpen(true);
            salon.getLabelOrder().setText("BARRA: B" + salon.getTableAux().getNum());
            salon.getLabelWaiter().setText("Cajero: " + salon.getUser().getName() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
            salon.getTableAux().setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter(salon);
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            salon.setTableAux(salon.getJbbAux().getTable());
            tableFullerProp(salon);
        }
    }

//PANEL DELIVERY BUTTONS...............................................................................................
//PANEL DELIVERY BUTTONS...............................................................................................    
    public JPanel returnPanelDeli(Salon salon) {
        JPanel panelDeli = new JPanel();
        panelDeli.setLayout(null);
        panelDeli.setBackground(bluLg);
        panelDeli.setBounds(salon.getAnchoPane() / 2 + anchoUnit, altoUnit, (salon.getAnchoPane() / 2) - anchoUnit * 3, salon.getAlturaPane());

        JLabel labelDP = utiliGraf.labelTitleBackerA4("Delivery");
        labelDP.setBounds(anchoUnit, altoUnit, anchoUnit * 12, altoUnit * 4);
        panelDeli.add(labelDP);

        JButton butCreateDeli = utiliGraf.button1("Crear pedido delivery", anchoUnit * 7, altoUnit * 6, anchoUnit * 20);
        butCreateDeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createDelivery(salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelDeli.add(butCreateDeli);

        salon.getPanelDeliContainer().setLayout(new FlowLayout(FlowLayout.CENTER));
        salon.getPanelDeliContainer().setBackground(bluLg);
        salon.getPanelDeliContainer().setBounds(0, altoUnit * 14, (salon.getAnchoPane() / 2) - anchoUnit * 3, (salon.getAlturaPane() - altoUnit * 13));
        panelDeli.add(salon.getPanelDeliContainer());

        panelDeliContainerSetter(salon);

        return panelDeli;
    }

    private void createDelivery(Salon salon) throws Exception {
        boolean testN = false;
        boolean emptyButton = false;
        int deliBq = salon.getDeliButtons().size();
        if (deliBq > 0) {
            testN = true;
        }

        //To not create new buttons if is empty 
        if (testN) {
            Table tab = salon.getDeliButtons().get(0).getTable();
            if (salon.getDeliButtons().get(0).isOpenJBD() == false && tab.isBill() == false) {
                emptyButton = true;
                utiliMsg.erroNotNewButton();
            }
        }

        if (emptyButton == false) {
            innDeliveryData(salon);
        }
    }

    private void innDeliveryData(Salon salon) throws Exception {
        new DeliveryTemplate(salon, null);
        salon.setEnabled(false);
    }

    public void panelDeliContainerSetter(Salon salon) {
        salon.getPanelDeliContainer().removeAll();
        int height = altoUnit * ((salon.getDeliButtons().size() * 10) + 1);
        salon.getPanelDeliBut().setLayout(null);
        if (height < altoUnit * 52) {
            height = altoUnit * 52;
        }

        salon.getPanelDeliBut().setPreferredSize(new Dimension(anchoUnit * 32, height));
        salon.getPanelDeliBut().setBackground(narLg);

        for (int i = 0; i < salon.getDeliButtons().size(); i++) {
            salon.getDeliButtons().get(i).setBounds(anchoUnit, altoUnit * ((i * 10) + 1), anchoUnit * 23, altoUnit * 10);
            salon.getPanelDeliBut().add(salon.getDeliButtons().get(i));

            salon.getDeliButtonsSees().get(i).setBounds(anchoUnit * 24, altoUnit * ((i * 10) + 1), anchoUnit * 6, altoUnit * 10);
            salon.getPanelDeliBut().add(salon.getDeliButtonsSees().get(i));
        }

        JScrollPane scrPaneDeli = new JScrollPane(salon.getPanelDeliBut());
        scrPaneDeli.setPreferredSize(new Dimension(anchoUnit * 32, altoUnit * 55));
        scrPaneDeli.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPaneDeli.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        salon.getPanelDeliContainer().add(scrPaneDeli);
    }

    private void deliButUpdater(Salon salon) {
        for (int i = 0; i < salon.getDeliButtons().size(); i++) {
            JButtonDelivery butSelDelivery = salon.getDeliButtons().get(i);
            butSelDelivery.setBackground(narUlg);
            butSelDelivery.setBorder(new LineBorder(narLg, 8));
            butSelDelivery.setFont(salon.getFont2());
            butSelDelivery.setText("Delivery pedido " + butSelDelivery.getNum());
            butSelDelivery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (salon.getScheduler() != null) {
                            salon.getScheduler().shutdown();
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        } else {
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        }

                        if (!salon.isLoopBreaker()) {
                            selectDeli(ae, salon);
                            if (!salon.isLoopBreaker()) {
                                selectDeli(ae, salon);
                                salon.setLoopBreaker(true);
                                Runnable duty = () -> {
                                    salon.setLoopBreaker(false);
                                };
                                long timeWait = 1000; // en segundos
                                salon.getScheduler().schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );

            //Test
            JButtonDeliverySee butSee = salon.getDeliButtonsSees().get(i);
            butSee.setBackground(narUlg);
            butSee.setBorder(new LineBorder(narLg, 8));
            butSee.setFont(salon.getFont3());
            butSee.setText("ver");
            butSee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (salon.getScheduler() != null) {
                            salon.getScheduler().shutdown();
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        } else {
                            salon.setScheduler(Executors.newSingleThreadScheduledExecutor());
                        }

                        if (!salon.isLoopBreaker()) {
                            selectDeliSee(ae, salon);
                            salon.setLoopBreaker(true);
                            Runnable duty = () -> {
                                salon.setLoopBreaker(false);
                            };
                            long timeWait = 1000; // en segundos
                            salon.getScheduler().schedule(duty, timeWait, TimeUnit.MILLISECONDS);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            );

            if (salon.getDeliButtons().get(i).isOpenJBD()) {
                salon.getDeliButtons().get(i).setBackground(green);
                salon.getDeliButtonsSees().get(i).setBackground(green);
            }

            if (salon.getDeliButtons().get(i).getTable().isBill() == true) {
                salon.getDeliButtons().get(i).setBackground(red);
                //Test
                salon.getDeliButtonsSees().get(i).setBackground(red);
            }

            if (salon.getDeliButtons().get(i).isOpenJBD() == false && salon.getDeliButtons().get(i).getTable().isBill() == true) {
                salon.getDeliButtons().get(i).setBackground(narUlgX);
                salon.getDeliButtons().get(i).setEnabled(false);
                //Test
                salon.getDeliButtonsSees().get(i).setBackground(narUlgX);
                salon.getDeliButtonsSees().get(i).setEnabled(false);
            }

            salon.getPanelDeliBut().add(butSelDelivery);
        }
        salon.revalidate();
        salon.repaint();
    }

    private void selectDeli(ActionEvent ae, Salon salon) {
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBorder(null);
            salon.setJbtAux(null);
        }
        if (salon.getJbdAux() != null) {
            salon.getJbdAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdAux(null);
            salon.getJbdSAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdSAux(null);
        }

        if (salon.getJbbAux() != null) {
            salon.getJbbAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbbAux(null);
        }

        JButtonDelivery butClicked = (JButtonDelivery) ae.getSource();
        for (int i = 0; i < salon.getDeliButtons().size(); i++) {
            if (salon.getDeliButtons().get(i).getNum() == butClicked.getNum()) {
                salon.setJbdAux(salon.getDeliButtons().get(i));
                salon.setJbdSAux(salon.getDeliButtonsSees().get(i));
            }
        }
        try {
            resetTableValues(salon);
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        salon.getJbdAux().setBorder(new LineBorder(bluSt, 8));
        salon.getJbdSAux().setBorder(new LineBorder(bluSt, 8));

        if (salon.getJbdAux().isOpenJBD() == false) {
            salon.setTableAux(salon.getJbdAux().getTable());
            salon.setWaiterAux(salon.getUser());
            salon.getTableAux().setWaiter(salon.getUser());
            salon.getTableAux().setOpen(true);
            salon.getLabelOrder().setText("DELIV.: D" + salon.getTableAux().getNum());
            salon.getLabelWaiter().setText("Cajero: " + salon.getUser().getName() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
            salon.getTableAux().setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter(salon);
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            salon.setTableAux(salon.getJbdAux().getTable());
            tableFullerProp(salon);
        }

    }

    private void selectDeliSee(ActionEvent ae, Salon salon) throws Exception {
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBorder(null);
            salon.setJbtAux(null);
        }

        if (salon.getJbdAux() != null) {
            salon.getJbdAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdAux(null);
            salon.getJbdSAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbdSAux(null);
        }

        if (salon.getJbbAux() != null) {
            salon.getJbbAux().setBorder(new LineBorder(narLg, 8));
            salon.setJbbAux(null);
        }

        JButtonDeliverySee butClicked = (JButtonDeliverySee) ae.getSource();
        for (int i = 0; i < salon.getDeliButtonsSees().size(); i++) {
            if (salon.getDeliButtonsSees().get(i).getNumDeli() == butClicked.getNumDeli()) {
                salon.setJbdAux(salon.getDeliButtons().get(i));
                salon.setJbdSAux(salon.getDeliButtonsSees().get(i));
                Delivery deli = salon.getJbdSAux().getDelivery();
                if (salon.getJbdSAux().getDelivery().getConsumer() != null) {
                    new DeliveryTemplate(salon, deli);
                } else {
                    utiliMsg.errorNullDeli();
                }
            }
        }
        try {
            resetTableValues(salon);
        } catch (Exception ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }

        salon.getJbdAux().setBorder(new LineBorder(bluSt, 8));
        salon.getJbdSAux().setBorder(new LineBorder(bluSt, 8));

        if (salon.getJbdAux().isOpenJBD() == false) {
            salon.setTableAux(salon.getJbdAux().getTable());
            salon.setWaiterAux(salon.getUser());
            salon.getTableAux().setWaiter(salon.getUser());
            salon.getTableAux().setOpen(true);
            salon.getLabelOrder().setText("DELIV.: D" + salon.getTableAux().getNum());
            salon.getLabelWaiter().setText("Cajero: " + salon.getUser().getName() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
            salon.getTableAux().setOrder(new ArrayList<Itemcard>());
            try {
                jButExtSetter(salon);
            } catch (Exception ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            salon.setTableAux(salon.getJbdAux().getTable());
            tableFullerProp(salon);
        }
    }

    public void getDeliOrder(Delivery dOrder, Salon salon) throws Exception {
        salon.setDeliOrderAux(dOrder);
        daoD.saveDelivery(salon.getDeliOrderAux());
        int num = salon.getDeliButtons().size() + 1;
        JButtonDelivery newJBD = new JButtonDelivery(num, dOrder);
        Table newTable = new Table(newJBD.getNum(), newJBD.getPos(), salon.getUser());
        newJBD.setTable(newTable);
        salon.getDeliButtons().add(0, newJBD);

        JButtonDeliverySee butSee = new JButtonDeliverySee(num, dOrder);
        salon.getDeliButtonsSees().add(0, butSee);

        salon.getPanelDeliBut().repaint();

        panelDeliContainerSetter(salon);
        deliButUpdater(salon);
        resetTableValues(salon);
    }

    public void setDeliOrder(Delivery dOrder, Salon salon) throws Exception {
        salon.setDeliOrderAux(dOrder);
        daoD.updateDelivery(salon.getDeliOrderAux());
        deliButUpdater(salon);
        resetTableValues(salon);
    }

//PANEL LATERAL--------------------------------------------------------------------------------------------------------        
//PANEL LATERAL--------------------------------------------------------------------------------------------------------        
//PANEL LATERAL--------------------------------------------------------------------------------------------------------        
//PANEL LATERAL--------------------------------------------------------------------------------------------------------        
//PANEL TABLE..........................................................................................................
//PANEL TABLE.......................................................................................................... 
    public JPanel returnPanelTable(Salon salon) {
        JPanel panelTable = new JPanel();
        panelTable.setLayout(null);
        panelTable.setBackground(narLg);
        panelTable.setBounds(anchoUnit, anchoUnit, anchoFrame - (salon.getAnchoPane() + anchoUnit * 9), altoUnit * 89);
        salon.setLabelOrder(utiliGraf.labelTitleBackerA2b("MESA: --"));
        salon.getLabelOrder().setBounds(altoUnit, altoUnit, anchoUnit * 23, altoUnit * 6);
        panelTable.add(salon.getLabelOrder());

        salon.setLabelWaiter(utiliGraf.labelTitleBackerA4("Mozo: --"));
        salon.getLabelWaiter().setBounds(altoUnit, altoUnit * 7, anchoUnit * 20, 30);
        panelTable.add(salon.getLabelWaiter());
        return panelTable;
    }

//PANEL SELECT ITEM....................................................................................................
//PANEL SELECT ITEM....................................................................................................
    public JPanel returnPanelSelItem(Salon salon) {
        JPanel panelSelItem = new JPanel();
        panelSelItem.setLayout(null);
        panelSelItem.setBounds(6, altoUnit * 12, anchoFrame - (salon.getAnchoPane() + anchoUnit * 10), altoUnit * 16);
        panelSelItem.setBackground(bluLg);

        JButton butCaptionBebidas = utiliGraf.button3("Bebidas", 5, 5, 55);
        butCaptionBebidas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Bebidas", salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionBebidas);

        JButton butCaptionPlatos = utiliGraf.button3("Platos", 65, 5, 55);
        butCaptionPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Platos", salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionPlatos);

        JButton butCaptionCafeteria = utiliGraf.button3("Cafetería", 125, 5, 80);
        butCaptionCafeteria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Cafeteria", salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionCafeteria);

        JButton butCaptionOtros = utiliGraf.button3("Otros", 210, 5, 65);
        butCaptionOtros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Otros", salon);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionOtros);

        salon.getComboItems().setModel(utili.itemsComboModelReturnWNull(salon.getItemsDB()));
        salon.getComboItems().setBounds(anchoUnit, altoUnit * 5, anchoUnit * 11, altoUnit * 4);
        salon.getComboItems().setSelectedIndex(salon.getItemsDB().size());
        panelSelItem.add(salon.getComboItems());

        JLabel labelUnitsItem = utiliGraf.labelTitleBacker3("Unidades");
        labelUnitsItem.setBounds(anchoUnit * 13, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelSelItem.add(labelUnitsItem);
        salon.setSpinnerUnitsItem(utiliGraf.spinnerBack(anchoUnit * 18, altoUnit * 5, anchoUnit * 3, altoUnit * 4, salon.getSpinnerUnitsItem()));
        panelSelItem.add(salon.getSpinnerUnitsItem());

        //Boton Ingreso Item
        JButton butSelItem = utiliGraf.button2("Ingresar item", anchoUnit * 1, altoUnit * 11, anchoUnit * 10);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getWaiterAux() == null) {
                        utiliMsg.errorWaiterNull();
                        resetTableValues(salon);
                    } else {
                        if (salon.getTableAux().isBill() == false) {
                            String item = (String) salon.getComboItems().getSelectedItem();
                            if (!item.equals("")) {
                                butSelItemActionPerformed(item, salon);
                            } else {
                                utiliMsg.errorSeleccion();
                            }
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butSelItem);

        JLabel labelAddIndication = utiliGraf.labelTitleBacker3("Indicación item");
        labelAddIndication.setBounds(anchoUnit * 12, altoUnit * 12, anchoUnit * 8, altoUnit * 3);
        panelSelItem.add(labelAddIndication);

        salon.getCheckBoxIndic().setBounds(anchoUnit * 20 - 5, altoUnit * 12, altoUnit * 3, altoUnit * 3);
        salon.getCheckBoxIndic().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    salon.setIndiBool(true);
                } else {
                    salon.setIndiBool(false);
                }
            }
        });
        panelSelItem.add(salon.getCheckBoxIndic());

        return panelSelItem;
    }

    // Select a kind o items
    private void itemCaptionBack(String capt, Salon salon) {
        ArrayList<Itemcard> aic = new ArrayList<Itemcard>();
        for (Itemcard ic : salon.getItemsDB()) {
            if (ic.getCaption().toLowerCase().equals(capt.toLowerCase())) {
                aic.add(ic);
            }
        }
        salon.getComboItems().setModel(utili.itemsComboModelReturn(aic));
    }

    //Add items to order
    private void butSelItemActionPerformed(String item, Salon salon) throws Exception {
        int u = (int) salon.getSpinnerUnitsItem().getValue();

        if (salon.isIndiBool() == true && u > 1) {
            utiliMsg.errorMultipleIndications();
            resetTableValues(salon);
        } else {
            if (salon.getItemsTableAux().size() < 1 && salon.getItemsGift().size() == 0) {
                ss.createTable(salon, salon.getTableAux());
                if (salon.getJbtAux() != null) {
                    salon.getJbtAux().setOpenJBT(true);
                    salon.getJbtAux().setBackground(green);
                }

                if (salon.getJbbAux() != null) {
                    salon.getJbbAux().setOpenJBB(true);
                    salon.getJbbAux().setBackground(green);
                }

                if (salon.getJbdAux() != null) {
                    salon.getJbdAux().setOpenJBD(true);
                    salon.getJbdAux().setBackground(green);
                    salon.getJbdSAux().setBackground(green);
                }
            }

            Itemcard ic = null;

            ic = utili.ItemcardBacker(item, salon.getItemsDB());
            salon.getButCloseTable().setText("CERRAR CUENTA");
            int counter = 0;
            while (counter < u) {
                salon.getItemsTableAux().add(ic);
                counter += 1;
            }
            salon.getTableAux().setOrder(salon.getItemsTableAux());
            salon.getSpinnerUnitsItem().setValue(1);
            salon.setTotal(ss.countBill(salon.getTableAux()));
            salon.getTableAux().setTotal(salon.getTotal());
            daoT.updateTableTotal(salon.getTableAux());
            ArrayList<Itemcard> arrayAux = ss.itemDeployer(ic, u);
            ss.addItemOrder(salon, salon.getTableAux(), arrayAux, salon.isIndiBool());
            jButExtSetter(salon);
            salon.getLabelCuenta().setText("$ " + salon.getTotal());
            salon.getComboItems().setModel(utili.itemsComboModelReturnWNull(salon.getItemsDB()));
            salon.getComboItems().setSelectedIndex(salon.getItemsDB().size());
            setTableItems(salon);
        }
    }

//TABLE LIST ITEMS.....................................................................................................
//TABLE LIST ITEMS.....................................................................................................
    public JScrollPane scrollItemsBack(int marginW, int marginH, int anchoPane, int alturaPane, Salon salon) {
        setTableItems(salon);
        JScrollPane scrollPane = new JScrollPane(salon.getJTableItems());
        scrollPane.setPreferredSize(new Dimension(anchoPane, alturaPane));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(marginW, marginH, anchoPane, alturaPane);
        return scrollPane;
    }

    public void setTableItems(Salon salon) {

        ArrayList<Itemcard> itemsAux = utili.unRepeatItems(salon.getItemsTableAux());

        ArrayList<Itemcard> partials =  utili.unRepeatItems(salon.getItemsPartialPaid());

        ArrayList<Itemcard> partialsND =  utili.unRepeatItems(salon.getItemsPartialPaidNoDiscount());

        ArrayList<Itemcard> gifts =  utili.unRepeatItems(salon.getItemsGift());

        ArrayList<Itemcard> totalItems =  utili.unRepeatItems(itemsAux);

        totalItems.addAll(partials);
        totalItems.addAll(partialsND);
        totalItems.addAll(gifts);

        salon.setRowsItems(totalItems.size());

        int aux = 0;
        if (salon.getDiscount() > 0) {
            aux += 1;
        }

        if (salon.getPriceCorrection() > 0) {
            aux += 1;
        }

        salon.setData(new String[salon.getRowsItems() + aux][salon.getColItems()]);

        int intAux = itemsAux.size();
        int intPartial = itemsAux.size() + partials.size();
        int intPartialND = itemsAux.size() + partials.size() + partialsND.size();

        double disc = (double) salon.getDiscount() / 100;

        
        for (int i = 0; i < salon.getRowsItems(); i++) {
            Itemcard ic = totalItems.get(i);

            if (i < intAux) {
                int u = st.itemUnitsBacker(salon.getItemsTableAux(), ic);
                salon.getData()[i][0] = " " + u;
                salon.getData()[i][1] = " " + ic.getName();
                salon.getData()[i][2] = " " + ic.getPrice() * u * (1 - disc);
            }

            if (partials.size() > 0 && i >= intAux && i < intPartial) {
                int u = st.itemUnitsBacker(salon.getItemsPartialPaid(), ic);
                salon.getData()[i][0] = " " + u;
                salon.getData()[i][1] = " PAG." + ic.getName();
                salon.getData()[i][2] = "PAGADO";
            }

            if (partialsND.size() > 0 && i >= intPartial && i < intPartialND) {
                int u = st.itemUnitsBacker(salon.getItemsPartialPaidNoDiscount(), ic);
                salon.getData()[i][0] = " " + u;
                salon.getData()[i][1] = " PAG*." + ic.getName();
                salon.getData()[i][2] = "PAGADO";
            }

            if (i >= intPartialND) {
                int u = st.itemUnitsBacker(salon.getItemsGift(), ic);
                salon.getData()[i][0] = " " + u;
                salon.getData()[i][1] = " OBS." + ic.getName();
                salon.getData()[i][2] = " 0";
            }
        }

        if (salon.getPriceCorrection() > 0) {
            if (salon.getDiscount() > 0) {
                salon.getData()[salon.getRowsItems() - 1][1] = "Corrección precio mod:";
                salon.getData()[salon.getRowsItems() - 1][2] = salon.getPriceCorrection() + "";
            } else {
                salon.getData()[salon.getRowsItems()][1] = "Corrección precio mod:";
                salon.getData()[salon.getRowsItems()][2] = salon.getPriceCorrection() + "";
            }
        }

        if (salon.getDiscount() > 0) {
            salon.getData()[salon.getRowsItems()][1] = "DESCUENTO: " + salon.getDiscount() + "%";
        }

        DefaultTableModel tableModel = new DefaultTableModel(salon.getData(), salon.getColNames());
        salon.getJTableItems().setModel(tableModel);
        salon.getJTableItems().setDefaultEditor(Object.class, null);

        JTableHeader header = salon.getJTableItems().getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(bluSt);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        salon.getJTableItems().setFont(cellFont);
        salon.getJTableItems().setRowHeight(25);
        salon.getJTableItems().setBackground(narUlg);

        int c = (salon.getAnchoPane() - 50) / 8;
        TableColumn column1 = salon.getJTableItems().getColumnModel().getColumn(0);
        column1.setPreferredWidth(c);
        TableColumn column2 = salon.getJTableItems().getColumnModel().getColumn(1);
        column2.setPreferredWidth(c * 5);
        TableColumn column3 = salon.getJTableItems().getColumnModel().getColumn(2);
        column3.setPreferredWidth(c * 2);
    }

    public void tableCarrector(Salon salon) {
        salon.getJTableItems().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = salon.getJTableItems().getSelectedRow();
                char[] pass = utiliMsg.requestMod();
                boolean perm = utili.requiredPerm(pass);
                if (perm) {
                    if (filaSeleccionada <= salon.getRowsItems()) {
                        itemCorrector(salon);
//                        setTableItems(salon);
                    }
                }
            }
        });
    }

//CORRECTOR............................................................................................................
//CORRECTOR............................................................................................................
    private void itemCorrector(Salon salon) {
        CorrectorItem ci = new CorrectorItem(salon);
        ci.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ci.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void correctItems(Itemcard ic, int num, Salon salon) throws Exception {
        switch (num) {
            case 1:
                salon.setItemsTableAux(ss.itemTableLesser(salon.getItemsTableAux(), ic));
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoI.downActiveItemOrderTable(ic, salon.getTableAux());
                break;
            case 2:
                salon.setItemsGift(ss.itemTableLesser(salon.getItemsGift(), ic));
                salon.getTableAux().setGifts(salon.getItemsGift());
                salon.getItemsTableAux().add(ic);
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoI.downActiveItemGiftTable(ic, salon.getTableAux());
                daoI.upActiveItemOrderTable(ic, salon.getTableAux());
                break;
            case 3:
                salon.setItemsPartialPaid(ss.itemTableLesser(salon.getItemsPartialPaid(), ic));
                salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
                salon.getItemsTableAux().add(ic);
                salon.getTableAux().setOrder(salon.getItemsTableAux());
                daoI.downActiveItemPayedTable(ic, salon.getTableAux());
                daoI.upActiveItemOrderTable(ic, salon.getTableAux());
                break;
        }
        jButExtSetter(salon);
        setTableItems(salon);
        salon.setEnabled(true);
    }

//GIFTS................................................................................................................
//GIFTS................................................................................................................
    public void actionButtonGift(Salon salon) {
        if (salon.getItemsTableAux().size() < 1) {
            utiliMsg.errorItemsTableNull();
        } else {
            if (salon.getTableAux().isBill() == false) {
                char[] pass = utiliMsg.requestMod();
                boolean perm = utili.requiredPerm(pass);
                if (perm) {
                    gifter(salon);
                }
            } else {
                utiliMsg.errorBillSend();
            }
        }
    }

    private void gifter(Salon salon) {
        salon.setItemsTableAux(salon.getTableAux().getOrder());
        GiftSelector gs = new GiftSelector(salon);
        gs.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        gs.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void giftBacker(Itemcard ic, Salon salon) throws Exception {
        salon.getItemsGift().add(ic);
        salon.getTableAux().setGifts(salon.getItemsGift());
        salon.setItemsTableAux(ss.itemTableLesser(salon.getTableAux().getOrder(), ic));
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        utiliMsg.cargaGift(ic.getName());
        setTableItems(salon);
        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal());
        daoI.downActiveItemOrderTable(ic, salon.getTableAux());
        daoI.saveItemGiftTable(ic, salon.getTableAux());
        daoT.updateTableTotal(salon.getTableAux());
        jButExtSetter(salon);
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        salon.setEnabled(true);
    }

//DISCOUNT.............................................................................................................
//DISCOUNT.............................................................................................................    
    public void actionButtonDiscount(Salon salon) {
        if (salon.getItemsTableAux().size() < 1) {
            utiliMsg.errorItemsTableNull();
        } else {
            if (salon.getDiscount() == 0) {
                if (salon.getTableAux().isBill() == false) {
                    char[] pass = utiliMsg.requestMod();
                    boolean perm = utili.requiredPerm(pass);
                    if (perm) {
                        discounter(salon);
                    }
                } else {
                    utiliMsg.errorBillSend();
                }
            } else {
                utiliMsg.errorDiscountRepeat();
            }
        }
    }

    private void discounter(Salon salon) {
        if (salon.getItemsPartialPaid().size() > 0) {
            salon.setItemsPartialPaidNoDiscount(salon.getItemsPartialPaid());
            salon.setItemsPartialPaid(new ArrayList<Itemcard>());
            salon.getTableAux().setPartialPayedND(salon.getItemsPartialPaidNoDiscount());
            salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
        }
        BillDiscounter bd = new BillDiscounter(salon);
        bd.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        bd.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void discountBacker(int disc, Salon salon) throws Exception {
        salon.setDiscount(disc);
        salon.getTableAux().setDiscount(disc);
        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());
        daoT.updateTableDiscount(salon.getTableAux());
        jButExtSetter(salon);
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        setTableItems(salon);
        salon.setEnabled(true);
    }

//PARTIAL PAY..........................................................................................................
//PARTIAL PAY..........................................................................................................
    public JPanel returnPanelPartial(Salon salon) {
        JPanel panelPartial = new JPanel();
        panelPartial.setLayout(null);
        panelPartial.setBounds(altoUnit, altoUnit * 64, anchoUnit * 21 + altoUnit, altoUnit * 5);
        panelPartial.setBackground(bluLg);

        salon.setButPartialPay(utiliGraf.button3("PAGO PARCIAL", altoUnit, altoUnit * 1, anchoUnit * 8));
        salon.getButPartialPay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getItemsTableAux().size() == 0) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        partialPayer(salon);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPartial.add(salon.getButPartialPay());

        salon.getLabelPartialPay().setText("Pagado: $0.0");
        salon.getLabelPartialPay().setBounds(anchoUnit * 10, altoUnit, anchoUnit * 10, altoUnit * 3);
        panelPartial.add(salon.getLabelPartialPay());
        return panelPartial;
    }

    private void partialPayer(Salon salon) {
        PartialPayer pp = new PartialPayer(salon);
        pp.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        pp.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void partialPayTaker(ArrayList<Itemcard> itemsPayed, Salon salon) throws Exception {
        salon.getJbtAux().setBackground(viol);
        for (int i = 0; i < itemsPayed.size(); i++) {
            salon.setItemsTableAux(ss.itemTableLesser(salon.getItemsTableAux(), itemsPayed.get(i)));
        }
        salon.getItemsPartialPaid().addAll(itemsPayed);
        salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());
        salon.getTableAux().setToPay(true);
        daoT.updateToPay(salon.getTableAux());
        for (Itemcard ic : itemsPayed) {
            daoI.saveItemPayedTable(ic, salon.getTableAux());
            daoI.downActiveItemOrderTable(ic, salon.getTableAux());
        }
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        double payed = ss.partialBillPayed(salon.getTableAux());
        salon.getLabelPartialPay().setText("Pagado: $" + (payed));
        jButExtSetter(salon);
        setTableItems(salon);
        salon.setEnabled(true);
    }

    public void totalPayTaker(ArrayList<Itemcard> itemsPayed, Salon salon) throws Exception {
        salon.getItemsTableAux().addAll(salon.getItemsPartialPaid());
        salon.getTableAux().setOrder(salon.getItemsTableAux());
        salon.setItemsPartialPaid(new ArrayList<Itemcard>());
        salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
        daoI.downActiveItemPayedTableAll(salon.getTableAux());
        daoI.upActiveItemOrderTableAll(salon.getTableAux());

        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());

        salon.getTableAux().setOpen(false);
        daoT.updateTableOpen(salon.getTableAux());
        salon.getTableAux().setToPay(false);
        daoT.updateToPay(salon.getTableAux());
        salon.getLabelCuenta().setText(salon.getTotal() + "");
        salon.setEnabled(true);
    }

//TABLE CLOSER.........................................................................................................
//TABLE CLOSER.........................................................................................................
    public void tableClose(Salon salon) throws Exception {
        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal());
        daoT.updateTableTotal(salon.getTableAux());
        salon.getTableAux().setBill(true);
        daoT.updateTableBill(salon.getTableAux());
        jButExtSetter(salon);
        salon.getLabelTotalParcial().setText("Total $:");
        salon.getLabelCuenta().setText("" + salon.getTotal());
        salon.getLabelTip().setText("Prop.: " + Math.round(salon.getTotal() * 0.1));
        double tot = salon.getTotal() + Math.round(salon.getTotal() * 0.1);
        salon.getLabelTotal().setText("Total: " + tot);

        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBackground(red);
        }

        if (salon.getJbbAux() != null) {
            salon.getJbbAux().setBackground(red);
        }

        if (salon.getJbdAux() != null) {
            salon.getJbdAux().setBackground(red);
            salon.getJbdSAux().setBackground(red);
        }

        salon.getButCloseTable().setText("CONFIRMAR PAGO");
        salon.setEnabled(true);
    }

    public void moneyKind(Salon salon, boolean end, ArrayList<Itemcard> itemsPayed, boolean toPay, double amountToPay) {
        salon.getTableAux().setToPay(toPay);
        MoneyType mt = new MoneyType(salon, end, itemsPayed, amountToPay);
        mt.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        mt.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

    public void amountsTypes(ArrayList<Double> amounts, boolean endex, ArrayList<Itemcard> itemsPayed, String comments, Salon salon) throws Exception {
        double amountC = amounts.get(0);
        double amountE = amounts.get(1);
        salon.getTableAux().setAmountCash(salon.getTableAux().getAmountCash() + amountC);
        salon.getTableAux().setAmountElectronic(salon.getTableAux().getAmountElectronic() + amountE);
        daoT.updateTableMountCash(salon.getTableAux());
        daoT.updateTableMountElectronic(salon.getTableAux());

        if (itemsPayed != null) {
            if (endex == true) {
                totalPayTaker(itemsPayed, salon);
            } else {
                partialPayTaker(itemsPayed, salon);
            }
        } else {
            salon.getItemsTableAux().addAll(salon.getItemsPartialPaid());
//            salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
            salon.getTableAux().setOrder(salon.getItemsTableAux());
            salon.setTotal(ss.countBill(salon.getTableAux()));

            salon.getTableAux().setTotal(salon.getTotal());
            daoT.updateTableTotal(salon.getTableAux());
            salon.getTableAux().setOpen(false);
            daoT.updateTableOpen(salon.getTableAux());
            salon.getLabelCuenta().setText(salon.getTotal() + "");
            if (salon.getTableAux().isToPay() == true) {
                salon.getTableAux().setToPay(false);
            }
            daoT.updateTableOpen(salon.getTableAux());
            salon.getTableAux().setComments(comments);
            daoT.updateComments(salon.getTableAux());
        }

        if (endex == true) {
            salon.setItemsMntr(sim.downOpenIMon(salon.getItemsMntr(), salon.getTableAux()));
            tablePaid(salon);
        }
        salon.setEnabled(true);
    }

//Pago de cuenta y cierre de mesa
    public void tablePaid(Salon salon) throws Exception {
        jButExtSetter(salon);
        resetTableFull(salon);
        setTableItems(salon);
        salon.setEnabled(true);
    }

//Ingreso a error
    public void errorTaker(Salon salon) {
        ErrorTableCount etc = new ErrorTableCount(salon);
        etc.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        etc.setAlwaysOnTop(true);
        salon.setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//Monto faltante por cash
    public void errorMountBacker(double errorBack, String cause, Salon salon, double cash, double elec) throws Exception {
        if (salon.getItemsPartialPaid().size() > 0) {
            salon.getItemsTableAux().addAll(salon.getItemsPartialPaid());
            salon.getTableAux().setOrder(salon.getItemsTableAux());
            salon.setItemsPartialPaid(new ArrayList<Itemcard>());
            salon.getTableAux().setPartialPayed(salon.getItemsPartialPaid());
            daoI.downActiveItemPayedTableAll(salon.getTableAux());
            daoI.upActiveItemOrderTableAll(salon.getTableAux());
            salon.getTableAux().setToPay(false);
            daoT.updateToPay(salon.getTableAux());
        }

//        salon.setAmoutnCash(cash);
        salon.getTableAux().setAmountCash(salon.getTableAux().getAmountCash() + cash);
        daoT.updateTableMountCash(salon.getTableAux());

//        salon.setAmountElectronic(elec);
        salon.getTableAux().setAmountElectronic(salon.getAmountElectronic() + elec);
        daoT.updateTableMountElectronic(salon.getTableAux());

        salon.setTotal(ss.countBill(salon.getTableAux()));
        salon.getTableAux().setTotal(salon.getTotal() - errorBack);
        daoT.updateTableTotal(salon.getTableAux());

        salon.setError(errorBack);
        salon.getTableAux().setError(salon.getError());
        daoT.updateError(salon.getTableAux());

        salon.getTableAux().setComments(cause);
        daoT.updateComments(salon.getTableAux());

        salon.getTableAux().setOpen(false);
        daoT.updateTableOpen(salon.getTableAux());

        tablePaid(salon);
        utiliMsg.cargaError();
        salon.setEnabled(true);
    }

//PANEL COUNT..........................................................................................................    
//PANEL COUNT..........................................................................................................
    public JPanel returnPanelCount(Salon salon) {
        JPanel panelCount = new JPanel();
        panelCount.setLayout(null);
        panelCount.setBounds(altoUnit, altoUnit * 78, anchoUnit * 21 + altoUnit, altoUnit * 10);
        panelCount.setBackground(narUlg);

        salon.setLabelTotalParcial(utiliGraf.labelTitleBacker2("Parcial $:"));
        salon.getLabelTotalParcial().setBounds(5, altoUnit * 2, anchoUnit * 6, altoUnit * 3);
        panelCount.add(salon.getLabelTotalParcial());

        salon.setLabelCuenta(utiliGraf.labelTitleBackerA3("00,0"));
        salon.getLabelCuenta().setBounds(anchoUnit * 7, altoUnit * 2, anchoUnit * 15, altoUnit * 4);
        salon.getLabelCuenta().setBackground(viol);
        panelCount.add(salon.getLabelCuenta());

        salon.setLabelTip(utiliGraf.labelTitleBacker3("Prop.: $ 00,0"));
        salon.getLabelTip().setBounds(5, altoUnit * 7, anchoUnit * 10, altoUnit * 2);
        panelCount.add(salon.getLabelTip());

        salon.setLabelTotal(utiliGraf.labelTitleBacker3("Total: $ 00,0"));
        salon.getLabelTotal().setBounds(anchoUnit * 11, altoUnit * 7, anchoUnit * 10, altoUnit * 2);
        panelCount.add(salon.getLabelTotal());
        return panelCount;
    }

//FUNCIONES GENERALES--------------------------------------------------------------------------------------------------        
//FUNCIONES GENERALES--------------------------------------------------------------------------------------------------        
//FUNCIONES GENERALES--------------------------------------------------------------------------------------------------        
//FUNCIONES GENERALES--------------------------------------------------------------------------------------------------         
    public void resetTableValues(Salon salon) throws Exception {
        salon.setItemsTableAux(new ArrayList<Itemcard>());//items a cobrar de la mesa
        salon.setItemsGift(new ArrayList<Itemcard>()); //items obsequiados
        salon.setItemsPartialPaid(new ArrayList<Itemcard>()); // items cobrados por pago parcial
        salon.setItemsPartialPaidNoDiscount(new ArrayList<Itemcard>()); // items cobrados anted de aplicar descuento
        salon.setWaiterAux(null);
        salon.setTableAux(null);
        salon.setTotal(0);
        salon.setError(0);
        salon.setDiscount(0);
        salon.setPriceCorrection(0);
        setTableItems(salon);
        salon.getCheckBoxIndic().setSelected(false);
        salon.getSpinnerUnitsItem().setValue(1);
        salon.getLabelTotalParcial().setText("Parcial $:");
        salon.getLabelCuenta().setText("0.00");
        salon.getLabelTip().setText("Prop: $0.00");
        salon.getLabelTotal().setText("Total: $0.00");
        salon.getLabelPartialPay().setText("Pagado: $0.00");
        salon.getLabelOrder().setText("MESA:--");
        salon.getLabelWaiter().setText("Mozo: --");
        salon.getButCloseTable().setText("CERRAR ORDEN");
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBorder(null);
            if (salon.getJbtAux().isOpenJBT() == true) {
                if (salon.getItemsTableAux().size() > 0) {
                    if (salon.getTableAux().isBill() == true) {
                        salon.getButCloseTable().setText("CONFIRMAR PAGO");
                    } else {
                        salon.getButCloseTable().setText("CERRAR CUENTA");
                    }
                }
            }
        }
    }

    public void jButExtSetter(Salon salon) {
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setTable(salon.getTableAux());

            for (int i = 0; i < salon.getTableButtons().size(); i++) {
                if (salon.getTableButtons().get(i).getNum() == salon.getJbtAux().getNum()) {
                    salon.getTableButtons().set(i, salon.getJbtAux());
                }
            }
        }

        if (salon.getJbbAux() != null) {
            salon.getJbbAux().setTable(salon.getTableAux());
            for (int i = 0; i < salon.getBarrButtons().size(); i++) {
                if (salon.getBarrButtons().get(i).getNum() == salon.getJbbAux().getNum()) {
                    salon.getBarrButtons().set(i, salon.getJbbAux());
                }
            }
        }

        if (salon.getJbdAux() != null) {
            salon.getJbdAux().setTable(salon.getTableAux());
            for (int i = 0; i < salon.getDeliButtons().size(); i++) {
                if (salon.getDeliButtons().get(i).getNum() == salon.getJbdAux().getNum()) {
                    salon.getDeliButtons().set(i, salon.getJbdAux());
                }
            }
        }
    }

    public void tableFullerProp(Salon salon) {
        salon.setItemsTableAux(salon.getTableAux().getOrder());
        salon.setItemsGift(salon.getTableAux().getGifts());
        salon.setItemsPartialPaid(salon.getTableAux().getPartialPayed());
        salon.setItemsPartialPaidNoDiscount(salon.getTableAux().getPartialPayedND());
        salon.setWaiterAux(salon.getTableAux().getWaiter());
        salon.setDiscount(salon.getTableAux().getDiscount());
        salon.setPriceCorrection(salon.getTableAux().getPriceCorrection());
        salon.setTotal(salon.getTableAux().getTotal());
        salon.setError(salon.getTableAux().getError());
        setTableItems(salon);
        if (salon.getItemsPartialPaid().size() > 0) {
            double payed = ss.partialBillPayed(salon.getTableAux());
            salon.getLabelPartialPay().setText("Pagado: $" + (payed));
        }

        if (salon.getTableAux().isBill() == true) {
            salon.getLabelTotalParcial().setText("Total $:");
            salon.getLabelTip().setText("Prop.: " + Math.round(salon.getTotal() * 0.1));
            double tot = Math.round(salon.getTotal() * 0.1) + salon.getTotal();
            salon.getLabelTotal().setText("Total: " + tot);
        } else {
            salon.getLabelTotalParcial().setText("Parcial $:");
        }

        if (salon.getJbtAux() != null) {
            salon.getLabelWaiter().setText("Mozo: " + salon.getWaiterAux().getName() + " " + utili.strShorter(salon.getWaiterAux().getLastName(), 2).toUpperCase());
            String nameT = salon.getTableAux().getPos() + salon.getTableAux().getNum();
            salon.getLabelOrder().setText("MESA:" + nameT);
            if (salon.getJbtAux().isOpenJBT() == false) {
                salon.getButCloseTable().setText("CERRAR ORDEN");
            } else {
                if (salon.getTableAux().isBill() == true) {
                    salon.getButCloseTable().setText("CONFIRMAR PAGO");
                } else {
                    salon.getButCloseTable().setText("CERRAR CUENTA");
                }
            }
        }

        if (salon.getJbbAux() != null) {
            salon.getLabelWaiter().setText("Cajero: " + salon.getUser().getName() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
            salon.getLabelOrder().setText("BARRA: B" + salon.getTableAux().getNum());
        }

        if (salon.getJbdAux() != null) {
            salon.getLabelWaiter().setText("Cajero: " + salon.getUser().getName() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
            salon.getLabelOrder().setText("DELIV.: D" + salon.getTableAux().getNum());
        }

        salon.getLabelCuenta().setText(salon.getTotal() + "");
    }

    public void actionButtonCloseTable(Salon salon) throws Exception {
        if (salon.getTableAux() == null) {
            utiliMsg.errorTableNull();
        } else {
            if (salon.getItemsTableAux().size() < 1) {
                resetTableFull(salon);
            } else {
                if (salon.getTableAux().isBill() == false) {
                    boolean confirm = utiliMsg.cargaConfirmarCierre();
                    if (confirm) {
                        tableClose(salon);
                    }
                } else {
                    moneyKind(salon, true, null, false, salon.getTotal());
                }
            }
        }
    }

    public void resetTableFull(Salon salon) throws Exception {
        if (salon.getJbtAux() != null) {
            salon.getJbtAux().setBackground(narUlg);
            salon.getJbtAux().setTable(null);
            salon.getJbtAux().setOpenJBT(false);
        }

        if (salon.getJbbAux() != null) {
            if (salon.getJbbAux().isOpenJBB() == false && salon.getTableAux().isBill() == false) {
                int barrBIndex = -1;
                for (int i = 0; i < salon.getBarrButtons().size(); i++) {
                    if (salon.getJbbAux().getNum() == salon.getBarrButtons().get(i).getNum()) {
                        barrBIndex = i;
//                        jbbAux.setVisible(false);
                    }
                }
                salon.getBarrButtons().remove(barrBIndex);
                salon.getPanelBarrBut().removeAll();
                barrButUpdater(salon);
            } else {
                salon.getJbbAux().setBackground(narUlgX);
                salon.getJbbAux().setEnabled(false);
                salon.getJbbAux().setOpenJBB(false);
                salon.getJbbAux().setText("B" + salon.getJbbAux().getTable().getNum() + " Cerrado");
            }
        }

        if (salon.getJbdAux() != null) {
            if (salon.getJbdAux().isOpenJBD() == false && salon.getTableAux().isBill() == false) {
                int deliBIndex = -1;
                for (int i = 0; i < salon.getDeliButtons().size(); i++) {
                    if (salon.getJbdAux().getNum() == salon.getDeliButtons().get(i).getNum()) {
                        deliBIndex = i;
                    }
                }
                salon.getDeliButtons().remove(deliBIndex);
                salon.getPanelDeliBut().removeAll();
                deliButUpdater(salon);
            } else {
                salon.getJbdAux().setBackground(narUlgX);
                salon.getJbdSAux().setBackground(narUlgX);
                salon.getJbdAux().setEnabled(false);
                salon.getJbdSAux().setEnabled(false);
                salon.getJbdAux().setOpenJBD(false);
                salon.getJbdAux().setText("D" + salon.getJbdAux().getTable().getNum() + " Cerrado");
            }
        }

        jButExtSetter(salon);
        resetTableValues(salon);

        utiliMsg.cargaTableErase();
    }

    public void tableManager(ArrayList<Table> tables, Salon salon) throws Exception {
        for (int i = 0; i < tables.size(); i++) {
            Table tab = tables.get(i);
            if (tab.getPos().equals("barra")) {
                String text = tab.getPos() + " " + tab.getNum();
                JButtonBarr b = new JButtonBarr(tab.getPos(), tab.getNum(), tab, text, tab.isOpen());
                salon.getBarrButtons().add(0, b);
                salon.setJbbAux(b);
                salon.getJbbAux().setOpenJBB(true);
                if (salon.getJbbAux().getTable().isBill()) {
                    salon.getJbbAux().setBackground(red);
                } else {
                    salon.getJbbAux().setBackground(green);
                }
                salon.getBarrButtons().set(0, b);

            } else if (tab.getPos().equals("delivery")) {
                String text = tab.getPos() + " " + tab.getNum();
                Delivery deli = daoD.findDeliveryByTableId(tab.getId());
                JButtonDelivery d = new JButtonDelivery(tab.getPos(), tab.getNum(), tab, text, tab.isOpen(), deli);
                salon.getDeliButtons().add(d);

                salon.setJbdAux(d);
                salon.getJbdAux().setOpenJBD(true);
                if (salon.getJbdAux().getTable().isBill()) {
                    salon.getJbdAux().setBackground(red);
                } else {
                    salon.getJbdAux().setBackground(green);
                }
            } else {
                for (int j = 0; j < salon.getTableButtons().size(); j++) {
                    if (tab.getNum() == salon.getTableButtons().get(j).getNum()) {
                        salon.getTableButtons().get(j).setTable(tab);
                        salon.setJbtAux(salon.getTableButtons().get(j));
                        salon.getJbtAux().setOpenJBT(true);
                        if (salon.getJbtAux().getTable().isBill()) {
                            salon.getJbtAux().setBackground(red);
                        } else {
                            salon.getJbtAux().setBackground(green);
                        }
                    }
                }
            }
        }

        if (salon.getBarrButtons().size() > 0) {
            barrButUpdater(salon);
        }

        if (salon.getDeliButtons().size() > 0) {
            barrButUpdater(salon);
        }

        if (salon.getTableButtons().size() > 0) {
            barrButUpdater(salon);
        }
    }
}
