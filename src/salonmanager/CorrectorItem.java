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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class CorrectorItem extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
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
    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    Salon salon = null;
    ArrayList<ItemCard> itemsOrder = new ArrayList<>();
    ArrayList<ItemCard> itemsGift = new ArrayList<>();
    ArrayList<ItemCard> itemsPayed = new ArrayList<>();
    ArrayList<ItemCard> itemsPayedNoDiscount = new ArrayList<>();
    ArrayList<ItemCard> itemsCombo = new ArrayList<>();

    int numArray = 1;
    String itemCategory = "a Pagar";

    public CorrectorItem(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        tab = new Table(salon.getTableAux());
        setTitle("Corrector de Items");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 4);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Corrector de Ingresos");
        panelLabel.add(labelTit);

        itemsOrder = tab.getOrder();
        itemsGift = tab.getGifts();
        itemsPayed = tab.getPartialPayed();
        itemsPayedNoDiscount = tab.getPartialPayedND();
        itemsCombo = new ArrayList<>();

        JLabel labelComboItems1 = utiliGraf.labelTitleBacker2W("<html>Seleccione una de las 3 listas<br>para quitar uno de los items</html>");
        labelComboItems.setHorizontalAlignment(SwingConstants.CENTER);
        labelComboItems1.setBounds(anchoUnit * 6, altoUnit * 4, anchoUnit * 20, altoUnit * 8);
        panelPpal.add(labelComboItems1);
        
        
        JButtonMetalBlu butItemsOrder = utiliGraf.button2("Items A Pagar", anchoUnit * 2, altoUnit * 13, anchoUnit * 13);
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

        JButtonMetalBlu butItemsGift = utiliGraf.button2("Items Obsequiados", anchoUnit * 2, altoUnit * 18, anchoUnit * 13);
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

        JButtonMetalBlu butItemsPayed = utiliGraf.button2("Items pagados", anchoUnit * 2, altoUnit * 23, anchoUnit * 13);
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



        itemsComboChanger(itemsOrder, 1);
        comboItems.setBounds(anchoUnit * 16, altoUnit * 17, anchoUnit * 12, altoUnit * 6);
        comboItems.setFont(salon.getFont4());
        panelPpal.add(comboItems);


        butInGift = utiliGraf.button1("Eliminar", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
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
        panelPpal.add(butInGift);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                salon.setEnabled(true);
                dispose();
            }
        });
        panelPpal.add(butSalir);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                salon.setEnabled(true);
                dispose();
            }
        });
    }

    private void itemsComboChanger(ArrayList<ItemCard> items, int num) {
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
        ItemCard ic = new ItemCard();
        switch (numArray) {
            case 1:
                ic = itemsOrder.get(i);
                ss.correctItems(ic, numArray, salon);
                break;
            case 2:
                ic = itemsGift.get(i);
                ss.correctItems(ic, numArray, salon);
                break;
            case 3:
                ic = itemsPayed.get(i);
                ss.correctItems(ic, numArray, salon);
                break;
        }
        utiliGrafSal.jButExtSetter(salon);
        utiliGrafSal.setTableItems(salon);
        dispose();
    }

}
