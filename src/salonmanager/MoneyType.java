package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.Itemcard;
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
    ArrayList<Itemcard> itemsPayed = null;

    JLabel labelSubTotal = new JLabel();
    JLabel labelMixed = new JLabel();
    JTextField fieldAmountCash = new JTextField();
    JTextArea textAreaCause = new JTextArea();
    JButton butInPartialCash = new JButton();
    JButton butCashIn = new JButton();
    JButton butElectronicIn = new JButton();
    JButton butMixedIn = new JButton();
    JButton butBack = new JButton();

    public MoneyType(Salon sal, boolean end, ArrayList<Itemcard> itemsPayed1, double amountToPay) {
        salon = sal;
        sm.addFrame(this);
        tab = salon.getTable();
        itemsPayed = itemsPayed1;
        endex = end;
        total = amountToPay;
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
        labelMount.setBounds(100, 35, 190, 25);
        panelPpal.add(labelMount);

        JLabel labelComment = utiliGraf.labelTitleBacker3("Ingrese un comentario(opcional): ");
        labelComment.setBounds(70, 65, 250, 20);
        panelPpal.add(labelComment);

        textAreaCause.setRows(3);
        textAreaCause.setColumns(5);
        textAreaCause.setLineWrap(true);
        textAreaCause.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textAreaCause);
        scrollPane.setBounds(70, 85, 250, 55);
        panelPpal.add(scrollPane);

        butCashIn = utiliGraf.button2("Pago Total Efectivo", 85, 150, 220);
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

        butElectronicIn = utiliGraf.button2("Pago Total Electronico", 85, 190, 220);
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

        butMixedIn = utiliGraf.button2("Pago Mixto", 85, 230, 220);
        butMixedIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMixedIn.setVisible(false);
                    butCashIn.setVisible(false);
                    butElectronicIn.setVisible(false);
                    mixedPay = true;
                    butMoneyTypeActionPerformed(3);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMixedIn);

        labelMixed = utiliGraf.labelTitleBacker2("Ingrese el monto que recibió en efectivo:");
        labelMixed.setBounds(40, 150, 340, 25);
        labelMixed.setVisible(false);
        panelPpal.add(labelMixed);

        fieldAmountCash.setBounds(75, 180, 100, 30);
        fieldAmountCash.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldAmountCash.setVisible(false);
        panelPpal.add(fieldAmountCash);

        butInPartialCash = utiliGraf.button2("Confirmar", 185, 180, 130);
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

        butBack = utiliGraf.button2("Volver", 140, 220, 100);
        butBack.setVisible(false);
        butBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMixedIn.setVisible(true);
                    butCashIn.setVisible(true);
                    butElectronicIn.setVisible(true);
                    labelMixed.setVisible(false);
                    fieldAmountCash.setVisible(false);
                    butInPartialCash.setVisible(false);
                    butBack.setVisible(false);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butBack);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sal.enableSalon();
                dispose();
            }
        });
    }

    private void butMoneyTypeActionPerformed(int type) throws Exception {
        switch (type) {
            case 1:
                amounts.set(0, total);
                salon.amountsTypes(amounts, endex, itemsPayed, getCommentIn());
                dispose();
                break;
            case 2:
                amounts.set(1, total);
                salon.amountsTypes(amounts, endex, itemsPayed, getCommentIn());
                dispose();
                break;
            case 3:
                labelMixed.setVisible(true);
                fieldAmountCash.setVisible(true);
                butInPartialCash.setVisible(true);
                butBack.setVisible(true);
                break;
        }
        System.out.println(tab);

    }

    private void butMoneyCashActionPerformed() throws Exception {
        String amountCash = fieldAmountCash.getText();
        double cash = 0;
        try {
            cash = parseDouble(amountCash);
            amounts.set(0, cash);
            amounts.set(1, total - cash);
            salon.amountsTypes(amounts, endex, itemsPayed, getCommentIn());
            dispose();
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            fieldAmountCash.setText("");
        }
    }

    private String getCommentIn() {
        String comment = textAreaCause.getText();
        return comment;
    }
}
