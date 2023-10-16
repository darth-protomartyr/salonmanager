package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class MoneyType extends FrameWindow {

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

    Table tab = new Table();
    Salon salon = null;
    boolean endex = false; //cierre o no de la operación
    double total = 0;
    double cash = 0;
    double electronic = 0;
    boolean mixedPay = false;
    ArrayList<Double> amounts = new ArrayList<Double>();
    ArrayList<ItemCarta> itemsPayed = null;
    
    JLabel labelSubTotal = new JLabel();
    JLabel labelMixed = new JLabel();
    JTextField fieldAmountCash = new JTextField();
    JButton butInPartialCash = new JButton();
    JButton butCashIn = new JButton();
    JButton butElectronicIn = new JButton();
    JButton butMixedIn = new JButton();

    public MoneyType(Salon sal, boolean end, ArrayList<ItemCarta> itemsPayed1) {
        salon = sal;
        sm.addFrame(this);
        Table table = salon.getTable();
        itemsPayed = itemsPayed1;
        tab = new Table(table);
        endex = end;
        total = tab.getTotal();
        amounts.add(cash);
        amounts.add(electronic);
        setTitle("Modos de Pago");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Seleccionar Modo de Pago");
        panelLabel.add(labelTit);

        JLabel labelMount = utiliGraf.labelTitleBacker2("Monto a pagar: $" + total);
        labelMount.setBounds(100, 40, 190, 25);
        panelPpal.add(labelMount);

        butCashIn = utiliGraf.button2("Pago Total Efectivo", 85, 65, 220);
        butCashIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (mixedPay == false) {
                        butMoneyTypeActionPerformed(1);
                        dispose();
                    } else {
                        utiliMsg.errorMixedPayUp();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butCashIn);

        butElectronicIn = utiliGraf.button2("Pago Total Electronico", 85, 105, 220);
        butElectronicIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (mixedPay == false) {
                        butMoneyTypeActionPerformed(2);
                    } else {
                        utiliMsg.errorMixedPayUp();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butElectronicIn);

        butMixedIn = utiliGraf.button2("Pago Mixto", 85, 145, 220);
        butMixedIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    mixedPay = true;
                    butMoneyTypeActionPerformed(3);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMixedIn);

        labelMixed = utiliGraf.labelTitleBacker2("Ingrese el monto que recibió en efectivo:");
        labelMixed.setBounds(40, 180, 340, 25);
        labelMixed.setVisible(false);
        panelPpal.add(labelMixed);

        fieldAmountCash.setBounds(75, 210, 100, 30);
        fieldAmountCash.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldAmountCash.setVisible(false);
        panelPpal.add(fieldAmountCash);

        butInPartialCash = utiliGraf.button2("Confirmar", 185, 210, 130);
        butInPartialCash.setVisible(false);
        butInPartialCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMoneyCashActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butInPartialCash);
    }

    private void butMoneyTypeActionPerformed(int type) throws Exception {
        switch (type) {
            case 1:
                amounts.set(0, total);
                salon.amountsTypes(amounts, endex, itemsPayed);
                dispose();
                break;
            case 2:
                amounts.set(1, total);
                salon.amountsTypes(amounts, endex, itemsPayed);
                dispose();
                break;
            case 3:
                labelMixed.setVisible(true);
                fieldAmountCash.setVisible(true);
                butInPartialCash.setVisible(true);
                break;
        }
    }

    private void butMoneyCashActionPerformed() throws Exception {
        String amountCash = fieldAmountCash.getText();
        double cash = parseDouble(amountCash);
        amounts.set(0, cash);
        amounts.set(1, total - cash);
        salon.amountsTypes(amounts, endex, itemsPayed);
        dispose();
    }
}
