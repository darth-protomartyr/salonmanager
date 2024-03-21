package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelMonitorBarr;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAOItemMonitor;
import salonmanager.persistencia.DAOTable;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Monitor extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();
    DAOTable daoT = new DAOTable();
    DAOItemMonitor daoIM = new DAOItemMonitor();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    Salon salon = null;
    ArrayList<Table> tabs = new ArrayList<Table>();
    ArrayList<ItemMonitor> itemsMnrOld = new ArrayList<ItemMonitor>();
    ArrayList<ItemMonitor> itemsMnrNew = new ArrayList<ItemMonitor>();

    User user = null;

    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    JSpinner spinnerDiscount = null;

    JPanel panelHeader = new JPanel(null);
    JPanel panelIMon = new JPanel(null);
    JLabel labelOrder = new JLabel();
    JLabel labelInit = new JLabel();
    JLabel labelOnWorks = new JLabel();
    JLabel labelReady = new JLabel();
    JLabel labelOtw = new JLabel();
    JLabel labelItemsMnr = new JLabel();
    private Timer timer;
    int orderKind = 0;
    String k = "";

    public Monitor(Salon sal) throws Exception {
        salon = sal;
        user = salon.getUser();
        itemsMnrOld = daoIM.getItemsMonitorOpen();

        sm.addFrame(this);
        setTitle("Seguimiento");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("SEGUIMIENTO DE ÓRDENES");
        labelTit.setBounds(10, 20, 400, 30);
        panelPpal.add(labelTit);

        JButtonMetalBlu butMonitTabs = utiliGraf.button2("Órdenes de Mesa", anchoUnit * 2, altoUnit * 8, anchoUnit * 15);
        butMonitTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMonitOrders(1);
                } catch (Exception ex) {
                    Logger.getLogger(Ingresar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMonitTabs);

        JButtonMetalBlu butMonitBarrs = utiliGraf.button2("Órdenes de Barra", anchoUnit * 19, altoUnit * 8, anchoUnit * 14);
        butMonitBarrs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMonitOrders(2);
                } catch (Exception ex) {
                    Logger.getLogger(Ingresar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMonitBarrs);

        JButtonMetalBlu butMonitDelis = utiliGraf.button2("Órdenes de Delivery", anchoUnit * 35, altoUnit * 8, anchoUnit * 15);
        butMonitDelis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMonitOrders(3);
                } catch (Exception ex) {
                    Logger.getLogger(Ingresar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMonitDelis);

        JPanel panelIn = new JPanel();
        panelIn.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelIn.setBackground(narUlg);
        panelIn.setBounds(0, altoUnit * 15, anchoFrameHalf, alturaFrame - altoUnit * 10);
        panelPpal.add(panelIn);

        panelHeader = panelHeaderBacker();
        panelIn.add(panelHeader);

        panelIMon.setBackground(narUlg);
        panelIMon.setPreferredSize(new Dimension(600, altoUnit * 67));

        labelItemsMnr = utiliGraf.labelTitleBackerA2("<html>Seleccione un tipo de Orden</html>");
        if (itemsMnrOld.size() < 1) {
            labelItemsMnr.setText("<html>No hay pedidos en curso</html>");
        }
        labelItemsMnr.setBounds(130, 70, 400, 150);
        panelIMon.add(labelItemsMnr);

        JScrollPane scrPaneBarr = new JScrollPane(panelIMon);
        scrPaneBarr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrPaneBarr.setPreferredSize(new Dimension(620, altoUnit * 67));
        panelIn.add(scrPaneBarr);

        initTimer();

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

    private JPanel panelHeaderBacker() {
        panelHeader.setPreferredSize(new Dimension(620, altoUnit * 5));
        panelHeader.setBackground(narUlg);

        JLabel labelOrderKind = utiliGraf.labelTitleBacker1("Orden");
        labelOrderKind.setBounds(20, 10, 70, 15);
        panelHeader.add(labelOrderKind);

        labelInit = utiliGraf.labelTitleBacker3("");
        labelInit.setBounds(165, 10, 70, 15);
        panelHeader.add(labelInit);

        labelOnWorks = utiliGraf.labelTitleBacker3("");
        labelOnWorks.setBounds(310, 10, 70, 15);
        panelHeader.add(labelOnWorks);

        labelReady = utiliGraf.labelTitleBacker3("");
        labelReady.setBounds(455, 10, 70, 15);
        panelHeader.add(labelReady);

        if (orderKind != 2) {
            panelHeader.add(labelOtw);
        }

        return panelHeader;
    }

    private void panelHeaderSetter() {
        labelInit.setText("En espera");

        labelOnWorks.setText("Preparando");
        if (orderKind != 2) {
            labelOnWorks.setBounds(250, 10, 100, 15);
        } else {
            labelOnWorks.setBounds(280, 10, 100, 15);
        }

        labelReady.setText("Listo");
        if (orderKind != 2) {
            labelReady.setBounds(360, 10, 80, 15);
        } else {
            labelReady.setBounds(420, 10, 80, 15);
        }

        if (orderKind != 2) {
            if (orderKind == 3) {
                labelOtw.setVisible(true);
                labelOtw.setText("Enviado");
            } else if (orderKind == 1) {
                labelOtw.setVisible(true);
                labelOtw.setText("Servido");
            }
            labelOtw.setBounds(440, 10, 70, 15);
        } else {
            labelOtw.setVisible(false);
        }
    }

    private void butMonitOrders(int kind) throws Exception {
        panelIMon.removeAll();
        orderKind = kind;

        if (kind == 3) {
            k = "delivery";
        } else if (kind == 2) {
            k = "barra";
        } else {
            k = "mesa";
        }

        panelHeaderSetter();

        panelItemMomFiller();

    }

    private void initTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (itemsMnrNew.size() > 0) {
                        panelItemMomFiller();
                        salon.setItemsMnr(itemsMnrOld);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 15000); // Realizar consulta cada 10 segundos (ajusta el intervalo según tus necesidades)
    }

    private void panelItemMomFiller() throws Exception {
        itemsMnrNew = new ArrayList<ItemMonitor>();
        itemsMnrOld = daoIM.getItemsMonitorOpen();

        for (int i = 0; i < itemsMnrOld.size(); i++) {
            ItemMonitor im = itemsMnrOld.get(i);
            String ty = im.getPosIMon();
            boolean boolTy = im.isOpenItemMonitor();

            if (ty.equals(k) && boolTy) {
                itemsMnrNew.add(itemsMnrOld.get(i));
            }
        }

        panelIMon.removeAll();

        int size = itemsMnrNew.size();
        int height = 0;
        if ((size * 70) + 10 < altoUnit * 70) {
            height = altoUnit * 70;
        } else {
            height = (size * 70) + 10;
        }

        panelIMon.setPreferredSize(new Dimension(600, height));

        int round = 0;
        if (itemsMnrNew.size() > 0) {
            for (int i = 0; i < itemsMnrNew.size(); i++) {
                PanelMonitorBarr pmb = new PanelMonitorBarr(salon, itemsMnrNew.get(i), round);
                panelIMon.add(pmb);
                round += 1;
            }
        } else {
            labelItemsMnr = utiliGraf.labelTitleBackerA2("<html>No hay órdenes de " + k + " pendientes.<html>");
            labelItemsMnr.setBounds(70, 70, 500, 130);
            panelIMon.add(labelItemsMnr);
        }
        panelIMon.revalidate();
        panelIMon.repaint();
    }

}
