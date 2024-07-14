package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.awt.font.TextAttribute.FONT;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNestedSpace;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigSalonFrame extends FrameThird {

    DAOConfig daoC = new DAOConfig();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    Font font1 = new Font("Arial", Font.BOLD, 20);
    SalonManager sm = new SalonManager();

    JList listSpaces = new JList();
    JList listSelected = new JList();

    JList listCat = new JList();
    JList listCatSel = new JList();

    ArrayList<String> spacesDB = new ArrayList<>();
    ArrayList<String> spaces = new ArrayList<>();
    ArrayList<String> spacesSel = new ArrayList<>();
    ArrayList<String> charsDB = new ArrayList<>();
    ArrayList<String> charsSel = new ArrayList<>();
    ArrayList<Integer> quants = new ArrayList<>();
    ArrayList<String> categoriesDB = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> categoriesSel = new ArrayList<>();

    JButtonMetalBlu butConfirm1 = null;
    JButtonMetalBlu butConfirm2 = null;
    JButtonMetalBlu butReset = null;
    JButtonMetalBlu butAddSpace = null;
    JButtonMetalBlu butAddCategory = null;
    JSpinner spinnerTip = new JSpinner();
    String data1 = "";
    String data2 = "";

    JLabel labelData1 = new JLabel();
    JLabel labelData2 = new JLabel();

    JPanel panelPanel = new JPanel();
    ArrayList<PanelNestedSpace> panels = new ArrayList<>();

    ConfigGeneral cfgGen = new ConfigGeneral();
    User user = null;

    boolean selSectors = false;

    public ConfigSalonFrame(User u) throws Exception {
        sm.addFrame(this);
        user = u;
        cfgGen = daoC.askConfigGeneral();
        setTitle("Configuración Salón");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        spacesDB = daoC.askSpaces();
        spaces = spacesDB;
        categoriesDB = daoC.askCategories();
        categories = categoriesDB;
        charsDB = daoC.askChars();

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Configurar Salón");
        labelTit.setBounds(anchoUnit * 9, altoUnit * 0, anchoUnit * 18, altoUnit * 5);
        panelPpal.add(labelTit);

        JLabel labelIndications1 = utiliGraf.labelTitleBacker2W("Seleccione el nombre de los sectores del local");
        labelIndications1.setBounds(anchoUnit * 3, altoUnit * 5, anchoUnit * 28, altoUnit * 3);
        panelPpal.add(labelIndications1);

        JLabel labelItemsTP = utiliGraf.labelTitleBacker3W("Sectores a Seleccionar");
        labelItemsTP.setBounds(anchoUnit * 2, altoUnit * 8, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelItemsTP);

        listSpaces.setModel(utili.spacesListModelReturnMono(spaces));
        listSpaces.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (spacesSel.size() < 8) {
                        String selectedValue = (String) listSpaces.getSelectedValue();
                        selSpace(selectedValue, 1);
                    } else {
                        utiliMsg.errorSpacesExcess();
                    }
                }
            }
        });
        JScrollPane jSSpaces = new JScrollPane(listSpaces);
        jSSpaces.setBounds(anchoUnit * 2, altoUnit * 11, anchoUnit * 8, altoUnit * 8);
        panelPpal.add(jSSpaces);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3W("Sectores Seleccionados");
        labelItemsSel.setBounds(anchoUnit * 15, altoUnit * 8, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelItemsSel);

        listSelected.setModel(utili.spacesListModelReturnMono(spacesSel));
        listSelected.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    unSelSpace();
                }
            }
        });

        JScrollPane jSSelected = new JScrollPane(listSelected);
        jSSelected.setBounds(anchoUnit * 15, altoUnit * 11, anchoUnit * 8, altoUnit * 8);
        panelPpal.add(jSSelected);

        butAddSpace = utiliGraf.button3("Crear Espacio", anchoUnit * 24, altoUnit * 16, anchoUnit * 9);
        butAddSpace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createSpace();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butAddSpace);
        
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBounds(anchoUnit * 1, altoUnit * 19 + 3, anchoUnit * 32, 2); // Posición y tamaño del separador
        panelPpal.add(separator1);

        JLabel labelIndications2 = utiliGraf.labelTitleBacker2W("Seleccione la cantidad de mesas por sector:");
        labelIndications2.setBounds(anchoUnit * 3, altoUnit * 20, anchoUnit * 28, altoUnit * 3);
        panelPpal.add(labelIndications2);

        panelPanel.setLayout(null);
        panelPanel.setBackground(bluLg);
        panelPanel.setPreferredSize(new Dimension(anchoUnit * 30, altoUnit * 20));

        JScrollPane scrollPane = new JScrollPane(panelPanel);
        scrollPane.setBounds(anchoUnit * 1, altoUnit * 23, anchoUnit * 32, altoUnit * 17);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelPpal.add(scrollPane);

        butConfirm1 = utiliGraf.button2("Confirmar Sectores", anchoUnit * 9, altoUnit * 40 + 4, anchoUnit * 16);
        butConfirm1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panels.size() > 0) {
                    try {
                        confirmTables();
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelPpal.add(butConfirm1);
        
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setBounds(anchoUnit * 1, altoUnit * 45, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelPpal.add(separator2);

        JLabel labelIndications3 = utiliGraf.labelTitleBacker2W("Seleccione hasta 6 categorías");
        labelIndications3.setBounds(anchoUnit * 8, altoUnit * 46, anchoUnit * 18, altoUnit * 3);
        panelPpal.add(labelIndications3);

        JLabel labelCat = utiliGraf.labelTitleBacker3W("Categ. a Seleccionar");
        labelCat.setBounds(anchoUnit * 2, altoUnit * 49, anchoUnit * 13, altoUnit * 3);
        panelPpal.add(labelCat);

        listCat.setModel(utili.spacesListModelReturnMono(categories));
        listCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (categoriesSel.size() < 6) {
                        String selectedValue = (String) listCat.getSelectedValue();
                        selCategory(selectedValue, 1);
                    } else {
                        utiliMsg.errorCatExcess();
                    }
                }
            }
        });
        JScrollPane jSCat = new JScrollPane(listCat);
        jSCat.setBounds(anchoUnit * 2, altoUnit * 52, anchoUnit * 8, altoUnit * 8);
        panelPpal.add(jSCat);

        JLabel labelCatSel = utiliGraf.labelTitleBacker3W("Categ. Seleccionadas");
        labelCatSel.setBounds(anchoUnit * 15, altoUnit * 49, anchoUnit * 13, altoUnit * 3);
        panelPpal.add(labelCatSel);

        listCatSel.setModel(utili.spacesListModelReturnMono(categoriesSel));
        listCatSel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedValue = (String) listCatSel.getSelectedValue();
                    unSelCategory(selectedValue);
                }
            }
        });

        JScrollPane jSCatSel = new JScrollPane(listCatSel);
        jSCatSel.setBounds(anchoUnit * 15, altoUnit * 52, anchoUnit * 8, altoUnit * 8);
        panelPpal.add(jSCatSel);

        butAddCategory = utiliGraf.button3("Crear Categoría", anchoUnit * 24, altoUnit * 57, anchoUnit * 9);
        butAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createCategory();
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butAddCategory);
        
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        separator3.setBounds(anchoUnit * 1, altoUnit * 60 + 3, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelPpal.add(separator3);

        JLabel labelIndications4 = utiliGraf.labelTitleBacker2W("Seleccione el porcentaje de las Propinas:");
        labelIndications4.setBounds(anchoUnit * 4, altoUnit * 61, anchoUnit * 25, altoUnit * 3);
        panelPpal.add(labelIndications4);
        
        JLabel labelPorcentaje = utiliGraf.labelTitleBacker1W("Porcentaje %:");
        labelPorcentaje.setBounds(anchoUnit * 8, altoUnit * 64, anchoUnit * 11, altoUnit * 4);
        panelPpal.add(labelPorcentaje);        

        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
        spinnerTip.setModel(model);
        spinnerTip.setBounds(anchoUnit * 19, altoUnit * 64, anchoUnit * 4, altoUnit * 4);
        spinnerTip.setFont(font1);
        spinnerTip.setValue(10);
        panelPpal.add(spinnerTip);
        
        JSeparator separator4 = new JSeparator(SwingConstants.HORIZONTAL);
        separator4.setBounds(anchoUnit * 1, altoUnit * 69 + 3, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelPpal.add(separator4);

        Font font = new Font("Arial", Font.PLAIN, 16);
        data1 = "<html><b>SECTORES</b>:</html>";
        labelData1 = utiliGraf.labelTitleBacker2W(data1);
        labelData1.setText(data1);
        labelData1.setBounds(anchoUnit * 1, altoUnit * 70, anchoUnit * 32, altoUnit * 8);
        labelData1.setFont(font);
        labelData1.setBackground(bluLg);
        panelPpal.add(labelData1);

        data2 = "<html><b>CATEGORÍAS</b>:</html>";
        labelData2 = utiliGraf.labelTitleBacker2W(data2);
        labelData2.setText(data2);
        labelData2.setBounds(anchoUnit * 1, altoUnit * 78, anchoUnit * 32, altoUnit * 6);
        labelData2.setFont(font);
        labelData2.setBackground(bluLg);
        panelPpal.add(labelData2);

        butConfirm2 = utiliGraf.button1("Confirmar Configuración", anchoUnit, altoUnit * 85, anchoUnit * 21);
        butConfirm2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (categoriesSel.size() > 0) {
                    try {
                        if (selSectors) {
                            boolean confirm = utiliMsg.cargaConfirmRestart();
                            if (confirm) {
                                String pass = utiliMsg.requestPass();
                                if (pass.equals(user.getPassword())) {
                                    createConfig();
                                } else {
                                    utiliMsg.errorAccessDenied();
                                }
                            }
                        }
                        utiliMsg.errorUnconfirmTable();
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (quants.size() == 0) {
                    utiliMsg.errorNullSector();
                } else {
                    utiliMsg.errorCategoriesNull();
                }
            }
        });
        panelPpal.add(butConfirm2);

        butReset = utiliGraf.button1("Resetear", anchoUnit * 23, altoUnit * 85, anchoUnit * 10);
        butReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panels.size() > 0) {
                    try {
                        resetValues();
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelPpal.add(butReset);

        if (cfgGen.getTableItemCategories() != null) {
            updateValues();
        }
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
    }

    private void selSpace(String selectedValue, int i) {
        if (i == 1) {
            spacesSel.add(selectedValue);
        }
        ListModel modeloLista1 = utili.spacesListModelReturnMono(spacesSel);
        listSelected.setModel(modeloLista1);
        Iterator<String> iterador = spaces.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(spaces);
        listSpaces.setModel(modeloLista2);

        PanelNestedSpace pn = new PanelNestedSpace(selectedValue, panels.size());

        panelManager(pn, selectedValue);
    }

    private void unSelSpace() {
        String selectedValue = (String) listSelected.getSelectedValue();
        spaces.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(spaces);
        listSpaces.setModel(modeloLista1);
        Iterator<String> iterador = spacesSel.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(spacesSel);
        listSelected.setModel(modeloLista2);

        panelManager(null, selectedValue);
    }

    private void confirmTables() throws Exception {
        ArrayList<Integer> tabsQ = new ArrayList<>();
        spacesDB = daoC.askSpaces();
        boolean zero = false;
        for (int i = 0; i < panels.size(); i++) {
            PanelNestedSpace paN = panels.get(i);
            ButtonGroup group = paN.getGroup();
            int m = 0;
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    String selection = button.getText();
                    if (selection.equals("12 Mesas")) {
                        m = 12;
                    } else if (selection.equals("24 Mesas")) {
                        m = 24;
                    } else if (selection.equals("35 Mesas")) {
                        m = 35;
                    } else if (selection.equals("48 Mesas")) {
                        m = 48;
                    }
                    break;
                }
            }
            if (m == 0) {
                zero = true;
            } else {
                tabsQ.add(m);
            }
        }

        if (zero == false) {
            quants = tabsQ;
            for (int i = 0; i < spacesSel.size(); i++) {
                String st1 = spacesSel.get(i);
                for (int j = 0; j < spacesDB.size(); j++) {
                    String st2 = spacesDB.get(j);
                    if (st1.equals(st2)) {
                        charsSel.add(charsDB.get(j));
                    }
                }
            }

            for (int i = 0; i < panels.size(); i++) {
                Enumeration<AbstractButton> buttons = panels.get(i).getGroup().getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    button.setEnabled(false);
                }
            }

            data1Updater();
            selSectors = true;
            butConfirm1.setEnabled(false);
            listSelected.setEnabled(false);
            listSpaces.setEnabled(false);
            butAddSpace.setEnabled(false);
        } else {
            utiliMsg.errorNullTabsQ();
            zero = true;
        }
    }

    private void panelManager(PanelNestedSpace pn, String st) {
        panelPanel.removeAll();
        if (pn != null) {
            panels.add(pn);
        } else {
            Iterator<PanelNestedSpace> iterador = panels.iterator();
            while (iterador.hasNext()) {
                PanelNestedSpace p = iterador.next();
                if (p.getSt().equals(st)) {
                    iterador.remove();
                }
            }

            ArrayList<PanelNestedSpace> panels2 = new ArrayList<>();
            int counter = 0;
            for (int i = 0; i < panels.size(); i++) {
                PanelNestedSpace pan = new PanelNestedSpace(panels.get(i).getSt(), counter);
                panels2.add(pan);
                counter += 1;
            }
            panels = panels2;

        }

        int h = 0;
        h = 1 + 8 * panels.size();
        if (h < 20) {
            h = 20;
        }

        panelPanel.setPreferredSize(new Dimension(anchoUnit * 30, altoUnit * h));

        for (int i = 0; i < panels.size(); i++) {
            panelPanel.add(panels.get(i));
        }

        panelPanel.revalidate();
        panelPanel.repaint();
    }

    private void selCategory(String selectedValue, int i) {
        if (i == 1) {
            categoriesSel.add(selectedValue);
        }
        ListModel modeloLista1 = utili.spacesListModelReturnMono(categoriesSel);
        listCatSel.setModel(modeloLista1);

        Iterator<String> iterador = categories.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(categories);
        listCat.setModel(modeloLista2);

        data2Updater();
    }

    private void unSelCategory(String selectedValue) {
        selectedValue = (String) listCatSel.getSelectedValue();
        categories.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(categories);
        listCat.setModel(modeloLista1);
        Iterator<String> iterador = categoriesSel.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(categoriesSel);
        listCatSel.setModel(modeloLista2);
        data2Updater();
    }
    
    private void data1Updater() {
        String st = "<html><b>SECTORES</b>: ";
        for (int i = 0; i < spacesSel.size(); i++) {
            if (i == spacesSel.size() - 1) {
                st += spacesSel.get(i) + "(" + quants.get(i) + ")";
            } else {
                st += spacesSel.get(i) + "(" + quants.get(i) + ") - ";
            }
        }
        st = st + "</html>";
        labelData1.setText(st);
    }

    private void data2Updater() {
        String st = "<html><b>CATEGORÍAS</b>: ";
        for (int i = 0; i < categoriesSel.size(); i++) {
            if (i == categoriesSel.size() - 1) {
                st += categoriesSel.get(i);
            } else {
                st += categoriesSel.get(i) + " - ";
            }
        }
        st = st + "</html>";
        labelData2.setText(st);
    }

    private void createConfig() throws Exception {
        int totalTab = 0;
        int tipPc = (int) spinnerTip.getValue();
        for (int i = 0; i < quants.size(); i++) {
            totalTab += quants.get(i);
        }
        daoC.saveConfigGeneral(totalTab, quants, spacesSel, categoriesSel, charsSel, rootPaneCheckingEnabled, tipPc);
        sm.frameCloser();
        System.exit(0);
    }

    private void resetValues() throws Exception {
        categoriesDB = daoC.askCategories();
        categories = categoriesDB;
        spacesDB = daoC.askSpaces();
        spaces = spacesDB;
        categoriesSel = new ArrayList<>();
        spacesSel = new ArrayList<>();
        charsSel = new ArrayList<>();
        quants = new ArrayList<>();
        listSpaces.setModel(utili.spacesListModelReturnMono(spaces));
        listSelected.setModel(utili.spacesListModelReturnMono(spacesSel));
        listCat.setModel(utili.spacesListModelReturnMono(categories));
        listCatSel.setModel(utili.spacesListModelReturnMono(categoriesSel));
        spinnerTip.setValue(10);
        data1Updater();
        data2Updater();
        panels = new ArrayList<>();
        butConfirm1.setEnabled(true);
        butAddSpace.setEnabled(true);
        selSectors = false;
        listSelected.setEnabled(true);
        listSpaces.setEnabled(true);
        panelPanel.removeAll();
        panelPanel.revalidate();
        panelPanel.repaint();
    }

    private void createSpace() throws Exception {
        String space = utiliMsg.cargaNewString(1, 15);
        spaces.add(space);
        daoC.saveSpace(space);
        String cha = space.substring(0, 1);
        charsDB.add(cha);
        daoC.saveChar(cha);
        spaces = daoC.askSpaces();
        listSpaces.setModel(utili.spacesListModelReturnMono(spaces));
    }

    private void createCategory() throws Exception {
        String category = utiliMsg.cargaNewString(2, 10);
        categories.add(category);
        daoC.saveCategory(category);
        categories = daoC.askCategories();
        listCat.setModel(utili.spacesListModelReturnMono(categories));
    }

    private void updateValues() throws Exception {
        categoriesSel = cfgGen.getTableItemCategories();
        spacesSel = cfgGen.getTablePan();
        quants = cfgGen.getTableNum();

        for (int i = 0; i < categoriesSel.size(); i++) {
            selCategory(categoriesSel.get(i), 0);
        }

        for (int i = 0; i < spacesSel.size(); i++) {
            selSpace(spacesSel.get(i), 0);
        }

        for (int i = 0; i < quants.size(); i++) {
            PanelNestedSpace paN = panels.get(i);
            ButtonGroup group = paN.getGroup();
            int m = quants.get(i);
            String tabs = "";
            if (m == 12) {
                tabs = "12 Mesas";
            } else if (m == 24) {
                tabs = "24 Mesas";
            } else if (m == 35) {
                tabs = "35 Mesas";
            } else {
                tabs = "48 Mesas";
            }

            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                String selection = button.getText();
                if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                }
            }
        }

        data1Updater();
        data2Updater();
    }
}
