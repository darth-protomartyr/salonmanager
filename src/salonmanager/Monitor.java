package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.ItemMonitor;
import salonmanager.entidades.PanelMonitorBarr;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
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

    JButton butInGift = new JButton();
    JSpinner spinnerDiscount = null;
//    Salon salon = null;

    ArrayList<Table> tabs = new ArrayList<Table>();
    ArrayList<ItemMonitor> itemsMnrOld = new ArrayList<ItemMonitor>();
    ArrayList<ItemMonitor> itemsMnrNew = new ArrayList<ItemMonitor>();


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

        public Monitor() throws Exception {

//      public Monitor(Salon sal) throws Exception {
//      salon = sal;
        itemsMnrOld= daoIM.getItemsMonitorOpen();

        sm.addFrame(this);
        setTitle("Seguimiento");
        PanelPpal panelPpal = new PanelPpal(anchoFrameHalf, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Seguimiento de órdenes");
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        JButton butMonitTabs = utiliGraf.button2("Órdenes de Mesa", anchoUnit * 2, altoUnit * 8, anchoUnit * 15);
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

        JButton butMonitBarrs = utiliGraf.button2("Órdenes de Barra", anchoUnit * 19, altoUnit * 8, anchoUnit * 14);
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

        JButton butMonitDelis = utiliGraf.button2("Órdenes de Delivery", anchoUnit * 35, altoUnit * 8, anchoUnit * 15);
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
        panelIn.setBounds(0, altoUnit * 15, anchoFrameHalf, altoUnit * 75);
        panelPpal.add(panelIn);

        panelHeader = panelHeaderBacker();
        panelIn.add(panelHeader);

        panelIMon.setBackground(narUlg);
        panelIMon.setPreferredSize(new Dimension(600, altoUnit * 67));

        labelItemsMnr = utiliGraf.labelTitleBackerA2("<html>Seleccione un tipo de Orden</html>");
        labelItemsMnr.setBounds(130, 70, 400, 150);
        panelIMon.add(labelItemsMnr);

        JScrollPane scrPaneBarr = new JScrollPane(panelIMon);
        scrPaneBarr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scrPaneBarr.setPreferredSize(new Dimension(620, altoUnit * 67));
        panelIn.add(scrPaneBarr);

//        initTimer();
    }
    
    
    
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Monitor();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        String k = "";
        itemsMnrNew = new ArrayList<ItemMonitor>();
        orderKind = kind;

        if (kind == 3) {
            k = "delivery";
        } else if (kind == 2) {
            k = "barra";
        } else {
            k = "mesa";
        }

        panelHeaderSetter();

        for (int i = 0; i < itemsMnrOld.size(); i++) {
            ItemMonitor im = itemsMnrOld.get(i);
            String ty = im.getPosIMon();
            boolean boolTy = im.isOpenItemMonitor();
            
            if (ty.equals(k) && boolTy) {
                itemsMnrNew.add(itemsMnrOld.get(i));
            }

//            if (!k.equals("delivery") && !k.equals("barra")) {
//                itemsMnrNew.add(itemsMnrOld.get(i));
//            }
        }

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
                PanelMonitorBarr pmb = new PanelMonitorBarr(itemsMnrNew.get(i), round);
//              PanelMonitorBarr pmb = new PanelMonitorBarr(salon, itemsMnrNew.get(i), round);

                panelIMon.add(pmb);
                round += 1;
            }
        } else {
            labelItemsMnr = utiliGraf.labelTitleBackerA2("<html>No hay órdenes de " + k + " pendientes.<html>");
            labelItemsMnr.setBounds(70, 70, 500, 130);
            panelIMon.add(labelItemsMnr);
        }
        revalidate();
        repaint();
    }

    private void initTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    
                    for (int i = 0; i < itemsMnrNew.size(); i++) {
                        
                    }
 
                    
                } catch (Exception ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
//                salon.setItemsMnr(itemsMnrOld);
            }
        }, 0, 15000); // Realizar consulta cada 10 segundos (ajusta el intervalo según tus necesidades)
    }

    
    private ArrayList<ItemMonitor> updateItemMnr() throws Exception {
        ArrayList<ItemMonitor> newItemsMnr = new ArrayList<ItemMonitor>();
        daoIM.getItemsMonitorOpen();
        return newItemsMnr;
    }
}
