package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import salonmanager.entidades.bussiness.Register;
import salonmanager.entidades.graphics.FrameFull;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAORegister;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class RegisterListViewer extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();
    Color bluStBarr = new Color(2, 82, 67);
    Color narSt = new Color(217, 153, 4);
    Color bluLg = new Color(194, 242, 206);
    DAORegister daoR = new DAORegister();
    DAOUser daoU = new DAOUser();
    DAOItemcard daoI = new DAOItemcard();
    DAODeliveryClient daoDC = new DAODeliveryClient();

    ArrayList<Register> listRgos = null;
    String col1 = "Fecha";
    String col2 = "Usuario";
    String col3 = "Tipo de Objeto";
    String col4 = "Nombre de Objeto";
    String col5 = "Operación";
    JScrollPane scrollPane = new JScrollPane();
    JComboBox comboOk = new JComboBox();
    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable();

    TableColumn column1 = null;
    TableColumn column2 = null;
    TableColumn column3 = null;
    TableColumn column4 = null;
    TableColumn column5 = null;

    public RegisterListViewer() throws Exception {
        listRgos = daoR.listRegister();

        sm.addFrame(this);
        setTitle("Lista de registros");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Lista de Registros");
        labelTit.setBounds(anchoUnit * 2, altoUnit * 1, anchoUnit * 28, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        if (listRgos.size() > 0) {
            updateTable(listRgos);
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(narSt);
        Font cellFont = new Font("Arial", Font.BOLD, 14);
        table.setFont(cellFont);
        table.setRowHeight(25);
        table.setBackground(bluLg);
        table.setForeground(bluStBarr);

//        if (listRgos != null) {
//            int c = this.getWidth() / 35;
//            column1 = table.getColumnModel().getColumn(0);
//            column1.setPreferredWidth(c * 4);
//            column2 = table.getColumnModel().getColumn(1);
//            column2.setPreferredWidth(c * 4);
//            column3 = table.getColumnModel().getColumn(2);
//            column3.setPreferredWidth(c * 4);
//            column4 = table.getColumnModel().getColumn(3);
//            column4.setPreferredWidth(c * 6);
//            column5 = table.getColumnModel().getColumn(4);
//            column5.setPreferredWidth(c * 17);
//        }

        scrollPane = new JScrollPane(table);
        scrollPane.setBackground(bluLg);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(2 * anchoUnit, altoUnit * 7, this.getWidth() - anchoUnit * 6, this.getHeight() - altoUnit * 30);
        panelPpal.add(scrollPane);

        JLabel labelSel = utiliGraf.labelTitleBacker1W("Seleccione una categoría");
        labelSel.setBounds(anchoUnit * 3, this.getHeight() - altoUnit * 20, anchoUnit * 28, altoUnit * 4);
        panelPpal.add(labelSel);

        comboOk.setModel(utili.objectKindComboModelReturnWNull());
        comboOk.setBounds(anchoUnit * 3, this.getHeight() - altoUnit * 15, anchoUnit * 12, altoUnit * 4);
        comboOk.setFont(new Font("Arial", Font.BOLD, 14));
        comboOk.setSelectedItem("");
        panelPpal.add(comboOk);

        JButtonMetalBlu butSelCat = utiliGraf.button2("SELECCIONAR", anchoUnit * 16, this.getHeight() - altoUnit * 15, anchoUnit * 10);
        butSelCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sel = (String) comboOk.getSelectedItem();
                    filterRegister(sel);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterListViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelCat);

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

    private void updateTable(ArrayList<Register> rgos) throws Exception {
        String[] colNames = {col1, col2, col3, col4, col5};
        String[][] data = new String[rgos.size()][5]; // Ajusta el tamaño de la tabla de acuerdo al tamaño de rgos

        // Llenar los datos
        if (rgos != null) {
            for (int i = 0; i < rgos.size(); i++) {
                Register rgo = rgos.get(i);
                data[i][0] = "  " + utili.friendlyDate2(rgo.getEjecution());
                data[i][1] = "  " + daoU.getUserNameById(rgo.getUserId());
                String ok = rgo.getObjectKind();
                data[i][2] = "  " + ok;

                String objName = "";
                if (ok.equals("Itemcard")) {
                    objName = daoI.getItemNameById(Integer.parseInt(rgo.getObjectId()));
                } else if (ok.equals("Cliente")) {
                    objName = daoDC.getClientNameById(Integer.parseInt(rgo.getObjectId()));
                } else if (ok.equals("Usuario")) {
                    objName = daoU.getUserNameById(rgo.getObjectId());
                } else if (ok.equals("Orden")) {
                    objName = rgo.getObjectId();
                } else if (ok.equals("Turno")) {
                    objName = "Turno: " + rgo.getObjectId();
                }
                data[i][3] = "  " + objName;
                data[i][4] = "  " + rgo.getOperation();
            }
        }

        // Actualizar el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
        table.setModel(tableModel); // Aquí actualizas la tabla existente con el nuevo modelo
        int c = this.getWidth() / 35;
        column1 = table.getColumnModel().getColumn(0);
        column1.setPreferredWidth(c * 4);
        column2 = table.getColumnModel().getColumn(1);
        column2.setPreferredWidth(c * 4);
        column3 = table.getColumnModel().getColumn(2);
        column3.setPreferredWidth(c * 4);
        column4 = table.getColumnModel().getColumn(3);
        column4.setPreferredWidth(c * 6);
        column5 = table.getColumnModel().getColumn(4);
        column5.setPreferredWidth(c * 17);

        // Forzar la actualización de la tabla
        table.revalidate();
        table.repaint();
    }

    private void filterRegister(String objSt) throws Exception {
        ArrayList<Register> nRgo = new ArrayList<>();
        if (objSt.equals("Todo") || objSt.equals("")) {
            nRgo = listRgos;
        } else {
            for (int i = 0; i < listRgos.size(); i++) {
                if (listRgos.get(i).getObjectKind().equals(objSt)) {
                    nRgo.add(listRgos.get(i));
                }
            }
        }
        updateTable(nRgo);
    }
}
