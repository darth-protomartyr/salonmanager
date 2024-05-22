package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.graphics.JButtonMetalBlu;
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
        setTitle("Consulta Mesa");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("CONSULTA MESA");
        labelTit.setBounds(20, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelPn = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 9, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mesa: ", tabAux.getPos() + tabAux.getNum());
        panelPpal.add(panelPn);

        String dateOpen = utili.friendlyDate1(tabAux.getOpenTime());
        JPanel panelInit = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 14, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Inicio de mesa: ", dateOpen);
        panelPpal.add(panelInit);

        String dateClose = utili.friendlyDate1(tabAux.getCloseTime());
        JPanel panelEnd = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 19, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Fin de mesa: ", dateClose);
        panelPpal.add(panelEnd);

        JPanel panelWaiter = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 24, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mozo: ", tabAux.getWaiter().getName() + " " + tabAux.getWaiter().getLastName());
        panelPpal.add(panelWaiter);

        JPanel panelDiscount = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 29, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Descuento: ", tabAux.getDiscount() + "%");
        panelPpal.add(panelDiscount);

        JPanel panelFact = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 34, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Facturación: ", "$" + tabAux.getTotal() + "");
        panelPpal.add(panelFact);

        JPanel panelCash = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 39, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Efectivo: ", "$" + tabAux.getAmountCash() + "");
        panelPpal.add(panelCash);

        JPanel panelElectronic = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 44, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Transferencia: ", "$" + tabAux.getAmountElectronic());
        panelPpal.add(panelElectronic);

        JPanel panelError = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 49, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Error: ", "$" + tabAux.getError());
        panelPpal.add(panelError);

        JPanel panelCorrection = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 54, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Corrección precio: ", "$" + tabAux.getPriceCorrection());
        panelPpal.add(panelCorrection);

        String message = utili.listarItems(tabAux);
        
        JLabel labelMess = new JLabel(message);
        Font customFont = new Font("Arial", Font.BOLD, 15); // Puedes ajustar el tipo de fuente, estilo y tamaño
        labelMess.setFont(customFont);
        labelMess.setVerticalAlignment(SwingConstants.TOP);
        labelMess.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JScrollPane scrollPane = new JScrollPane(labelMess);

        scrollPane.setBounds(anchoUnit * 4, altoUnit * 61, anchoUnit * 26, altoUnit * 30);
        panelPpal.add(scrollPane);
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
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
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
    }
}
