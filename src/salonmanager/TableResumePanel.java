package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import salonmanager.entidades.FrameThird;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class TableResumePanel extends FrameThird {

    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioTable st = new ServicioTable();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();

    TextArea textAreaList = new TextArea();
    Table tabAux = new Table();
    ArrayList<Table> tabs = null;

    TableResumePanel(Table tab) throws Exception {
        sm.addFrame(this);
        tabAux = tab;
//        tabAux = st.tableItemsByTab(tabAux);
        setTitle("Consulta Mesa");
        PanelPpal panelPpal = new PanelPpal(anchoFrameThird, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4("Consulta Mesa");
        labelTit.setBounds(20, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelPn = utiliGraf.panelInfoBacker(70, 60, 300, 40, bluLg, 20, "Mesa: ", tabAux.getPos() + tabAux.getNum());
        panelPpal.add(panelPn);

        JPanel panelInit = utiliGraf.panelInfoBacker(70, 100, 300, 40, bluLg, 20, "Inicio de mesa: ", utili.friendlyDate(tabAux.getOpenTime()));
        panelPpal.add(panelInit);

        JPanel panelWaiter = utiliGraf.panelInfoBacker(70, 140, 300, 40, bluLg, 20, "Mozo: ", tabAux.getWaiter().getName() + " " + tabAux.getWaiter().getLastName());
        panelPpal.add(panelWaiter);

        JPanel panelDiscount = utiliGraf.panelInfoBacker(70, 180, 300, 40, bluLg, 20, "Descuento: ", tabAux.getDiscount() + "%");
        panelPpal.add(panelDiscount);

        JPanel panelFact = utiliGraf.panelInfoBacker(70, 220, 300, 40, bluLg, 20, "Facturación: ", "$" + tabAux.getTotal() + "");
        panelPpal.add(panelFact);

        JPanel panelCash = utiliGraf.panelInfoBacker(70, 260, 300, 40, bluLg, 20, "Efectivo: ", "$" + tabAux.getTotal() + "");
        panelPpal.add(panelCash);

        JPanel panelElectronic = utiliGraf.panelInfoBacker(70, 300, 300, 40, bluLg, 20, "Transferencia: ", "$" + tabAux.getAmountElectronic());
        panelPpal.add(panelElectronic);

        JPanel panelError = utiliGraf.panelInfoBacker(70, 340, 300, 40, bluLg, 20, "Error: ", "$" + tabAux.getError());
        panelPpal.add(panelError);

        JPanel panelCorrection = utiliGraf.panelInfoBacker(70, 380, 300, 40, bluLg, 20, "Corrección precio: ", "$" + tabAux.getPriceCorrection());
        panelPpal.add(panelCorrection);

        String message = utili.listarItems(tabAux);
        
        JLabel labelMess = new JLabel(message);
        Font customFont = new Font("Arial", Font.BOLD, 15); // Puedes ajustar el tipo de fuente, estilo y tamaño
        labelMess.setFont(customFont);
        labelMess.setVerticalAlignment(SwingConstants.TOP);
        labelMess.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JScrollPane scrollPane = new JScrollPane(labelMess);

        scrollPane.setBounds(70, 440, 300, 220);
        panelPpal.add(scrollPane);
    }



//    private void showItems(int i) {
//        switch (i) {
//            case 1:
//                ArrayList<Itemcard> items = tabAux.getOrder();
//                if (items.size() > 0) {
//                    CustomShowItems csi = new CustomShowItems(items, 1);
//                    csi.setVisible(true);
//                } else {
//                    utiliMsg.itemNull();
//                }
//        }
//    }
}
