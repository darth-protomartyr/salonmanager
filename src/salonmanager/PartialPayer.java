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
    ArrayList<Itemcard> itemsPartialToPay = new ArrayList<Itemcard>();
//    ArrayList<Itemcard> itemsPartialPayed = new ArrayList<Itemcard>();
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
        itemsToPay = new ArrayList<Itemcard>(itemsAux);
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
        labelItemsTP.setBounds(20, 40, 180, 20);
        panelPpal.add(labelItemsTP);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3W("Items Seleccionados");
        labelItemsSel.setBounds(205, 40, 165, 20);
        panelPpal.add(labelItemsSel);

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
        jSPtoPay.setBounds(20, 65, 165, 100);
        panelPpal.add(jSPtoPay);

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
        jSPPayed.setBounds(205, 65, 165, 100);
        panelPpal.add(jSPPayed);

        JPanel panelSubTotal = new JPanel();
        panelSubTotal.setBounds(145, 170, 100, 30);
        panelPpal.add(panelSubTotal);

        labelSubTotal = utiliGraf.labelTitleBacker2W("Subtotal $:");
        labelSubTotal.setBounds(40, 170, 100, 30);
        panelPpal.add(labelSubTotal);

        labelPrice = utiliGraf.labelTitleBacker2("0.0");
//        labelPrice.setBounds(100, 170, 40, 30);
        panelSubTotal.add(labelPrice);

        labelRest = utiliGraf.labelTitleBacker3W("Resto $:" + total);
        labelRest.setBounds(260, 160, 130, 25);
        panelPpal.add(labelRest);

        labelTip = utiliGraf.labelTitleBacker3W("Prop $:" + Math.round(subTotal / 10));
        labelTip.setBounds(260, 180, 130, 25);
        panelPpal.add(labelTip);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluSt);
        panelBut.setBounds(0, 210, 390, 50);
        panelPpal.add(panelBut);

        butPartialIn = utiliGraf.button1("Pago Parcial", 206, 580, 270);
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
        panelBut.add(butPartialIn);

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
        labelPrice.setText(ss.billPartial(itemsPartialToPay, discount) + "");
        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsToPay);
        listToPay.setModel(modeloLista2);
        subTotal = ss.billPartial(itemsPartialToPay, discount);
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

        subTotal = ss.billPartial(itemsPartialToPay, discount);
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
            toPay += iPTP.get(i).getPrice();
        }
        return toPay;
    }
}