package salonmanager;

import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCarta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
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
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesSalon;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesSalon utiliSal = new UtilidadesSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();

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
    ArrayList<ItemCarta> itemsGift = new ArrayList<ItemCarta>();
    ArrayList<ItemCarta> itemsPartialPaid = new ArrayList<ItemCarta>();
    ArrayList<Integer> itemUnits = new ArrayList<Integer>();
    ArrayList<ItemCarta> aic = new ArrayList<ItemCarta>();
    User waiterAux = null;
    Table tableAux = null;

    int discount = 0;
    double total = 0;
    double error = 0;

    //Botonera
    ArrayList<JPanel> panelsPane = new ArrayList<JPanel>();
    ArrayList<JButtonTable> tableButtons = new ArrayList<JButtonTable>();
    JPanel panelA = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jbtAux = new JButtonTable();
    JComboBox comboWaiters = new JComboBox();
    JLabel labelWaiter = new JLabel();

    //Menu Lateral
    int rowsItems = 0;
    int colItems = 3;
    String col1 = "Uni.";
    String col2 = "Items";
    String col3 = "Total $";
    String[] colNames = {col1, col2, col3};
    String[][] data = new String[rowsItems][colItems];
    JLabel labelMesa = new JLabel();
    JComboBox comboItems = new JComboBox();
    JSpinner spinnerUnitsItem = new JSpinner();
    JScrollPane scrollPaneItems = new JScrollPane();
    JTable jTableItems = new JTable();
    JPanel panelCuenta = new JPanel();
    JPanel panelFlotante = new JPanel();
    JLabel labelTotalParcial = new JLabel();
    JLabel labelCuenta = new JLabel();
    JButton butCloseTable = new JButton();
    JButton butErrorTable = new JButton();
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
        panelA.setBounds(20, altoUnit * 18, (anchoPane + anchoUnit * 2), (alturaPane + altoUnit * 7));
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);

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
                    jbtAux = botonClicado;
                    tableAux = new Table(jbtAux.getNum(), jbtAux.getPos(), waiterAux);
                    tableAux.setOpen(true);
                    String nameT = tableAux.getPos() + tableAux.getNum();
                    labelMesa.setText("Mesa:" + nameT);
                    botonClicado.setBackground(green);
                    tableAux.setOrder(new ArrayList<ItemCarta>());
                    jbtSetter();
//                    }
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
        panelLateral.setBounds((anchoFrame * 7 / 10) + anchoUnit * 5, altoUnit * 3, anchoFrame - (anchoPane + anchoUnit * 7), (alturaPane + altoUnit * 22));
        panelPpal.add(panelLateral);

        JPanel panelTable = new JPanel();
        panelTable.setLayout(null);
        panelTable.setBackground(narLg);
        panelTable.setBounds(anchoUnit, anchoUnit, anchoFrame - (anchoPane + anchoUnit * 9), altoUnit * 89);
        panelLateral.add(panelTable);
        labelMesa = utiliGraf.labelTitleBackerA3("Mesa:--");
        labelMesa.setBounds(altoUnit, altoUnit * 1, anchoUnit * 13, 50);
        panelTable.add(labelMesa);
        labelWaiter = utiliGraf.labelTitleBacker1("Mozo:--");
        labelWaiter.setBounds(altoUnit, altoUnit * 7, anchoUnit * 10, 30);
        panelTable.add(labelWaiter);

        JPanel panelWaiter = new JPanel();
        panelWaiter.setBackground(bluLg);
        panelWaiter.setLayout(null);
        panelWaiter.setBounds(anchoUnit * 14, altoUnit * 1, anchoUnit * 8, altoUnit * 10);
        panelTable.add(panelWaiter);

        comboWaiters.setModel(utili.userComboModelReturn(waiters));
        comboWaiters.setBounds(altoUnit, altoUnit, anchoUnit * 7, altoUnit * 3);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setFont(new Font("Arial", Font.PLAIN, 8)); // Ajusta el tamaño de la fuente aquí    
        comboWaiters.setRenderer(renderer);
        panelWaiter.add(comboWaiters);
        JButton butSelWaiter = utiliGraf.button3("Elija mozo", anchoUnit * 1, altoUnit * 6, anchoUnit * 6);
        butSelWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        utiliMsg.errorTableNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            if (waiterAux == null) {
                                waiterAux = getWaiter();
                                labelWaiter.setText("Mozo:" + waiterAux.getNombre());
                                tableAux.setWaiter(waiterAux);
                            } else {
                                User waiter = getWaiter();
                                if (!waiter.getMail().equals(waiterAux.getMail())) {
                                    int confirm = utiliMsg.cargaNewWaiter();
                                    if (confirm == 0) {
                                        waiterAux = getWaiter();
                                        tableAux.setWaiter(waiterAux);
                                        labelWaiter.setText("Mozo:" + waiterAux.getNombre());
                                    }
                                }
                            }
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelWaiter.add(butSelWaiter);

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

        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        comboItems.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 11, altoUnit * 4);
        comboItems.setSelectedIndex(itemsDB.size());
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
                    if (waiterAux == null) {
                        utiliMsg.errorWaiterNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            butSelItemActionPerformed();
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelItem.add(butSelItem);

        scrollPaneItems = scrollItemsBack(altoUnit, altoUnit * 28, anchoUnit * 21 + altoUnit, altoUnit * 30);
        panelTable.add(scrollPaneItems);

        JButton butGift = utiliGraf.button2("Obsequio", altoUnit, altoUnit * 59, anchoUnit * 11);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            gifter();
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butGift);

        JButton butDiscount = utiliGraf.button2("Descuento", altoUnit * 2 + anchoUnit * 11, altoUnit * 59, anchoUnit * 10);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            discounter();
                        } else {
                            utiliMsg.errorBillSend();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butDiscount);

        butCloseTable = utiliGraf.button1("CERRAR MESA", altoUnit, altoUnit * 64, anchoUnit * 21 + altoUnit);
        panelTable.add(butCloseTable);
        butCloseTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (waiterAux == null) {
                        resetTable();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            resetTable();
                        } else {
                            if (tableAux.isBill() == false) {
                                int confirm = utiliMsg.cargaConfirmarCierre();
                                if (confirm == 0) {
                                    closeTable();
                                }
                            } else {
                                tablePaid();
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton butPartialPay = utiliGraf.button3("PAGO PARCIAL", altoUnit, altoUnit * 71, anchoUnit * 11);
        butPartialPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux.isBill() == false) {
                        utiliMsg.errorBillUnsend();
                    } else {
                        partialPayer();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butPartialPay);

        JButton butErrorTable = utiliGraf.button3("ERROR MESA", altoUnit * 2 + anchoUnit * 11, altoUnit * 71, anchoUnit * 10);
        butErrorTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux.isBill() == false) {
                        utiliMsg.errorBillUnsend();
                    } else {
                        errorTaker();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butErrorTable);

        panelCuenta.setLayout(null);
        panelCuenta.setBounds(altoUnit, altoUnit * 76, anchoUnit * 21 + altoUnit, altoUnit * 11);
        panelCuenta.setBackground(narUlg);
        panelTable.add(panelCuenta);

        labelTotalParcial = utiliGraf.labelTitleBacker2("Parcial $:");
        labelTotalParcial.setBounds(5, altoUnit * 2, anchoUnit * 6, altoUnit * 3);
        panelCuenta.add(labelTotalParcial);

        labelCuenta = utiliGraf.labelTitleBackerA3("00,0");
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
    public User getWaiter() {
        User waiter = new User();
        String selection = (String) comboWaiters.getSelectedItem();
        waiter = utili.userSelReturn(selection, waiters);
        return waiter;
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
                tableAux = st.orderTable(tableAux);
                spinnerUnitsItem.setValue(1);
                total = utiliSal.countBill(tableAux, discount);
                tableAux.setTotal(total);
                jbtSetter();
                labelCuenta.setText("$ " + total);
                comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
                comboItems.setSelectedIndex(itemsDB.size());
            } else {
                int uAux = itemUnits.get(repeat) + u;
                itemUnits.set(repeat, uAux);
                tableAux.setUnits(itemUnits);
                total = utiliSal.countBill(tableAux, discount);
                tableAux.setTotal(total);
                jbtSetter();
                labelCuenta.setText("" + total);
                comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
                comboItems.setSelectedIndex(itemsDB.size());
            }
            setTableItems();
        } else {
            utiliMsg.errorSeleccion();
        }
    }

    private JScrollPane scrollItemsBack(int marginW, int marginH, int anchoPane, int alturaPane) {
        setTableItems();
        JScrollPane scrollPane = new JScrollPane(jTableItems);
        scrollPane.setPreferredSize(new Dimension(anchoPane, alturaPane));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(marginW, marginH, anchoPane, alturaPane);
        return scrollPane;
    }

    private void setTableItems() {
        ArrayList<ItemCarta> itemsAux = new ArrayList<ItemCarta>(itemsTableAux);

        if (itemsGift.size() > 0) {
            for (int i = 0; i < itemsGift.size(); i++) {
                itemsAux.add(itemsGift.get(i));
            }
        }
        if (itemsTableAux != null) {
            rowsItems = itemsTableAux.size() + itemsGift.size();
        } else {
            rowsItems = 0;
        }

        if (discount > 0) {
            data = new String[rowsItems + 1][colItems];
        } else {
            data = new String[rowsItems][colItems];
        }
        for (int i = 0; i < rowsItems; i++) {
            ItemCarta ic = itemsAux.get(i);
            if (i >= itemsTableAux.size()) {
                data[i][0] = " 1";
                data[i][1] = " Obs." + ic.getName();
                data[i][2] = " 0";
            } else {
                int u = itemUnits.get(i);
                data[i][0] = " " + u;
                data[i][1] = " " + ic.getName();
                data[i][2] = " " + ic.getPrice() * u;
            }
        }

        if (discount > 0) {
            data[rowsItems][1] = "DESCUENTO: " + discount + "%";
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        jTableItems.setModel(tableModel);
        jTableItems.setDefaultEditor(Object.class, null);

        JTableHeader header = jTableItems.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(bluSt);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        jTableItems.setFont(cellFont);
        jTableItems.setRowHeight(25);

        jTableItems.setBackground(narUlg);

        int c = (anchoPane - 50) / 8;
        TableColumn column1 = jTableItems.getColumnModel().getColumn(0);
        column1.setPreferredWidth(c);
        TableColumn column2 = jTableItems.getColumnModel().getColumn(1);
        column2.setPreferredWidth(c * 5);
        TableColumn column3 = jTableItems.getColumnModel().getColumn(2);
        column3.setPreferredWidth(c * 2);

        jTableItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = jTableItems.getSelectedRow();
                int columnaSeleccionada = jTableItems.getSelectedColumn();
                if (filaSeleccionada <= rowsItems && columnaSeleccionada >= 0) {
                    utiliMsg.errorDataNull();
                }
            }
        });
    }

    private void closeTable() {
        total = utiliSal.countBill(tableAux, discount);
        tableAux.setTotal(total);
        tableAux.setBill(true);
        jbtSetter();
        labelTotalParcial.setText("Total $:");
        labelCuenta.setText("" + total);
        labelTip.setText("Prop.: " + Math.round(total * 0.1));
        double tot = total + Math.round(total * 0.1);
        labelTotal.setText("Total: " + tot);
        jbtAux.setBackground(red);
        butCloseTable.setText("CONFIRMAR PAGO");
    }

    private void resetTable() {
        Iterator<Table> iterador = openTables.iterator();
        while (iterador.hasNext()) {
            Table t = iterador.next();
            if (t.getNum() == tableAux.getNum()) {
                iterador.remove();
            }
        }
        waiterAux = null;
        jbtAux.setTable(null);
        jbtSetter();
        jbtAux.setBackground(narUlg);
        labelMesa.setText("Mesa:--");
        labelWaiter.setText("Mozo:--");
        butCloseTable.setText("CERRAR MESA");
        utiliMsg.cargaTableErase();
    }

    private void gifter() {
        tableAux = st.orderTable(tableAux);
        itemsTableAux = tableAux.getOrder();
        itemUnits = tableAux.getUnits();
        new GiftSelector(this);
    }

    private void discounter() {
        new BillDiscounter(this);
    }

    public Table getTable() {
        return tableAux;
    }

    double getTotal() {
        return total;
    }

    public void giftBacker(ItemCarta ic) {
        itemsGift.add(ic);
        tableAux.setGifts(itemsGift);
        int iCIndexEmpty = -1;
        for (int i = 0; i < itemsTableAux.size(); i++) {
            if (itemsTableAux.get(i).getId() == ic.getId()) {
                int u = itemUnits.get(i);
                u = u - 1;
                if (u == 0) {
                    iCIndexEmpty = i;
                }
                itemUnits.set(i, u);
                tableAux.setUnits(itemUnits);
            }
        }

        if (iCIndexEmpty > -1) {
            itemsTableAux.remove(iCIndexEmpty);
            itemUnits.remove(iCIndexEmpty);
        }
        tableAux.setOrder(itemsTableAux);
        tableAux.setUnits(itemUnits);

        utiliMsg.cargaGift(ic.getName());
        setTableItems();
        total = utiliSal.countBill(tableAux, discount);
        tableAux.setTotal(total);
        jbtSetter();
        labelCuenta.setText(total + "");
    }

    public void discountBacker(int disc) {
        discount = disc;
        tableAux.setDiscount(disc);
        setTableItems();
        total = utiliSal.countBill(tableAux, discount);
        tableAux.setTotal(total);
        jbtSetter();
        labelCuenta.setText(total + "");
    }

    private void tablePaid() {
        tableAux.setBill(true);
        tableAux.setOpen(false);
        butErrorTable.setVisible(false);
//        butCloseTable.setBounds(altoUnit, altoUnit * 66, anchoUnit * 21 + altoUnit, 40);
        jbtAux.setBackground(red);
        jbtSetter();
        resetTable();
    }

    private void errorTaker() {
        new ErrorTableCount(this);
    }
    
    private void partialPayer() {
        new PartialPayer(this);
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

    void ErrorBacker(double errorBack) {
        error = total - errorBack;
        utiliMsg.cargaError();
    }
}
