package salonmanager.entidades.graphics;

import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Table;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.Salon;
import salonmanager.IndicationsShower;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class PanelMonitorBarr extends JPanel {
    
    Graphics initCircle;
    Graphics cookCircle;
    Graphics readyCircle;
    Graphics finishCircle;
    Graphics interLine1;
    Graphics interLine2;
    Graphics interLine3;
    JPanel panelContentGr;
    int anchoUnit = 2;
    int altoUnit = 2;
    Color white = new Color(255, 255, 255);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    DAOTable daoT = new DAOTable();
    DAOItemcard daoI = new DAOItemcard();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    int type = 0;

    JLabel labelTCook = new JLabel();
    JLabel labelTReady = new JLabel();
    JLabel labelTOtw = new JLabel();

    Salon salon = null;
    User user = null;

    
    public PanelMonitorBarr(Salon sal, ItemMonitor itemM, int i) throws Exception {
        salon = sal;
        user = salon.getUser();
        ItemMonitor im = itemM;
        
//        user = itemM.getTableIMon().getWaiter();        

        if (im.getPosIMon().equals("barra")) {
            type = 2;
        } else if (im.getPosIMon().equals("delivery")) {
            type = 3;
        } else {
            type = 1;
        }

        int rounds = 70 * i;

        setBounds(10, 10 + rounds, 580, 60);
        setSize(580, 60);

        setLayout(new BorderLayout());
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(white);

                //1
                g.fillOval(170, 10, 30, 30);

                //2
                if (type == 2) {
                    g.fillOval(290, 10, 30, 30);
                } else {
                    g.fillOval(260, 10, 30, 30);
                }

                //3
                if (type == 2) {
                    g.fillOval(410, 10, 30, 30);
                } else {
                    g.fillOval(350, 10, 30, 30);
                }

                //4
                if (type != 2) {
                    g.fillOval(440, 10, 30, 30);
                }

                Graphics2D g2d = (Graphics2D) g;

                float lineWeight = 3.0f;
                BasicStroke stroke = new BasicStroke(lineWeight);
                g2d.setStroke(stroke);
                if (type == 2) {
                    g2d.drawLine(175, 25, 425, 25);
                } else {
                    g2d.drawLine(175, 25, 455, 25);
                }
                g.setColor(green);
                //1
                if (im.isInitIMon()) {
                    g.fillOval(175, 15, 20, 20);
                }

                //2
                if (im.isCookIMon()) {
                    if (type == 2) {
                        g.fillOval(295, 15, 20, 20);
                    } else {
                        g.fillOval(265, 15, 20, 20);
                    }
                }

                //3
                if (im.isReadyIMon()) {
                    if (type == 2) {
                        g.fillOval(415, 15, 20, 20);
                    } else {
                        g.fillOval(355, 15, 20, 20);
                    }
                }

                //4
                if (type != 2) {
                    if (im.isOtwIMon()) {
                        g.fillOval(445, 15, 20, 20);
                    }
                }
            }
        };
        panel.setBackground(bluLg);
        panel.setLayout(null);
        add(panel, BorderLayout.CENTER);

        JLabel labelOrder = utiliGraf.labelTitleBacker2(orderBacker(im));
        labelOrder.setBounds(10, 5, 180, 20);
        panel.add(labelOrder);

        JLabel labelItem = utiliGraf.labelTitleBacker3(im.getItemIMon().getName());
        labelItem.setBounds(10, 23, 180, 15);
        panel.add(labelItem);

        JLabel labelWaiter = utiliGraf.labelTitleBacker3("");
        labelWaiter.setBounds(10, 38, 180, 15);
        panel.add(labelWaiter);

        //1
        JLabel labelTWait = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateInitIMon()));
        labelTWait.setBounds(160, 40, 60, 15);
        panel.add(labelTWait);

        if (im.isCookIMon()) {
            JLabel labelTCook = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateCookIMon()));
            panel.add(labelTCook);
        }

        if (im.isReadyIMon()) {
            JLabel labelTReady = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateReadyIMon()));
            panel.add(labelTReady);
        }

        if (im.isOtwIMon()) {
            JLabel labelTOtw = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateOtwIMon()));
            panel.add(labelTOtw);
        }

        if (im.isInitIMon()) {
        }

        
        //2
        labelTCook = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateInitIMon()));
        panel.add(labelTCook);
        
        if (im.isCookIMon()) {
            
            if (type == 2) {
                labelTCook.setBounds(280, 40, 60, 15);
            } else {
                labelTCook.setBounds(250, 40, 60, 15);
            }
        }

        //3
        labelTReady = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateInitIMon()));
        panel.add(labelTReady);
        
        if (im.isReadyIMon()) {
            if (type == 2) {
                labelTReady.setBounds(400, 40, 60, 15);
            } else {
                labelTReady.setBounds(340, 40, 60, 15);
            }
        }

        //4
        labelTOtw = utiliGraf.labelTitleBacker4(utili.friendlyHour(im.getDateInitIMon()));
        panel.add(labelTOtw);
        
        if (type != 2) {
            if (im.isOtwIMon()) {
                labelTOtw.setBounds(430, 40, 60, 15);
            }
        }

        if (type == 1) {
            labelWaiter.setText("Mozo: " + im.getTableIMon().getWaiter().getName() + " " + utili.strShorter(im.getTableIMon().getWaiter().getName(), 2));
        } else if (type == 2) {
            labelWaiter.setText("Cajero: " + user.getName() + " " + utili.strShorter(user.getLastName(), 2));
        } else {
            labelWaiter.setText("Delivery: " + im.getTableIMon().getWaiter().getName() + " " + utili.strShorter(im.getTableIMon().getWaiter().getLastName(), 1) + ".");
        }

        JButton butIndi = utiliGraf.button3("Indicaciones", 480, 13, 85);
        butIndi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new IndicationsShower(im);
                } catch (Exception ex) {
                    Logger.getLogger(PanelMonitorBarr.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.add(butIndi);
        
        if(im.getIndications().length()<1) {
            butIndi.setEnabled(false);
        }
    }

    private String orderBacker(ItemMonitor im) {
        String st = "";
        Table tab = im.getTableIMon();
        if (type == 2) {
            st = "Barra P" + tab.getNum();
        } else if (type == 3) {
            st = "Delivery P" + tab.getNum();
        } else {
            st = "Mesa " + tab.getNum();
        }
        return st;
    }
}
