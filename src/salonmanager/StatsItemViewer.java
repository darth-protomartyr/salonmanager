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
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.servicios.ServiceStatics;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasStatics;
import salonmanager.utilidades.UtilidadesMensajes;

public class StatsItemViewer extends FrameFull {

    DAOItemCard daoI = new DAOItemCard();
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
    JPanel chartPanel = new JPanel();

    int kind = 0;
    int page = 0;
    int maxPage = 0;
    int maxUnit = 0;
    HashMap<Integer, Integer> countItems = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> newCountItems = new HashMap<Integer, Integer>();
    ArrayList<ItemSale> iSales = null;
    ArrayList<ItemCard> itemsDB = null;
    ArrayList<ItemSale> iSalesByItem = null;
    JComboBox comboItems = null;
    JLabel labelItem = null;

    public StatsItemViewer(StaticsManager statsM, int k, String period) throws Exception {
        sm.addFrame(this);
        sMan = statsM;
        kind = k;
        String tit = "";
        iSales = statsM.getISales();
        itemsDB = daoI.listarItemsCard();

        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth() - anchoUnit * 10, altoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("");
        panelLabel.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        labelItem = utiliGraf.labelTitleBacker1W("");
        labelItem.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 50, altoUnit * 5);
        panelPpal.add(labelItem);

        chartPanel.setBounds(anchoUnit * 2, altoUnit * 15, anchoUnit * 101, altoUnit * 70);
        chartPanel.setLayout(new BorderLayout());
        panelPpal.add(chartPanel);

        JButtonMetalBlu butPrev = utiliGraf.button2("Prev", anchoUnit * 43, altoUnit * 87, anchoUnit * 8);
        butPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                page -= 1;
                if (page > 0) {
                    try {
                        updater();
                    } catch (Exception ex) {
                        Logger.getLogger(StatsItemViewer.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(StatsItemViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    page -= 1;
                }
            }
        });

        comboItems = new JComboBox();
        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        Font font = new Font("Arial", Font.BOLD, 16);
        comboItems.setFont(font);
        comboItems.setBounds(anchoUnit * 2, altoUnit * 87, anchoUnit * 16, altoUnit * 4);
        comboItems.setSelectedIndex(itemsDB.size());

        JButtonMetalBlu butSelItem = utiliGraf.button2("Seleccionar Item", anchoUnit * 2, altoUnit * 93, anchoUnit * 13);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                selectItem();
            }
        });

        if (kind == 1) {
            tit = "Estadísticas venta de Items " + "(" + period + ")";
            setTitle(tit);
            labelTit.setText(tit);
            ArrayList<Integer> list1 = daoI.listarItemsCardId();
            ArrayList<Integer> list2 = new ArrayList<>();
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

        } else if (kind == 2) {
            tit = "Evolución de ventas" + "(" + period + ")";
            setTitle(tit);
            labelTit.setText(tit);
            panelPpal.add(comboItems);
            panelPpal.add(butSelItem);
            String itemName = "Item:";

        } else if (kind == 3) {
            tit = "Evolución de precios" + "(" + period + ")";
            setTitle(tit);
            labelTit.setText(tit);
            panelPpal.add(comboItems);
            panelPpal.add(butSelItem);
            String itemName = "Item:";

        }

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

    private void selectItem() {
        String st = (String) comboItems.getSelectedItem();
        ItemCard ic = null;
        labelItem.setText(st);

        for (ItemCard i : itemsDB) {
            if (i.getName().equals(st)) {
                ic = i;
            }
        }

        iSalesByItem = new ArrayList<>();
        for (ItemSale is : iSales) {
            if (is.getItemSaleId() == ic.getId()) {
                iSalesByItem.add(is);
            }
        }

        if (kind == 2) {
            HashSet<Integer> wsIds = new HashSet();
            ArrayList<Workshift> wsS = sMan.getWorkshifts();
            for (int i = 0; i < wsS.size(); i++) {
                wsIds.add(wsS.get(i).getId());
            }

            HashMap<Integer, Integer> countQuant = new HashMap<>();
            for (int ws : wsIds) {
                countQuant.put(ws, 0);
            }

            for (ItemSale is : iSalesByItem) {
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

            countQuant = statsS.orderHsIIDownToUp(countQuant);
            
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
                chartItemQ.getStyler().setYAxisMin(0.0);
                chartItemQ.setTitle("");
                chartPanel.add(new XChartPanel<>(chartItemQ));
                chartPanel.removeAll();
                chartPanel.add(new XChartPanel<>(chartItemQ));
                chartPanel.revalidate();
                chartPanel.repaint();
            } else {
                utiliMsg.errorNullDates();
            }
        } else if (kind == 3) {
            ArrayList<Timestamp> dates = new ArrayList<>();
            ArrayList<Double> prices = new ArrayList<>();
            for (ItemSale is : iSalesByItem) {
                dates.add(is.getItemSaleDate());
                prices.add(is.getItemSalePrice());
            }

            if (dates.size() > 0) {
                XYChart chartPrice = new XYChartBuilder()
                        .title("Evolución precio")
                        .xAxisTitle("Fecha")
                        .yAxisTitle("Precios")
                        .build();

                chartPrice.setTitle("");
                chartPrice.getStyler().setYAxisMin(0.0);
                chartPrice.getStyler().setYAxisDecimalPattern("#");
                chartPrice.getStyler().setXAxisDecimalPattern("#");
                chartPrice.addSeries("Precio de venta", dates, prices);
                chartPanel.removeAll();
                chartPanel.add(new XChartPanel<>(chartPrice));
                chartPanel.revalidate();
                chartPanel.repaint();
            } else {
                utiliMsg.errorNullItemDates();
            }
        }
    }

    private void updater() throws Exception {
        int limitInf = 1;
        if (page > 1) {
            limitInf = 8 * (page - 1) + 1;
        }
        int limitSup = 8 * page;
        int counter = 1;

        int fakeId = 10000000;
        while (countItems.size() % 8 != 0) {
            countItems.put(fakeId, 0);
            fakeId += 1;
        }
        maxPage = countItems.size() / 8;

        countItems = statsS.orderHsII(countItems);

        newCountItems.clear();

        for (Map.Entry<Integer, Integer> entry : countItems.entrySet()) {
            if (counter >= limitInf && counter <= limitSup) {
                newCountItems.put(entry.getKey(), entry.getValue());
            }
            counter += 1;
        }
        newCountItems = statsS.orderHsII(newCountItems);

        chartItemsSelled = utiliGrafStats.chartItemsBacker(newCountItems, maxUnit);
        chartPanel.removeAll();
        chartPanel.add(new XChartPanel<>(chartItemsSelled));
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}
