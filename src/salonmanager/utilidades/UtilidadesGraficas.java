package salonmanager.utilidades;

import java.awt.BorderLayout;
import salonmanager.entidades.User;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.ItemcardIngreso;
import salonmanager.SalonManager;
import salonmanager.ItemSelector;
import salonmanager.Manager;
import salonmanager.WorkshiftSession;
import salonmanager.entidades.Admin;
import salonmanager.entidades.Config;
import salonmanager.entidades.Itemcard;
import salonmanager.entidades.Register;

public class UtilidadesGraficas extends JFrame {

    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(242, 29, 41);
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    SalonManager sm = new SalonManager();
    Manager manager = null;

    public JMenuBar navegador(User user, String pass, Manager man) throws Exception {
        manager = man;
        JMenuBar menuBar = new JMenuBar();
        JMenu menuInicio = new JMenu("Inicio");
        JMenu menuCard = new JMenu("Carta");
        JMenu menuFacturacion = new JMenu("Facturación");
        JMenu menuSalon = new JMenu("Salón");
        JMenuItem itemAdministrador = new JMenuItem("Administrador");
        JMenuItem itemSalir = new JMenuItem("Salir");
        JMenuItem itemIngresoItemcard = new JMenuItem("Ingreso Itemcarta");
        JMenuItem itemModificacionItemcard = new JMenuItem("Modificación Itemcarta");
        JMenuItem itemConsultaItemcard = new JMenuItem("Consulta Itemcarta");
        JMenuItem itemSesion = new JMenuItem("Sesiones");
        JMenuItem itemTurno = new JMenuItem("Turnos");
        JMenuItem itemSalon = new JMenuItem("Salón");

        if (sm.rolPermission(2)) {
            menuInicio.add(itemAdministrador);
        }
        menuInicio.add(itemSalir);
        menuCard.add(itemIngresoItemcard);
        menuCard.add(itemModificacionItemcard);
        menuCard.add(itemConsultaItemcard);
        menuFacturacion.add(itemSesion);
        menuFacturacion.add(itemTurno);
        menuSalon.add(itemSalon);

        menuBar.add(menuInicio);
        if (sm.rolPermission(2)) {
            menuBar.add(menuCard);
        }
        menuBar.add(menuFacturacion);
        menuBar.add(menuSalon);

//---------------------------------------------------------------------------------------------------------        
        if (sm.rolPermission(2)) {
            itemAdministrador.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        if (user.getPassword().equals(pass)) {
                            new Admin(user);
                        } else {
                            sm.salir();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    sm.salir();
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

//----------------------------------------------------------------------------------------------------------
        itemIngresoItemcard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (user.getPassword().equals(pass)) {
                        new ItemcardIngreso();
                    } else {
                        sm.salir();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        itemModificacionItemcard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (user.getPassword().equals(pass)) {
                        new ItemSelector("m");
                    } else {
                        sm.salir();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        itemConsultaItemcard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (user.getPassword().equals(pass)) {
                        new ItemSelector("s");
                    } else {
                        sm.salir();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        if (sm.rolPermission(2)) {
            itemSalon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        if (user.getPassword().equals(pass)) {
                            Config cfg = sm.getConfig();
                            if (cfg.isActiveConfig()) {
                                new WorkshiftSession(manager);
                            } else {
                                utiliMsg.configNull();
                            }
                        } else {
                            sm.salir();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
//-------------------------------------------
        return menuBar;
    }

    public JPanel dataPanelBacker(String labelData, int font) {
        javax.swing.border.Border bordeInterior = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        JPanel panelData = new JPanel();
        panelData.setLayout(new BoxLayout(panelData, BoxLayout.X_AXIS));
        panelData.setBackground(bluLg);
        panelData.setBorder(bordeInterior);
        JLabel nameData = new JLabel(labelData);
        Font fuente = new Font("Arial", Font.BOLD, font);
        nameData.setFont(fuente);
        panelData.add(nameData);
        panelData.add(Box.createHorizontalStrut(40)); // Espacio 
        return panelData;
    }

    public JScrollPane scrollBackPane(JList listaIngreDB, int mw, int mh, int w, int h) {
        JScrollPane scrollPane = new JScrollPane(listaIngreDB);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(mw, mh, w, h);
        return scrollPane;
    }

    public JPanel selectUserPanel(int mWidth, int mHeight, int width, int height, JList listaUserDB, ArrayList<User> usersDB, ArrayList<User> usersNull, boolean mail) {
        JPanel panelListRctDB = new JPanel();
        panelListRctDB.setLayout(null);
        panelListRctDB.setBackground(narSt);
        panelListRctDB.setBounds(mWidth, mHeight, width, height);

        JLabel labelPanelA = labelTitleBacker2("Seleccione un Usuario");
        labelPanelA.setBounds(10, 5, width / 4 * 3, 30);
        panelListRctDB.add(labelPanelA);

        if (mail) {
            listaUserDB.setModel(utili.usersListModelReturn(usersDB, usersNull, true));
        } else {
            listaUserDB.setModel(utili.usersListModelReturn(usersDB, usersNull, false));
        }

        JScrollPane scrollPane = scrollBackPane(listaUserDB, width / 8, height / 3, width / 2, height / 2);
        panelListRctDB.add(scrollPane);

        return panelListRctDB;
    }

    public JLabel labelTitleBackerA1(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(75f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBackerA2(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(50f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBackerA2b(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(42f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBackerA3(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(35f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBackerA4(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(25f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBacker1(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(20f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBacker2(String tit) {
        JLabel title = new JLabel();
        title.setText(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(16f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBacker3(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(13f);
        title.setFont(newFont);
        return title;
    }

    public JLabel labelTitleBacker4(String tit) {
        JLabel title = new JLabel(tit);
        Font font = title.getFont();
        Font newFont = font.deriveFont(9f);
        title.setFont(newFont);
        return title;
    }

    public JButton button1(String text, int mWidth, int mHeight, int width) {
        JButton bot = new JButton();
        text = text.toUpperCase();
        Font font = new Font("Arial", Font.BOLD, 16); // Crear una nueva fuente
        bot.setFont(font); // Establecer la fuente en el botón
        bot.setText(text);
        bot.setBorder(new EmptyBorder(10, 10, 10, 10));
        bot.setBounds(mWidth, mHeight, width, 40);
        return bot;
    }

    public JButton button2(String text, int mWidth, int mHeight, int width) {
        JButton bot = new JButton();
        text = text.toUpperCase();
        Font font = new Font("Arial", Font.BOLD, 14); // Crear una nueva fuente
        bot.setFont(font); // Establecer la fuente en el botón
        bot.setText(text);
        bot.setBorder(new EmptyBorder(8, 8, 8, 8));
        bot.setBounds(mWidth, mHeight, width, 30);
        return bot;
    }

    public JButton button3(String text, int mWidth, int mHeight, int width) {
        JButton bot = new JButton();
        text = text.toUpperCase();
        Font font = new Font("Arial", Font.BOLD, 11); // Crear una nueva fuente
        bot.setFont(font); // Establecer la fuente en el botón
        bot.setText(text);
        bot.setBorder(new EmptyBorder(5, 5, 5, 5));
        bot.setBounds(mWidth, mHeight, width, 25);
        return bot;
    }

    public JButton buttonSalir(int width) {
        JButton butSalir = new JButton();
        butSalir = button2("salir", width - 90, 691 - 45, 70);
        return butSalir;
    }

    public JButton buttonSalir2(int width, int height) {
        JButton butSalir = new JButton();
        butSalir = button2("salir", width - 90, height, 70);
        return butSalir;
    }

    public JButton buttonModify() {
        JButton butSalir = new JButton();
        butSalir = button1("modificar", 140, 691 - 75, 120);
        return butSalir;
    }

    public JScrollPane scrollBackerAdmin(int anchoPantalla, int alturaPantalla, ArrayList<Register> listReg, ArrayList<User> listUsers) {
        int rows = 0;
        int col = 0;
        int posY = 0;
        int altY = 0;
        String col1 = "";
        String col2 = "";
        String col3 = "";
        String col4 = "";
        String col5 = "";
        String col6 = "";

        if (listReg != null) {
            altY = 300;
            posY = 260;
            rows = listReg.size();
            col = 6;
            col1 = "Id";
            col2 = "Fecha";
            col3 = "Usuario";
            col4 = "Operación";
            col5 = "Objeto";
            col6 = "Modificaciones";
        }

        if (listUsers != null) {
            altY = 100;
            posY = 70;
            rows = listUsers.size();
            col = 6;
            col1 = "Id";
            col2 = "Nombre";
            col3 = "LastName";
            col4 = "E-Mail";
            col5 = "Rol";
            col6 = "Estado";
        }

        String[] colNames = {col1, col2, col3, col4, col5, col6};
        String[][] data = new String[rows][col];

        if (listUsers != null) {
            for (int i = 0; i < rows; i++) {
                User u = listUsers.get(i);
                data[i][0] = "  " + u.getId();
                data[i][1] = "  " + u.getName();
                data[i][2] = "  " + u.getLastName();
                data[i][3] = "  " + u.getMail();
                data[i][4] = "  " + u.getRol();
                data[i][5] = "  " + u.isActiveUser();
            }
        }

        if (listReg != null) {
            for (int i = 0; i < rows; i++) {
                Register reg = listReg.get(i);
                data[i][0] = "  " + reg.getId();
                data[i][1] = "  " + reg.getEjecution();
                data[i][2] = "  " + reg.getUser();
                data[i][3] = "  " + reg.getOperation();
                data[i][4] = "  " + reg.getObject();
                data[i][5] = "  " + reg.getModification();
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        JTable table = new JTable(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(narSt);
        Font cellFont = new Font("Arial", Font.BOLD, 12);
        table.setFont(cellFont);
        table.setRowHeight(25);
        table.setBackground(bluLg);
        table.setForeground(bluSt);

        if (listReg != null) {
            int c = anchoPantalla / 35;
            TableColumn column1 = table.getColumnModel().getColumn(0);
            column1.setPreferredWidth(c);
            TableColumn column2 = table.getColumnModel().getColumn(1);
            column2.setPreferredWidth(c * 4);
            TableColumn column3 = table.getColumnModel().getColumn(2);
            column3.setPreferredWidth(c * 5);
            TableColumn column4 = table.getColumnModel().getColumn(3);
            column4.setPreferredWidth(c * 4);
            TableColumn column5 = table.getColumnModel().getColumn(4);
            column5.setPreferredWidth(c * 4);
            TableColumn column6 = table.getColumnModel().getColumn(5);
            column6.setPreferredWidth(c * 16);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(bluLg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, posY, anchoPantalla, alturaPantalla - altY);
        return scrollPane;
    }

    public JPanel panelItemcardForm(JTextField fieldName, JComboBox comboCaption, JTextArea areaDescription, JTextField fieldCost, JTextField fieldPrice, JTextField fieldStock, JCheckBox checkTip, ArrayList<String> captionsDB, Itemcard item) {
        JPanel panelA = new JPanel();
        panelA.setLayout(null);
        panelA.setBounds(135, 50, 416, 545);
        panelA.setBackground(viol);

        JPanel panelData1 = dataPanelBacker("Name", 14);
        panelData1.setBounds(20, 20, 375, 50);
        panelData1.add(fieldName);
        panelA.add(panelData1);

        JPanel panelData2 = dataPanelBacker("Rubro", 14);
        panelData2.setBounds(20, 90, 375, 50);
        comboCaption.setModel(utili.captionComboModelReturn(captionsDB));
        panelData2.add(comboCaption);
        panelData2.add(Box.createHorizontalStrut(30));
        panelA.add(panelData2);

        JPanel panelData3 = dataPanelBacker("Descripción", 14);
        panelData3.setBounds(20, 160, 375, 100);
        areaDescription.setRows(4);
        areaDescription.setColumns(5);
        areaDescription.setLineWrap(true);
        areaDescription.setWrapStyleWord(true);
        JScrollPane paneDesc = new JScrollPane(areaDescription);
        panelData3.add(paneDesc);
        panelA.add(panelData3);

        JPanel panelData4 = dataPanelBacker("Costo", 14);
        panelData4.setBounds(20, 280, 375, 50);
        panelData4.add(fieldCost);
        panelA.add(panelData4);

        JPanel panelData5 = dataPanelBacker("Precio", 14);
        panelData5.setBounds(20, 350, 375, 50);
        panelData5.add(fieldPrice);
        panelA.add(panelData5);

        JPanel panelData6 = dataPanelBacker("Stock", 14);
        panelData6.setBounds(20, 420, 375, 50);
        panelData6.add(fieldStock);
        panelA.add(panelData6);

        JPanel panelData7 = dataPanelBacker("Propina deducible", 14);
        panelData7.setBounds(180, 490, 215, 35);
        panelData7.add(checkTip);
        panelA.add(panelData7);

        if (item != null) {
            fieldName.setText(item.getName());
            comboCaption.setSelectedItem(item.getCaption());
            areaDescription.setText(item.getDescription());
            fieldCost.setText(item.getCost() + "");
            fieldPrice.setText(item.getPrice() + "");
            fieldStock.setText(item.getStock() + "");
            checkTip.setSelected(item.isActiveTip());
        }
        return panelA;
    }

    public JPanel panelListItemBack(int mWidth, int mHeight, int width, int height, JList listaItems, ArrayList<Itemcard> itemsDB, ArrayList<Itemcard> itemsMesa) {
        JPanel panelListIngreDB = new JPanel();
        panelListIngreDB.setLayout(null);
        panelListIngreDB.setBackground(narSt);
        panelListIngreDB.setBounds(mWidth, mHeight, width, height);

        if (listaItems != null) {
            listaItems.setModel(utili.itemsListModelReturn(itemsDB, itemsMesa));
            JScrollPane scrollPane = scrollBackPane(listaItems, 5, 5, width - 10, height - 10);
            panelListIngreDB.add(scrollPane);
        }
        return panelListIngreDB;
    }

    public JSpinner spinnerBack(int mw, int mh, int w, int h, JSpinner spinner) {
        JSpinner spin = spinner;
        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
        spin.setModel(model);
        spin.setBounds(mw, mh, w, h);
        return spin;
    }

    public JPanel panelInfoBacker(int a, int b, int c, int d, Color col, int f, String stL, String stR) {
        JPanel panelBack = new JPanel();
        panelBack.setLayout(new BorderLayout());
        panelBack.setBackground(col);
        panelBack.setBounds(a, b, c, d);

        JLabel left = new JLabel(stL);
        JLabel right = new JLabel(stR);
        int tamañoFuenteInt = f;
        float tamañoFuenteFloat = (float) tamañoFuenteInt;
        Font font = left.getFont();
        Font newFont = font.deriveFont(tamañoFuenteFloat);
        left.setFont(newFont);
        right.setFont(newFont);

        JPanel panelLeft = new JPanel();
        panelLeft.setSize(c - 4, (c / 2) - 4);
        panelLeft.setBackground(col);
        panelLeft.add(left);
        panelBack.add(panelLeft, BorderLayout.WEST);

        JPanel panelRight = new JPanel();
        panelRight.setSize(c - 4, (c / 2) - 4);
        panelRight.setBackground(col);
        panelRight.add(right);
        panelBack.add(panelRight, BorderLayout.EAST);

        return panelBack;
    }

}
