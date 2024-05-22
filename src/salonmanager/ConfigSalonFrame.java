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
import javax.swing.ListModel;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNested;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigSalonFrame extends FrameThird {

    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
    DAOConfig daoC = new DAOConfig();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();

    JList listSpaces = new JList();
    JList listSelected = new JList();

    JList listCap = new JList();
    JList listCapSel = new JList();

    ArrayList<String> spaces = new ArrayList<>();
    ArrayList<String> selected = new ArrayList<>();
    ArrayList<String> chars = new ArrayList<>();
    ArrayList<Integer> quants = new ArrayList<>();

    ArrayList<String> captions = new ArrayList<>();
    ArrayList<String> captionsSel = new ArrayList<>();

    JButtonMetalBlu butConfirm1 = null;
    JButtonMetalBlu butConfirm2 = null;
    JButtonMetalBlu butReset = null;

    String data1 = "";
    String data2 = "";

    JLabel labelData1 = new JLabel();
    JLabel labelData2 = new JLabel();

    JPanel panelPanel = new JPanel();
    ArrayList<PanelNested> panels = new ArrayList<>();

    User user = null;

    public ConfigSalonFrame(User u) throws Exception {
        sm.addFrame(this);
        user = u;
        setTitle("Configuración Salón");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        spaces.add("salón");
        spaces.add("patio");
        spaces.add("vereda");
        spaces.add("frente");
        spaces.add("fondo");
        spaces.add("sala arriba");
        spaces.add("sala adelante");
        spaces.add("sector norte");
        spaces.add("sector sur");
        spaces.add("sector este");
        spaces.add("sector oeste");

        captions.add("PLATOS");
        captions.add("BEBIDAS");
        captions.add("TRAGOS");
        captions.add("ENTRADAS");
        captions.add("POSTRES");
        captions.add("OTROS");
        captions.add("PIZZAS");
        captions.add("PASTAS");
        captions.add("CAFETERÍA");
        captions.add("PANADERÍA");
        captions.add("ENSALADAS");
        captions.add("SANDWICHS");
        captions.add("VINOS");
        captions.add("CERVEZAS");

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
                    if (selected.size() < 8) {
                        selSpace();
                    } else {
                        utiliMsg.errorSpacesExcess();
                    }
                }
            }
        });
        JScrollPane jSSpaces = new JScrollPane(listSpaces);
        jSSpaces.setBounds(anchoUnit * 2, altoUnit * 11, anchoUnit * 13, altoUnit * 10);
        panelPpal.add(jSSpaces);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3W("Sectores Seleccionados");
        labelItemsSel.setBounds(anchoUnit * 19, altoUnit * 8, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelItemsSel);

        listSelected.setModel(utili.spacesListModelReturnMono(selected));
        listSelected.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    unSelSpace();
                }
            }
        });

        JScrollPane jSSelected = new JScrollPane(listSelected);
        jSSelected.setBounds(anchoUnit * 19, altoUnit * 11, anchoUnit * 13, altoUnit * 10);
        panelPpal.add(jSSelected);

        JLabel labelIndications2 = utiliGraf.labelTitleBacker2W("Seleccione la cantidad de mesas por sector:");
        labelIndications2.setBounds(anchoUnit * 3, altoUnit * 22, anchoUnit * 28, altoUnit * 3);
        panelPpal.add(labelIndications2);

        panelPanel.setLayout(null);
        panelPanel.setBackground(bluLg);
        panelPanel.setPreferredSize(new Dimension(anchoUnit * 30, altoUnit * 20));

        JScrollPane scrollPane = new JScrollPane(panelPanel);
        scrollPane.setBounds(anchoUnit * 1, altoUnit * 25, anchoUnit * 32, altoUnit * 20);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelPpal.add(scrollPane);

        butConfirm1 = utiliGraf.button2("Confirmar Sectores", anchoUnit * 9, altoUnit * 46, anchoUnit * 16);
        butConfirm1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panels.size() > 0) {
                    confirmTables();
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelPpal.add(butConfirm1);

        JLabel labelIndications3 = utiliGraf.labelTitleBacker2W("Seleccione hasta 6 Rubros");
        labelIndications3.setBounds(anchoUnit * 9, altoUnit * 52, anchoUnit * 16, altoUnit * 3);
        panelPpal.add(labelIndications3);

        JLabel labelCap = utiliGraf.labelTitleBacker3W("Rubros a Seleccionar");
        labelCap.setBounds(anchoUnit * 2, altoUnit * 55, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelCap);

        listCap.setModel(utili.spacesListModelReturnMono(captions));
        listCap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (captionsSel.size() < 6) {
                        selCaption();
                    } else {
                        utiliMsg.errorCapExcess();
                    }
                }
            }
        });
        JScrollPane jSCap = new JScrollPane(listCap);
        jSCap.setBounds(anchoUnit * 2, altoUnit * 58, anchoUnit * 13, altoUnit * 10);
        panelPpal.add(jSCap);

        JLabel labelCapSel = utiliGraf.labelTitleBacker3W("Rubros Seleccionados");
        labelCapSel.setBounds(anchoUnit * 19, altoUnit * 55, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelCapSel);

        listCapSel.setModel(utili.spacesListModelReturnMono(captionsSel));
        listCapSel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    unSelCaption();
                }
            }
        });

        JScrollPane jSCapSel = new JScrollPane(listCapSel);
        jSCapSel.setBounds(anchoUnit * 19, altoUnit * 58, anchoUnit * 13, altoUnit * 10);
        panelPpal.add(jSCapSel);

        Font font = new Font("Arial", Font.PLAIN, 16);
        data1 = "<html><b>SECTORES</b>:</html>";
        labelData1 = utiliGraf.labelTitleBacker2W(data1);
        labelData1.setText(data1);
        labelData1.setBounds(anchoUnit * 1, altoUnit * 70, anchoUnit * 32, altoUnit * 8);
        labelData1.setFont(font);
        labelData1.setBackground(bluLg);
        panelPpal.add(labelData1);

        data2 = "<html><b>RUBROS</b>:</html>";
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
                if (!panels.isEmpty() || !captions.isEmpty()) {
                    try {
                        String pass = utiliMsg.requestPass();
                        if (pass.equals(user.getPassword())) {
                            createConfig();
                        } else {
                            utiliMsg.errorAccessDenied();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelPpal.add(butConfirm2);

        butReset = utiliGraf.button1("Resetear", anchoUnit * 23, altoUnit * 85, anchoUnit * 10);
        butReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panels.size() > 0) {
                    confirmTables();
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelPpal.add(butReset);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
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

    private void selSpace() {
        String selectedValue = (String) listSpaces.getSelectedValue();
        selected.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(selected);
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

        PanelNested pn = new PanelNested(selectedValue, panels.size());

        panelManager(pn, selectedValue);
    }

    private void unSelSpace() {
        String selectedValue = (String) listSelected.getSelectedValue();
        spaces.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(spaces);
        listSpaces.setModel(modeloLista1);
        Iterator<String> iterador = selected.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(selected);
        listSelected.setModel(modeloLista2);

        panelManager(null, selectedValue);
    }

    private void confirmTables() {
        ArrayList<Integer> tabsQ = new ArrayList<>();
        boolean zero = false;
        for (int i = 0; i < panels.size(); i++) {
            PanelNested paN = panels.get(i);
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
            for (int i = 0; i < selected.size(); i++) {
                String st = selected.get(i);
                String ch = "";
                switch (st) {
                    case "salón":
                        ch = "s";
                        break;
                    case "patio":
                        ch = "p";
                        break;
                    case "vereda":
                        ch = "v";
                        break;
                    case "fremne":
                        ch = "f";
                        break;
                    case "fondo":
                        ch = "F";
                        break;
                    case "sala arriba":
                        ch = "a";
                        break;
                    case "sala adelante":
                        ch = "A";
                        break;
                    case "sector norte":
                        ch = "N";
                        break;
                    case "sector sur":
                        ch = "S";
                        break;
                    case "sector este":
                        ch = "E";
                        break;
                    case "sector oeste":
                        ch = "O";
                        break;
                }
                chars.add(ch);
            }

            for (int i = 0; i < panels.size(); i++) {
                Enumeration<AbstractButton> buttons = panels.get(i).getGroup().getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    button.setEnabled(false);
                }
            }

            data1Updater();
            butConfirm1.setEnabled(false);
            listSelected.setEnabled(false);
            listSpaces.setEnabled(false);
        } else {
            utiliMsg.errorNullTabsQ();
            zero = true;
        }

    }

    private void panelManager(PanelNested pn, String st) {
        panelPanel.removeAll();
        if (pn != null) {
            panels.add(pn);
        } else {
            Iterator<PanelNested> iterador = panels.iterator();
            while (iterador.hasNext()) {
                PanelNested p = iterador.next();
                if (p.getSt().equals(st)) {
                    iterador.remove();
                }
            }

            ArrayList<PanelNested> panels2 = new ArrayList<>();
            int counter = 0;
            for (int i = 0; i < panels.size(); i++) {
                PanelNested pan = new PanelNested(panels.get(i).getSt(), counter);
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

    private void selCaption() {
        String selectedValue = (String) listCap.getSelectedValue();
        captionsSel.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(captionsSel);
        listCapSel.setModel(modeloLista1);
        int counter = 0;
        Iterator<String> iterador = captions.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
            counter += 1;
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(captions);
        listCap.setModel(modeloLista2);

        data2Updater();

    }

    private void unSelCaption() {
        String selectedValue = (String) listCapSel.getSelectedValue();
        captions.add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(captions);
        listCap.setModel(modeloLista1);
        Iterator<String> iterador = captionsSel.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(captionsSel);
        listCapSel.setModel(modeloLista2);

        data2Updater();
    }

    private void data1Updater() {
        String st = "<html><b>SECTORES</b>: ";
        for (int i = 0; i < selected.size(); i++) {
            if (i == selected.size() - 1) {
                st += selected.get(i) + "(" + quants.get(i) + ")";
            } else {
                st += selected.get(i) + "(" + quants.get(i) + ") - ";
            }
        }
        st = st + "</html>";
        labelData1.setText(st);
    }

    private void data2Updater() {
        String st = "<html><b>RUBROS</b>: ";
        for (int i = 0; i < captionsSel.size(); i++) {
            if (i == captionsSel.size() - 1) {
                st += captionsSel.get(i);
            } else {
                st += captionsSel.get(i) + " - ";
            }
        }
        st = st + "</html>";
        labelData2.setText(st);
    }

    private void createConfig() throws Exception {
        ConfigGeneral cfgGen = new ConfigGeneral();
        int totalTab = 0;
        for (int i = 0; i < quants.size(); i++) {
            totalTab += quants.get(i);
        }

        daoC.saveConfigGeneral(totalTab, quants, spaces, captionsSel, chars, rootPaneCheckingEnabled);
    }
}
