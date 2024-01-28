package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class CorrectorItem extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);

    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    JLabel labelComboItems = new JLabel();
    JComboBox comboItems = new JComboBox();
    Table tab = new Table();
    JButton butInGift = new JButton();
    Salon salon = null;
    ArrayList<Itemcard> itemsOrder = new ArrayList<Itemcard>();
    ArrayList<Itemcard> itemsGift = new ArrayList<Itemcard>();
    ArrayList<Itemcard> itemsPayed = new ArrayList<Itemcard>();
    ArrayList<Itemcard> itemsPayedNoDiscount = new ArrayList<Itemcard>();
    ArrayList<Itemcard> itemsCombo = new ArrayList<Itemcard>();

    int numArray = 1;
    String itemCategory = "a Pagar";

    public CorrectorItem(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        tab = new Table(salon.getTableAux());
        setTitle("Corrector de Items");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Corrector de Ingresos");
        panelLabel.add(labelTit);

        itemsOrder = tab.getOrder();
        itemsGift = tab.getGifts();
        itemsPayed = tab.getPartialPayed();
        itemsPayedNoDiscount = tab.getPartialPayedND();
        itemsCombo = new ArrayList<Itemcard>();

        JButton butItemsOrder = utiliGraf.button3("Items A Pagar", 30, 75, 150);
        butItemsOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemsComboChanger(itemsOrder, 1);
//                    labelComboItems.setText("lista de pedidos:");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butItemsOrder);

        JButton butItemsGift = utiliGraf.button3("Items Obsequiados", 30, 105, 150);
        butItemsGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemsComboChanger(itemsGift, 2);
//                    labelComboItems.setText("lista de Obsequios:");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butItemsGift);

        JButton butItemsPayed = utiliGraf.button3("Items pagados", 30, 135, 150);
        butItemsPayed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemsComboChanger(itemsPayed, 3);
//                    labelComboItems.setText("lista de pagados:");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butItemsPayed);

//        JButton butItemsPayedNoDiscount = utiliGraf.button3("Items pag. s/descuento", 30, 165, 150);
//        butItemsPayedNoDiscount.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                try {
//                    itemsComboChanger(itemsPayedNoDiscount, 4);
////                    labelComboItems.setText("lista de pagados s/DTO:");
//                } catch (Exception ex) {
//                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        panelPpal.add(butItemsPayedNoDiscount);

        JLabel labelComboItems1 = utiliGraf.labelTitleBacker2("Seleccione un item");
        labelComboItems1.setBounds(200, 70, 170, 20);
        panelPpal.add(labelComboItems1);

        JLabel labelComboItems2 = utiliGraf.labelTitleBacker2("para quitar de la");
        labelComboItems2.setBounds(200, 90, 170, 20);
        panelPpal.add(labelComboItems2);

        labelComboItems = utiliGraf.labelTitleBacker2("lista de pedidos:");
        labelComboItems.setBounds(200, 110, 170, 20);
        panelPpal.add(labelComboItems);

        itemsComboChanger(itemsOrder, 1);
        comboItems.setBounds(200, 140, 150, 40);
        panelPpal.add(comboItems);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluSt);
        panelBut.setBounds(0, 210, 390, 40);
        panelPpal.add(panelBut);

        butInGift = utiliGraf.button1("Eliminar", 206, 580, 270);
        butInGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCorrectionActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(CorrectorItem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBut.add(butInGift);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sal.enableSalon();
                dispose();
            }
        });
    }

    private void itemsComboChanger(ArrayList<Itemcard> items, int num) {
        comboItems.setModel(utili.itemsComboModelReturn(items));
        numArray = num;
        switch (num) {
            case 1:
                labelComboItems.setText("lista de pedidos:");
                break;
            case 2:
                labelComboItems.setText("lista de obsequios:");
                break;
            case 3:
                labelComboItems.setText("lista de pagados:");
                break;
        }
    }

    private void butCorrectionActionPerformed() throws Exception {
        int i = comboItems.getSelectedIndex();
        Itemcard ic = new Itemcard();
        switch (numArray) {
            case 1:
                ic = itemsOrder.get(i);
                salon.correctItems(ic, numArray);
                break;
            case 2:
                ic = itemsGift.get(i);
                salon.correctItems(ic, numArray);
                break;
            case 3:
                ic = itemsPayed.get(i);
                salon.correctItems(ic, numArray);
                break;
        }
        dispose();

    }

}
