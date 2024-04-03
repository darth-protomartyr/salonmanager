package salonmanager;



import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesUpdate;

public class StaticsManager extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesUpdate utiliUpd = new UtilidadesUpdate();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    private User userIn = null;
    ArrayList<Table> tabs = new ArrayList<Table>();
    ArrayList<ItemSale> iSales = new ArrayList<ItemSale>();
    Manager manager = null;

    public StaticsManager(Manager man) {
        setTitle("Administrador");
        manager = man;
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();

        JLabel labelStatics = utiliGraf.labelTitleBackerA3W("Estadísticas");
        labelStatics.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelStatics);

        JPanel panelStatsBySell = new JPanel(null);
        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 17, altoUnit * 28);
        panelStatsBySell.setBackground(bluLg);
        panelPpal.add(panelStatsBySell);

        JLabel labelStats = utiliGraf.labelTitleBacker1("Período de consulta:");
        labelStats.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 16, altoUnit * 3);
        panelStatsBySell.add(labelStats);

        JButtonMetalBlu butStatsPeriod = utiliGraf.button1("ESTABLECER", anchoUnit * 2, altoUnit * 6, anchoUnit * 13);
        butStatsPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                openSelectorPeriod();
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butStatsPeriod);

        JButtonMetalBlu butViewAllItemSales = utiliGraf.button2("Ver Ventas", anchoUnit * 2, altoUnit * 15, anchoUnit * 13);
        butViewAllItemSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openItemSaleViewer();
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllItemSales);

        JButtonMetalBlu butViewAllTabs = utiliGraf.button2("Ver Mesas", anchoUnit * 2, altoUnit * 20, anchoUnit * 13);
        butViewAllTabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    openTabViewer();
                } catch (Exception ex) {
                    Logger.getLogger(StaticsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                setEnabled(false);
            }
        });
        panelStatsBySell.add(butViewAllTabs);

        double numTabs = 0;
        double numBar = 0;
        double numDeli = 0;
        
        for (Table tab : tabs ) {
            if (tab.getPos().equals("barra")) {
                numBar += tab.getTotal();
            } else if (tab.getPos().equals("delivery")) {
                numDeli += tab.getTotal();
            } else {
                numTabs += tab.getTotal();
            }
        }    
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    dispose();
                }
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

    public void openSelectorPeriod() {
        new SelectorPeriod(this);
    }

    private void openItemSaleViewer() throws Exception {
        if (iSales != null) {
            new ItemSaleViewer(this);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }

    private void openTabViewer() throws Exception {
        if (tabs != null) {
            new TabViewer(tabs);
        } else {
            utiliMsg.errorPeriodNull();
        }
    }
    
    
    
    
    
    

    public void setTabs(ArrayList tables) {
        tabs = tables;
    }

    public ArrayList<Table> getTabs() {
        return tabs;
    }

    public void setItemsSale(ArrayList iSals) {
        iSales = iSals;
    }

    public ArrayList<ItemSale> getItemsales() {
        return iSales;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    


        // Crear un conjunto de datos para el gráfico de torta

    
    
}
