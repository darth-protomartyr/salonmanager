package salonmanager;

import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCarta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.FrameFullManager;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.JButtonTable;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesSalon;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesSalon utiliSal = new UtilidadesSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    SalonManager sm = new SalonManager();
    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();
    DAOItemCarta daoI = new DAOItemCarta();

    ServicioSalon ss = new ServicioSalon();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);

    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    int anchoPane = (anchoFrame * 7 / 10);
    int alturaPane = (alturaFrame * 7 / 10);
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    int totalTable = 0;
    int numBut = 1;
    int rowsButtons = 0;
    int colButtons = 0;
    int fontSizeTable = 0;
    int wUnit = 0;
    int hUnit = 0;

    ArrayList<Table> openTables = new ArrayList<Table>();
    ArrayList<String> configSalon = new ArrayList<String>();
    ArrayList<Integer> tableNum = new ArrayList<Integer>();
    ArrayList<String> tablePan = new ArrayList<String>();
    ArrayList<String> tablePanCh = new ArrayList<String>();
    ArrayList<User> waiters = new ArrayList<User>();
    ArrayList<ItemCarta> itemsDB = new ArrayList<ItemCarta>();
    ArrayList<ItemCarta> itemsTableAux = new ArrayList<ItemCarta>();
    ArrayList<Integer> itemUnits = new ArrayList<Integer>();
    ArrayList<ItemCarta> aic = new ArrayList<ItemCarta>();
    User waiterAux = null;
    Table tableAux = null;

    int prop = 0;
    int discount = 0;
    ArrayList<ItemCarta> gifts = new ArrayList<ItemCarta>();

    int rowsItems = 0;
    int colItems = 2;
    String col1 = "Items";
    String col2 = "Uni.";
    String[] colNames = {col1, col2};
    String[][] data = new String[rowsItems][colItems];
    ArrayList<JButtonTable> tableButtons = new ArrayList<JButtonTable>();

    //Botonera
    ArrayList<JPanel> panelsPane = new ArrayList<JPanel>();
    JPanel panelA = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jbtAux = new JButtonTable();
    JComboBox comboWaiters = new JComboBox();
    JLabel labelWaiter = new JLabel();

    //Menu Lateral
    JLabel labelMesa = new JLabel();
    JComboBox comboItems = new JComboBox();
    JSpinner spinnerUnitsItem = new JSpinner();
    JScrollPane scrollPaneItems = new JScrollPane();
    JTable tableItems = new JTable();
    JPanel panelCuenta = new JPanel();
    JLabel labelTotalParcial = new JLabel();
    JLabel labelCuenta = new JLabel();
    JButton butCloseTable = new JButton();
    JLabel labelTip = new JLabel();
    JLabel labelTotal = new JLabel();

    public Salon() throws Exception {
        sm.addFrame(this);
        setTitle("Salón Manager");
        itemsDB = daoI.listarItemsCarta();
        waiters = daoU.listWaiter();
        PanelPpal panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);

//        Config cfg = daoC.consultarConfig();
//        totalTable = cfg.getTotalTable();
//        tableNum = cfg.getTableNum();
//        tablePan = cfg.getTablePan();
//        tablePanCh = cfg.getTablePanCh();
        tableNum.add(12);
        tablePan.add("Salón");
        tablePanCh.add("s");

//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
//PANEL TABLEBUTTONS--------------------------------------------------------------------------------------------------
        panelA.setLayout(null);
        panelA.setBounds(20, 100, (anchoPane + anchoUnit * 2), (alturaPane + altoUnit * 7));
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);

        JPanel panelWaiter = new JPanel();
        panelWaiter.setBackground(bluLg);
        panelWaiter.setLayout(null);
        panelWaiter.setBounds(anchoUnit * 56, altoUnit * 4, anchoUnit * 18, altoUnit * 12);
        panelPpal.add(panelWaiter);

        comboWaiters.setModel(utili.userComboModelReturn(waiters));
        comboWaiters.setBounds(anchoUnit, altoUnit, anchoUnit * 16, 30);
        panelWaiter.add(comboWaiters);
        JButton butSelWaiter = utiliGraf.button2("Elija mozo", anchoUnit * 5, altoUnit * 6, anchoUnit * 8);
        butSelWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    getWaiter();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelWaiter.add(butSelWaiter);

        for (int i = 0; i < tableNum.size(); i++) {
            JPanel panelB = new JPanel();
            panelB.setBackground(narLg);
            panelB.setLayout(null);
            ArrayList<Integer> configValues = utiliSal.salonConfigValues(tableNum.get(i), anchoPane, alturaPane);
            fontSizeTable = configValues.get(0);
            wUnit = configValues.get(1);
            hUnit = configValues.get(2);
            rowsButtons = configValues.get(3);
            colButtons = configValues.get(4);

            for (int y = 0; y < rowsButtons; y++) {
                for (int z = 0; z < colButtons; z++) {
                    JButtonTable jbt = new JButtonTable(tablePanCh.get(i), numBut, wUnit * (5 * z + 1), hUnit * (5 * y + 1), (wUnit * 4), (hUnit * 4));
                    Font font = new Font("Arial", Font.BOLD, fontSizeTable);
                    jbt.setFont(font);
                    jbt.setText(jbt.getText());
                    jbt.setBackground(narUlg);
                    jbt.setBorder(new EmptyBorder(10, 10, 10, 10));
                    jbt.setBounds(jbt.getMarginW(), jbt.getMarginH(), jbt.getWidth(), jbt.getHeight());
                    numBut += 1;
                    tableButtons.add(jbt);
                    panelB.add(jbt);
                }
            }
            panelB.setBounds(wUnit, hUnit, anchoPane, alturaPane + hUnit);
            panelsPane.add(panelB);
        }

        for (int i = 0; i < panelsPane.size(); i++) {
            tabbedPane.addTab(tablePan.get(i), panelsPane.get(i));
        }

        tabbedPane.setBounds(anchoUnit, anchoUnit, anchoPane, (alturaPane + altoUnit * 3));
        panelA.add(tabbedPane);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButtonTable botonClicado = (JButtonTable) e.getSource();
                if (botonClicado.getTable() == null) {
                    if (waiterAux == null) {
                        utiliMsg.errorWaiterNull();
                    } else {
                        jbtAux = botonClicado;
                        tableAux = new Table(jbtAux.getNum(), jbtAux.getPos(), waiterAux);
                        tableAux.setOpen(true);
                        String nameT = tableAux.getPos() + tableAux.getNum();
                        labelMesa.setText("Mesa: " + nameT);
                        botonClicado.setBackground(green);
                        tableAux.setOrder(new ArrayList<ItemCarta>());
                        jbtSetter();
                        waiterAux = null;
                    }
                } else {

                }
            }
        };

        for (int i = 0; i < tableButtons.size(); i++) {
            jbtAux = tableButtons.get(i);
            jbtAux.addActionListener(actionListener);
        }

// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
// PANEL LATERAL------------------------------------------------------------------------------------------------------
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(bluLg);
        panelLateral.setLayout(null);
        panelLateral.setBounds((anchoFrame * 7 / 10) + anchoUnit * 5, 20, anchoFrame - (anchoPane + anchoUnit * 7), (alturaPane + altoUnit * 18));
        panelPpal.add(panelLateral);

        JPanel panelTable = new JPanel();
        panelTable.setLayout(null);
        panelTable.setBackground(narLg);
        panelTable.setBounds(anchoUnit, anchoUnit, anchoFrame - (anchoPane + anchoUnit * 9), altoUnit * 85);
        panelLateral.add(panelTable);
        labelMesa = utiliGraf.labelTitleBackerA("Mesa: --");
        labelMesa.setBounds(anchoUnit, altoUnit * 1, 250, 50);
        panelTable.add(labelMesa);
        labelWaiter = utiliGraf.labelTitleBacker1("Mozo: --");
        labelWaiter.setBounds(anchoUnit, altoUnit * 7, 250, 30);
        panelTable.add(labelWaiter);

        JPanel panelSelItem = new JPanel();
        panelSelItem.setLayout(null);
        panelSelItem.setBounds(6, altoUnit * 12, anchoFrame - (anchoPane + anchoUnit * 10), altoUnit * 16);
        panelSelItem.setBackground(bluLg);
        panelTable.add(panelSelItem);

        JButton butCaptionBebidas = utiliGraf.button3("Bebidas", 5, 5, 55);
        butCaptionBebidas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Bebidas");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionBebidas);

        JButton butCaptionPlatos = utiliGraf.button3("Platos", 65, 5, 55);
        butCaptionPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Platos");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionPlatos);

        JButton butCaptionCafeteria = utiliGraf.button3("Cafetería", 125, 5, 80);
        butCaptionCafeteria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Cafeteria");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionCafeteria);

        JButton butCaptionOtros = utiliGraf.button3("Otros", 210, 5, 65);
        butCaptionOtros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    itemCaptionBack("Otros");
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butCaptionOtros);

        comboItems.setModel(utili.itemsComboModelReturn(itemsDB));
        comboItems.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 11, altoUnit * 4);
        panelSelItem.add(comboItems);

        JLabel labelUnitsItem = utiliGraf.labelTitleBacker3("Unidades");
        labelUnitsItem.setBounds(anchoUnit * 13, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelSelItem.add(labelUnitsItem);

        spinnerUnitsItem = utiliGraf.spinnerBack(anchoUnit * 18, altoUnit * 5, anchoUnit * 3, altoUnit * 4, spinnerUnitsItem);

        panelSelItem.add(spinnerUnitsItem);

        JButton butSelItem = utiliGraf.button2("Ingresar item", anchoUnit * 5, altoUnit * 11, anchoUnit * 12);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        butSelItemActionPerformed();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butSelItem);

        scrollPaneItems = scrollItemsBack(altoUnit, altoUnit * 30, anchoUnit * 21 + altoUnit, altoUnit * 30);
        panelTable.add(scrollPaneItems);

        JButton butGift = utiliGraf.button2("Obsequio", altoUnit, altoUnit * 61, anchoUnit * 11);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            utiliMsg.errorItemsTableNull();
                        } else {
//                    obsequio();    
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butGift);

        JButton butDiscount = utiliGraf.button2("Descuento", altoUnit * 2 + anchoUnit * 11, altoUnit * 61, anchoUnit * 10);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            utiliMsg.errorItemsTableNull();
                        } else {
//                    discounter();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butDiscount);

        butCloseTable = utiliGraf.button1("CERRAR MESA", altoUnit, altoUnit * 66, anchoUnit * 21 + altoUnit);
        panelTable.add(butCloseTable);
        butCloseTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            resetTable();
                        } else {
                            closeTable();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        panelCuenta.setLayout(null);
        panelCuenta.setBounds(altoUnit, altoUnit * 73, anchoUnit * 21 + altoUnit, altoUnit * 11);
        panelCuenta.setBackground(narUlg);
        panelTable.add(panelCuenta);

        labelTotalParcial = utiliGraf.labelTitleBacker2("Parcial $:");
        labelTotalParcial.setBounds(5, altoUnit * 2, anchoUnit * 6, altoUnit * 3);
        panelCuenta.add(labelTotalParcial);

        labelCuenta = utiliGraf.labelTitleBackerA("00,0");
        labelCuenta.setBounds(anchoUnit * 7, altoUnit * 2, anchoUnit * 15, altoUnit * 4);
        labelCuenta.setBackground(viol);
        panelCuenta.add(labelCuenta);

        labelTip = utiliGraf.labelTitleBacker3("Prop.: $ 00,0");
        labelTip.setBounds(5, altoUnit * 8, anchoUnit * 10, altoUnit * 2);
        panelCuenta.add(labelTip);

        labelTotal = utiliGraf.labelTitleBacker3("Total: $ 00,0");
        labelTotal.setBounds(anchoUnit * 11, altoUnit * 8, anchoUnit * 10, altoUnit * 2);
        panelCuenta.add(labelTotal);

//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
//EXTRAS--------------------------------------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
//MAIN----------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Salon();
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
//FUNCTIONS-----------------------------------------------------------------------------------------------------------
    public void getWaiter() {
        String selection = (String) comboWaiters.getSelectedItem();
        waiterAux = utili.userSelReturn(selection, waiters);
        labelWaiter.setText("Mozo: " + waiterAux.getNombre());
    }

    private void itemCaptionBack(String capt) {
        ArrayList<ItemCarta> aic = new ArrayList<ItemCarta>();
        for (ItemCarta ic : itemsDB) {
            if (ic.getCaption().toLowerCase().equals(capt.toLowerCase())) {
                aic.add(ic);
            }
        }
        comboItems.setModel(utili.itemsComboModelReturn(aic));
    }

    // Seleccion Item para Tabla
    private void butSelItemActionPerformed() {
        ItemCarta ic = null;
        String item = (String) comboItems.getSelectedItem();
        int u = (int) spinnerUnitsItem.getValue();

        if (!item.equals("")) {
            ic = utili.itemCartaBacker(item, itemsDB);
            butCloseTable.setText("CERRAR CUENTA");
            int repeat = ss.itemRepeat(ic, itemsTableAux);
            if (repeat == -1) {
                itemsTableAux.add(ic);
                itemUnits.add(u);
                tableAux.setOrder(itemsTableAux);
                tableAux.setUnits(itemUnits);
                jbtSetter();
                comboItems.setSelectedIndex(0);
                spinnerUnitsItem.setValue(1);
                double billParcial = utiliSal.countBill(tableAux, prop, gifts, discount);
                labelCuenta.setText("$ " + billParcial);
            } else {
                int uAux = itemUnits.get(repeat) + u;
                itemUnits.set(repeat, uAux);
                tableAux.setUnits(itemUnits);
                double billParcial = utiliSal.countBill(tableAux, prop, gifts, discount);
                labelCuenta.setText("" + billParcial);
            }
            setTableItems();
        } else {
            utiliMsg.errorSeleccion();
        }
    }

    private JScrollPane scrollItemsBack(int marginW, int marginH, int anchoPane, int alturaPane) {
        setTableItems();
        JScrollPane scrollPane = new JScrollPane(tableItems);
        scrollPane.setPreferredSize(new Dimension(anchoPane, alturaPane));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(marginW, marginH, anchoPane, alturaPane);
        return scrollPane;
    }

    private void setTableItems() {
        if (itemsTableAux != null) {
            rowsItems = itemsTableAux.size();
        } else {
            rowsItems = 0;
        }
        data = new String[rowsItems][colItems];
        for (int i = 0; i < rowsItems; i++) {
            ItemCarta ic = itemsTableAux.get(i);
            data[i][0] = "  " + ic.getName();
            int u = itemUnits.get(i);
            data[i][1] = u + "  ";
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        tableItems.setModel(tableModel);
        tableItems.setDefaultEditor(Object.class, null);

        JTableHeader header = tableItems.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(bluSt);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        tableItems.setFont(cellFont);
        tableItems.setRowHeight(25);

        tableItems.setBackground(narUlg);
//        tableItems.setForeground(narSt);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tableItems.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        int c = (anchoPane - 50) / 4;
        TableColumn column1 = tableItems.getColumnModel().getColumn(0);
        column1.setPreferredWidth(c * 3);
        TableColumn column2 = tableItems.getColumnModel().getColumn(1);
        column2.setPreferredWidth(c);
    }

    private void closeTable() {
        if (tableAux.isBill()) {
            tableAux.setBill(true);
            tableAux.setOpen(false);
            jbtSetter();
        } else {
            double billParcial = utiliSal.countBill(tableAux, prop, gifts, discount);
            tableAux.setBill(true);
            jbtSetter();
            labelTotalParcial.setText("Total $:");
            labelCuenta.setText("" + billParcial);
            labelTip.setText("Prop.: " + Math.round(billParcial * 0.1));
            double tot = billParcial + Math.round(billParcial * 0.1);
            labelTotal.setText("Total: " + tot);
            jbtAux.setBackground(red);
            butCloseTable.setText("Confirmar Pago");
        }
    }

    private void jbtSetter() {
        for (int i = 0; i < openTables.size(); i++) {
            if (openTables.get(i).getNum() == tableAux.getNum()) {
                openTables.set(i, tableAux);
                jbtAux.setTable(tableAux);
                tableButtons.set(i, jbtAux);
            }
        }
    }

    private void resetTable() {
        Iterator<Table> iterador = openTables.iterator();
        while (iterador.hasNext()) {
            Table t = iterador.next();
            if (t.getNum() == tableAux.getNum()) {
                iterador.remove();
            }
        }
         
        jbtAux.setTable(null);
        jbtSetter();
        jbtAux.setBackground(narUlg);
        labelMesa.setText("Mesa: --");
        labelWaiter.setText("Mozo: --");
        butCloseTable.setText("CERRAR MESA");

        utiliMsg.cargaTableErases();
    }
}
