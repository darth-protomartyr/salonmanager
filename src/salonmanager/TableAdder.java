package salonmanager;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Double.parseDouble;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class TableAdder extends FrameThird {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOConfig daoC = new DAOConfig();
    DAOItemCard daoI = new DAOItemCard();
    ServicioSalon ss = new ServicioSalon();
    ServicioTable st = new ServicioTable();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Manager manager = null;
    Admin admin = null;
    Color bluLg = new Color(194, 242, 206);
    Color narUlg = new Color(255, 255, 176);
    SalonManager sm = new SalonManager();
    int f1 = anchoUnit * 3;
    Font font1 = new Font("Arial", Font.BOLD, f1);
    int f2 = (int) Math.round(anchoUnit * 2.3);
    Font font2 = new Font("Arial", Font.BOLD, f2);
    int f3 = (int) Math.round(anchoUnit * 1.6);
    Font font3 = new Font("Arial", Font.BOLD, f3);
    int f4 = (int) Math.round(anchoUnit * 1.2);
    Font font4 = new Font("Arial", Font.BOLD, f4);
    int f5 = (int) Math.round(anchoUnit * 1.1);
    Font font5 = new Font("Arial", Font.BOLD, f5);

    double amountCash = 0;
    double amountElec = 0;
    double sum = 0;
    double wrong = 0;
    double totalMount = 0;
    double totalMountInit = 0;

    int discount = 0;

    ArrayList<ItemCard> itemsDB = new ArrayList<>();
    ArrayList<ItemCard> items = new ArrayList<>();
    ArrayList<ItemCard> itemsGift = new ArrayList<>();
    ArrayList<ItemCard> itemsPartialPayed = new ArrayList<>();
    ArrayList<ItemCard> itemsPartialPayedND = new ArrayList<>();

    JTextField fieldAmountCash = new JTextField();
    JTextField fieldAmountElectronic = new JTextField();
    JLabel labelLoss = new JLabel();
    JLabel labelTot2 = null;
    Table tabAux = new Table();
    JComboBox comboItems = new JComboBox();
    JSpinner spinner = new JSpinner();
    JScrollPane scrollPaneItems = new JScrollPane();

    JTable jTableItems = new JTable();
    String col1 = "Uni.";
    String col2 = "Items";
    String col3 = "Subtotal $";
    boolean indiBool = false;
    String[] colNames = {col1, col2, col3};
    String[][] data = new String[0][0];
    Timestamp dateOpenTab = null;
    Timestamp dateCloseTab = null;
    Timestamp dateCloseWs = null;
    Workshift ws = null;
    int num = 0;

    public TableAdder(Workshift w, Manager man, Table tab, Admin adm) throws Exception {
        sm.addFrame(this);
        manager = man;
        admin = adm;
        ws = w;
        itemsDB = daoI.listarItemsCard();
        if (tab != null) {
            tabAux = tab;
            totalMountInit = tabAux.getTotal() - tabAux.getError();
            setTabElements();
        } else {
            num = daoT.maxBarrTab(ws) + 1;
        }

        Timestamp ts = null;
        if (ws != null) {
            ts = ws.getCloseDateWs();
        }

        if (ts != null) {
            dateOpenTab = ws.getCloseDateWs();
            dateOpenTab = utili.updateTmestamp(dateOpenTab, -(10 * num));
            dateCloseTab = utili.updateTmestamp(dateOpenTab, 5);
        } else {
            dateCloseWs = new Timestamp(new Date().getTime());
            dateOpenTab = utili.updateTmestamp(dateCloseWs, -(10 * num));
            dateCloseTab = utili.updateTmestamp(dateOpenTab, 5);
        }

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        String tit = "Registrar Mesa";
        if (tabAux != null) {
            tit = "Finalizar Mesa";
        }
        setTitle(tit);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W(tit.toUpperCase());
        labelTit.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 26, altoUnit * 5);
        panelPpal.add(labelTit);

        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        comboItems.setFont(font4);
        comboItems.setBounds(anchoUnit * 2, altoUnit * 9, anchoUnit * 16, altoUnit * 5);
        comboItems.setSelectedIndex(itemsDB.size());
        panelPpal.add(comboItems);

        JLabel labelUnitsItem = utiliGraf.labelTitleBacker1W("Unidades");
        labelUnitsItem.setBounds(anchoUnit * 21, altoUnit * 9, anchoUnit * 7, altoUnit * 5);
        panelPpal.add(labelUnitsItem);

        spinner = utiliGraf.spinnerBack(anchoUnit * 28, altoUnit * 9, anchoUnit * 4, altoUnit * 5, spinner);
        spinner.setFont(font3);
        panelPpal.add(spinner);

        //Boton Ingreso Item
        JButtonMetalBlu butSelItem = utiliGraf.button2("Ingresar item", anchoUnit * 10, altoUnit * 16, anchoUnit * 12);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (!comboItems.getSelectedItem().equals("")) {
                        addItem();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelItem);

        scrollPaneItems = new JScrollPane(jTableItems);
        scrollPaneItems.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneItems.setBounds(anchoUnit * 2, altoUnit * 22, anchoUnit * 30, altoUnit * 35);
        panelPpal.add(scrollPaneItems);

        JButtonMetalBlu butGift = utiliGraf.button2("Obsequio", anchoUnit * 2, altoUnit * 59, anchoUnit * 13);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (items.size() > 0) {
                        actionButtonGift();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butGift);

//Boton Descuento-------------------------------------------------------
        JButtonMetalBlu butDiscount = utiliGraf.button2("Descuento", anchoUnit * 19, altoUnit * 59, anchoUnit * 13);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (items.size() > 0) {
                        actionButtonDiscount();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butDiscount);

        JPanel panelTotal = new JPanel(null);
        panelTotal.setBackground(bluLg);
        panelTotal.setBounds(anchoUnit * 2, altoUnit * 65, anchoUnit * 30, altoUnit * 7);
        panelPpal.add(panelTotal);

        String st = "Monto Total $:";
        if (tabAux != null) {
            st = "Monto Abonado $:";
        } 
        
        JLabel labelTot1 = utiliGraf.labelTitleBacker1(st);
        labelTot1.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 14, altoUnit * 4);
        panelTotal.add(labelTot1);

        labelTot2 = utiliGraf.labelTitleBackerA3("");
        labelTot2.setBounds(anchoUnit * 15, altoUnit * 1, anchoUnit * 15, altoUnit * 5);
        labelTot2.setHorizontalAlignment(CENTER);
        panelTotal.add(labelTot2);

        JLabel labelInn = utiliGraf.labelTitleBacker2W("Ingrese el dinero recibido");
        labelInn.setBounds(anchoUnit * 10, altoUnit * 72, anchoUnit * 18, altoUnit * 4);
        panelPpal.add(labelInn);

        JLabel labelEf = utiliGraf.labelTitleBacker3W("Efectivo $:");
        labelEf.setBounds(anchoUnit * 5, altoUnit * 76, anchoUnit * 8, altoUnit * 3);
        panelPpal.add(labelEf);

        fieldAmountCash.setBounds(anchoUnit * 5, altoUnit * 79, anchoUnit * 10, altoUnit * 5);
        fieldAmountCash.setFont(new Font("Arial", Font.BOLD, 24));
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
                updateMount(1);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                updateMount(1);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelPpal.add(fieldAmountCash);

        JLabel labelEl = utiliGraf.labelTitleBacker3W("Transferencia $:");
        labelEl.setBounds(anchoUnit * 19, altoUnit * 76, anchoUnit * 10, altoUnit * 3);
        panelPpal.add(labelEl);

        fieldAmountElectronic.setBounds(anchoUnit * 19, altoUnit * 79, anchoUnit * 10, altoUnit * 5);
        fieldAmountElectronic.setFont(new Font("Arial", Font.BOLD, 24));
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
                updateMount(1);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                updateMount(1);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelPpal.add(fieldAmountElectronic);

        labelLoss = utiliGraf.labelTitleBacker2W("Ingrese Información requerida");
        labelLoss.setHorizontalAlignment(CENTER);
        labelLoss.setBounds(anchoUnit * 1, altoUnit * 85, anchoUnit * 32, altoUnit * 3);
        panelPpal.add(labelLoss);

        JButtonMetalBlu butConfirmMount = utiliGraf.button1("Confirmar pago", anchoUnit * 9, altoUnit * 89, anchoUnit * 14);
        butConfirmMount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    closeTab();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butConfirmMount);

        JButtonMetalBlu butReset = utiliGraf.button3("Resetear", anchoUnit * 25, altoUnit * 89, anchoUnit * 8);
        butReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    reset();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butReset);

        setTableItems();
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                if (admin != null) {
                    admin.setEnabled(true);
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                if (admin != null) {
                    admin.setEnabled(true);
                }
            }
        });
    }

    private void addItem() {
        String itemSt = (String) comboItems.getSelectedItem();
        int num = (int) spinner.getValue();
        ItemCard ic = null;
        for (int i = 0; i < itemsDB.size(); i++) {
            if (itemSt.equals(itemsDB.get(i).getName())) {
                ic = itemsDB.get(i);
            }
        }
        int counter = 0;
        while (counter < num) {
            items.add(ic);
            counter += 1;
        }
        setTableItems();
        comboItems.setSelectedItem("");
        spinner.setValue(1);
    }

    private void actionButtonGift() {
        GiftSelector gs = new GiftSelector(null, null, this, items);
        gs.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        gs.setAlwaysOnTop(true);
    }

    private void closeTab() throws Exception {
        boolean confirm = utiliMsg.cargaConfirmarFacturacion(totalMount, sum - totalMount);
        if (confirm) {
            if (tabAux == null) {
                Table tab = new Table(num, "barra", manager.getUser());
                tab.setDiscount(discount);
                tab.setOpenTime(dateOpenTab);
                tab.setCloseTime(dateCloseTab);
                tab.setOpen(false);
                tab.setBill(true);
                double error = totalMount - sum;
                tab.setError(error);
                tab.setAmountCash(amountCash);
                tab.setAmountElectronic(amountElec);
                tab.setTotal(totalMount - error);
                tab.setComments("Los datos de la mesa fueron ingresados con posterirdad.");
                daoT.saveTable(tab, dateCloseWs);
                tab.setOrder(items);
                for (int i = 0; i < tab.getOrder().size(); i++) {
                    daoI.saveItemOrderTable(tab.getOrder().get(i), tab);
                }
                for (int i = 0; i < tab.getGifts().size(); i++) {
                    daoI.saveItemGiftTable(tab.getGifts().get(i), tab);
                }
                daoU.saveWaiterTable(tab);
                boolean confirm1 = utiliMsg.cargaConfirmNewTab();
                if (confirm1) {
                    new TableAdder(ws, manager, null, null);
                    dispose();
                } else {
                    ss.closeWorkshift(null, manager, ws, null, null, null, null, null, true, 2);
                    dispose();
                }
            } else {
                if (sum <= totalMount) {
                    tabAux.setDiscount(discount);
                    daoT.updateTableDiscount(tabAux);
                    tabAux.setCloseTime(dateCloseTab);
                    daoT.updateCloseTime(tabAux);
                    tabAux.setOpen(false);
                    daoT.updateTableOpen(tabAux);
                    tabAux.setBill(true);
                    daoT.updateTableBill(tabAux);
                    double error = totalMount - sum;
                    if (error > 0) {
                        tabAux.setError(error);
                        daoT.updateError(tabAux);
                    }
                    tabAux.setAmountCash(amountCash);
                    daoT.updateTableMountCash(tabAux);
                    tabAux.setAmountElectronic(amountElec);
                    daoT.updateTableMountElectronic(tabAux);
                    tabAux.setTotal(totalMount - error);
                    daoT.updateTableTotal(tabAux);
                    tabAux.setComments(tabAux.getComments() + "<br>" + "La mesa fue cerrada con posteridad por " + manager.getUser().getName() + " " + manager.getUser().getLastName() + ".");
                    daoT.updateComments(tabAux);
                    tabAux.setOrder(items);
                    daoI.downActiveItemOrderTableAll(tabAux);
                    for (int i = 0; i < tabAux.getOrder().size(); i++) {
                        daoI.saveItemOrderTable(tabAux.getOrder().get(i), tabAux);
                    }

                    tabAux.setGifts(itemsGift);
                    for (int i = 0; i < tabAux.getGifts().size(); i++) {
                        daoI.saveItemGiftTable(tabAux.getGifts().get(i), tabAux);
                    }

                    daoI.downActiveItemPayedTableAll(tabAux);
                    daoI.downActiveItemPayedNDTableAll(tabAux);
                    boolean confirm1 = utiliMsg.cargaConfirmNewTab();
                    if (confirm1) {
                        new TableAdder(ws, manager, null, null);
                        dispose();
                    } else {
                        ss.closeWorkshift(null, manager, ws, null, null, null, null, null, true, 2);
                        dispose();
                    }
                } else {
                    utiliMsg.errorSumOverMount();
                }
            }
        }
    }

    private void setTableItems() {
        ArrayList<ItemCard> itemsAux = utili.unRepeatItems2(items);
        ArrayList<ItemCard> partialsND = itemsPartialPayedND;
        ArrayList<ItemCard> gifts = itemsGift;
        ArrayList<ItemCard> totalItems = itemsAux;
        totalItems.addAll(utili.unRepeatItems2(partialsND));
        totalItems.addAll(utili.unRepeatItems2(gifts));
        int rowsItems = totalItems.size();

        int aux = 0;
        if (discount > 0) {
            aux += 1;
        }

        if (partialsND.size() > 0) {
            aux += 1;
        }

        data = (new String[rowsItems + aux][3]);
        double disc = (double) discount / 100;

        for (int i = 0; i < totalItems.size(); i++) {
            ItemCard ic = totalItems.get(i);
            if (i < itemsAux.size()) {
                int u = st.itemUnitsBacker(items, ic);
                data[i][0] = " " + u;
                data[i][1] = " " + ic.getName();
                data[i][2] = " " + ic.getPrice().get(0) * u * (1 - disc);
            }

            if (partialsND.size() > 0 && i >= itemsAux.size() && i < (totalItems.size() - gifts.size())) {
                int u = st.itemUnitsBacker(itemsPartialPayedND, ic);
                data[i][0] = " " + u;
                data[i][1] = " PAG.* " + ic.getName();
                data[i][2] = "PAGADO";
            }

            if (i >= totalItems.size() - gifts.size()) {
                int u = st.itemUnitsBacker(itemsGift, ic);
                data[i][0] = " " + u;
                data[i][1] = " OBS." + ic.getName();
                data[i][2] = " 0";
            }
        }

        if (discount > 0) {
            data[rowsItems][1] = "DESCUENTO: " + discount + "%";
        }

        if (partialsND.size() > 0) {
            if (discount > 0) {
                data[rowsItems][1] = "DESCUENTO: " + discount + "%";
                data[rowsItems + 1][1] = "* PAGADO S/DTO";
            } else {
                data[rowsItems][1] = "* PAGADO S/DTO";
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        jTableItems.setModel(tableModel);
        jTableItems.setDefaultEditor(Object.class, null);

        JTableHeader header = jTableItems.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBackground(bluLg);

        Font cellFont = new Font("Arial", Font.BOLD, 16);
        jTableItems.setFont(cellFont);
        jTableItems.setRowHeight(altoUnit * 4);
        jTableItems.setBackground(narUlg);

        int c = (anchoUnit * 65) / 8;
        TableColumn column1 = jTableItems.getColumnModel().getColumn(0);
        column1.setPreferredWidth(c * 1);
        TableColumn column2 = jTableItems.getColumnModel().getColumn(1);
        column2.setPreferredWidth(c * 5);
        TableColumn column3 = jTableItems.getColumnModel().getColumn(2);
        column3.setPreferredWidth(c * 2);

        totalMount = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isActiveItem()) {
                ItemCard ic = items.get(i);
                double pr = ic.getPrice().get(0);
                totalMount = totalMount + pr;
            }
        }

        if (discount > 0) {
            totalMount = totalMount - Math.round(totalMount * discount / 100);
        }

        for (int i = 0; i < itemsPartialPayedND.size(); i++) {
            if (itemsPartialPayedND.get(i).isActiveItem()) {
                ItemCard ic = itemsPartialPayedND.get(i);
                double pr = ic.getPrice().get(0);
                totalMount = totalMount + pr;
            }
        }

        labelTot2.setText(totalMount - tabAux.getError() + "");
        updateMount(0);
    }

    private void updateMount(int prev) {
        boolean error = false;
        if (prev == 1) {
            String erC = fieldAmountCash.getText();
            String erE = fieldAmountElectronic.getText();

            if (erC.equals("")) {
                erC = "0";
            }

            if (erE.equals("")) {
                erE = "0";
            }

            try {

                if (!erC.equals("")) {
                    amountCash = parseDouble(erC);
                }

                if (!erE.equals("")) {
                    amountElec = parseDouble(erE);
                }

            } catch (NumberFormatException e) {
                utiliMsg.errorNumerico();
                error = true;
            } catch (Exception ex) {
                Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        sum = amountCash + amountElec + totalMountInit;

        if (sum <= totalMount) {
            wrong = totalMount - sum;
        } else {
            labelLoss.setText("El monto supera el Total");
            error = true;
        }

        if (error == false) {
            if (wrong == totalMount) {
                labelLoss.setText("El monto faltante es total");
            } else if (wrong == 0) {
                labelLoss.setText("No hay monto faltante");
            } else {
                labelLoss.setText("Monto Faltante: $" + wrong);
            }
        }
    }

    public void addGift(ItemCard iCard) {
        itemsGift.add(iCard);
        Iterator<ItemCard> iterador = items.iterator();
        int counter = 0;
        while (iterador.hasNext()) {
            ItemCard ic = iterador.next();
            if (ic.getId() == iCard.getId()) {
                while (counter < 1) {
                    iterador.remove();
                    counter += 1;
                }
            }
        }
        setTableItems();
    }

    private void actionButtonDiscount() {
        BillDiscounter bd = new BillDiscounter(null, this);
        bd.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        bd.setAlwaysOnTop(true);
        setEnabled(false);
    }

    void setDiscount(int u) {
        discount = u;
        setTableItems();
    }

    private void reset() {
        amountCash = 0;
        amountElec = 0;
        sum = 0;
        wrong = 0;
        totalMount = 0;
        discount = 0;
        items = new ArrayList<>();
        itemsGift = new ArrayList<>();
        itemsPartialPayed = new ArrayList<>();
        itemsPartialPayedND = new ArrayList<>();
        if (tabAux != null) {
            setTabElements();
        }
        spinner.setValue(1);
        setTableItems();
    }

    private void setTabElements() {
        amountCash = tabAux.getAmountCash();
        amountElec = tabAux.getAmountElectronic();
        items = tabAux.getOrder();
        itemsGift = tabAux.getGifts();
        itemsPartialPayed = tabAux.getPartialPayed();
        if (itemsPartialPayed.size() > 0) {
            items.addAll(itemsPartialPayed);
        }
        itemsPartialPayedND = tabAux.getPartialPayedND();
        discount = tabAux.getDiscount();
    }
}
