package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
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
import salonmanager.servicios.ServicioItemSale;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class ErrorTableCount extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioSalon ss = new ServicioSalon();
    ServicioItemSale sis = new ServicioItemSale();
    SalonManager sm = new SalonManager();

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color whi = new Color(255, 255, 255);

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

    public ErrorTableCount(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        salon.setTotal(ss.countBill(salon.getTableAux(), salon, false));
        total = salon.getTotal();
        setTitle("Error Mesa");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBackerA4W("ERROR");
        panelLabel.add(labelTit);        

        JLabel labelAmount = utiliGraf.labelTitleBacker1W("Ingrese el monto recibido");
        labelAmount.setBounds(anchoUnit * 5, altoUnit * 5, anchoUnit * 20, altoUnit * 4);
        panelPpal.add(labelAmount);

        JLabel labelEf = utiliGraf.labelTitleBacker2W("Efect. $:");
        labelEf.setBounds(anchoUnit, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        panelPpal.add(labelEf);

        fieldAmountCash.setBounds(anchoUnit * 6, altoUnit * 10, anchoUnit * 7, altoUnit * 4);
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
        panelPpal.add(fieldAmountCash);

        JLabel labelEl = utiliGraf.labelTitleBacker2W("Transf. $:");
        labelEl.setBounds(anchoUnit * 14, altoUnit * 10, anchoUnit * 6, altoUnit * 4);
        panelPpal.add(labelEl);

        fieldAmountElectronic.setBounds(anchoUnit * 20, altoUnit * 10, anchoUnit * 7, altoUnit * 4);
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
        panelPpal.add(fieldAmountElectronic);

        checkTotalLossMount.setText("Perdida Total");
        
        checkTotalLossMount.setBounds(anchoUnit * 19, altoUnit * 15, anchoUnit * 8, altoUnit * 3);
        checkTotalLossMount.setBackground(bluSt);
        checkTotalLossMount.setForeground(whi);
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
        panelPpal.add(checkTotalLossMount);

        labelLoss = utiliGraf.labelTitleBacker3W("Ingrese Información requerida");
        labelLoss.setBounds(anchoUnit * 2, altoUnit * 15, anchoUnit * 16, altoUnit * 3);
        panelPpal.add(labelLoss);

        JLabel labelComment = utiliGraf.labelTitleBacker3W("Causa del Error (obligatorio):");
        labelComment.setBounds(anchoUnit * 2, altoUnit * 18, anchoUnit * 15, altoUnit * 3);
        panelPpal.add(labelComment);

        textAreaCause.setRows(3);
        textAreaCause.setColumns(5);
        textAreaCause.setLineWrap(true);
        textAreaCause.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textAreaCause.setFont(newFont);
        textAreaCause.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textAreaCause);
        scrollPane.setBounds(anchoUnit * 2, altoUnit * 21, anchoUnit * 25, altoUnit * 8);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butErrorMount = utiliGraf.button2("Confirmar Error", anchoUnit * 8, altoUnit * 30, anchoUnit * 13);
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
        panelPpal.add(butErrorMount);
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
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
            } else if (wrong == 0) {
                labelLoss.setText("No hay monto faltante");
            } else {
                labelLoss.setText("Monto Faltante $: " + wrong);
            }
        }
    }

    private void butErrorMountActionPerformed(String cause) throws Exception {
        cause = "Causa del Error: " + cause + "<br>";
        boolean error = false;
        boolean totalLoss = checkTotalLossMount.isSelected();

        if (totalLoss == true) {
            wrong = total;
        }

        if (wrong >= total) {
            utiliMsg.errorTotalLess();
            error = true;
        }

        if (wrong < 1) {
            utiliMsg.errorMountNull();
            error = true;
        }

        if (error == false) {
            boolean confirm = utiliMsg.optionConfirmarMontoError(total - wrong, wrong);
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