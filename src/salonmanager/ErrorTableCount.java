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
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class ErrorTableCount extends FrameWindow {

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

    double errorMount = 0;
    double total = 0;
    JLabel labelLoss = new JLabel();
    JTextField fieldAmount = new JTextField();
    JCheckBox checkTotalLossMount = new JCheckBox("");
    JTextArea textAreaCause = new JTextArea();
    Salon salon = null;
    JLabel labelTotalMount = null;

    public ErrorTableCount(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        total = salon.getTotal();
        setTitle("Error Mesa");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);
//        itemsToPay = new ArrayList<Itemcard>(salon.getItemsTableAux());
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBacker1("ERROR");
        panelLabel.add(labelTit);

        JPanel panelAmount = new JPanel();
        panelAmount.setLayout(null);
        panelAmount.setBackground(bluLg);
        panelAmount.setBounds(15, 40, 350, 220);

        JLabel labelAmount = utiliGraf.labelTitleBacker1("Ingrese el monto recibido");
        labelAmount.setBounds(60, 5, 240, 30);
        panelAmount.add(labelAmount);

        JLabel label$ = utiliGraf.labelTitleBackerA4("$");
        label$.setBounds(15, 37, 45, 40);
        panelAmount.add(label$);

        fieldAmount.setBounds(40, 40, 170, 35);
        fieldAmount.setFont(new Font("Arial", Font.PLAIN, 30));
        fieldAmount.addKeyListener(new KeyListener() {
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

        fieldAmount.getDocument().addDocumentListener(new DocumentListener() {

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

        panelAmount.add(fieldAmount);

        JLabel labelLossCheck = utiliGraf.labelTitleBacker3("Pérdida Total");
        labelLossCheck.setBounds(220, 50, 120, 20);
        panelAmount.add(labelLossCheck);

        checkTotalLossMount.setBounds(310, 50, 20, 20);
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

        labelLoss = utiliGraf.labelTitleBacker3("");
        labelLoss.setBounds(100, 70, 230, 25);
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


        JButton butErrorMount = utiliGraf.button2("Confirmar Error", 90, 185, 160);
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
                sal.enableSalon();
                dispose();
            }
        });
    }

//FUNCIONES------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//Funciones por Monto--------------------------------------------------------------------------------------------------    
    private void updateMount() {
        String er = fieldAmount.getText();
        boolean error = false;
        boolean totalLoss = checkTotalLossMount.isSelected();
        double wrong = 0;

        try {
            if (totalLoss == true) {
                wrong = total;
                fieldAmount.setText("");
            } else {
                if (!er.equals("")) {
                    double aux = parseDouble(er);
                    if (aux <= total) {
                        wrong = total - parseDouble(er);
                    } else {
                        labelLoss.setText("El monto supera el Total");
                        error = true;
                    }
                }
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            if (wrong == total) {
                labelLoss.setText("El monto faltante es total");
                fieldAmount.setText(total + "");
            } else if (wrong == 0) {
                labelLoss.setText("No hay monto faltante");
            } else {
                labelLoss.setText("Monto Faltante: $" + wrong);
            }
        }
    }

    private void butErrorMountActionPerformed(String cause) throws Exception {
        boolean error = false;
        String err = fieldAmount.getText();
        boolean totalLoss = checkTotalLossMount.isSelected();

        try {
            if (totalLoss == true) {
                errorMount = total;
            } else {
                errorMount = total - parseInt(err);
            }

            if (errorMount > total) {
                utiliMsg.errorTotalLess();
                error = true;
            }

            if (errorMount < 1) {
                utiliMsg.errorMountNull();
                error = true;
            }

        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            boolean confirm = utiliMsg.cargaConfirmarMontoError(total - errorMount, errorMount);
            if (confirm) {
                salon.errorMountBacker(errorMount, cause);
                dispose();
            }
        } else {
            resetFrame();
        }
    }

    private void resetFrame() {
        checkTotalLossMount.setSelected(false);
        fieldAmount.setText("");
    }

//Funciones por Item--------------------------------------------------------------------------------------------------
//    private void updateItems() {
//        boolean totalLoss = checkTotalLossItems.isSelected();
//        if (totalLoss == true) {
//            for (Itemcard ic : itemsToPay) {
//                itemsError.add(ic);
//            }
//            itemsToPay = new ArrayList<Itemcard>();
//        } else {
//            for (Itemcard ic : itemsError) {
//                itemsToPay.add(ic);
//            }
//            itemsError = new ArrayList<Itemcard>();
//        }
//
//        listToPay.setModel(utili.itemListModelReturnMono(itemsToPay));
//        listError.setModel(utili.itemListModelReturnMono(itemsError));
//
//        double error = 0;
//        for (Itemcard ic : itemsError) {
//            error += ic.getPrice();
//        }
//        labelTotalMount.setText("Faltante: $" + error);
//    }
//    private void selItemToPay() {
//        checkTotalLossItems.setSelected(false);
//        String selectedValue = (String) listToPay.getSelectedValue();
//        Itemcard itemAux = utili.ItemcardBacker(selectedValue, itemsToPay);
//        itemsError.add(itemAux);
//        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsError);
//        listError.setModel(modeloLista1);
//
//        Iterator<Itemcard> iterador = itemsToPay.iterator();
//        int counter = 0;
//        while (iterador.hasNext()) {
//            Itemcard ic = iterador.next();
//            if (ic.getId() == itemAux.getId()) {
//                while (counter < 1) {
//                    iterador.remove();
//                    counter += 1;
//                }
//            }
//        }
//        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsToPay);
//        listToPay.setModel(modeloLista2);
//        double error = 0;
//        for (Itemcard ic : itemsError) {
//            error += ic.getPrice();
//        }
//        labelTotalMount.setText("Faltante: $" + error);
//    }
//    private void unSelItemToPay() {
//        checkTotalLossItems.setSelected(false);
//        String selectedValue = (String) listError.getSelectedValue();
//        Itemcard itemAux = utili.ItemcardBacker(selectedValue, itemsError);
//        itemsToPay.add(itemAux);
//        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsToPay);
//        listToPay.setModel(modeloLista1);
//        int counter = 0;
//        Iterator<Itemcard> iterador = itemsError.iterator();
//        while (iterador.hasNext()) {
//            Itemcard ic = iterador.next();
//            if (ic.getId() == itemAux.getId()) {
//                while (counter < 1) {
//                    iterador.remove();
//                    counter += 1;
//                }
//            }
//        }
//        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsError);
//        listError.setModel(modeloLista2);
//
//        double error = 0;
//        for (Itemcard ic : itemsError) {
//            error += ic.getPrice();
//        }
//        labelTotalMount.setText("Faltante: $" + error);
//
//    }
//    private void butErrorItemActionPerformed() throws Exception {
//        salon.errorItemsBacker(itemsError);
//    }
}
