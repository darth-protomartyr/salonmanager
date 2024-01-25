/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.utilidades;

import java.awt.Color;
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
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.DeliveryTemplate;
import salonmanager.ItemSelector;
import salonmanager.Monitor;
import salonmanager.Salon;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.graphics.JButtonBarr;
import salonmanager.entidades.graphics.JButtonTable;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAODelivery;
import salonmanager.persistencia.DAOItemcard;
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

    public JPanel panelActualBacker(Salon salon) {
        JPanel panelActual = new JPanel();
        panelActual.setBounds(anchoUnit * 11, altoUnit * 3, anchoUnit * 17, altoUnit * 14);
        panelActual.setBackground(bluLg);
        panelActual.setLayout(null);

        JLabel labelUser = utiliGraf.labelTitleBacker3("Usuario: " + salon.getUser().getName().toUpperCase() + " " + utili.strShorter(salon.getUser().getLastName(), 2).toUpperCase());
        labelUser.setBounds(altoUnit, altoUnit, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelUser);

        JLabel labelSession = utiliGraf.labelTitleBacker3("");

        Timestamp timeInitSes = new Timestamp(new Date().getTime());
        if (timeInitSes != null) {
            labelSession.setText("Inicio Sesion: " + utili.friendlyDate(timeInitSes));
        } else {
            labelSession.setText("Sesion no iniciada.");
        }
        labelSession.setBounds(altoUnit, altoUnit * 4, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelSession);

        JLabel labelWorkshift = utiliGraf.labelTitleBacker3("");
        if (salon.getWorkshiftNow() != null) {
            Timestamp timeInitWork = salon.getWorkshiftNow().getWsOpen();
            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(timeInitWork));
        } else {
            labelWorkshift.setText("Turno no iniciado.");
        }
        labelWorkshift.setBounds(altoUnit, altoUnit * 7, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelWorkshift);

        salon.setButInitWorkshift(utiliGraf.button2("Abrir Turno", anchoUnit * 3, altoUnit * 9, anchoUnit * 11));
        salon.getButInitWorkshift().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (salon.getWorkshiftNow() == null) {
                        boolean confirm1 = utiliMsg.cargaConfirmarInicioTurno(salon.getUser().getName(), salon.getUser().getLastName());
                        if (confirm1 == true) {
                            salon.setWorkshiftNow(new Workshift(salon.getUser()));
                            daoW.saveWorkshift(salon.getWorkshiftNow());
                            sm.workshiftBacker(salon.getWorkshiftNow());
                            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(salon.getWorkshiftNow().getWsOpen()));
                            salon.getButInitWorkshift().setText("CERRAR TURNO");
                        }
                    } else {
                        ss.endWorkshift(salon);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelActual.add(salon.getButInitWorkshift());

        return panelActual;
    }

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

    private void resetTableValues(Salon salon) throws Exception {
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
//        comboItems.setSelectedIndex(itemsDB.size());
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

    private void setTableItems(Salon salon) {
        HashSet<Itemcard> itemsSet = new HashSet<Itemcard>(salon.getItemsTableAux());
        ArrayList<Itemcard> itemsAux = new ArrayList<Itemcard>(itemsSet);

        HashSet<Itemcard> partialSet = new HashSet<Itemcard>(salon.getItemsPartialPaid());
        ArrayList<Itemcard> partials = new ArrayList<Itemcard>(partialSet);

        HashSet<Itemcard> partialSetND = new HashSet<Itemcard>(salon.getItemsPartialPaidNoDiscount());
        ArrayList<Itemcard> partialsND = new ArrayList<Itemcard>(partialSetND);

        HashSet<Itemcard> giftSet = new HashSet<Itemcard>(salon.getItemsGift());
        ArrayList<Itemcard> gifts = new ArrayList<Itemcard>(giftSet);

        HashSet<Itemcard> totalSet = new HashSet<Itemcard>(itemsAux);
        ArrayList<Itemcard> totalItems = new ArrayList<Itemcard>(totalSet);

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
//        scrPaneBarr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        salon.getScrPaneBarr().setBounds(anchoUnit, altoUnit * 15, anchoUnit * 32, altoUnit * 55);

//                scrPaneBarr.setPreferredSize(new Dimension(anchoUnit * 32, altoUnit * 55));
//        scrPaneBarr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrPaneBarr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

    private void barrButUpdater(Salon salon) {
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

    private void jButExtSetter(Salon salon) {
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

    private void tableFullerProp(Salon salon) {
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

    private void panelDeliContainerSetter(Salon salon) {
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

            //Test
            salon.getDeliButtonsSees().get(i).setBounds(anchoUnit * 24, altoUnit * ((i * 10) + 1), anchoUnit * 6, altoUnit * 10);
            salon.getPanelDeliBut().add(salon.getDeliButtonsSees().get(i));
        }

        JScrollPane scrPaneDeli = new JScrollPane(salon.getPanelDeliBut());
        scrPaneDeli.setPreferredSize(new Dimension(anchoUnit * 32, altoUnit * 55));
        scrPaneDeli.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPaneDeli.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        salon.getPanelDeliContainer().add(scrPaneDeli);
    }

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

        /*        for (int i = 0; i < salon.getTableNum().size(); i++) {
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
                    salon.setNumBut( salon.getNumBut() + 1);
                    salon.getTableButtons().add(jbt);
                    panelB.add(jbt);
                }
            }
            panelB.setBounds(salon.getWUnit(), salon.getHUnit(), salon.getAnchoPane(), salon.getAlturaPane() + salon.getHUnit());
            salon.getPanelsPane().add(panelB);
        }

         */
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
                        if (salon.getJbtAux().isOpenJBT() == true) {
                            try {
                                resetTableValues(salon);
                            } catch (Exception ex) {
                                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
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
                            salon.setWaiter(0);
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

    private void itemCaptionBack(String capt, Salon salon) {
        ArrayList<Itemcard> aic = new ArrayList<Itemcard>();
        for (Itemcard ic : salon.getItemsDB()) {
            if (ic.getCaption().toLowerCase().equals(capt.toLowerCase())) {
                aic.add(ic);
            }
        }
        salon.getComboItems().setModel(utili.itemsComboModelReturn(aic));
    }

    private void butSelItemActionPerformed(String item, Salon salon) throws Exception {
        int u = (int) salon.getSpinnerUnitsItem().getValue();

        if (salon.isIndiBool() == true && u > 1) {
            utiliMsg.errorMultipleIndications();
            resetTableValues(salon);
        } else {
            if (salon.getItemsTableAux().size() < 1) {
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

}
