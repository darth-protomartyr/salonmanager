package salonmanager.utilidades;

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
import salonmanager.ItemCartaIngreso;
import salonmanager.SalonManager;
import salonmanager.ItemSelector;
import salonmanager.Salon;
import salonmanager.entidades.Administrador;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Register;

public class UtilidadesGraficas extends JFrame {

    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(242, 29, 41);
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();

    public JMenuBar navegador(User user, String pass) throws Exception {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuInicio = new JMenu("Inicio");
        JMenu menuCarta = new JMenu("Carta");
        JMenu menuFacturacion = new JMenu("Facturación");
        JMenu menuSalon = new JMenu("Salón");
        JMenuItem itemAdministrador = new JMenuItem("Administrador");
        JMenuItem itemSalir = new JMenuItem("Salir");
        JMenuItem itemIngresoItemCarta = new JMenuItem("Ingreso Item Carta");
        JMenuItem itemModificacionItemCarta = new JMenuItem("Modificación Item Carta");
        JMenuItem itemConsultaItemCarta = new JMenuItem("Consulta Item Carta");
        JMenuItem itemSesion = new JMenuItem("Sesiones");
        JMenuItem itemTurno = new JMenuItem("Turnos");
        JMenuItem itemSalon = new JMenuItem("Salón");

        if (sm.rolPermission(2)) {
            menuInicio.add(itemAdministrador);
        }
        menuInicio.add(itemSalir);
        menuCarta.add(itemIngresoItemCarta);
        menuCarta.add(itemModificacionItemCarta);
        menuCarta.add(itemConsultaItemCarta);
        menuFacturacion.add(itemSesion);
        menuFacturacion.add(itemTurno);
        menuSalon.add(itemSalon);


        menuBar.add(menuInicio);
        if (sm.rolPermission(2)) {
            menuBar.add(menuCarta);
        }
        menuBar.add(menuFacturacion);
        menuBar.add(menuSalon);

//---------------------------------------------------------------------------------------------------------        
        if (sm.rolPermission(2)) {
            itemAdministrador.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        if (user.getPassword().equals(pass)) {
                            new Administrador(user);
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
        itemIngresoItemCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (user.getPassword().equals(pass)) {
                        new ItemCartaIngreso();
                    } else {
                        sm.salir();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UtilidadesGraficas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        itemModificacionItemCarta.addActionListener(new java.awt.event.ActionListener() {
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

        itemConsultaItemCarta.addActionListener(new java.awt.event.ActionListener() {
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
                            new Salon();
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
        JLabel nombreData = new JLabel(labelData);
        Font fuente = new Font("Arial", Font.BOLD, font);
        nombreData.setFont(fuente);
        panelData.add(nombreData);
        panelData.add(Box.createHorizontalStrut(40)); // Espacio 
        return panelData;
    }

    public JScrollPane scrollBackPane(JList listaIngreDB, int mw, int mh, int w, int h) {
        JScrollPane scrollPane = new JScrollPane(listaIngreDB);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(mw, mh, w, h);
        return scrollPane;
    }

//    public JPanel fieldIngrePanel(int mWidth, int mHeight, int width, int height, JTextField fieldCantidad, JTextField fieldMerma, JTextField fieldDesperdicio, int type) {
//        JPanel panelFieldIngre = new JPanel();
//        panelFieldIngre.setLayout(null);
//        panelFieldIngre.setBackground(narSt);
//        panelFieldIngre.setBounds(mWidth, mHeight, width, height);
//
//        JLabel labelPanelA = new JLabel();
//        if (type == 1) {
//            labelPanelA = labelTitleBacker2("Ingrese los valores");
//        } else {
//            labelPanelA = labelTitleBacker2("Modifique los valores");
//        }
//        labelPanelA.setBounds(10, 5, width / 4 * 3, 30);
//        panelFieldIngre.add(labelPanelA);
//
//        String medi = "Cantidad:";
//
//        JPanel panelData1 = dataPanelBacker(medi, 12);
//        panelData1.setBounds(10, 40, width - 20, 40);
//        panelData1.add(fieldCantidad);
//        panelFieldIngre.add(panelData1);
//
//        JPanel panelData2 = dataPanelBacker("Merma %;", 12);
//        panelData2.setBounds(10, 85, width - 20, 40);
//        panelData2.add(fieldMerma);
//        panelFieldIngre.add(panelData2);
//
//        JPanel panelData3 = dataPanelBacker("Desperdicio %:", 12);
//        panelData3.setBounds(10, 130, width - 20, 40);
//        panelData3.add(fieldDesperdicio);
//        panelFieldIngre.add(panelData3);
//
//        return panelFieldIngre;
//    }
//    public JPanel selectIngrePanel(int mWidth, int mHeight, int width, int height, JList listaIngreDB, ArrayList<Ingrediente> ingredientesDB, ArrayList<Ingrediente> ingredientesSbr) {
//        JPanel panelListIngreDB = new JPanel();
//        panelListIngreDB.setLayout(null);
//        panelListIngreDB.setBackground(narSt);
//        panelListIngreDB.setBounds(mWidth, mHeight, width, height);
//
//        JLabel labelPanelA = labelTitleBacker2("Seleccione un ingrediente");
//        labelPanelA.setBounds(10, 5, width / 4 * 3, 30);
//        panelListIngreDB.add(labelPanelA);
//
//        if (listaIngreDB != null) {
//            listaIngreDB.setModel(utili.ingreListModelReturn(ingredientesDB, ingredientesSbr));
//            JScrollPane scrollPane = scrollBackPane(listaIngreDB, width / 8, height / 3, width / 2, height / 2);
//            panelListIngreDB.add(scrollPane);
//        }
//        return panelListIngreDB;
//    }
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

//    public JPanel panelPorcSpinner(int mWidth, int mHeight, int width, int height, JSpinner spinnerPorcR) {
//        JPanel panelPorciones = new JPanel();
//        panelPorciones.setBounds(mWidth, mHeight, width, height);
//        panelPorciones.setBackground(narSt);
//        panelPorciones.setLayout(null);
//        javax.swing.border.Border bordeInterior = BorderFactory.createEmptyBorder(10, 10, 10, 10);
//        panelPorciones.setBorder(bordeInterior);
//
//        JLabel labelPorc = new JLabel(" Ingrese Porciones de Receta");
//        Font fuente = new Font("Arial", Font.BOLD, 16);
//        labelPorc.setFont(fuente);
//        labelPorc.setBounds(10, 10, 250, 20);
//        panelPorciones.add(labelPorc);
//
//        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
//        spinnerPorcR.setModel(model);
//        spinnerPorcR.setBounds(60, 35, 35, 30);
//        panelPorciones.add(spinnerPorcR);
//
//        return panelPorciones;
//    }
//    public JPanel panelIngredienteForm(JTextField fieldName, JTextField fieldPresentation, JTextField fieldCosto, JComboBox comboRubro, JComboBox comboMedida, ArrayList<Rubro> rubrosDB, Ingrediente ingreAux) {
//
//        JPanel panelB = new JPanel();
//        panelB.setLayout(null);
//        panelB.setBounds(134, 70, 416, 490);
//        panelB.setBackground(narYellow);
//
//        JPanel panelData1 = dataPanelBacker("Rubro", 16);
//        panelData1.setBounds(20, 20, 375, 50);
//        comboRubro.setModel(utili.rubComboModelReturn(rubrosDB));
//        panelData1.add(comboRubro);
//        panelB.add(panelData1);
//
//        JPanel panelData2 = dataPanelBacker("Nombre", 16);
//        panelData2.setBounds(20, 120, 375, 50);
//        panelData2.add(fieldName);
//        panelB.add(panelData2);
//
//        JPanel panelData3 = dataPanelBacker("Unidad de Medición", 16);
//        panelData3.setBounds(20, 220, 375, 50);
//        comboMedida.setPreferredSize(new Dimension(0, 40));
//        ArrayList<String> unidadesDB = utili.listarUnidades();
//        DefaultComboBoxModel<String> modeloLista2 = new DefaultComboBoxModel<String>();
//        for (String u : unidadesDB) {
//            modeloLista2.addElement(u);
//        }
//        comboMedida.setModel(modeloLista2);
//        panelData3.add(comboMedida);
//        panelB.add(panelData3);
//
//        JPanel panelData4 = dataPanelBacker("Presentación", 16);
//        panelData4.setBounds(20, 320, 375, 50);
//        panelData4.add(fieldPresentation);
//        panelB.add(panelData4);
//
//        JPanel panelData5 = dataPanelBacker("Costo", 16);
//        panelData5.setBounds(20, 420, 375, 50);
//        panelData5.add(fieldCosto);
//        panelB.add(panelData5);
//
//        if (ingreAux == null) {
//
//        } else {
//            fieldName.setText(ingreAux.getNombre());
//            fieldPresentation.setText(ingreAux.getPresentacion() + "");
//            fieldCosto.setText(ingreAux.getCosto() + "");
//            String rub = ingreAux.getRubro();
//            comboRubro.setSelectedItem(rub);
//            String med = ingreAux.getUnidadMedicion();
//            comboMedida.setSelectedItem(med);
//        }
//        return panelB;
//    }
//    public JScrollPane scrollBacker(int anchoPanel, int alturaPanel, ArrayList<Ingrediente> ingredientes, ArrayList<Subreceta> subrecetas, ArrayList<Receta> recetas) {
//        int xPan = 0;
//        int yPan = alturaPanel / 4;
//        int rows = 0;
//        int col = 2;
//        String col1 = "";
//        String col2 = "Costo";
//
//        if (ingredientes != null) {
//            xPan = 15;
//            rows = ingredientes.size();
//            col1 = "Ingrediente";
//        }
//
//        if (subrecetas != null) {
//            xPan = 30 + anchoPanel;
//            rows = subrecetas.size();
//            col1 = "Subrecetas";
//        }
//
//        if (recetas != null) {
//            xPan = 45 + anchoPanel * 2;
//            rows = recetas.size();
//            col1 = "Recetas";
//        }
//
//        String[] colNames = {col1, col2};
//        String[][] data = new String[rows][col];
//
//        if (ingredientes != null) {
//            xPan = 15;
//            rows = ingredientes.size();
//            col1 = "Ingrediente";
//            for (int i = 0; i < rows; i++) {
//                Ingrediente ingre = ingredientes.get(i);
//                data[i][0] = "  " + ingre.getNombre();
//                data[i][1] = ingre.getCosto() + "  ";
//            }
//        }
//
//        if (subrecetas != null) {
//            xPan = 30 + anchoPanel;
//            rows = subrecetas.size();
//            col1 = "Subrecetas";
//            for (int i = 0; i < rows; i++) {
//                data[i][0] = "  " + subrecetas.get(i).getNombre();
//                data[i][1] = subrecetas.get(i).getCosto() + "  ";
//            }
//        }
//
//        if (recetas != null) {
//            xPan = 45 + anchoPanel * 2;
//            rows = recetas.size();
//            col1 = "Recetas";
//            for (int i = 0; i < rows; i++) {
//                data[i][0] = "  " + recetas.get(i).getNombre();
//                data[i][1] = recetas.get(i).getCosto() + "  ";
//            }
//        }
//
//        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
//
//        JTable table = new JTable(tableModel);
//        table.setBounds(10, 10, anchoPanel - 50, alturaPanel - 50);
//        JTableHeader header = table.getTableHeader();
//        header.setFont(new Font("Arial", Font.BOLD, 16));
//        header.setBackground(narSt);
//
//        Font cellFont = new Font("Arial", Font.BOLD, 14);
//        table.setFont(cellFont);
//        table.setRowHeight(25);
//
//        table.setBackground(narYellow);
//        table.setForeground(narSt);
//
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
//        
//        int c = (anchoPanel -50)/4;
//        TableColumn column1 = table.getColumnModel().getColumn(0);
//        column1.setPreferredWidth(c*3);
//        TableColumn column2 = table.getColumnModel().getColumn(1);
//        column2.setPreferredWidth(c); 
//
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBackground(narYellow);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBounds(xPan, yPan, anchoPanel, alturaPanel);
//
//        return scrollPane;
//    }
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
            col3 = "Apellido";
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
                data[i][1] = "  " + u.getNombre();
                data[i][2] = "  " + u.getApellido();
                data[i][3] = "  " + u.getMail();
                data[i][4] = "  " + u.getRol();
                data[i][5] = "  " + u.isAlta();
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

    public JPanel panelItemCartaForm(JTextField fieldName, JComboBox comboCaption, JTextArea areaDescription, JTextField fieldCost, JTextField fieldPrice, JTextField fieldStock, JCheckBox checkTip, ArrayList<String> captionsDB, ItemCarta item) {
        JPanel panelA = new JPanel();
        panelA.setLayout(null);
        panelA.setBounds(135, 50, 416, 545);
        panelA.setBackground(viol);

        JPanel panelData1 = dataPanelBacker("Nombre", 14);
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
            checkTip.setSelected(item.isAltaTip());
        }
        return panelA;
    }

    public JPanel panelListItemBack(int mWidth, int mHeight, int width, int height, JList listaItems, ArrayList<ItemCarta> itemsDB, ArrayList<ItemCarta> itemsMesa) {
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
        JSpinner spin  = spinner; 
        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
        spin.setModel(model);
        spin.setBounds(mw, mh, w, h);
        return spin;
    }

   
}
