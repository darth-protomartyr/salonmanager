package salonmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServiceStatics;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

import salonmanager.utilidades.UtilidadesMensajes;

public class StatsClientViewer extends FrameFull {

    DAODeliveryClient daoDC = new DAODeliveryClient();
    DAOItemcard daoI = new DAOItemcard();
    DAOWorkshift daoW = new DAOWorkshift();
    Color bluSt = new Color(3, 166, 136);

    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
//    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceStatics statsS = new ServiceStatics();
    JPanel panelChart1 = new JPanel();
    JPanel panelChart2 = new JPanel();
    ArrayList<ItemSale> iSales = null;
    ArrayList<DeliveryClient> dcDB = null;
    ArrayList<ItemSale> iSalesByClient = null;
    JLabel labelClient = null;
    JLabel labelPanel1 = null;
    JLabel labelPanel2 = null;

    JComboBox comboClients = null;

    public StatsClientViewer(StaticsManager statsM, String period) throws Exception {

        sm.addFrame(this);
        String tit = "";
        iSales = statsM.getISales();
        dcDB = daoDC.listarDeliveryClientByActive(false);

        tit = "Estadísticas individuales de Clientes(" + period + ")";
        setTitle(tit);

        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth() - anchoUnit * 10, altoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W(tit);
        panelLabel.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        panelChart1.setLayout(new BorderLayout());
        panelPpal.add(panelChart1);

        panelChart2.setLayout(new BorderLayout());
        panelPpal.add(panelChart2);

        labelPanel1 = utiliGraf.labelTitleBacker2W("");
        panelPpal.add(labelPanel1);

        labelPanel2 = utiliGraf.labelTitleBacker2W("");
        panelPpal.add(labelPanel2);

        comboClients = new JComboBox();
        comboClients.setModel(utili.consumer2ComboModelReturnWNull(dcDB));
        Font font = new Font("Arial", Font.BOLD, 16);
        comboClients.setFont(font);
        comboClients.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 16, altoUnit * 4);
        comboClients.setSelectedIndex(dcDB.size());
        panelPpal.add(comboClients);

        JButtonMetalBlu butSelItem = utiliGraf.button2("Seleccionar Cliente", anchoUnit * 19, altoUnit * 6, anchoUnit * 15);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    selectClient();
                } catch (Exception ex) {
                    Logger.getLogger(StatsClientViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelItem);

        labelClient = utiliGraf.labelTitleBacker1W("fgjdjjdgdjg");
        labelClient.setBounds(anchoUnit * 35, altoUnit * 6, anchoUnit * 33, altoUnit * 4);
        panelPpal.add(labelClient);

        labelPanel1.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 30, altoUnit * 4);
        labelPanel1.setText("Evolución de facturación por Cliente");
        labelPanel2.setBounds(anchoUnit * 53, altoUnit * 10, anchoUnit * 20, altoUnit * 4);
        labelPanel2.setText("Items más comprados por cliente");

        panelChart1.setBounds(anchoUnit * 2, altoUnit * 14, anchoUnit * 50, altoUnit * 79);
        panelChart2.setBounds(anchoUnit * 53, altoUnit * 14, anchoUnit * 50, altoUnit * 79);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
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
                dispose();
            }
        });
    }

    private void selectClient() throws Exception {
        String st = (String) comboClients.getSelectedItem();
        DeliveryClient cmr = null;

        for (DeliveryClient dc : dcDB) {
            String uName = dc.getName() + " " + dc.getLastname();
            if (uName.equals(st)) {
                cmr = dc;
            }
        }

        iSalesByClient = new ArrayList<>();
        for (ItemSale is : iSales) {
            if (is.getItemSaleTabPos().equals("delivery")) {
                if (is.getIdClient() == cmr.getId()) {
                    iSalesByClient.add(is);
                }
            }
        }
        
        ArrayList<Integer> wsIds = daoW.listarWsIds();
        HashSet<Integer> wssID = new HashSet<Integer>();

        for (int i : wsIds) {
            wssID.add(i);
        }


        HashMap<Integer, Double> volSells = new HashMap<Integer, Double>();
        ArrayList<Integer> wssIDAL = new ArrayList<>(wssID);
        for (int i = 0; i < wssID.size(); i++) {
            int ws = wssIDAL.get(i);
            volSells.put(ws, 0.0);
        }

        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
        ArrayList<Integer> itemsIds = daoI.listarItemsCardId();
        for (int i = 0; i < itemsIds.size(); i++) {
            int id = itemsIds.get(i);
            items.put(id, 0);
        }

        
        for (ItemSale is : iSalesByClient) {
            for (Map.Entry<Integer, Double> entry : volSells.entrySet()) {
                int key = entry.getKey();
                if (key == is.getItemSaleWorkshiftId()) {
                    double newValue = entry.getValue() + is.getItemSalePrice();
                    entry.setValue(newValue);
                }
            }

            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                int key = entry.getKey();
                if (key == is.getItemSaleId()) {
                    int newValue = entry.getValue() + 1;
                    entry.setValue(newValue);
                }
            }
        }

        if (iSalesByClient.size() > 0) {
            labelClient.setText(st);

            volSells = statsS.orderHsIDDownToUp(volSells);
            ArrayList<Integer> wss = new ArrayList<>(volSells.keySet());
            ArrayList<Double> sellsByWs = new ArrayList<>(volSells.values());

            XYChart chartVol = new XYChartBuilder()
                    .title("Facturación por Turno")
                    .xAxisTitle("Turnos")
                    .yAxisTitle("Venta por turno")
                    .build();

            chartVol.setTitle("");
            chartVol.getStyler().setYAxisDecimalPattern("#");
            chartVol.getStyler().setXAxisDecimalPattern("#");
            chartVol.addSeries("Precio de venta", wss, sellsByWs);

            panelChart1.removeAll();
            panelChart1.add(new XChartPanel<>(chartVol));
            panelChart1.revalidate();
            panelChart1.repaint();

            items = statsS.orderHsII(items);
            ArrayList<Integer> ids = new ArrayList<>(items.keySet());
            ArrayList<String> iNames = new ArrayList<>();
            ArrayList<Integer> units = new ArrayList<>(items.values());
            for (int i = 0; i < ids.size(); i++) {
                String name = daoI.getItemNameById(ids.get(i));
                name = utili.reduxSt(name, 2);
                iNames.add(name);
            }

            int w = iNames.size() * 6;

            CategoryChart chartItemsS = new CategoryChartBuilder()
                    .height(altoUnit * 60)
                    .width(anchoUnit * w)
                    .xAxisTitle("Items")
                    .yAxisTitle("Ventas")
                    .build();
            // Establece el valor máximo del eje y (vertical) a 100
            chartItemsS.getStyler().setYAxisDecimalPattern("#");
            chartItemsS.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            chartItemsS.getStyler().setAxisTitlesVisible(false);

            chartItemsS.addSeries("Items más vendidos por mozo", iNames, units);

            JScrollPane scrollPane = new JScrollPane(new XChartPanel<>(chartItemsS));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            panelChart2.removeAll();
            panelChart2.add(scrollPane);
            panelChart2.revalidate();
            panelChart2.repaint();

        } else {
            utiliMsg.errorNullItemDates();
        }
    }
    
    private void pageSetter() {
        
    }
    
    
}
