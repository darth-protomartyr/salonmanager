package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class TableResumePanel extends FrameThird {

    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOConfig daoC = new DAOConfig();
    DAOItemCard daoI = new DAOItemCard();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    SalonManager sm = new SalonManager();
    double amountCash = 0;
    double amountElec = 0;
    double sum = 0;
    double loss = 0;
    double totalMount = 0;

    Table tabAux = new Table();

    TableResumePanel(Table tab) throws Exception {
        sm.addFrame(this);
        tabAux = tab;
        String tit = "Consulta Mesa";
        setTitle(tit);
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W(tit.toUpperCase());
        labelTit.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 26, altoUnit * 5);
        panelPpal.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelPn = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 7, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mesa: ", tabAux.getPos() + tabAux.getNum());
        panelPpal.add(panelPn);

        String dateOpen = utili.friendlyDate1(tabAux.getOpenTime());
        JPanel panelInit = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 12, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Inicio de mesa: ", dateOpen);
        panelPpal.add(panelInit);

        String dateClose = "";
        if (tabAux.getCloseTime() != null) {
            dateClose = utili.friendlyDate1(tabAux.getCloseTime());
        } else {
            tabAux.setCloseTime(new Timestamp(new Date().getTime()));
            dateClose = utili.friendlyDate1(tabAux.getCloseTime());
        }

        JPanel panelEnd = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 17, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Fin de mesa: ", dateClose);
        panelPpal.add(panelEnd);

        JPanel panelWaiter = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 22, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mozo: ", tabAux.getWaiter().getName() + " " + tabAux.getWaiter().getLastName());
        panelPpal.add(panelWaiter);

        JPanel panelDiscount = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 27, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Descuento: ", tabAux.getDiscount() + "%");
        panelPpal.add(panelDiscount);

        JPanel panelFact = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 32, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Facturación  $: ", "" + tabAux.getTotal() + "");
        panelPpal.add(panelFact);

        JPanel panelCash = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 37, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Efectivo $: ", "" + tabAux.getAmountCash() + "");
        panelPpal.add(panelCash);

        JPanel panelElectronic = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 42, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Transferencia $: ", "" + tabAux.getAmountElectronic());
        panelPpal.add(panelElectronic);

        JPanel panelError = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 47, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Error $: ", "" + tabAux.getError());
        panelPpal.add(panelError);

        String message = utili.listarItems(tabAux);

        JLabel labelMess = new JLabel(message);
        Font customFont = new Font("Arial", Font.BOLD, 15);
        labelMess.setFont(customFont);
        labelMess.setVerticalAlignment(SwingConstants.TOP);
        labelMess.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        JScrollPane scrollPane = new JScrollPane(labelMess);
        scrollPane.setBounds(anchoUnit * 4, altoUnit * 59, anchoUnit * 26, altoUnit * 32);
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
                dispose();
            }
        });
    }

    private void confirmMount() throws Exception {
        boolean error = false;
        Workshift ws = daoW.askWorshiftByTabDate(tabAux.getOpenTime());

        if (ws.getCloseDateWs() == null) {
            utiliMsg.errorWsOpen();
            error = true;
        }

        if (sum < 1 && error == false) {
            utiliMsg.errorMountNull();
            error = true;
        }

        if (sum > loss && error == false) {
            utiliMsg.errorTotalLess();
            error = true;
        }

        if (error == false) {
            boolean confirm = false;
            if (loss > sum) {
                confirm = utiliMsg.cargaConfirmErrorInsuf();
                String mess = tabAux.getComments() + "El error fue revisado y quedó un monto pendiente.<br>";
                tabAux.setComments(mess);
            } else {
                confirm = utiliMsg.cargaConfirmErrorSuf();
                String mess = tabAux.getComments() + "El monto pendiente fue subsanado.<br>";
                tabAux.setComments(mess);
            }

            if (confirm) {
                tabAux.setAmountCash(tabAux.getAmountCash() + amountCash);
                tabAux.setAmountElectronic(tabAux.getAmountElectronic() + amountElec);
                tabAux.setTotal(tabAux.getTotal() - tabAux.getError() + sum);
                tabAux.setError(tabAux.getError() - sum);
                daoT.updateTableMountCash(tabAux);
                daoT.updateTableMountElectronic(tabAux);
                daoT.updateTableTotal(tabAux);
                daoT.updateError(tabAux);
                daoT.updateComments(tabAux);

                ws.setTotalMountCashWs(ws.getTotalMountCashWs() + amountCash);
                ws.setTotalMountElectronicWs(ws.getTotalMountElectronicWs() + amountElec);
                ws.setTotalMountTabs(ws.getTotalMountTabs() + sum);
                ws.setTotalMountWs(ws.getTotalMountWs() + sum);
                ws.setErrorMountWs(ws.getErrorMountTabs() - sum);

                daoW.updateWorkshiftCash(ws);
                daoW.updateWorkshiftElectronic(ws);
                daoW.updateWorkshiftTabs(ws);
                daoW.updateWorkshiftMountWs(ws);
                daoW.updateWorkshiftError(ws);

                ConfigActual cfgAct = daoC.askConfigActual();
                ArrayList<String> ids = cfgAct.getArrayDeferWs();
                Iterator<String> iterador = ids.iterator();
                while (iterador.hasNext()) {
                    String st = iterador.next();
                    if (st.equals(tabAux.getId())) {
                        iterador.remove();
                    }
                }

                cfgAct.setArrayDeferWs(ids);
                daoC.updateCfgActDeferWs(ids);
                dispose();
            }
        }
    }

    private void closeTab() throws Exception {
        boolean confirm = utiliMsg.cargaConfirmarFacturacion(totalMount, sum - totalMount);
        if (confirm) {
            daoT.updateCloseTime(tabAux);
            tabAux.setOpen(false);
            daoT.updateTableOpen(tabAux);
            tabAux.setBill(true);
            daoT.updateTableBill(tabAux);
            tabAux.setAmountCash(amountCash);
            daoT.updateTableMountCash(tabAux);
            tabAux.setAmountElectronic(amountElec);
            daoT.updateTableMountElectronic(tabAux);
            tabAux.setTotal(totalMount);
            daoT.updateTableTotal(tabAux);
            String comment3 = "Mesa cerrada en turno diferido.<br>";
            tabAux.setComments(tabAux.getComments() + comment3);
            if (totalMount - sum > 0) {
                tabAux.setError(totalMount - sum);
                daoT.updateError(tabAux);
                ConfigActual cfgAct = daoC.askConfigActual();
                ArrayList<String> errorTabs = cfgAct.getArrayDeferWs();
                errorTabs.add(tabAux.getId());
                daoC.updateCfgActDeferWs(errorTabs);
                String comment2 = "La mesa contiene un error y deberá ser revisada.<br>";
                tabAux.setComments(tabAux.getComments() + comment2);
            }
            daoT.updateComments(tabAux);

            if (tabAux.isToPay()) {
                tabAux.setToPay(false);
                daoT.updateToPay(tabAux);
                daoI.downActiveItemPayedTableAll(tabAux);
                daoI.upActiveItemOrderTableAll(tabAux);
            }
            dispose();
        }
    }
}