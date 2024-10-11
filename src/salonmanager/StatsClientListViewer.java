package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.graphics.FrameFull;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

/**
 *
 * @author Gonzalo
 */
public class StatsClientListViewer extends FrameFull {

    DAODeliveryClient daoDC = new DAODeliveryClient();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Color bluLg = new Color(194, 242, 206);
    SalonManager sm = new SalonManager();

    Color bluStBarr = new Color(2, 82, 67);
    Color narSt = new Color(217, 153, 4);

    public StatsClientListViewer(StaticsManager statsM, HashMap<Integer, Double> countClientBuys) throws Exception {
        ArrayList<Integer> clientsBuys1 = new ArrayList<>(countClientBuys.keySet());
        ArrayList<String> clientsBuys2 = new ArrayList<>();
        ArrayList<Double> amounts3 = new ArrayList<>(countClientBuys.values());
        ArrayList<Double> amounts4 = new ArrayList<>();
        ArrayList<DeliveryClient> listDC = daoDC.listarDeliveryClientByActive(true);

        sm.addFrame(this);
        setTitle("Lista de Clientes");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Lista de Clientes");
        labelTit.setBounds(anchoUnit * 2, altoUnit * 1, anchoUnit * 28, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        for (int i = 0; i < clientsBuys1.size(); i++) {
            String name = "--";
            double mount = 00;
            if (i < clientsBuys1.size()) {
                name = daoDC.getClientNameById(clientsBuys1.get(i));
                mount = amounts3.get(i);
            }
            clientsBuys2.add(name);
            amounts4.add(mount);
        }

        int rows = listDC.size();
        int col = 7;
        String col1 = "Apellido";
        String col2 = "Nombre";
        String col3 = "Domicilio";
        String col4 = "Localidad";
        String col5 = "Redes Sociales";
        String col6 = "Teléfono";
        String col7 = "Total Gastado";

        String[] colNames = {col1, col2, col3, col4, col5, col6, col7};
        String[][] data = new String[rows][col];

        if (listDC != null) {
            for (int i = 0; i < rows; i++) {
                DeliveryClient dc = listDC.get(i);
                data[i][0] = "  " + dc.getLastname();
                data[i][1] = "  " + dc.getName();
                data[i][2] = "  " + dc.getStreet() + " " + dc.getNumSt();
                data[i][3] = "  " + dc.getDistrict() + ", " + dc.getArea();
                data[i][4] = "  " + dc.getSocialNetwork();
                data[i][5] = "  " + dc.getPhone();
                String nln = dc.getName().substring(0, 1) + ". " + dc.getLastname();
                if (i < clientsBuys2.size()) {
                    if (clientsBuys2.get(i).equals(nln)) {
                        data[i][6] = "  " + amounts4.get(i);
                    }
                } else {
                    data[i][6] = "  " + 0.0;
                }
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        JTable table = new JTable(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(narSt);
        Font cellFont = new Font("Arial", Font.BOLD, 14);
        table.setFont(cellFont);
        table.setRowHeight(25);
        table.setBackground(bluLg);
        table.setForeground(bluStBarr);

        if (listDC != null) {
            int c = this.getWidth() / 35;
            TableColumn column1 = table.getColumnModel().getColumn(0);
            column1.setPreferredWidth(c * 3);
            TableColumn column2 = table.getColumnModel().getColumn(1);
            column2.setPreferredWidth(c * 3);
            TableColumn column3 = table.getColumnModel().getColumn(2);
            column3.setPreferredWidth(c * 6);
            TableColumn column4 = table.getColumnModel().getColumn(3);
            column4.setPreferredWidth(c * 6);
            TableColumn column5 = table.getColumnModel().getColumn(4);
            column5.setPreferredWidth(c * 6);
            TableColumn column6 = table.getColumnModel().getColumn(5);
            column6.setPreferredWidth(c * 3);
            TableColumn column7 = table.getColumnModel().getColumn(6);
            column7.setPreferredWidth(c * 3);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(bluLg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(2 * anchoUnit, altoUnit * 7, this.getWidth() - anchoUnit * 6, this.getHeight() - altoUnit * 20);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
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
}
