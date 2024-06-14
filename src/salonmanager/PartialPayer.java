package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class PartialPayer extends FrameWindow {

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

    Table tab = new Table();
    Salon salon = null;
    ArrayList<Itemcard> itemsToPay = null;
    ArrayList<Itemcard> itemsPartialToPay = new ArrayList<>();
//    ArrayList<Itemcard> itemsPartialPayed = new ArrayList<>();
    Itemcard itemAux = new Itemcard();
    double subTotal = 0;
    double total = 0;
    int discount = 0;

    JList listToPay = new JList();
    JList listPartialToPay = new JList();
    JLabel labelSubTotal = new JLabel();
    JLabel labelPrice = new JLabel();
    JLabel labelRest = new JLabel();
    JLabel labelTip = new JLabel();
    JButtonMetalBlu butPartialIn = new JButtonMetalBlu();

    public PartialPayer(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        Table table = salon.getTableAux();
        tab = new Table(table);
        total = tab.getTotal();
        discount = tab.getDiscount();
        ArrayList<Itemcard> itemsAux = tab.getOrder();
        itemsToPay = new ArrayList<>(itemsAux);
        setTitle("Pago Parcial");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("PAGO PARCIAL");
        panelLabel.add(labelTit);

        JLabel labelItemsTP = utiliGraf.labelTitleBacker3W("Items a pagar");
        labelItemsTP.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 10, altoUnit * 3);
        panelPpal.add(labelItemsTP);

        listToPay.setModel(utili.itemListModelReturnMono(itemsToPay));
        listToPay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selItemToPay();
                }
            }
        });
        JScrollPane jSPtoPay = new JScrollPane(listToPay);
        jSPtoPay.setBounds(anchoUnit * 2, altoUnit * 9, anchoUnit * 12, altoUnit * 13);
        panelPpal.add(jSPtoPay);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3W("Items Seleccionados");
        labelItemsSel.setBounds(anchoUnit * 15, altoUnit * 6, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelItemsSel);
        
        listPartialToPay.setModel(utili.itemListModelReturnMono(itemsPartialToPay));
        listPartialToPay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    unSelItemToPay();
                }
            }
        });

        JScrollPane jSPPayed = new JScrollPane(listPartialToPay);
        jSPPayed.setBounds(anchoUnit * 15, altoUnit * 9, anchoUnit * 12, altoUnit * 13);
        panelPpal.add(jSPPayed);

        labelSubTotal = utiliGraf.labelTitleBacker2W("Subtotal $:");
        labelSubTotal.setBounds(anchoUnit * 3, altoUnit * 23, anchoUnit * 8, altoUnit * 4);
        panelPpal.add(labelSubTotal);

        JPanel panelSubTotal = new JPanel();
        panelSubTotal.setBounds(anchoUnit * 10, altoUnit * 23, anchoUnit * 8, altoUnit * 4);
        panelPpal.add(panelSubTotal);
        
        labelPrice = utiliGraf.labelTitleBacker2("0.0");
        labelPrice.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 8, altoUnit * 4);
        panelSubTotal.add(labelPrice);

        labelRest = utiliGraf.labelTitleBacker3W("Resto $: " + total);
        labelRest.setBounds(anchoUnit * 19, altoUnit * 23, anchoUnit * 10, altoUnit * 2);
        panelPpal.add(labelRest);

        labelTip = utiliGraf.labelTitleBacker3W("Prop $: " + Math.round(subTotal / 10));
        labelTip.setBounds(anchoUnit * 19, altoUnit * 25, anchoUnit * 10, altoUnit * 2);
        panelPpal.add(labelTip);

        butPartialIn = utiliGraf.button1("Pago Parcial", anchoUnit * 8, altoUnit * 28, anchoUnit * 12);
        butPartialIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butPartialInActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(PartialPayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butPartialIn);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(this);
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

    private void selItemToPay() {
        String selectedValue = (String) listToPay.getSelectedValue();
        Itemcard itemAux = utili.ItemcardBacker(selectedValue, itemsToPay);
        itemsPartialToPay.add(itemAux);
        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsPartialToPay);
        listPartialToPay.setModel(modeloLista1);

        Iterator<Itemcard> iterador = itemsToPay.iterator();
        int counter = 0;
        while (iterador.hasNext()) {
            Itemcard ic = iterador.next();
            if (ic.getId() == itemAux.getId()) {
                while (counter < 1) {
                    iterador.remove();
                    counter += 1;
                }
            }
        }
        labelPrice.setText(ss.billPartial(itemsPartialToPay, discount, salon) + "");
        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsToPay);
        listToPay.setModel(modeloLista2);
        subTotal = ss.billPartial(itemsPartialToPay, discount, salon);
        labelPrice.setText(subTotal + "");
        labelRest.setText("Resto:" + (total - subTotal));
        labelTip.setText("Prop:" + (Math.round(subTotal / 10)));
        if ((total - subTotal) == 0) {
            butPartialIn.setText("Finalizar Pago");
        }
    }

    private void unSelItemToPay() {
        String selectedValue = (String) listPartialToPay.getSelectedValue();
        Itemcard itemAux = utili.ItemcardBacker(selectedValue, itemsPartialToPay);
        itemsToPay.add(itemAux);
        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsToPay);
        listToPay.setModel(modeloLista1);
        int counter = 0;
        Iterator<Itemcard> iterador = itemsPartialToPay.iterator();
        while (iterador.hasNext()) {
            Itemcard ic = iterador.next();
            if (ic.getId() == itemAux.getId()) {
                while (counter < 1) {
                    iterador.remove();
                    counter += 1;
                }
            }
        }

        subTotal = ss.billPartial(itemsPartialToPay, discount, salon);
        labelPrice.setText(subTotal + "");
        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsPartialToPay);
        listPartialToPay.setModel(modeloLista2);
        labelPrice.setText(subTotal + "");
        labelRest.setText("Resto: " + (total - subTotal));
        labelTip.setText("Prop.: " + Math.round(subTotal / 10));
        if ((total - subTotal) > 0) {
            butPartialIn.setText("Pago Parcial");
        }
    }

    private void butPartialInActionPerformed() throws Exception {
        double amountToPay = pricer(itemsPartialToPay);
        if (itemsPartialToPay.size() > 0) {
            if ((total - subTotal == 0)) {
                boolean toPay = false;
                utiliGrafSal.moneyKind(salon, true, itemsPartialToPay, toPay, amountToPay);
                dispose();
            } else {
                boolean toPay = true;
                utiliGrafSal.moneyKind(salon, false, itemsPartialToPay, toPay, amountToPay);
                dispose();
            }
        } else {
            utiliMsg.errorItemNull();
        }
    }

    private double pricer(ArrayList<Itemcard> iPTP) {
        double toPay = 0;
        for (int i = 0; i < iPTP.size(); i++) {
            toPay += utili.priceMod(iPTP.get(i), salon);
        }
        return toPay;
    }
}