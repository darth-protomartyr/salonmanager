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
import salonmanager.entidades.bussiness.MoneyFlow;
import salonmanager.entidades.graphics.FrameHalfFlat;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOMoneyFlow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class MoneyFlowViewer extends FrameHalfFlat {

    DAOMoneyFlow daoCF = new DAOMoneyFlow();

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(252, 203, 5);

    ArrayList<MoneyFlow> moneyFlowList = new ArrayList<>();

//    Salon salon = null;
    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    JTable jTable = new JTable();

    String col1 = "Movimientos";
    String col2 = "Tipo de Transacción";
    String col3 = "Fecha y Hora";
    String col4 = "Monto";
    String col5 = "Comentario";

    int rows; //nro filas de la tabla
    int cols = 5; //nro columnas de las tablas    
    String[] colNames = {col1, col2, col3, col4, col5};
    String[][] data = null;

    public MoneyFlowViewer(WorkshiftEndPanel wep) throws Exception {
        sm.addFrame(this);
        moneyFlowList = daoCF.askMFByWorkshift(wep.getActualWs().getId());
        setTitle("Pago Parcial");
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), anchoUnit * 6);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("PAGO PARCIAL");
        panelLabel.add(labelTit);

        rows = moneyFlowList.size();
        data = (new String[rows][cols]);

        for (int i = 0; i < rows; i++) {
            MoneyFlow cf = moneyFlowList.get(i);
            String mov = "Entrada";
            String kindM = "Transferencia";
            String date = "" + utili.friendlyDate1(cf.getMoneyFwTime());
            String mount = "" + cf.getMoneyFwAmount();
            String comment = cf.getMoneyFwComment();

            if (cf.isMoneyFwKind() == false) {
                mov = "Salida";
            }

            if (cf.isMoneyFwMoneyKind()) {
                kindM = "Efectivo";
            }

            data[i][0] = " " + mov;
            data[i][1] = " " + kindM;
            data[i][2] = " " + date;
            data[i][3] = " " + mount;
            data[i][4] = " " + comment;
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
        column1.setPreferredWidth(anchoUnit * 6);
        TableColumn column2 = jTable.getColumnModel().getColumn(1);
        column2.setPreferredWidth(anchoUnit * 11);
        TableColumn column3 = jTable.getColumnModel().getColumn(2);
        column3.setPreferredWidth(anchoUnit * 7);
        TableColumn column4 = jTable.getColumnModel().getColumn(3);
        column4.setPreferredWidth(anchoUnit * 4);
        TableColumn column5 = jTable.getColumnModel().getColumn(4);
        column5.setPreferredWidth(anchoUnit * 11);

        TableCellRenderer tableCellRenderer = new TableCellRenderer() {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Configurar tooltip solo para la columna 5
                if (column == 4) {
                    String tooltipText = (String) value;
                    JLabel label = (JLabel) component;
                    label.setToolTipText(tooltipText);
                }
                return component;
            }
        };

        // Aplicar el renderizador personalizado a la columna 5
        jTable.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBackground(narUlg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 53, altoUnit * 72);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                wep.setEnableWEP();
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                wep.setEnableWEP();
                dispose();

            }
        });
    }
}
