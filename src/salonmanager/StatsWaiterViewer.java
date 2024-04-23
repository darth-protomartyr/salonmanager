package salonmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
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
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServiceStatics;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasStatics;
import salonmanager.utilidades.UtilidadesMensajes;

public class StatsWaiterViewer extends FrameFull {

    DAOUser daoU = new DAOUser();
    DAOItemcard daoI = new DAOItemcard();
    Color bluSt = new Color(3, 166, 136);

    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceStatics statsS = new ServiceStatics();
    StaticsManager sMan = null;
    CategoryChart chartItemsSelled = null;
    CategoryChart chartItemPriceEvol = null;
    JPanel panelChart1 = new JPanel();
    JPanel panelChart2 = new JPanel();
    HashMap<Integer, Integer> countItems = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> newCountItems = new HashMap<Integer, Integer>();
    ArrayList<ItemSale> iSales = null;
    ArrayList<User> usersDB = null;
    ArrayList<ItemSale> iSalesByWaiter = null;
    JLabel labelWaiter = null;
    JLabel labelPanel1 = null;
    JLabel labelPanel2 = null;

    int kind = 0;

    JComboBox comboWaiters = null;

    public StatsWaiterViewer(StaticsManager statsM, int k, String period) throws Exception {

        sm.addFrame(this);
        sMan = statsM;
        kind = k;
        String tit = "";
        iSales = statsM.getItemsales();
        usersDB = daoU.listUserByRol("MOZO");

        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), altoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("");
        panelLabel.add(labelTit);

        panelChart1.setLayout(new BorderLayout());
        panelPpal.add(panelChart1);

        panelChart2.setLayout(new BorderLayout());
        panelPpal.add(panelChart2);

        labelPanel1 = utiliGraf.labelTitleBacker2W("");
        panelPpal.add(labelPanel1);

        labelPanel2 = utiliGraf.labelTitleBacker2W("");
        panelPpal.add(labelPanel2);

        if (kind == 1) {
            tit = "Estadísticas globales de Mozos(" + period + ")";
            setTitle(tit);
            labelTit.setText(tit);

            labelPanel1.setText("Por volumen de venta");
            labelPanel1.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 20, altoUnit * 4);

            HashMap<String, Double> countWSells = sMan.getCountWSells();
            PieChart chartWSells = utiliGrafStats.chartWSellBacker(countWSells, statsM);
            panelChart1.setBounds(anchoUnit * 2, altoUnit * 12, anchoUnit * 50, altoUnit * 80);
            panelChart1.add(new XChartPanel<>(chartWSells));

            labelPanel2.setText("Por cantidad de turnos");
            labelPanel2.setBounds(anchoUnit * 53, altoUnit * 6, anchoUnit * 20, altoUnit * 4);

            HashMap<String, Integer> countWWs = sMan.getCountWWs();
            CategoryChart chartWWs = utiliGrafStats.chartWWsBacker(countWWs, statsM);

            JScrollPane scrollPane = new JScrollPane(new XChartPanel<>(chartWWs));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            panelPpal.add(scrollPane);

            panelChart2.setBounds(anchoUnit * 53, altoUnit * 12, anchoUnit * 50, altoUnit * 80);
            panelChart2.add(scrollPane);

        } else if (k == 2) {
            tit = "Estadísticas individuales de Mozos(" + period + ")";
            setTitle(tit);
            labelTit.setText(tit);

            comboWaiters = new JComboBox();
            comboWaiters.setModel(utili.userComboModelReturnWNull(usersDB));
            Font font = new Font("Arial", Font.BOLD, 16);
            comboWaiters.setFont(font);
            comboWaiters.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 16, altoUnit * 4);
            comboWaiters.setSelectedIndex(usersDB.size());
            panelPpal.add(comboWaiters);

            JButtonMetalBlu butSelItem = utiliGraf.button2("Seleccionar Mozo", anchoUnit * 19, altoUnit * 6, anchoUnit * 13);
            butSelItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        selectWaiter();
                    } catch (Exception ex) {
                        Logger.getLogger(StatsWaiterViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panelPpal.add(butSelItem);

            labelWaiter = utiliGraf.labelTitleBacker1W("");
            labelWaiter.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 33, altoUnit * 4);
            panelPpal.add(labelWaiter);

            labelPanel1.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 30, altoUnit * 4);
            labelPanel1.setText("Evoulución de facturación por mozo");
            labelPanel2.setBounds(anchoUnit * 53, altoUnit * 10, anchoUnit * 20, altoUnit * 4);
            labelPanel2.setText("Items más vencdidos por mozo");

            panelChart1.setBounds(anchoUnit * 2, altoUnit * 14, anchoUnit * 50, altoUnit * 79);
            panelChart2.setBounds(anchoUnit * 53, altoUnit * 14, anchoUnit * 50, altoUnit * 79);

        }


        /*
        JButtonMetalBlu butPrev = utiliGraf.button2("Prev", anchoUnit * 43, altoUnit * 87, anchoUnit * 8);
        butPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                page -= 1;
                if (page > 0) {
                    try {
                        updater();
                    } catch (Exception ex) {
                        Logger.getLogger(StatsWaiterViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    page += 1;
                }
            }
        });

        JButtonMetalBlu butNext = utiliGraf.button2("Next", anchoUnit * 54, altoUnit * 87, anchoUnit * 8);
        butNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                page += 1;
                if (page <= maxPage) {
                    try {
                        updater();
                    } catch (Exception ex) {
                        Logger.getLogger(StatsWaiterViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    page -= 1;
                }
            }
        });



        
        if (kind == 1) {
            tit = "Estadísticas venta de Mozos";
            setTitle(tit);
            labelTit.setText(tit);
            ArrayList<Integer> list1 = daoI.listarItemsCardId();
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            for (ItemSale is : iSales) {
                int i = is.getItemSaleId();
                int q = countItems.getOrDefault(i, 0) + 1;
                if (q > maxUnit) {
                    maxUnit = q;
                }
                countItems.put(i, q);
                list2.add(i);
            }

            ArrayList<Integer> list3 = new ArrayList<>(list1);
            list3.removeAll(list2);

            for (int i : list3) {
                countItems.put(i, 0);
            } 

            page = 1;
            updater();

            panelPpal.add(butPrev);
            panelPpal.add(butNext);

        } /*else if (kind == 2) {
            tit = "Evolución de precios";
            setTitle(tit);
            labelTit.setText(tit);
            panelPpal.add(comboItems);
            panelPpal.add(butSelItem);
            String itemName = "Item:";
        } else if (kind == 3) {
            tit = "Evolución de ventas";
            setTitle(tit);
            labelTit.setText(tit);
            panelPpal.add(comboItems);
            panelPpal.add(butSelItem);
            String itemName = "Item:";
        }*/
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

    private void selectWaiter() throws Exception {
        String st = (String) comboWaiters.getSelectedItem();
        User user = null;
        labelWaiter.setText(st);

        for (User u : usersDB) {
            String uName = u.getName() + " " + u.getLastName();
            if (uName.equals(st)) {
                user = u;
            }
        }

        iSalesByWaiter = new ArrayList<ItemSale>();
        for (ItemSale is : iSales) {
            if (is.getItemSaleWaiterId().equals(user.getId())) {
                iSalesByWaiter.add(is);
            }
        }

        HashSet<Integer> wssID = new HashSet<Integer>();
        HashSet<Integer> itemsId = new HashSet<Integer>();

        ArrayList<Integer> wssIDAL = new ArrayList<Integer>(wssID);
        ArrayList<Integer> itemsIdAL = new ArrayList<Integer>(itemsId);

        for (ItemSale is : iSalesByWaiter) {
            wssID.add(is.getItemSaleWorkshiftId());
            itemsId.add(is.getItemSaleId());
        }

        HashMap<Integer, Double> volSells = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();

        for (int i = 0; i < wssID.size(); i++) {
            int ws = wssIDAL.get(i);
            volSells.put(i, 0.0);
        }

        for (int i = 0; i < itemsId.size(); i++) {
            int id = itemsIdAL.get(i);
            items.put(id, 0);
        }

        for (ItemSale is : iSalesByWaiter) {
            for (Map.Entry<Integer, Double> entry : volSells.entrySet()) {
                int key = entry.getKey();
                if (key == is.getItemSaleWorkshiftId()) {
                    double newValue = entry.getValue() + is.getItemSalePrice();
                    entry.setValue(newValue);
                }
            }

            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                int key = entry.getKey();
                if (key == is.getItemSaleWorkshiftId()) {
                    int newValue = entry.getValue() + 1;
                    entry.setValue(newValue);
                }
            }
        }

        ArrayList<Integer> wss = new ArrayList<Integer>(volSells.keySet());
        ArrayList<Double> sellsByWs = new ArrayList<Double>(volSells.values());

        if (iSalesByWaiter.size() > 0) {
            XYChart chartVol = new XYChartBuilder()
                    .title("Facturación por Turno")
                    .xAxisTitle("Turnos")
                    .yAxisTitle("Venta por turno")
                    .build();

            chartVol.getStyler().setYAxisDecimalPattern("#");
            chartVol.getStyler().setXAxisDecimalPattern("#");
            chartVol.addSeries("Precio de venta", wss, sellsByWs);

            panelChart1.removeAll();
            panelChart1.add(new XChartPanel<>(chartVol));
            panelChart1.revalidate();
            panelChart1.repaint();

            items = statsS.orderHsII(items);

            ArrayList<Integer> ids = new ArrayList<Integer>(items.keySet());
            ArrayList<String> iNames = new ArrayList<String>();
            ArrayList<Integer> units = new ArrayList<Integer>(items.values());
            for (int i = 0; i < ids.size(); i++) {
                String name = daoI.getItemNameById(ids.get(i));
                iNames.add(name);
            }

            CategoryChart chartItemsS = new CategoryChartBuilder()
                    .xAxisTitle("Items")
                    .yAxisTitle("Ventas")
                    .build();
            // Establece el valor máximo del eje y (vertical) a 100
            chartItemsS.getStyler().setYAxisDecimalPattern("#");
            chartItemsS.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            chartItemsS.getStyler().setAxisTitlesVisible(false);

            chartItemsS.addSeries("Items más vendidos por mozo", iNames, units);

            JScrollPane scrollPane = new JScrollPane(new XChartPanel<>(chartItemsS));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            panelPpal.add(scrollPane);

            panelChart2.setBounds(anchoUnit * 53, altoUnit * 12, anchoUnit * 50, altoUnit * 80);
            panelChart2.add(scrollPane);

        } else {
            utiliMsg.errorNullItemDates();
        }

        /*
        HashSet<Integer> wsIds = new HashSet();
        ArrayList<Workshift> wsS = sMan.getWorkshift();
        for (int i = 0; i < wsS.size(); i++) {
            wsIds.add(wsS.get(i).getId());
        }

        HashMap<Integer, Integer> countQuant = new HashMap<>();
        for (int ws : wsIds) {
            countQuant.put(ws, 0);
        }

        for (ItemSale is : iSalesByWaiter) {
            for (Map.Entry<Integer, Integer> entry : countQuant.entrySet()) {
                int key = entry.getKey();
                int newValue = 0;
                int value = entry.getValue();
                if (key == is.getItemSaleWorkshiftId()) {
                    newValue = value + 1;
                    entry.setValue(newValue);
                }
            }
        }

        ArrayList<Integer> wss = new ArrayList<>(countQuant.keySet());
        ArrayList<Integer> quants = new ArrayList<>(countQuant.values());
        if (quants.size() > 0) {
            XYChart chartItemQ = new XYChartBuilder()
                    .width(anchoUnit * 40)
                    .height(altoUnit * 40)
                    .title("Evolución de Venta")
                    .xAxisTitle("Turno")
                    .yAxisTitle("Unidades Vendidas")
                    .build();
            // Agregar series de datos al gráfico
            chartItemQ.getStyler().setYAxisDecimalPattern("#");
            chartItemQ.getStyler().setXAxisDecimalPattern("#");
            chartItemQ.addSeries("Unidades Vendidas", wss, quants);
            panelChart2.add(new XChartPanel<>(chartItemQ));
            panelChart2.removeAll();
            panelChart2.add(new XChartPanel<>(chartItemQ));
            panelChart2.revalidate();
            panelChart2.repaint();
        } else {
            utiliMsg.errorNullDates();
        }
         */
    }
}
