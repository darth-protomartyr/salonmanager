package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class TabsToEnd extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();
    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    ArrayList<Table> openTabs = new ArrayList<>();
    ArrayList<String> tabsSt1 = new ArrayList<>();
    ArrayList<String> tabsSt2 = new ArrayList<>();
    Manager manager = null;

    Color bluSt = new Color(3, 166, 136);

    Workshift ws = null;
    JComboBox comboOtabs = new JComboBox();
    TabsToEnd tte = null;
    

    public TabsToEnd(Manager man, Workshift w, boolean error) throws Exception {
        sm.addFrame(this);
        setTitle("Resolución Mesas Pendientes");
        tte = this;
        manager = man;
        ws = w;
        
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        openTabs = st.workshiftTableslistComplete(ws, 2);


        JPanel panelLabel = new JPanel(null);
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 10);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("<html>Cerrar todas las mesas <br> pendientes para continuar</html>");
        labelTit.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 23, altoUnit * 8);
        panelLabel.add(labelTit);

        for (Table t : openTabs) {
            tabsSt1.add(t.getId());
        }

        tabsSt2 = utili.tabsEasyReader(tabsSt1);


        comboOtabs.setModel(utili.categoryComboModelReturn(tabsSt2));
        comboOtabs.setBounds(anchoUnit * 3, altoUnit * 15, anchoUnit * 13, altoUnit * 4);
        comboOtabs.setFont(manager.getFont4());
        comboOtabs.setSelectedItem("");
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setFont(new Font("Arial", Font.PLAIN, 50));
        comboOtabs.setRenderer(renderer);
        panelPpal.add(comboOtabs);
        
        JButtonMetalBlu butResolveTab = utiliGraf.button2("Resolver", anchoUnit * 18, altoUnit * 15, anchoUnit * 8);
        butResolveTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String tab = (String) comboOtabs.getSelectedItem();
                    tabOpener(tab);
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butResolveTab);

        JButtonMetalBlu butConfirm = utiliGraf.button1("Continuar", anchoUnit * 8, altoUnit * 24, anchoUnit * 12);
        butConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (openTabs.size() == 0) {
                        boolean confirm4 = utiliMsg.optionConfirmAddTables();
                        if (confirm4) {
                            new TableAdder(ws, manager, null, null);
                        } else {
                            ss.closeWorkshift(null, manager, ws, null, null, null, null, null, true, 2);
                        }
                    } else {
                        utiliMsg.errorTabsToResolve();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butConfirm);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void tabOpener(String t) throws Exception {
        Table tab = null;
        for (int i = 0; i < tabsSt2.size(); i++) {
            if (tabsSt2.get(i).equals(t)) {
                String id = tabsSt1.get(i);
                for (int j = 0; j < openTabs.size(); j++) {
                    if (openTabs.get(i).getId().equals(id)) {
                        tab = openTabs.get(i); 
                    }
                }
            }
        }
        new TableAdder(ws,manager,tab, null);
        setEnabled(false);
    }

    void confirmEndTable(Table tabAux) {
        Iterator<Table> iterador = openTabs.iterator();
        while (iterador.hasNext()) {
            Table tab = iterador.next();
            if (tab.getId() == tabAux.getId()) {
                iterador.remove();
            }
        }
        
        tabsSt1 = new ArrayList<>();
        for (Table t : openTabs) {
            tabsSt1.add(t.getId());
        }
        tabsSt2 = utili.tabsEasyReader(tabsSt1);
        comboOtabs.setModel(utili.categoryComboModelReturn(tabsSt2));
        setEnabled(true);
    }
}
