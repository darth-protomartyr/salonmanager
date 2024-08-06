package salonmanager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.UtilidadesGraficas;

public class TabViewer extends FrameFull {

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(252, 203, 5);

    DAOUser daoU = new DAOUser();
    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();

    JTable jTable = new JTable();
    String col1 = "Nro";
    String col2 = "Ubicación";
    String col3 = "Apertura";
    String col4 = "Cierre";
    String col5 = "Dto";
    String col6 = "Error";
    String col7 = "Corrección *";
    String col8 = "Efectivo";
    String col9 = "Transf. **";
    String col10 = "Total";
    String col11 = "Comentario";

    int rows; //nro filas de la tabla
    int cols = 11; //nro columnas de las tablas    
    String[] colNames = {col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11};
    String[][] data = null;
    ArrayList<Table> tabs = null;

    public TabViewer(ArrayList<Table> tables) throws Exception {
        sm.addFrame(this);
        setTitle("Lista de transacciones");
        tabs = tables;
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth() - 7, anchoUnit * 6);
        panelPpal.add(panelLabel);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Lista de transacciones");
        panelLabel.add(labelTit);

        rows = tabs.size();
        data = (new String[rows][cols]);

        for (int i = 0; i < rows; i++) {
            Table tab = tabs.get(i);
            String num = tab.getNum() + "";
            String pos = tab.getPos() + "";
            if (!pos.equals("barra") && !pos.equals("delivery")) {
                pos = "mesa";
            }

            String open = tab.getOpenTime() + "";
            String close = tab.getCloseTime() + "";
            String disc = tab.getDiscount() + "%";
            String error = tab.getError() + "";
            String correction = tab.getPriceCorrection() + "";
            String cash = tab.getAmountCash() + "";
            String transf = tab.getAmountElectronic() + "";
            String total = tab.getTotal() + "";
            String comment = tab.getComments().replace("<br>", " ");

            data[i][0] = " " + num;
            data[i][1] = " " + pos;
            data[i][2] = " " + open;
            data[i][3] = " " + close;
            data[i][4] = " " + disc;
            data[i][5] = " " + error;
            data[i][6] = " " + correction;
            data[i][7] = " " + cash;
            data[i][8] = " " + transf;
            data[i][9] = " " + total;
            data[i][10] = " " + comment;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        jTable.setModel(tableModel);
        jTable.setDefaultEditor(Object.class, null);

        JTableHeader header = jTable.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(header.getWidth(), altoUnit * 6));
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(narLg);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        jTable.setFont(cellFont);
        jTable.setRowHeight(altoUnit * 4);
        jTable.setBackground(narUlg);

        TableColumn column1 = jTable.getColumnModel().getColumn(0);
        column1.setPreferredWidth(anchoUnit * 5);
        TableColumn column2 = jTable.getColumnModel().getColumn(1);
        column2.setPreferredWidth(anchoUnit * 10);
        TableColumn column3 = jTable.getColumnModel().getColumn(2);
        column3.setPreferredWidth(anchoUnit * 20);
        TableColumn column4 = jTable.getColumnModel().getColumn(3);
        column4.setPreferredWidth(anchoUnit * 20);
        TableColumn column5 = jTable.getColumnModel().getColumn(4);
        column5.setPreferredWidth(anchoUnit * 5);
        TableColumn column6 = jTable.getColumnModel().getColumn(5);
        column6.setPreferredWidth(anchoUnit * 8);
        TableColumn column7 = jTable.getColumnModel().getColumn(6);
        column7.setPreferredWidth(anchoUnit * 12);
        TableColumn column8 = jTable.getColumnModel().getColumn(7);
        column8.setPreferredWidth(anchoUnit * 8);
        TableColumn column9 = jTable.getColumnModel().getColumn(8);
        column9.setPreferredWidth(anchoUnit * 8);
        TableColumn column10 = jTable.getColumnModel().getColumn(9);
        column10.setPreferredWidth(anchoUnit * 8);
        TableColumn column11 = jTable.getColumnModel().getColumn(10);
        column11.setPreferredWidth(anchoUnit * 25);

        TableCellRenderer tableCellRenderer = new TableCellRenderer() {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Configurar tooltip solo para la columna 5
                if (column == 10) {
                    String tooltipText = (String) value;
                    JLabel label = (JLabel) component;
                    label.setToolTipText(tooltipText);
                }
                return component;
            }
        };
        // Aplicar el renderizador personalizado a la columna 5
        jTable.getColumnModel().getColumn(10).setCellRenderer(tableCellRenderer);

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBackground(narUlg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 100, altoUnit * 72);
        panelPpal.add(scrollPane);

        JLabel star1 = utiliGraf.labelTitleBacker3W("* Corrección por modificación de precio durante Turno abierto");
        star1.setBounds(anchoUnit * 3, altoUnit * 85, anchoUnit * 50, altoUnit * 3);
        panelPpal.add(star1);

        JLabel star2 = utiliGraf.labelTitleBacker3W("** Pago con tarjeta y electrónico");
        star2.setBounds(anchoUnit * 3, altoUnit * 88, anchoUnit * 50, altoUnit * 3);
        panelPpal.add(star2);

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
