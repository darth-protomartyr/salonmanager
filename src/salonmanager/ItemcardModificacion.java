package salonmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.servicios.ServicioItemcard;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemcardModificacion extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioItemcard si = new ServicioItemcard();
    DAOItemcard daoIC = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    SalonManager sm = new SalonManager();

    ArrayList<Itemcard> itemsCardDB = null;
    String name = "";
    String category = "";
    String description = "";
    double cost = 0;
    double price = 0;
    int stock = 0;
    boolean tipAlta = false;
    Itemcard itemAux = new Itemcard();
    ArrayList<String> categoriesDB = utili.categoryList();

    JTextField fieldName = new JTextField();
    JComboBox comboCategory = new JComboBox();
    JTextArea areaDescription = new JTextArea();
    JTextField fieldCost = new JTextField();
    JTextField fieldPrice = new JTextField();
    JTextField fieldStock = new JTextField();
    JCheckBox checkTip = new JCheckBox("");
    JButtonMetalBlu butModificarItem = null;

    public ItemcardModificacion(Itemcard ic) throws Exception {
        itemAux = ic;
        sm.addFrame(this);
        setTitle("Modificación Item de la Carta");
        itemsCardDB = daoIC.listarItemsCard();
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("MODIFICAR ITEMS DEL MENÚ");
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 40, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelForm = utiliGraf.panelItemcardForm(fieldName, comboCategory, areaDescription, fieldCost, fieldPrice, fieldStock, checkTip, categoriesDB, itemAux);
        panelPpal.add(panelForm);

        butModificarItem = utiliGraf.button1("Modificar Item", 206, 600, 270);
        butModificarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butModificarItemActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butModificarItem);

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

    private void butModificarItemActionPerformed() throws Exception {
        boolean error = false;
        name = fieldName.getText();
        category = utili.selectorCategory(comboCategory.getSelectedIndex());
        description = areaDescription.getText();

        if (name.length() > 30 || name.length() < 2) {
            error = true;
            utiliMsg.errorCantCharName();
        }

        if (utili.itemcardRepeat(name, itemsCardDB)) {
            error = true;
            utiliMsg.errorNameRepeat();
        }

        if (description.length() > 149) {
            error = true;
            utiliMsg.errorCantCharDescription();
        }

        try {
            String cos = fieldCost.getText();
            if (cos.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (cos.equals("")) {
                cost = 0;
            } else {
                cost = utili.toNumberD(cos);
            }

            String pric = fieldPrice.getText();
            if (pric.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (pric.equals("")) {
                boolean confirm = utiliMsg.cargaConfirmErrorPriceNull();
                if (confirm) {
                    pric = "0";
                    price = utili.toNumberD(pric);
                } else {
                    error = true;
                }
            } else {
                price = utili.toNumberD(pric);
            }

            String sto = fieldStock.getText();
            if (sto.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (sto.equals("")) {
                stock = 0;
            } else {
                stock = utili.toNumberI(sto);
            }

        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
            resetItemcard();
        } catch (Exception ex) {
            Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cost > price) {
            utiliMsg.errorPriceCost();
            error = true;
        }

        tipAlta = checkTip.isSelected();

        if (error == false) {
            price = utili.round2Dec(price);
            cost = utili.round2Dec(cost);
            boolean confirm = true;

            if (price != itemAux.getPrice()) {
                ArrayList<String> tabIds = daoT.getActiveIds();
                ArrayList<String> tabIdsIc = daoT.getActiveIds();
                for (int i = 0; i < tabIds.size(); i++) {
                    tabIdsIc = daoT.activeTabIcMod(itemAux.getId());
                }
                
                if (tabIdsIc.size() > 0) {
                    confirm = utiliMsg.cargaConfirmarCambioPrAct();
                }
            }

            if (confirm) {
                daoIC.modificarItem(itemAux.getId(), name, category, description, cost, price, stock, tipAlta);
            }
            resetItemcard();
            dispose();
        }
    }

    private void resetItemcard() throws Exception {
        itemsCardDB = daoIC.listarItemsCard();
        name = "";
        category = "";
        description = "";
        cost = 0;
        price = 0;
        stock = 0;
        tipAlta = false;
        itemAux = new Itemcard();

        fieldName.setText("");
        areaDescription.setText("");
        fieldCost.setText("");
        fieldPrice.setText("");
        fieldStock.setText("");
        checkTip.setSelected(false);
    }
}
