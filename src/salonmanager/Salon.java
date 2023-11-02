package salonmanager;

import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCarta;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.FrameFullManager;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.JButtonTable;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.entidades.User;
import salonmanager.entidades.Workshift;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.servicios.ServicioCloseWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Salon extends FrameFullManager {

    Utilidades utili = new Utilidades();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();

    SalonManager sm = new SalonManager();
    DAOConfig daoC = new DAOConfig();
    DAOUser daoU = new DAOUser();
    DAOItemCarta daoI = new DAOItemCarta();
    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    ServicioSalon ss = new ServicioSalon();

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);
    ArrayList<String> configSalon = new ArrayList<String>(); //Configuración de l salón
    int anchoPane = (anchoFrame * 7 / 10);
    int alturaPane = (alturaFrame * 7 / 10);
    int totalTable = 0;
    int numBut = 1;
    int rowsButtons = 0;
    int colButtons = 0;
    int fontSizeTable = 0;
    int wUnit = 0;
    int hUnit = 0;

    ArrayList<Integer> tableNum = new ArrayList<Integer>(); // número de mesa
    ArrayList<String> tablePan = new ArrayList<String>(); // Nombre del sector
    ArrayList<String> tablePanCh = new ArrayList<String>(); // Primer Char del Nombre del sector
    ArrayList<User> waiters = new ArrayList<User>(); // mozos
    ArrayList<ItemCarta> itemsDB = new ArrayList<ItemCarta>(); // Items Completos
    ArrayList<ItemCarta> itemsTableAux = new ArrayList<ItemCarta>();//items a cobrar de la mesa
    ArrayList<ItemCarta> itemsGift = new ArrayList<ItemCarta>(); //items obsequiados
    ArrayList<ItemCarta> itemsPartialPaid = new ArrayList<ItemCarta>(); // items cobrados por pago parcial
    ArrayList<ItemCarta> itemsPartialPaidNoDiscount = new ArrayList<ItemCarta>(); // items cobrados anted de aplicar descuento
    ArrayList<ItemCarta> itemsError = new ArrayList<ItemCarta>();
    User user = null;
    User waiterAux = null; // mozo actual
    Table tableAux = null; // mesa actual
    Workshift workshiftActual = null;

    int discount = 0; //porcentaje de descuento
    double priceCorrection = 0; //correccion debido a modificación de precio de un item que se encontraba pago;
    double amoutnCash = 0; //dinero en billete
    double amountElectronic = 0; //dinero electrónico
    double total = 0; // total a pagar(pago parcial restado)
    double error = 0; // dinero faltante a pagar;

    //Botonera
    ArrayList<JPanel> panelsPane = new ArrayList<JPanel>();
    ArrayList<JButtonTable> tableButtons = new ArrayList<JButtonTable>();
    JPanel panelA = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    JButtonTable jbtAux = new JButtonTable(); //boton de mesa actual
    JComboBox comboWaiters = new JComboBox();
    ServicioCloseWorkshift sw = new ServicioCloseWorkshift();

    //Menu Lateral
    int rowsItems = 0; //nro filas de la tabla
    int colItems = 3; //nro columnas de las tablas
    String col1 = "Uni.";
    String col2 = "Items";
    String col3 = "Total $";

    String[] colNames = {col1, col2, col3};
    String[][] data = new String[rowsItems][colItems];
    JComboBox comboItems = new JComboBox();
    JSpinner spinnerUnitsItem = new JSpinner();
    JScrollPane scrollPaneItems = new JScrollPane();
    JTable jTableItems = new JTable();
    JButton butCloseTable = new JButton();
    JPanel panelCuenta = new JPanel();
    JPanel panelFlotante = new JPanel();
    JLabel labelTotalParcial = new JLabel();
    JLabel labelMesa = new JLabel();
    JLabel labelCuenta = new JLabel();
    JLabel labelWaiter = new JLabel();
    JLabel labelTip = new JLabel();
    JLabel labelTotal = new JLabel();
    JLabel labelPartialPay = new JLabel();
    JButton butInitWorkshift = new JButton();
    Salon sal = null;

    public Salon() throws Exception {
        sm.addFrame(this);
        /*test*/
        user = daoU.consultaUser("gon@gmail.com");
//        user = sm.getUserIn();
        setTitle("Salón Manager");
        sal = this;
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

        JPanel panelActual = new JPanel();
        panelActual.setBounds(anchoUnit * 60, altoUnit * 3, anchoUnit * 17, altoUnit * 14);
        panelActual.setBackground(bluLg);
        panelActual.setLayout(null);
        panelPpal.add(panelActual);

        JLabel labelUser = utiliGraf.labelTitleBacker3("Usuario: " + user.getNombre().toUpperCase() + " " + utili.strShorter(user.getApellido(), 2).toUpperCase());
        labelUser.setBounds(altoUnit, altoUnit, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelUser);

        JLabel labelSession = utiliGraf.labelTitleBacker3("");
        /*test*/
        Timestamp timeInitSes = new Timestamp(new Date().getTime());
        if (timeInitSes != null) {
            labelSession.setText("Inicio Sesion: " + utili.friendlyDate(timeInitSes));
        } else {
            labelSession.setText("Sesion no iniciada.");
        }
        labelSession.setBounds(altoUnit, altoUnit * 4, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelSession);

        JLabel labelWorkshift = utiliGraf.labelTitleBacker3("");
        if (workshiftActual != null) {
            Timestamp timeInitWork = workshiftActual.getOpenShift();
            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(timeInitWork));
        } else {
            labelWorkshift.setText("Turno no iniciado.");
        }
        labelWorkshift.setBounds(altoUnit, altoUnit * 7, anchoUnit * 17, altoUnit * 2);
        panelActual.add(labelWorkshift);

        butInitWorkshift = utiliGraf.button2("Abrir Turno", anchoUnit * 3, altoUnit * 9, anchoUnit * 11);
        butInitWorkshift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (workshiftActual == null) {
                        boolean confirm1 = utiliMsg.cargaConfirmarInicioTurno(user.getNombre(), user.getApellido());
                        if (confirm1 == true) {
                            workshiftActual = new Workshift(user);
                            daoW.guardarWorkshift(workshiftActual);
                            sm.workshiftBacker(workshiftActual);
                            labelWorkshift.setText("Inicio Turno: " + utili.friendlyDate(workshiftActual.getOpenShift()));
                            butInitWorkshift.setText("CERRAR TURNO");
                        }
                    } else {
                        if (ss.openJBTButtonsTester(tableButtons) == true) {
                            int id = daoW.findId(workshiftActual.getOpenShift());
                            workshiftActual.setId(id);
                            workshiftActual.setCloseShift(new Timestamp(new Date().getTime()));
                            workshiftActual.setStateWorkshift(false);
                            daoW.updateWorkshiftClose(workshiftActual);
                            daoW.updateWorkshiftState(workshiftActual);
                            boolean confirm2 = utiliMsg.cargaConfirmarCierreTurno(user.getNombre(), user.getApellido());
                            if (confirm2 == true) {
                                endWorkshift();
                                butInitWorkshift.setText("Iniciar Turno");
                            } else {
                                boolean confirm3 = utiliMsg.cargaConfirmarCambioTurno(user);
                                if (confirm3 == true) {
                                    //Cerrar Turno actual para que uno nuevo se abierto
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        panelActual.add(butInitWorkshift);

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
            ArrayList<Integer> configValues = ss.salonConfigValues(tableNum.get(i), anchoPane, alturaPane);
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
                if (workshiftActual != null) {
                    jbtAux.setBorder(null);
                    if (jbtAux.isOpenJBT() == true) {
                        try {
                            resetTableValues();
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    JButtonTable botonClicado = (JButtonTable) e.getSource();
                    for (int i = 0; i < tableButtons.size(); i++) {
                        if (tableButtons.get(i).getNum() == botonClicado.getNum()) {
                            jbtAux = tableButtons.get(i);
                        }
                    }
                    try {
                        resetTableValues();
                    } catch (Exception ex) {
                        Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jbtAux.setBorder(new LineBorder(bluSt, 8));

                    if (jbtAux.isOpenJBT() == false) {
                        try {
                            setWaiter(0);
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tableAux = new Table(jbtAux.getNum(), jbtAux.getPos(), waiterAux);
                        tableAux.setOpen(true);
                        String nameT = tableAux.getPos() + tableAux.getNum();
                        labelMesa.setText("Mesa:" + nameT);
                        tableAux.setOrder(new ArrayList<ItemCarta>());
                        try {
                            jbtSetter();
                        } catch (Exception ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        tableAux = jbtAux.getTable();
                        tableFullerProp();
                    }
                } else {
                    utiliMsg.errorWorkshift();
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
        labelMesa = utiliGraf.labelTitleBackerA2("Mesa:--");
        labelMesa.setBounds(altoUnit, altoUnit, anchoUnit * 20, 40);
        panelTable.add(labelMesa);

        labelWaiter = utiliGraf.labelTitleBackerA4("Mozo: --");
        labelWaiter.setBounds(altoUnit, altoUnit * 7, anchoUnit * 20, 30);
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

        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        comboItems.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 11, altoUnit * 4);
        comboItems.setSelectedIndex(itemsDB.size());
        panelSelItem.add(comboItems);

        JLabel labelUnitsItem = utiliGraf.labelTitleBacker3("Unidades");
        labelUnitsItem.setBounds(anchoUnit * 13, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelSelItem.add(labelUnitsItem);

        spinnerUnitsItem = utiliGraf.spinnerBack(anchoUnit * 18, altoUnit * 5, anchoUnit * 3, altoUnit * 4, spinnerUnitsItem);

        panelSelItem.add(spinnerUnitsItem);

//Boton Ingreso Item
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

//Generar Modificación Tabla        
        jTableItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = jTableItems.getSelectedRow();
                char[] pass = utiliMsg.solicitudMod();
                boolean perm = utili.requiredPerm(pass);
                if (perm) {
                    if (filaSeleccionada <= rowsItems) {
                        itemCorrector();
                        setTableItems();
                    }
                }
            }
        });

//Boton Obsequio
        JButton butGift = utiliGraf.button2("Obsequio", altoUnit, altoUnit * 59, anchoUnit * 11);
        butGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (tableAux.isBill() == false) {
                            char[] pass = utiliMsg.solicitudMod();
                            boolean perm = utili.requiredPerm(pass);
                            if (perm) {
                                gifter();
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
        panelTable.add(butGift);

//Boton Descuento
        JButton butDiscount = utiliGraf.button2("Descuento", altoUnit * 2 + anchoUnit * 11, altoUnit * 59, anchoUnit * 10);
        butDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() < 1) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        if (discount == 0) {
                            if (tableAux.isBill() == false) {
                                char[] pass = utiliMsg.solicitudMod();
                                boolean perm = utili.requiredPerm(pass);
                                if (perm) {
                                    discounter();
                                }
                            } else {
                                utiliMsg.errorBillSend();
                            }
                        } else {
                            utiliMsg.errorDiscountRepeat();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelTable.add(butDiscount);

        JPanel panelPartial = new JPanel();
        panelPartial.setLayout(null);
        panelPartial.setBounds(altoUnit, altoUnit * 64, anchoUnit * 21 + altoUnit, altoUnit * 5);
        panelPartial.setBackground(bluLg);
        panelTable.add(panelPartial);

//Boton Pago Parcial
        JButton butPartialPay = utiliGraf.button3("PAGO PARCIAL", altoUnit, altoUnit * 1, anchoUnit * 8);
        butPartialPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (itemsTableAux.size() == 0) {
                        utiliMsg.errorItemsTableNull();
                    } else {
                        partialPayer();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPartial.add(butPartialPay);

        labelPartialPay.setText("Pagado: $0.0");
        labelPartialPay.setBounds(anchoUnit * 10, altoUnit, anchoUnit * 10, altoUnit * 3);
        panelPartial.add(labelPartialPay);

//Boton Pago Final
        butCloseTable = utiliGraf.button1("CERRAR MESA", altoUnit, altoUnit * 70, anchoUnit * 13 + altoUnit);
        panelTable.add(butCloseTable);
        butCloseTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (waiterAux == null) {
                        resetTableFull();
                    } else {
                        if (itemsTableAux.size() < 1) {
                            resetTableFull();
                        } else {
                            if (tableAux.isBill() == false) {
                                boolean confirm = utiliMsg.cargaConfirmarCierre();
                                if (confirm) {
                                    tableClose();
                                }
                            } else {
                                moneyKind(sal, true, null);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//Boton Error
        JButton butErrorTable = utiliGraf.button1("ERROR", altoUnit + anchoUnit * 14, altoUnit * 70, anchoUnit * 7 + altoUnit);
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
        panelCuenta.setBounds(altoUnit, altoUnit * 77, anchoUnit * 21 + altoUnit, altoUnit * 11);
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
//------------------------------------------------------------------------------------------------------------------    
//Seleccionar Mozo

    public void setWaiter(int i) throws Exception {
        WaiterSelector ws = new WaiterSelector(this, i);
        ws.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ws.setAlwaysOnTop(true);
        setEnabled(false);
    }

    void waiterBacker(User waiter) {
        waiterAux = waiter;
        labelWaiter.setText("Mozo: " + waiterAux.getNombre() + " " + utili.strShorter(waiterAux.getApellido(), 2).toUpperCase());
        tableAux.setWaiter(waiterAux);
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Listar Items por etiqueta
    private void itemCaptionBack(String capt) {
        ArrayList<ItemCarta> aic = new ArrayList<ItemCarta>();
        for (ItemCarta ic : itemsDB) {
            if (ic.getCaption().toLowerCase().equals(capt.toLowerCase())) {
                aic.add(ic);
            }
        }
        comboItems.setModel(utili.itemsComboModelReturn(aic));
    }

//------------------------------------------------------------------------------------------------------------------
//Ingreso de items
    private void butSelItemActionPerformed() throws Exception {
        if (itemsTableAux.size() < 1) {
            ss.createTable(tableAux);
            jbtAux.setOpenJBT(true);
            jbtAux.setBackground(green);
        }

        ItemCarta ic = null;
        String item = (String) comboItems.getSelectedItem();
        int u = (int) spinnerUnitsItem.getValue();
        if (!item.equals("")) {
            ic = utili.itemCartaBacker(item, itemsDB);
            butCloseTable.setText("CERRAR CUENTA");
            int counter = 0;
            while (counter < u) {
                itemsTableAux.add(ic);
                counter += 1;
            }
            tableAux.setOrder(itemsTableAux);
            spinnerUnitsItem.setValue(1);
            total = ss.countBill(tableAux);
            tableAux.setTotal(total);
            daoT.updateTableTotal(tableAux);
            ArrayList<ItemCarta> arrayAux = ss.itemDeployer(ic, u);
            ss.addItemOrder(tableAux, arrayAux);
            jbtSetter();
            labelCuenta.setText("$ " + total);
            comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
            comboItems.setSelectedIndex(itemsDB.size());
            setTableItems();
        } else {
            utiliMsg.errorSeleccion();
        }
    }

//------------------------------------------------------------------------------------------------------------------
//Scroll de la Tabla
    private JScrollPane scrollItemsBack(int marginW, int marginH, int anchoPane, int alturaPane) {
        setTableItems();
        JScrollPane scrollPane = new JScrollPane(jTableItems);
        scrollPane.setPreferredSize(new Dimension(anchoPane, alturaPane));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(marginW, marginH, anchoPane, alturaPane);
        return scrollPane;
    }

//------------------------------------------------------------------------------------------------------------------
//Tabla de Items
    private void setTableItems() {
        HashSet<ItemCarta> itemsSet = new HashSet<ItemCarta>(itemsTableAux);
        ArrayList<ItemCarta> itemsAux = new ArrayList<ItemCarta>(itemsSet);

        HashSet<ItemCarta> partialSet = new HashSet<ItemCarta>(itemsPartialPaid);
        ArrayList<ItemCarta> partials = new ArrayList<ItemCarta>(partialSet);

        HashSet<ItemCarta> partialSetND = new HashSet<ItemCarta>(itemsPartialPaidNoDiscount);
        ArrayList<ItemCarta> partialsND = new ArrayList<ItemCarta>(partialSetND);

        HashSet<ItemCarta> giftSet = new HashSet<ItemCarta>(itemsGift);
        ArrayList<ItemCarta> gifts = new ArrayList<ItemCarta>(giftSet);

        HashSet<ItemCarta> totalSet = new HashSet<ItemCarta>(itemsAux);
        ArrayList<ItemCarta> totalItems = new ArrayList<ItemCarta>(totalSet);

        totalItems.addAll(partials);
        totalItems.addAll(partialsND);
        totalItems.addAll(gifts);

        rowsItems = totalItems.size();

        int aux = 0;
        if (discount > 0) {
            aux += 1;
        }

        if (priceCorrection > 0) {
            aux += 1;
        }

        data = new String[rowsItems + aux][colItems];

        int intAux = itemsAux.size();
        int intPartial = itemsAux.size() + partials.size();
        int intPartialND = itemsAux.size() + partials.size() + partialsND.size();

        double disc = (double) discount / 100;

        for (int i = 0; i < rowsItems; i++) {
            ItemCarta ic = totalItems.get(i);

            if (i < intAux) {
                int u = st.itemUnitsBacker(itemsTableAux, ic);
                data[i][0] = " " + u;
                data[i][1] = " " + ic.getName();
                data[i][2] = " " + ic.getPrice() * u * (1 - disc);
            }

            if (partials.size() > 0 && i >= intAux && i < intPartial) {
                int u = st.itemUnitsBacker(itemsPartialPaid, ic);
                data[i][0] = " " + u;
                data[i][1] = " PAG." + ic.getName();
                data[i][2] = "PAGADO";
            }

            if (partialsND.size() > 0 && i >= intPartial && i < intPartialND) {
                int u = st.itemUnitsBacker(itemsPartialPaidNoDiscount, ic);
                data[i][0] = " " + u;
                data[i][1] = " PAG*." + ic.getName();
                data[i][2] = "PAGADO";
            }

            if (i >= intPartialND) {
                int u = st.itemUnitsBacker(itemsGift, ic);
                data[i][0] = " " + u;
                data[i][1] = " OBS." + ic.getName();
                data[i][2] = " 0";
            }
        }

        if (priceCorrection > 0) {
            if (discount > 0) {
                data[rowsItems - 1][1] = "Corrección precio mod:";
                data[rowsItems - 1][2] = priceCorrection + "";
            } else {
                data[rowsItems][1] = "Corrección precio mod:";
                data[rowsItems][2] = priceCorrection + "";
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
    }

//-----------------------------------------------------OBSEQUIO-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
    private void gifter() {
        itemsTableAux = tableAux.getOrder();
        GiftSelector gs = new GiftSelector(this);
        gs.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        gs.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
    public void giftBacker(ItemCarta ic) throws Exception {
        itemsGift.add(ic);
        tableAux.setGifts(itemsGift);
        itemsTableAux = ss.itemTableLesser(tableAux.getOrder(), ic);
        tableAux.setOrder(itemsTableAux);
        utiliMsg.cargaGift(ic.getName());
        setTableItems();
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoI.eliminarItemOrderTable(ic, tableAux);
        daoI.guardarItemGiftTable(ic, tableAux);
        daoT.updateTableTotal(tableAux);
        jbtSetter();
        labelCuenta.setText(total + "");
        setEnabled(true);

    }

//-------------------------------------------------PAGO DESCUENTO---------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
    private void discounter() {
        if (itemsPartialPaid.size() > 0) {
            itemsPartialPaidNoDiscount = itemsPartialPaid;
            itemsPartialPaid = new ArrayList<ItemCarta>();
            tableAux.setAuxiliarPartialPayedNoDiscount(itemsPartialPaidNoDiscount);
            tableAux.setPartialPayed(itemsPartialPaid);
        }
        BillDiscounter bd = new BillDiscounter(this);
        bd.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        bd.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
    public void discountBacker(int disc) throws Exception {
        discount = disc;
        tableAux.setDiscount(disc);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        daoT.updateTableDiscount(tableAux);
        jbtSetter();
        labelCuenta.setText(total + "");
        setTableItems();
        setEnabled(true);

    }

//-------------------------------------------------PAGO PARCIAL-----------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------ 
//Ingreso a pago parcial
    private void partialPayer() {
        PartialPayer pp = new PartialPayer(this);
        pp.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        pp.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//pago parcial de cuenta fraccionada
    void partialPayTaker(ArrayList<ItemCarta> itemsPayed) throws Exception {
        jbtAux.setBackground(viol);
        for (int i = 0; i < itemsPayed.size(); i++) {
            itemsTableAux = ss.itemTableLesser(itemsTableAux, itemsPayed.get(i));
        }
        itemsPartialPaid.addAll(itemsPayed);
        tableAux.setPartialPayed(itemsPartialPaid);
        tableAux.setOrder(itemsTableAux);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        for (ItemCarta ic : itemsPayed) {
            daoI.guardarItemPayedTable(ic, tableAux);
            daoI.eliminarItemOrderTable(ic, tableAux);
        }
        labelCuenta.setText(total + "");
        double payed = ss.partialBillPayed(tableAux);
        labelPartialPay.setText("Pagado: $" + (payed));
        jbtSetter();
        setTableItems();
        setEnabled(true);

    }

//------------------------------------------------------------------------------------------------------------------ 
//pago completo de cuenta fraccionada
    void totalPayTaker(ArrayList<ItemCarta> itemsPayed) throws Exception {
        itemsPartialPaid.addAll(itemsPayed);
        tableAux.setPartialPayed(itemsPartialPaid);
        itemsTableAux = itemsPartialPaid;
        itemsPartialPaid = new ArrayList<ItemCarta>();
        tableAux.setOrder(itemsTableAux);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        for (ItemCarta ic : itemsPayed) {
            daoI.guardarItemPayedTable(ic, tableAux);
            daoI.eliminarItemOrderTable(ic, tableAux);
        }
        labelCuenta.setText(total + "");
        setEnabled(true);

//        double payed = ss.partialBillPayed(tableAux);
//        labelPartialPay.setText("Pagado: $" + payed);
//        tablePaid();
//        jbtSetter();
//        setTableItems();
    }

//----------------------------------------------CIERRE Y PAGO DE CUENTA---------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Cierre de cuenta
    private void tableClose() throws Exception {
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        tableAux.setBill(true);
        daoT.updateTableBill(tableAux);
        jbtSetter();
        labelTotalParcial.setText("Total $:");
        labelCuenta.setText("" + total);
        labelTip.setText("Prop.: " + Math.round(total * 0.1));
        double tot = total + Math.round(total * 0.1);
        labelTotal.setText("Total: " + tot);
        jbtAux.setBackground(red);
        butCloseTable.setText("CONFIRMAR PAGO");
        setEnabled(true);

    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
    public void moneyKind(Salon sal, boolean end, ArrayList<ItemCarta> itemsPayed) {
        MoneyType mt = new MoneyType(sal, end, itemsPayed);
        mt.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        mt.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//Pago de cuenta y cierre de mesa
    private void tablePaid() throws Exception {
        tableAux.setOpen(false);
        daoT.updateTableOpen(tableAux);
        jbtSetter();
        resetTableFull();
        setTableItems();
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Forma de pago
    public void amountsTypes(ArrayList<Double> amounts, boolean endex, ArrayList<ItemCarta> itemsPayed) throws Exception {
        double amountC = amounts.get(0);
        double amountE = amounts.get(1);
        tableAux.setAmountCash(amountC);
        tableAux.setAmountElectronic(amountE);
        tableAux.setTotal(total);
        total = ss.countBill(tableAux);
        tableAux.setTotal(total);
        daoT.updateTableMountCash(tableAux);
        daoT.updateTableMountElectronic(tableAux);
        if (itemsPayed != null) {
            if (endex == true) {
                totalPayTaker(itemsPayed);
            } else {
                partialPayTaker(itemsPayed);
            }
        }
        if (endex == true) {
            tablePaid();
        }
        setEnabled(true);
    }

//----------------------------------------------------ERROR---------------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Ingreso a error
    private void errorTaker() {
        ErrorTableCount etc = new ErrorTableCount(this);
        etc.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        etc.setAlwaysOnTop(true);
        setEnabled(false);
    }

//------------------------------------------------------------------------------------------------------------------
//Monto faltante por cash
    public void errorMountBacker(double errorBack) throws Exception {
        error = errorBack;
        tableAux.setError(error);
        utiliMsg.cargaError();
        if (itemsPartialPaid.size() > 0) {
            itemsTableAux = itemsPartialPaid;
            tableAux.setOrder(itemsTableAux);
            itemsPartialPaid = new ArrayList<ItemCarta>();
            tableAux.setPartialPayed(itemsPartialPaid);
            total = ss.countBill(tableAux);
            tableAux.setTotal(total);
            daoT.updateTableTotal(tableAux);
        } else {
            total = total - error;
        }
        tableAux.setTotal(total);
        if (total == 0) {
            itemsError = itemsTableAux;
        }
        tablePaid();
        setEnabled(true);
    }

//------------------------------------------------------------------------------------------------------------------
//Monto faltante por items
    public void errorItemsBacker(ArrayList<ItemCarta> iE) throws Exception {
        itemsError = iE;
        tableAux.setErrorItems(itemsError);
        double error = 0;
        for (ItemCarta ic : itemsError) {
            error += ic.getPrice();
        }
        tableAux.setError(error);

        if (itemsPartialPaid.size() > 0) {
            itemsTableAux = itemsPartialPaid;
            itemsPartialPaid = new ArrayList<ItemCarta>();
            tableAux.setPartialPayed(itemsPartialPaid);
            total = ss.countBill(tableAux);
        } else {
            for (ItemCarta ic : iE) {
                itemsTableAux = ss.itemTableLesser(itemsTableAux, ic);
            }
            total = total - error;
        }
        tableAux.setOrder(itemsTableAux);
        tableAux.setTotal(total);
        daoT.updateTableTotal(tableAux);
        utiliMsg.cargaError();
        tablePaid();
        setEnabled(true);

    }

//------------------------------------------------Corrector deItems-------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
//Corregir
    private void itemCorrector() {
        CorrectorItem ci = new CorrectorItem(this);
        ci.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        ci.setAlwaysOnTop(true);
        setEnabled(false);
    }

//Corregir
    public void correctItems(ItemCarta ic, int num) throws Exception {
        switch (num) {
            case 1:
                itemsTableAux = ss.itemTableLesser(itemsTableAux, ic);
                tableAux.setOrder(itemsTableAux);
                daoI.eliminarItemOrderTable(ic, tableAux);
                break;
            case 2:
                itemsGift = ss.itemTableLesser(itemsGift, ic);
                tableAux.setGifts(itemsGift);
                itemsTableAux.add(ic);
                tableAux.setOrder(itemsTableAux);
                daoI.eliminarItemGiftTable(ic, tableAux);
                daoI.guardarItemOrderTable(ic, tableAux);
                break;
            case 3:
                itemsPartialPaid = ss.itemTableLesser(itemsPartialPaid, ic);
                tableAux.setPartialPayed(itemsPartialPaid);
                itemsTableAux.add(ic);
                tableAux.setOrder(itemsTableAux);
                daoI.eliminarItemPayedTable(ic, tableAux);
                daoI.guardarItemOrderTable(ic, tableAux);
                break;
            case 4:
                itemsPartialPaidNoDiscount = ss.itemTableLesser(itemsPartialPaidNoDiscount, ic);
                tableAux.setPartialPayed(itemsPartialPaidNoDiscount);
                itemsTableAux.add(ic);
                tableAux.setOrder(itemsTableAux);
                daoI.eliminarItemPayedNDTable(ic, tableAux);
                daoI.guardarItemOrderTable(ic, tableAux);
                break;
        }
        jbtSetter();
        setTableItems();
        setEnabled(true);

    }

//----------------------------------------------Finalización Turno--------------------------------------------------    
//------------------------------------------------------------------------------------------------------------------
    private void endWorkshift() throws Exception {
        sw.closeWorkshift(workshiftActual, sal);
    }

//--------------------------------------------------FUNCIONES-------------------------------------------------------
//--------------------------------------------------GENERALES-------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
    public void enableSalon() {
        setEnabled(true);
    }

    public Table getTable() {
        return tableAux;
    }

//------------------------------------------------------------------------------------------------------------------
    double getTotal() {
        return total;
    }

//------------------------------------------------------------------------------------------------------------------
    double totalBacker() {
        return total;
    }

//------------------------------------------------------------------------------------------------------------------
    public ArrayList<ItemCarta> getItemsTableAux() {
        return itemsTableAux;
    }

//------------------------------------------------------------------------------------------------------------------
    private void jbtSetter() throws Exception {
        jbtAux.setTable(tableAux);
        for (int i = 0; i < tableButtons.size(); i++) {
            if (tableButtons.get(i).getNum() == jbtAux.getNum()) {
                tableButtons.set(i, jbtAux);
            }
        }
    }

//------------------------------------------------------------------------------------------------------------------
    private void tableFullerProp() {
        itemsTableAux = tableAux.getOrder();
        itemsGift = tableAux.getGifts();
        itemsPartialPaid = tableAux.getPartialPayed();
        itemsPartialPaidNoDiscount = tableAux.getAuxiliarPartialPayedNoDiscount();
        itemsError = tableAux.getErrorItems();
        waiterAux = tableAux.getWaiter();
        discount = tableAux.getDiscount();
        priceCorrection = tableAux.getPriceCorrection();
        total = tableAux.getTotal();
        error = tableAux.getError();
        setTableItems();
        if (itemsPartialPaid.size() > 0) {
            double payed = ss.partialBillPayed(tableAux);
            labelPartialPay.setText("Pagado: $" + (payed));
        }

        if (tableAux.isBill() == true) {
            labelTotalParcial.setText("Total $:");
            labelTip.setText("Prop.: " + Math.round(total * 0.1));
            labelTotal.setText("Total: " + Math.round(total * 0.1) + total);
        } else {
            labelTotalParcial.setText("Parcial $:");
        }
        labelWaiter.setText("Mozo: " + waiterAux.getNombre() + " " + utili.strShorter(waiterAux.getApellido(), 2).toUpperCase());
        String nameT = tableAux.getPos() + tableAux.getNum();
        labelMesa.setText("Mesa:" + nameT);
        labelCuenta.setText(total + "");

        if (jbtAux.isOpenJBT() == false) {
            butCloseTable.setText("CERRAR MESA");
        } else {
            if (tableAux.isBill() == true) {
                butCloseTable.setText("CONFIRMAR PAGO");
            } else {
                butCloseTable.setText("CERRAR CUENTA");
            }
        }
    }

//------------------------------------------------------------------------------------------------------------------
    private void resetTableFull() throws Exception {
        jbtAux.setBackground(narUlg);
        jbtAux.setTable(null);
        jbtAux.setOpenJBT(false);
        jbtSetter();
        resetTableValues();
        utiliMsg.cargaTableErase();
    }

    private void resetTableValues() throws Exception {
        itemsTableAux = new ArrayList<ItemCarta>();//items a cobrar de la mesa
        itemsGift = new ArrayList<ItemCarta>(); //items obsequiados
        itemsPartialPaid = new ArrayList<ItemCarta>(); // items cobrados por pago parcial
        itemsPartialPaidNoDiscount = new ArrayList<ItemCarta>(); // items cobrados anted de aplicar descuento
        waiterAux = null;
        tableAux = null;
        total = 0;
        error = 0;
        discount = 0;
        priceCorrection = 0;
        setTableItems();
        labelTotalParcial.setText("Parcial $:");
        labelCuenta.setText("0.00");
        labelTip.setText("Prop: $0.00");
        labelTotal.setText("Total: $0.00");
        labelPartialPay.setText("Pagado: $0.00");
        labelMesa.setText("Mesa:--");
        labelWaiter.setText("Mozo: --");
        butCloseTable.setText("CERRAR MESA");
        jbtAux.setBorder(null);
        if (jbtAux.isOpenJBT() == true) {
            if (itemsTableAux.size() > 0) {
                if (tableAux.isBill() == true) {
                    butCloseTable.setText("CONFIRMAR PAGO");
                } else {
                    butCloseTable.setText("CERRAR CUENTA");
                }
            }
        }
    }

    public void workshiftConclude(Workshift actualWs, ArrayList<Table> tabs, boolean partialEnd) throws Exception {  //new WorkshiftEndPanel(sal, tabs, partialEnd);
        workshiftActual = actualWs;
//        new WorkshiftEndPanel(sal, workshiftActual, tabs, partialEnd);
    }
}
