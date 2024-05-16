package salonmanager;

import java.awt.Color;
import java.awt.TextArea;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNested;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigSalonFrame extends FrameThird {

    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
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

    ArrayList<String> spaces = new ArrayList<>();
    ArrayList<String> selected = new ArrayList<>();
    ArrayList<String> chars = new ArrayList<>();
    ArrayList<String> charsSel = new ArrayList<>();
    ArrayList<Integer> quants = new ArrayList<>();

    JButtonMetalBlu butConfirm1 = null;
    JButtonMetalBlu butConfirm2 = null;
    JButtonMetalBlu butConfirm3 = null;

    String stJp = "";
    JPanel panelPanel = new JPanel();
    ArrayList<PanelNested> panels = new ArrayList<>();
    JScrollPane scrollPane = new JScrollPane();

    
    public ConfigSalonFrame() throws Exception {
        sm.addFrame(this);
        setTitle("Configuración Salón");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        spaces.add("SALON");
        spaces.add("PATIO");
        spaces.add("VEREDA");
        spaces.add("FRENTE");
        spaces.add("FONDO");

        spaces.add("SALA ARRIBA");
        spaces.add("SALA ADELANTE");

        spaces.add("SALA NORTE");
        spaces.add("SALA SUR");
        spaces.add("SALA ESTE");
        spaces.add("SALA OESTE");

        spaces.add("SECTOR NORTE");
        spaces.add("SECTOR SUR");
        spaces.add("SECTOR ESTE");
        spaces.add("SECTOR OESTE");

        chars.add("s");
        chars.add("p");
        chars.add("v");
        chars.add("f");
        chars.add("F");

        chars.add("a");
        chars.add("A");

        chars.add("n");
        chars.add("s");
        chars.add("e");
        chars.add("o");

        chars.add("N");
        chars.add("S");
        chars.add("E");
        chars.add("O");

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
                    selSpace();
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

        JLabel labelIndications2 = utiliGraf.labelTitleBacker2W("Seleccione el nombre de los sectores del local");
        labelIndications2.setBounds(anchoUnit * 3, altoUnit * 22, anchoUnit * 28, altoUnit * 3);
        panelPpal.add(labelIndications2);
        
        panelPanel.setLayout(null);
        panelPanel.setBackground(bluLg);
        panelPanel.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 32, altoUnit * 20);
        
        scrollPane.add(panelPanel);
        scrollPane.setBounds(anchoUnit * 1, altoUnit * 25, anchoUnit * 32, altoUnit * 20);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelPpal.add(scrollPane);

        
        butConfirm1 = utiliGraf.button1("Confirmar Sectores", anchoUnit * 14, altoUnit * 46, anchoUnit * 12);
        butConfirm1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                    confirmTables();
            }
        });
        panelPpal.add(butConfirm1);

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
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
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
        int counter = 0;
        Iterator<String> iterador = spaces.iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
            counter += 1;
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
        
        panelManager( null,selectedValue);
    }

    private void confirmTables() {
        for (int i = 0; i < spaces.size(); i++) {
            
        }
    }

    void setQuantityTabs(int i) {
        quants.add(i);
    }

    private void panelManager(PanelNested pn, String st) {
        panelPanel.removeAll();
        if (pn != null) {
            panels.add(pn);
        } else {
            Iterator<PanelNested> iterador = panels.iterator();
            while (iterador.hasNext()) {
                PanelNested p = iterador.next();
                if (p.getSt().equals(pn.getSt())) {
                    iterador.remove();
                }
            }
        }

        int h = 0;
        h = 1 + 8 * panels.size();
        if (h < 20) {
            h = 20;
        }

        panelPanel.setBounds(anchoUnit * 0, altoUnit * 0, anchoUnit * 32, altoUnit * h);

        for (int i = 0; i < panels.size(); i++) {
            panelPanel.add(panels.get(i));
        }

        panelPanel.revalidate();
        panelPanel.repaint();
        
        scrollPane.removeAll();
        scrollPane.add(panelPanel);
        scrollPane.revalidate();
        scrollPane.repaint();
    }
}
