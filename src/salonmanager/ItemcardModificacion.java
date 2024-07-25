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
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCard;
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
    DAOItemCard daoIC = new DAOItemCard();
    DAOTable daoT = new DAOTable();
    DAOConfig daoC = new DAOConfig();
    SalonManager sm = new SalonManager();

    ArrayList<ItemCard> itemsCardDB = null;
    String name = "";
    String category = "";
    String description = "";
    double cost = 0;
    double price = 0;
    int stock = 0;
    boolean tipAlta = false;
    ItemCard itemAux = new ItemCard();
    ArrayList<String> categoriesDB = null;

    JTextField fieldName = new JTextField();
    JComboBox comboCategory = new JComboBox();
    JTextArea areaDescription = new JTextArea();
    JTextField fieldCost = new JTextField();
    JTextField fieldPrice = new JTextField();
    JTextField fieldStock = new JTextField();
    JCheckBox checkTip = new JCheckBox("");
    JButtonMetalBlu butModificarItem = null;
    Manager manager = null;

    public ItemcardModificacion(ItemCard ic, Manager man) throws Exception {
        itemAux = ic;
        manager = man;
        sm.addFrame(this);
        setTitle("Modificación Item de la Carta");
        itemsCardDB = daoIC.listarItemsCard();
        categoriesDB = daoC.askCategoriesConfig();
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("MODIFICAR ITEMS DEL MENÚ");
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 40, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelForm = utiliGraf.panelItemcardForm(fieldName, comboCategory, areaDescription, fieldCost, fieldPrice, fieldStock, checkTip, categoriesDB, itemAux);
        panelPpal.add(panelForm);

        itemSetter();

        butModificarItem = utiliGraf.button1("Modificar Item", anchoUnit * 16, altoUnit * 86, anchoUnit * 19);
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
        name = utili.reduxName(fieldName.getText(), true);
        category = (String) comboCategory.getSelectedItem();
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
            boolean confirm = utiliMsg.errorPriceCost();
            if (!confirm) {
                error = true;
            }
        }

        tipAlta = checkTip.isSelected();

        if (error == false) {
            ArrayList<String> tabIds = new ArrayList<>();
            ArrayList<String> tabIdsIc = new ArrayList<>();
            price = utili.round2Dec(price);
            double priceOld = itemAux.getPrice().get(0);
            cost = utili.round2Dec(cost);
            boolean confirm = true;
            ArrayList<Double> prices = new ArrayList<>();
            prices.add(price);
            prices.add(priceOld);
            if (price != priceOld) {
                tabIds = daoT.getActiveIds();
                for (int i = 0; i < tabIds.size(); i++) {
                    ArrayList<Integer> arrayTabIdsIc = daoT.activeTabIcMod(itemAux.getId(), tabIds.get(i));
                    if (arrayTabIdsIc.size() > 0) {
                        tabIdsIc.add(tabIds.get(i));
                    }
                }
                if (tabIdsIc.size() > 0) {
                    confirm = utiliMsg.cargaConfirmarCambioPrAct();
                }
            }

            if (confirm) {
                if (manager.getSalon() != null) {
                    boolean confirm2 = utiliMsg.cargaConfirmarUpdateActiveTabs();
                    if (confirm2) {
                        ArrayList<String> modTabsNew = new ArrayList<>();
                        for (int i = 0; i < tabIdsIc.size(); i++) {
                            modTabsNew.add(tabIdsIc.get(i));
                            modTabsNew.add(itemAux.getId() + "");
                        }
                        ConfigActual cfgAct = daoC.askConfigActual();
                        ArrayList<String> modTabIds = cfgAct.getArrayUnModTabs();
                        for (int i = 0; i < modTabsNew.size(); i++) {
                            modTabIds.add(modTabsNew.get(i));
                        }
                        daoC.updateCfgActModTabs(modTabIds);
                        daoIC.modificarItem(itemAux.getId(), name, category, description, cost, prices, stock, tipAlta);
                        utiliMsg.cargaUpdatePriceItemActive();

                    }
                    manager.getSalon().dispose();
                    manager.setSalon(null);
                } else {
                    daoIC.modificarItem(itemAux.getId(), name, category, description, cost, prices, stock, tipAlta);
                }
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
        itemAux = new ItemCard();

        fieldName.setText("");
        areaDescription.setText("");
        fieldCost.setText("");
        fieldPrice.setText("");
        fieldStock.setText("");
        checkTip.setSelected(false);
    }

    
    private void itemSetter() {
        fieldName.setText(itemAux.getName());
        int index = 0;
        for (int i = 0; i < categoriesDB.size(); i++) {
            if (categoriesDB.get(i).equals(itemAux.getCategory())) {
                index = i;
            }
        }
        comboCategory.setSelectedIndex(index);
        areaDescription.setText(itemAux.getDescription());
        fieldCost.setText(itemAux.getCost() + "");
        fieldPrice.setText(itemAux.getPrice().get(0) + "");
        fieldStock.setText(itemAux.getStock() + "");
        checkTip.setSelected(itemAux.isActiveTip());
    }
}