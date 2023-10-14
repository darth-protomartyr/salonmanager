package salonmanager;

import salonmanager.entidades.FrameWindow;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.PanelPpal;
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
    ArrayList<ItemCarta> itemsToPay = new ArrayList<ItemCarta>();
    ArrayList<ItemCarta> itemsError = new ArrayList<ItemCarta>();

    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel labelLoss = new JLabel();
    JTextField fieldAmount = new JTextField();
    JCheckBox checkTotalLossMount = new JCheckBox("");
    JCheckBox checkTotalLossItems = new JCheckBox("");
    JList listToPay = new JList();
    JList listError = new JList();
    Salon salon = null;
    JLabel labelTotalMount = null;

    public ErrorTableCount(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        total = salon.getTotal();
        setTitle("Error Mesa");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);
        itemsToPay = new ArrayList<ItemCarta>(salon.getItemsTableAux());
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBacker1("ERROR");
        panelLabel.add(labelTit);
        
//----------------------------------------------------------------------------------------------------------------------
//PanelMount
        JPanel panelMount = new JPanel();
        panelMount.setLayout(null);
        panelMount.setBackground(bluLg);
        panelMount.setSize(280, 200);

        JLabel labelMount = utiliGraf.labelTitleBacker1("Ingrese el monto recibido");
        labelMount.setBounds(60, 5, 240, 30);
        panelMount.add(labelMount);

        JLabel label$ = utiliGraf.labelTitleBackerA2("$");
        label$.setBounds(10, 40, 45, 60);
        panelMount.add(label$);

        fieldAmount.setBounds(45, 40, 190, 50);
        fieldAmount.setFont(new Font("Arial", Font.PLAIN, 40));
        fieldAmount.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.CHAR_UNDEFINED) {
                    e.consume(); // Consume la tecla para evitar que se procese como entrada
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

        panelMount.add(fieldAmount);

        JLabel labelLossCheck = utiliGraf.labelTitleBacker3("Pérdida Total");
        labelLossCheck.setBounds(245, 70, 120, 20);
        panelMount.add(labelLossCheck);

        checkTotalLossMount.setBounds(335, 70, 20, 20);
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
        panelMount.add(checkTotalLossMount);

        labelLoss = utiliGraf.labelTitleBacker2("");
        labelLoss.setBounds(80, 100, 230, 25);
        panelMount.add(labelLoss);

        JButton butErrorMount = utiliGraf.button2("Confirmar Error", 90, 135, 160);
        butErrorMount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butErrorMountActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelMount.add(butErrorMount);


//----------------------------------------------------------------------------------------------------------------------
//PanelItem
        JPanel panelItem = new JPanel();
        panelItem.setLayout(null);
        panelItem.setBackground(bluLg);
        panelItem.setSize(280, 150);

        JLabel labelItemsTP = utiliGraf.labelTitleBacker3("Items Pendientes");
        labelItemsTP.setBounds(10, 5, 160, 15);
        panelItem.add(labelItemsTP);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3("Items No Abonados");
        labelItemsSel.setBounds(185, 5, 160, 15);
        panelItem.add(labelItemsSel);

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
        jSPtoPay.setBounds(10, 25, 165, 80);
        panelItem.add(jSPtoPay);

        listError.setModel(utili.itemListModelReturnMono(itemsError));
        listError.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    unSelItemToPay();
                }
            }
        });

        JScrollPane jSPPayed = new JScrollPane(listError);
        jSPPayed.setBounds(185, 25, 165, 80);
        panelItem.add(jSPPayed);
        
        labelTotalMount = utiliGraf.labelTitleBacker2("Faltante: $0.0");
        labelTotalMount.setBounds(20, 110, 180, 20);
        panelItem.add(labelTotalMount);
        
        JLabel labelLossCheck2 = utiliGraf.labelTitleBacker3("Pérdida Total");
        labelLossCheck2.setBounds(210, 110, 120, 20);
        panelItem.add(labelLossCheck2);
        
        checkTotalLossItems.setBounds(300, 110, 20, 20);
        checkTotalLossItems.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateItems();
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    updateItems();
                }
            }
        });
        panelItem.add(checkTotalLossItems);

        JButton butErrorItem = utiliGraf.button2("Confirmar Error", 90, 135, 160);
        butErrorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butErrorItemActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelItem.add(butErrorItem);


//----------------------------------------------------------------------------------------------------------------------
//Pestañas
        tabbedPane.addTab("Error por Monto", panelMount);
        tabbedPane.addTab("Error por Item", panelItem);

        tabbedPane.setBounds(10, 40, 365, 200);
        panelPpal.add(tabbedPane);
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
            } else if (wrong == 0) {     
                labelLoss.setText("No hay monto faltante");
            } else {
                labelLoss.setText("Monto Faltante: $" + wrong);
            }
        }

//        if (totalLoss == true) {
//            fieldAmount.setEditable(false);
//        } else {
//            fieldAmount.setEditable(true);
//        }
    }
    
    
    private void butErrorMountActionPerformed() throws Exception {
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
            int confirm = utiliMsg.cargaConfirmarMontoError(total - errorMount, errorMount);
            if (confirm == 0) {
                salon.errorMountBacker(errorMount);
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
    private void updateItems() {
        boolean totalLoss = checkTotalLossItems.isSelected();
        if(totalLoss == true) {
            for (ItemCarta ic : itemsToPay) {
                itemsError.add(ic);
            }
            itemsToPay = new ArrayList<ItemCarta>();
        } else {
            for (ItemCarta ic : itemsError) {
                itemsToPay.add(ic);
            }
            itemsError = new ArrayList<ItemCarta>();
        }
        
        listToPay.setModel(utili.itemListModelReturnMono(itemsToPay));
        listError.setModel(utili.itemListModelReturnMono(itemsError));
        
        double error = 0;
        for (ItemCarta ic : itemsError) {
            error += ic.getPrice();
        }
        labelTotalMount.setText("Faltante: $" + error);
    }
    
    private void selItemToPay() {
        checkTotalLossItems.setSelected(false);
        String selectedValue = (String) listToPay.getSelectedValue();
        ItemCarta itemAux = utili.itemCartaBacker(selectedValue, itemsToPay);
        itemsError.add(itemAux);
        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsError);
        listError.setModel(modeloLista1);

        Iterator<ItemCarta> iterador = itemsToPay.iterator();
        int counter = 0;
        while (iterador.hasNext()) {
            ItemCarta ic = iterador.next();
            if (ic.getId() == itemAux.getId()) {
                while (counter < 1) {
                    iterador.remove();
                    counter += 1;
                }
            }
        }
        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsToPay);
        listToPay.setModel(modeloLista2);
        double error = 0;
        for (ItemCarta ic : itemsError) {
            error += ic.getPrice();
        }
        labelTotalMount.setText("Faltante: $" + error);
    }

    
    private void unSelItemToPay() {
        checkTotalLossItems.setSelected(false);
        String selectedValue = (String) listError.getSelectedValue();
        ItemCarta itemAux = utili.itemCartaBacker(selectedValue, itemsError);
        itemsToPay.add(itemAux);
        ListModel modeloLista1 = utili.itemListModelReturnMono(itemsToPay);
        listToPay.setModel(modeloLista1);
        int counter = 0;
        Iterator<ItemCarta> iterador = itemsError.iterator();
        while (iterador.hasNext()) {
            ItemCarta ic = iterador.next();
            if (ic.getId() == itemAux.getId()) {
                while (counter < 1) {
                    iterador.remove();
                    counter += 1;
                }
            }
        }
        ListModel modeloLista2 = utili.itemListModelReturnMono(itemsError);
        listError.setModel(modeloLista2);
        
        double error = 0;
        for (ItemCarta ic : itemsError) {
            error += ic.getPrice();
        }
        labelTotalMount.setText("Faltante: $" + error);
        
    }


    private void butErrorItemActionPerformed() throws Exception {
        salon.errorItemsBacker(itemsError);
    }
}