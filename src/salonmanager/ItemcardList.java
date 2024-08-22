package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.Itemcard;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.utilidades.UtilidadesGraficas;

public class ItemcardList extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    SalonManager sm = new SalonManager();
    Color narUlg = new Color(255, 255, 176);
    Color narLg = new Color(252, 203, 5);
    DAOItemcard daoI = new DAOItemcard();
    JTable tableItems = null;

    public ItemcardList(Manager man) throws Exception {
        sm.addFrame(this);
        setTitle("Lista Items");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Carta Completa");
        labelTit.setBounds(anchoUnit * 5, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelPpal.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JScrollPane scrollPane = new JScrollPane(createTableItems());
        scrollPane.setPreferredSize(new Dimension(anchoUnit * 35, altoUnit * 72));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(anchoUnit * 8, altoUnit * 7, anchoUnit * 35, altoUnit * 79);
        panelPpal.add(scrollPane);

        JButtonMetalBlu butUpdate = utiliGraf.button1("Actualizar", (this.getWidth()/2) - 6 * anchoUnit, altoUnit * 88, anchoUnit * 12);
        butUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    listUpdater();
                } catch (Exception ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butUpdate);

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

    public JTable createTableItems() throws Exception {
        tableItems = new JTable();

        String col1 = "Item";
        String col2 = "Unidades";
        String col3 = "Precio";

        ArrayList<Itemcard> totalItems = daoI.listarItemsCard();
        int rows = totalItems.size();

        String[] colNames = {col1, col2, col3};
        String[][] data = new String[rows][3];

        for (int i = 0; i < rows; i++) {
            Itemcard ic = totalItems.get(i);

            data[i][0] = " " + ic.getName();
            data[i][1] = " " + ic.getStock() + " u.";
            data[i][2] = " $ " + ic.getPrice().get(0);
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        tableItems.setModel(tableModel);
        tableItems.setDefaultEditor(Object.class, null);

        JTableHeader header = tableItems.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(header.getWidth(), altoUnit * 6));
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(narLg);

        Font cellFont = new Font("Arial", Font.BOLD, 14);
        tableItems.setFont(cellFont);
        tableItems.setRowHeight(altoUnit * 4);
        tableItems.setBackground(narUlg);

        TableColumn column1 = tableItems.getColumnModel().getColumn(0);
        column1.setPreferredWidth(anchoUnit * 20);
        TableColumn column2 = tableItems.getColumnModel().getColumn(1);
        column2.setPreferredWidth(anchoUnit * 7);
        TableColumn column3 = tableItems.getColumnModel().getColumn(2);
        column3.setPreferredWidth(anchoUnit * 8);

        return tableItems;
    }

    private void listUpdater() {
        try {
            // Obtener los datos actualizados
            ArrayList<Itemcard> totalItems = daoI.listarItemsCard();
            int rows = totalItems.size();

            // Preparar los nuevos datos para la tabla
            String[][] data = new String[rows][3];
            for (int i = 0; i < rows; i++) {
                Itemcard ic = totalItems.get(i);
                data[i][0] = " " + ic.getName();
                data[i][1] = " " + ic.getStock() + " u.";
                data[i][2] = " $ " + ic.getPrice().get(0);
            }

            // Obtener el modelo de la tabla y actualizarlo
            DefaultTableModel tableModel = (DefaultTableModel) tableItems.getModel();
            tableModel.setDataVector(data, new String[]{"Item", "Unidades", "Precio"});

        } catch (Exception ex) {
            Logger.getLogger(ItemcardList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
