package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class MoneyType extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    SalonManager sm = new SalonManager();

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);

    Salon salon = null;
    boolean endex = false; //cierre o no de la operación
    double total = 0;
    double cash = 0;
    double electronic = 0;
    ArrayList<Double> amounts = new ArrayList<>();
    ArrayList<ItemCard> itemsPayed = null;
    int cashMix = 0;

    JLabel labelMixed = new JLabel();
    JLabel labelChange = new JLabel();
    JTextField fieldAmountCash = new JTextField();
    JTextArea textArea = new JTextArea();
    JButtonMetalBlu butInPartialCash = new JButtonMetalBlu();
    JButtonMetalBlu butCashIn = new JButtonMetalBlu();
    JButtonMetalBlu butElectronicIn = new JButtonMetalBlu();
    JButtonMetalBlu butMixedIn = new JButtonMetalBlu();
    JButtonMetalBlu butBack = new JButtonMetalBlu();

    public MoneyType(Salon sal, boolean end, ArrayList<ItemCard> itemsPayed1, double amountToPay) {
        salon = sal;
        sm.addFrame(this);
        itemsPayed = itemsPayed1;
        endex = end;
        total = amountToPay;
        amounts.add(cash);
        amounts.add(electronic);
        setTitle("Modo de Pago");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("SELECCIONAR MODO DE PAGO");
        panelLabel.add(labelTit);

        JLabel labelMount = utiliGraf.labelTitleBacker2W("Monto a pagar $: " + total);
        labelMount.setBounds(anchoUnit * 5, altoUnit * 5, anchoUnit * 29, altoUnit * 3);
        panelPpal.add(labelMount);

        JLabel labelComment = utiliGraf.labelTitleBacker3W("Ingrese un comentario(opcional): ");
        labelComment.setBounds(anchoUnit * 5, altoUnit * 8, anchoUnit * 19, altoUnit * 3);

        panelPpal.add(labelComment);

        textArea.setRows(3);
        textArea.setColumns(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textArea.setFont(newFont);
        textArea.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(anchoUnit * 5, altoUnit * 11, anchoUnit * 19, altoUnit * 8);
        panelPpal.add(scrollPane);

        butCashIn = utiliGraf.button2("Pago Total Efectivo", anchoUnit * 6, altoUnit * 21, anchoUnit * 17);

        butCashIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMixedIn.setVisible(false);
                    butCashIn.setVisible(false);
                    butElectronicIn.setVisible(false);
                    butMoneyTypeActionPerformed(1);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butCashIn);

        butElectronicIn = utiliGraf.button2("Pago Total Electronico", anchoUnit * 6, altoUnit * 26, anchoUnit * 17);
        butElectronicIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMoneyTypeActionPerformed(2);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butElectronicIn);

        butMixedIn = utiliGraf.button2("Pago Mixto", anchoUnit * 6, altoUnit * 31, anchoUnit * 17);
        butMixedIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMixedIn.setVisible(false);
                    butCashIn.setVisible(false);
                    butElectronicIn.setVisible(false);
                    butMoneyTypeActionPerformed(3);
                } catch (Exception ex) {
                    Logger.getLogger(MoneyType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMixedIn);

        labelMixed = utiliGraf.labelTitleBacker2W("Ingrese el monto que recibió en efectivo:");
        labelMixed.setBounds(anchoUnit * 3, altoUnit * 20, anchoUnit * 25, altoUnit * 3);        
        labelMixed.setVisible(false);
        panelPpal.add(labelMixed);

        fieldAmountCash.setBounds(anchoUnit * 5, altoUnit * 23, anchoUnit * 9, altoUnit * 4);
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

        labelChange = utiliGraf.labelTitleBacker2W("");
        labelChange.setBounds(anchoUnit * 15, altoUnit * 23, anchoUnit * 12, altoUnit * 4);
                                
        labelChange.setVisible(false);
        panelPpal.add(labelChange);

        butInPartialCash = utiliGraf.button1("Confirmar Monto", anchoUnit * 7, altoUnit * 29, anchoUnit * 15);
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

        butBack = utiliGraf.button3("Volver", anchoUnit * 23, altoUnit * 29, anchoUnit * 5);
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

    private void butMoneyTypeActionPerformed(int type) throws Exception {
        switch (type) {
            case 1:
                labelMixed.setVisible(true);
                fieldAmountCash.setVisible(true);
                butInPartialCash.setVisible(true);
                labelChange.setText("Vuelto $: 0.00");
                labelChange.setVisible(true);
                butBack.setVisible(true);
                cashMix = 1;
                break;
            case 2:
                amounts.set(1, total);
                utiliGrafSal.amountsTypes(amounts, endex, itemsPayed, getCommentIn(), salon);
                dispose();
                break;
            case 3:
                labelMixed.setVisible(true);
                fieldAmountCash.setVisible(true);
                butInPartialCash.setVisible(true);
                labelChange.setText("Transferencia $: 0.00");
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
            cash = parseDouble(amountCash);
            if (num == 1) {
                if (cash >= total) {
                    cash = total;
                    amounts.set(0, cash);
                    utiliGrafSal.amountsTypes(amounts, endex, itemsPayed, getCommentIn(), salon);
                    dispose();
                } else {
                    utiliMsg.errorInsufficientMount();
                    fieldAmountCash.setText("");
                }
            } else if (num == 2) {
                if (cash < total) {
                    amounts.set(0, cash);
                    amounts.set(1, total - cash);
                    utiliGrafSal.amountsTypes(amounts, endex, itemsPayed, getCommentIn(), salon);
                    dispose();
                } else {
                    utiliMsg.errorUnnecesaryOp();
                }
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            fieldAmountCash.setText("");
        }
    }

    private String getCommentIn() {
        String comment = textArea.getText() + "<br>";
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
                    labelChange.setText("Vuelto $= " + change + "");
                }
            } else if (cashMix == 2) {
                if (change > 0) {
                    labelChange.setText("Transferencia $= " + change + "");
                } else {
                    labelChange.setText("Transferencia innecesaria");
                }
            }
        }
    }
}
