package salonmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasStatics;
import salonmanager.utilidades.UtilidadesMensajes;

public class StatsViewer extends FrameFull {

    Color bluSt = new Color(3, 166, 136);

    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasStatics utiliGrafStats = new UtilidadesGraficasStatics();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    StaticsManager sMan = null;
    CategoryChart chartItemsSelled = null;

    JPanel chartPanelItemsSelled = new JPanel();
    HashMap<Integer, Integer> countItems = new HashMap<Integer, Integer>();

    public StatsViewer(StaticsManager statsM, int kind) throws Exception {
        sm.addFrame(this);
        sMan = statsM;
        setTitle("Pago Parcial");
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);
        countItems = statsM.getCountItems();

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), anchoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Estadísticas venta de Items");
        panelLabel.add(labelTit);

        chartItemsSelled = utiliGrafStats.chartItemsBacker(countItems);

        chartPanelItemsSelled.setBounds(anchoUnit * 2, altoUnit * 10, anchoUnit * 101, altoUnit * 70);
        chartPanelItemsSelled.setLayout(new BorderLayout());
        chartPanelItemsSelled.add(new XChartPanel<>(chartItemsSelled));
        panelPpal.add(chartPanelItemsSelled);

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
}

