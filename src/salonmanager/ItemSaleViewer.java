package salonmanager;

import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemSaleViewer extends FrameFull {

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    DAOItemCard daoI = new DAOItemCard();
    DAOUser daoU = new DAOUser();
    Salon salon = null;
    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    JTable jTable = new JTable();
    String col1 = "Nro";
    String col2 = "Item";
    String col3 = "Categoría";
    String col4 = "Tipo de orden";
    String col5 = "Mozo";
    String col6 = "Id Turno";
    String col7 = "Precio";
    String col8 = "Fecha";

    int rows; //nro filas de la tabla
    int cols = 8; //nro columnas de las tablas    
    String[] colNames = {col1, col2, col3, col4, col5, col6, col7, col8};
    String[][] data = null;

    ArrayList<ItemSale> iSales = null;
    ArrayList<User> waiters = null;
    ArrayList<ItemCard> items = null;

    public ItemSaleViewer(StaticsManager stM) throws Exception {
        Manager man = stM.getManager();
//        ConfigGeneral cg = man.getCfgGen();
        sm.addFrame(this);
        setTitle("Lista de transacciones");
        iSales = stM.getItemsales();
        items = daoI.listarItemsCard();
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        waiters = daoU.listUserByRol("MOZO");

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), anchoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Lista de transacciones");
        panelLabel.add(labelTit);

        rows = iSales.size();
        data = (new String[rows][cols]);

        for (int i = 0; i < rows; i++) {
            ItemSale is = iSales.get(i);
            String num = is.getSaleId() + "";
            String item = getItemDataById(is) + "";
            String category = is.getItemSaleCategory();
            String kind = utili.getTabPos(is.getItemSaleTabPos());
            String waiter = getWaiterNameById(is) + "";
            String ws = is.getItemSaleWorkshiftId() + "";
            String price = is.getItemSalePrice() + "";
            String date = is.getItemSaleDate() + "";

            data[i][0] = " " + num;
            data[i][1] = " " + item;
            data[i][2] = " " + category;
            data[i][3] = " " + kind;
            data[i][4] = " " + waiter;
            data[i][5] = " " + ws;
            data[i][6] = " " + price;
            data[i][7] = " " + date;

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
        column1.setPreferredWidth(anchoUnit * 8);
        TableColumn column2 = jTable.getColumnModel().getColumn(1);
        column2.setPreferredWidth(anchoUnit * 15);
        TableColumn column3 = jTable.getColumnModel().getColumn(2);
        column3.setPreferredWidth(anchoUnit * 8);
        TableColumn column4 = jTable.getColumnModel().getColumn(3);
        column4.setPreferredWidth(anchoUnit * 12);
        TableColumn column5 = jTable.getColumnModel().getColumn(4);
        column5.setPreferredWidth(anchoUnit * 12);
        TableColumn column6 = jTable.getColumnModel().getColumn(5);
        column6.setPreferredWidth(anchoUnit * 8);
        TableColumn column7 = jTable.getColumnModel().getColumn(6);
        column7.setPreferredWidth(anchoUnit * 8);
        TableColumn column8 = jTable.getColumnModel().getColumn(7);
        column8.setPreferredWidth(anchoUnit * 15);

//        TableCellRenderer tableCellRenderer = new TableCellRenderer() {
//            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                // Configurar tooltip solo para la columna 5
//                if (column == 4) {
//                    String tooltipText = (String) value;
//                    JLabel label = (JLabel) component;
//                    label.setToolTipText(tooltipText);
//                }
//                return component;
//            }
//        };
//
//        // Aplicar el renderizador personalizado a la columna 5
//        jTable.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBackground(narUlg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 84, altoUnit * 72);
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
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private String getItemDataById(ItemSale is) {
        String data = "";
        for (int i = 0; i < items.size(); i++) {
            int isId = is.getItemSaleId();
            int itemId = items.get(i).getId();
            if (isId == itemId) {
                data = items.get(i).getName();
            }
        }
        return data;
    }

    private String getWaiterNameById(ItemSale is) {
        String waiter = "";
        for (int i = 0; i < waiters.size(); i++) {
            if (is.getItemSaleWaiterId().equals(waiters.get(i).getId())) {
                waiter = waiters.get(i).getName() + " " + waiters.get(i).getLastName();
            }
        }
        return waiter;
    }
}
