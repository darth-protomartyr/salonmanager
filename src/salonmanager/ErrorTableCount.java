package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Double.parseDouble;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class ErrorTableCount extends FrameWindow {

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

    double total = 0;
    double amountCash = 0;
    double amountElec = 0;
    double sum = 0;
    double wrong = 0;
    JLabel labelLoss = new JLabel();
    JTextField fieldAmountCash = new JTextField();
    JTextField fieldAmountElectronic = new JTextField();
    JCheckBox checkTotalLossMount = new JCheckBox("");
    JTextArea textAreaCause = new JTextArea();
    Salon salon = null;
    JLabel labelTotalMount = null;

    public ErrorTableCount(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        salon.setTotal(ss.countBill(salon.getTableAux()));
        total = salon.getTotal();
        setTitle("Error Mesa");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBacker1W("ERROR");
        panelLabel.add(labelTit);

        JPanel panelAmount = new JPanel();
        panelAmount.setLayout(null);
        panelAmount.setBackground(bluLg);
        panelAmount.setBounds(15, 40, 350, 220);

        JLabel labelAmount = utiliGraf.labelTitleBacker1("Ingrese el monto recibido");
        labelAmount.setBounds(60, 5, 240, 30);
        panelAmount.add(labelAmount);

        JLabel labelEf = utiliGraf.labelTitleBacker2("Efect. $:");
        labelEf.setBounds(10, 40, 70, 25);
        panelAmount.add(labelEf);

        fieldAmountCash.setBounds(75, 40, 90, 25);
        fieldAmountCash.setFont(new Font("Arial", Font.PLAIN, 20));
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
                checkTotalLossMount.setSelected(false);
                updateMount();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                checkTotalLossMount.setSelected(false);
                updateMount();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelAmount.add(fieldAmountCash);

        JLabel labelEl = utiliGraf.labelTitleBacker2("Transf. $:");
        labelEl.setBounds(175, 40, 115, 25);
        panelAmount.add(labelEl);

        fieldAmountElectronic.setBounds(250, 40, 90, 25);
        fieldAmountElectronic.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldAmountElectronic.addKeyListener(new KeyListener() {
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

        fieldAmountElectronic.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                checkTotalLossMount.setSelected(false);
                updateMount();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                checkTotalLossMount.setSelected(false);
                updateMount();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelAmount.add(fieldAmountElectronic);

        JLabel labelLossCheck = utiliGraf.labelTitleBacker3("Pérdida Total");
        labelLossCheck.setBounds(220, 70, 120, 20);
        panelAmount.add(labelLossCheck);

        checkTotalLossMount.setBounds(310, 70, 20, 20);
        checkTotalLossMount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateMount();
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    updateMount();
                }
            }
        });
        panelAmount.add(checkTotalLossMount);

        labelLoss = utiliGraf.labelTitleBacker3("No hay monto faltante");
        labelLoss.setBounds(50, 70, 230, 20);
        panelAmount.add(labelLoss);

        JLabel labelComment = utiliGraf.labelTitleBacker3("Causa del Error (obligatorio):");
        labelComment.setBounds(70, 140, 250, 20);
        panelPpal.add(labelComment);

        textAreaCause.setRows(3);
        textAreaCause.setColumns(5);
        textAreaCause.setLineWrap(true);
        textAreaCause.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textAreaCause.setFont(newFont);
        textAreaCause.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textAreaCause);
        scrollPane.setBounds(70, 160, 250, 55);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butErrorMount = utiliGraf.button2("Confirmar Error", 90, 185, 160);
        butErrorMount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String cause = textAreaCause.getText();
                    if (cause.equals("")) {
                        utiliMsg.errorEmptyCause();
                    } else {
                        butErrorMountActionPerformed(cause);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelAmount.add(butErrorMount);
        panelPpal.add(panelAmount);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                salon.setEnabled(true);
                dispose();
            }
        });
    }

//FUNCIONES------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//Funciones por Monto--------------------------------------------------------------------------------------------------    
    private void updateMount() {
        String erC = fieldAmountCash.getText();
        String erE = fieldAmountElectronic.getText();

        boolean error = false;
        boolean totalLoss = checkTotalLossMount.isSelected();

        if (erC.equals("")) {
            erC = "0";
        }

        if (erE.equals("")) {
            erE = "0";
        }

        try {
            if (totalLoss == true) {
                wrong = total;
                fieldAmountCash.setText("");
            } else {
                if (!erC.equals("")) {
                    amountCash = parseDouble(erC);
                }

                if (!erE.equals("")) {
                    amountElec = parseDouble(erE);
                }
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        sum = amountCash + amountElec;

        if (sum <= total) {
            wrong = total - sum;
        } else {
            labelLoss.setText("El monto supera el Total");
            error = true;
        }

        if (error == false) {
            if (wrong == total) {
                labelLoss.setText("El monto faltante es total");
//                fieldAmountCash.setText(total + "");
            } else if (wrong == 0) {
                labelLoss.setText("No hay monto faltante");
            } else {
                labelLoss.setText("Monto Faltante: $" + wrong);
            }
        }
    }

    private void butErrorMountActionPerformed(String cause) throws Exception {
        boolean error = false;
        boolean totalLoss = checkTotalLossMount.isSelected();

        if (totalLoss == true) {
            wrong = total;
        }

        if (wrong > total) {
            utiliMsg.errorTotalLess();
            error = true;
        }

        if (wrong < 1) {
            utiliMsg.errorMountNull();
            error = true;
        }

        if (error == false) {
            boolean confirm = utiliMsg.cargaConfirmarMontoError(total - wrong, wrong);
            if (confirm) {
                salon.setTotal(total);
                salon.getTableAux().setTotal(salon.getTotal());
                ss.errorMountBacker(wrong, cause, salon, amountCash, amountElec);
                utiliGrafSal.tablePaid(salon);
                dispose();
            }
        } else {
            resetFrame();
        }
    }

    private void resetFrame() {
        checkTotalLossMount.setSelected(false);
        fieldAmountCash.setText("");
    }

}
