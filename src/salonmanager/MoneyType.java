package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
//    boolean mixedPay = false;
    ArrayList<Double> amounts = new ArrayList<Double>();
    ArrayList<Itemcard> itemsPayed = null;
    int cashMix = 0;

    JLabel labelSubTotal = new JLabel();
    JLabel labelMixed = new JLabel();
    JLabel labelChange = new JLabel();
    JTextField fieldAmountCash = new JTextField();
    JTextArea textArea = new JTextArea();
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

        textArea.setRows(3);
        textArea.setColumns(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textArea.setFont(newFont);
        textArea.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(70, 85, 250, 55);
        panelPpal.add(scrollPane);

        butCashIn = utiliGraf.button2("Pago Total Efectivo", 85, 150, 220);
        butCashIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {

//                    if (mixedPay == false) {
                    butMixedIn.setVisible(false);
                    butCashIn.setVisible(false);
                    butElectronicIn.setVisible(false);
                    butMoneyTypeActionPerformed(1);
//                    } else {
//                        utiliMsg.errorMixedPayUp();
//                    }
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
//                    if (mixedPay == false) {
                    butMoneyTypeActionPerformed(2);
//                    } else {
//                        utiliMsg.errorMixedPayUp();
//                    }
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
//                    mixedPay = true;
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

        fieldAmountCash.setBounds(40, 180, 120, 30);
        fieldAmountCash.setFont(new Font("Arial", Font.PLAIN, altoUnit * 3));
        fieldAmountCash.setVisible(false);
        fieldAmountCash.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.CHAR_UNDEFINED) {
                    e.consume(); // Consume la tecla para evitar que se procese como entrada
                }
                
                if (e.getKeyChar() == '-') {
                    e.consume();
                }
     

            }


            
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        fieldAmountCash.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                updateMount();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                updateMount();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelPpal.add(fieldAmountCash);

        labelChange = utiliGraf.labelTitleBacker2("");
        labelChange.setBounds(165, 180, 200, 30);
        labelChange.setVisible(false);
        panelPpal.add(labelChange);

        butInPartialCash = utiliGraf.button1("Confirmar Monto", 70, 220, 200);
        butInPartialCash.setVisible(false);
        butInPartialCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butInPartialCashActionPerformed(cashMix);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butInPartialCash);

        butBack = utiliGraf.button3("Volver", 290, 233, 70);
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
                    labelChange.setVisible(false);
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
                labelMixed.setVisible(true);
                fieldAmountCash.setVisible(true);
                butInPartialCash.setVisible(true);
                labelChange.setText("Vuelto: $0.00");
                labelChange.setVisible(true);
                butBack.setVisible(true);
                cashMix = 1;
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
                labelChange.setText("Transferencia: $0.00");
                labelChange.setVisible(true);
                butBack.setVisible(true);
                cashMix = 2;
                break;
        }
    }

    private void butInPartialCashActionPerformed(int num) throws Exception {
        String amountCash = fieldAmountCash.getText();
        double cash = 0;
        try {
            if (num == 1) {
                cash = parseDouble(amountCash);
                if (cash >= total) {
                    amounts.set(0, cash);
                    salon.amountsTypes(amounts, endex, itemsPayed, getCommentIn());
                    dispose();
                } else {
                    utiliMsg.errorInsufficientMount();
                    fieldAmountCash.setText("");
                }
            } else if (num == 2) {
                amounts.set(0, cash);
                amounts.set(1, total - cash);
                salon.amountsTypes(amounts, endex, itemsPayed, getCommentIn());
                dispose();
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            fieldAmountCash.setText("");
        }
    }

    private String getCommentIn() {
        String comment = textArea.getText();
        return comment;
    }

    private void updateMount() {
        String er = fieldAmountCash.getText();
        double paid = 0;
        boolean error = false;
        double change = 0;

        try {
            if (!er.equals("")) {
                paid = parseDouble(er);
                if (cashMix == 1) {
                    change = paid - total;
                } else if (cashMix == 2) {
                    change = total - paid;
                }
            }
        } catch (NumberFormatException e) {
            labelChange.setText("El dato no es numérico.");
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            if (cashMix == 1) {
                if (paid < total) {
                    labelChange.setText("Pago insuficiente.");
                } else {
                    labelChange.setText("Vuelto = $" + change + "");
                }
            } else if (cashMix == 2) {
                if (change > 0) {
                    labelChange.setText("Transferencia = $" + change + "");
                } else {
                    labelChange.setText("Transferencia innecesaria");
                }
            }
        }
    }
}
