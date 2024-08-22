package salonmanager;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JSpinner;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNestedItem;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.servicios.ServiceConfigItemList;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasConfigItemList;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigItemList extends FrameFull {

    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    Color white = new Color(255, 255, 255);
    ArrayList<Itemcard> items = new ArrayList<>();
    ArrayList<PanelNestedItem> panelsN = new ArrayList<>();
    DAOItemcard daoI = new DAOItemcard();
    DAOConfig daoC = new DAOConfig();
    DAOTable daoT = new DAOTable();
    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasConfigItemList utiliGrafCIL = new UtilidadesGraficasConfigItemList();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceConfigItemList sCIL = new ServiceConfigItemList();
    JPanel panelPanel = new JPanel();
    ArrayList<String> categories = new ArrayList<>();
    String cat = "";
    JCheckBox checkBox = null;
    JButtonMetalBlu butEnab = null;
    JButtonMetalBlu butSelCat2 = null;
    JButtonMetalBlu butSelPri = null;
    JComboBox comboCat = new JComboBox();
    JComboBox comboCat2 = new JComboBox();
    JComboBox comboRound = new JComboBox();
    JSpinner spinnerPC = new JSpinner();
    Manager manager = null;
    boolean vis = true;
    Font newFont = new Font("Arial", Font.PLAIN, 16);
    Font font = new Font("Arial", Font.BOLD, 18);
    ConfigItemList cil = this;
    

    public ConfigItemList(Manager man) throws Exception {
        manager = man;
        sm.addFrame(this);

        setTitle("Modificar Items de la carta en Lista");
        items = daoI.listarItemsCard();
        ConfigGeneral cfgGen = daoC.askConfigGeneral();
        categories = cfgGen.getTableItemCategories();
        cat = "TODOS";
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth() - anchoUnit * 10, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Lista de Items");
        panelLabel.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelList = utiliGrafCIL.panelListBacker(this);
        panelPpal.add(panelList);
          
        JPanel panelLat = utiliGrafCIL.panelLatBacker(this);
        panelList.add(panelLat);

        JPanel panelSelCat = utiliGrafCIL.panelSelCatBacker(this);
        panelLat.add(panelSelCat);

        JPanel panelModPri = utiliGrafCIL.panelPanelModPri(this);
        panelLat.add(panelModPri);

        sCIL.select(cat, false, this);

        JButtonMetalBlu butChangeSaver = utiliGrafCIL.butChangeSaver(this);
        panelList.add(butChangeSaver);

        JButtonMetalBlu butReset = utiliGraf.button2("RESETEAR", anchoUnit * 94, altoUnit * 84, anchoUnit * 8);
        butReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    cat = "TODOS";
                    sCIL.select(cat, false, cil);
                    comboCat.setSelectedItem(cat);
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelList.add(butReset);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
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

    private void selec(String sel, boolean type) {
        ArrayList<Itemcard> itemsSel = new ArrayList<>();
        if (sel.equals("TODOS")) {
            itemsSel = items;
        } else {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getCategory().equals(sel)) {
                    itemsSel.add(items.get(i));
                }
            }
        }

        if (itemsSel.size() > 0) {
            panelsN = new ArrayList<>();
            panelPanel.removeAll();

            for (int i = 0; i < itemsSel.size(); i++) {
                PanelNestedItem pni = new PanelNestedItem(itemsSel.get(i), i, categories);
                if (type) {
                    if (vis) {
                        pni.getButSel().setVisible(false);
                        pni.getTextF2().setEnabled(true);
                        pni.getTextF3().setEnabled(true);
                        pni.getTextF4().setEnabled(true);
                        pni.getTextF5().setEnabled(true);
                    } else {
                        pni.getButSel().setVisible(true);
                        pni.getTextF2().setEnabled(false);
                        pni.getTextF3().setEnabled(false);
                        pni.getTextF4().setEnabled(false);
                        pni.getTextF5().setEnabled(false);
                    }
                }
                panelsN.add(pni);
                panelPanel.add(pni);
            }

            if (vis) {
                vis = false;
            } else {
                vis = true;
            }

            panelPanel.revalidate();
            panelPanel.repaint();
        } else {
            utiliMsg.errorCat();
        }
    }

//    private void updateCat(String cat) throws Exception {
//        ArrayList<Integer> ids = new ArrayList<>();
//        for (int i = 0; i < panelsN.size(); i++) {
//            String mod = panelsN.get(i).getButSel().getText();
//            if (mod.equals("QUITAR")) {
//                String cat1 = panelsN.get(0).getIc().getCategory();
//                if (!cat.equals(cat1)) {
//                    int id = panelsN.get(i).getIc().getId();
//                    daoI.updateItemCategory(id, cat);
//                    ids.add(id);
//                } else {
//                    utiliMsg.errorCatEqual();
//                }
//            }
//        }
//        if (manager.getSalon() != null) {
//            manager.getSalon().dispose();
//            manager.setSalon(null);
//        }
//        reset();
//    }

//    private void updatePrice(int p, String r) throws Exception {
//        int pc = 100 + p;
//        double round = 0;
//        boolean error = false;
//        boolean confirm1 = true;
//        boolean confirm2 = true;
//
//        int counter1 = 0;
//        int counter2 = 0;
//        int counter3 = 0;
//
//        try {
//            round = utili.toNumberD(r);
//        } catch (NumberFormatException e) {
//            utiliMsg.errorNumerico();
//            error = true;
//        } catch (Exception ex) {
//            Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (error == false) {
//            for (int i = 0; i < panelsN.size(); i++) {
//                String mod = panelsN.get(i).getButSel().getText();
//                int idIc = panelsN.get(i).getIc().getId();
//                if (mod.equals("QUITAR")) {
//                    double oldPrice = panelsN.get(i).getIc().getPrice().get(0);
//                    double newPrice = oldPrice * pc / 100;
//                    double rou = newPrice % round;
//                    rou = round - rou;
//                    newPrice = newPrice + rou;
//                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
//                    DecimalFormat df = new DecimalFormat("#.00", symbols);
//                    String st = df.format(newPrice);
//                    newPrice = Double.parseDouble(st);
//                    ArrayList<Double> prices = new ArrayList<>();
//                    prices.add(newPrice);
//                    prices.add(oldPrice);
//                    
//                    if (newPrice <= panelsN.get(i).getIc().getCost()) {
//                        confirm1 = utiliMsg.errorPriceCost();
//                    }
//                    
//                    
//                    
//                    ArrayList<String> tabIds = new ArrayList<>();
//                    ArrayList<String> tabIdsIc = new ArrayList<>();
//                    tabIds = daoT.getActiveIds();
//                    for (int x = 0; x < tabIds.size(); x++) {
//                        ArrayList<Integer> arrayTabIdsIc = daoT.activeTabIcMod(idIc, tabIds.get(x));
//                        if (arrayTabIdsIc.size() > 0) {
//                            tabIdsIc.add(tabIds.get(x));
//                        }
//                    }
//
//                    if (tabIdsIc.size() > 0) {
//                        if (counter1 < 1) {
//                            confirm1 = utiliMsg.cargaConfirmarCambioPrAct();
//                            counter1 += 1;
//                        }
//                    }
//
//                    if (confirm1) {
//                        if (manager.getSalon() != null) {
//                            if (counter2 < 1) {
//                                confirm2 = utiliMsg.cargaConfirmarUpdateActiveTabs();
//                                counter2 += 1;
//                            }
//                            if (confirm2) {
//                                ArrayList<String> modTabsNew = new ArrayList<>();
//                                for (int y = 0; y < tabIdsIc.size(); y++) {
//                                    modTabsNew.add(tabIdsIc.get(y));
//                                    modTabsNew.add(idIc + "");
//                                }
//                                ConfigActual cfgAct = daoC.askConfigActual();
//                                ArrayList<String> modTabIds = cfgAct.getArrayUnModTabs();
//                                for (int y = 0; y < modTabsNew.size(); y++) {
//                                    modTabIds.add(modTabsNew.get(y));
//                                }
//                                daoC.updateCfgActModTabs(modTabIds);
//
//                                daoI.updateItemPrice(idIc, prices);
//
//                                manager.getSalon().dispose();
//                                manager.setSalon(null);
//                                if (counter3 < 1) {
//                                    utiliMsg.cargaUpdatePriceItemActive();
//                                    counter3 += 1;
//
//                                }
//                            }
//                        } else {
//                            daoI.updateItemPrice(idIc, prices);
//                        }
//
//                    }
//                }
//            }
//        }
//        reset();
//    }

//    private void changeSaver() throws Exception {
//        for (int i = 0; i < panelsN.size(); i++) {
//            boolean error = false;
//            String mod = panelsN.get(i).getButSel().getText();
//            String cat = (String) panelsN.get(i).getTextF2().getSelectedItem();
//            int stock = 0;
//            double cost = 0;
//            double price = 0;
//            if (mod.equals("QUITAR")) {
//                try {
//                    stock = (Integer) panelsN.get(i).getTextF3().getValue();
//                    String co = panelsN.get(i).getTextF4().getText();
//                    if (co.length() > 12) {
//                        error = true;
//                        utiliMsg.errorCantCharNum();
//                    }
//
//                    if (co.equals("")) {
//                        cost = 0;
//                    } else {
//                        cost = utili.toNumberD(co);
//                    }
//
//                    String pri = panelsN.get(i).getTextF5().getText();
//                    if (pri.length() > 12) {
//                        error = true;
//                        utiliMsg.errorCantCharNum();
//                    }
//
//                    if (pri.equals("")) {
//                        boolean confirm = utiliMsg.cargaConfirmErrorPriceNull();
//                        if (confirm) {
//                            pri = "0";
//                            price = utili.toNumberD(pri);
//                        } else {
//                            error = true;
//                        }
//                    } else {
//                        price = utili.toNumberD(pri);
//                    }
//
//                } catch (NumberFormatException e) {
//                    utiliMsg.errorNumerico();
//                    error = true;
//
//                } catch (Exception ex) {
//                    Logger.getLogger(ItemcardInn.class
//                            .getName()).log(Level.SEVERE, null, ex);
//                }
//
//                if (error == false) {
//                    int id = panelsN.get(i).getIc().getId();
//                    Itemcard ic = panelsN.get(i).getIc();
//                    daoI.updateItemCategory(id, cat);
//                    daoI.updateItemStock(id, stock);
//                    daoI.updateItemCost(id, cost);
//
//                    ArrayList<Double> prices = new ArrayList<>();
//                    prices.add(price);
//                    prices.add(ic.getPrice().get(0));
//
//                    boolean confirm1 = true;
//                    if (price <= cost) {
//                        confirm1 = utiliMsg.cargaConfirmLowerPrice();
//                    }
//
//                    if (confirm1) {
//                        ArrayList<String> tabIds = new ArrayList<>();
//                        ArrayList<String> tabIdsIc = new ArrayList<>();
//                        tabIds = daoT.getActiveIds();
//                        for (int x = 0; x < tabIds.size(); x++) {
//                            ArrayList<Integer> arrayTabIdsIc = daoT.activeTabIcMod(ic.getId(), tabIds.get(x));
//                            if (arrayTabIdsIc.size() > 0) {
//                                tabIdsIc.add(tabIds.get(x));
//                            }
//                        }
//
//                        boolean confirm2 = true;
//                        if (tabIdsIc.size() > 0) {
//                            confirm2 = utiliMsg.cargaConfirmarCambioPrAct();
//                        }
//
//                        if (confirm2) {
//                            if (manager.getSalon() != null) {
//                                boolean confirm3 = utiliMsg.cargaConfirmarUpdateActiveTabs();
//                                if (confirm3) {
//                                    ArrayList<String> modTabsNew = new ArrayList<>();
//                                    for (int y = 0; y < tabIdsIc.size(); y++) {
//                                        modTabsNew.add(tabIdsIc.get(y));
//                                        modTabsNew.add(ic.getId() + "");
//                                    }
//                                    ConfigActual cfgAct = daoC.askConfigActual();
//                                    ArrayList<String> modTabIds = cfgAct.getArrayUnModTabs();
//                                    for (int y = 0; y < modTabsNew.size(); y++) {
//                                        modTabIds.add(modTabsNew.get(y));
//                                    }
//
//                                    daoC.updateCfgActModTabs(modTabIds);
//                                    daoI.modificarItem(id, ic.getName(), cat, ic.getDescription(), cost, prices, stock, ic.isActiveTip());
//                                    manager.getSalon().dispose();
//                                    manager.setSalon(null);
//                                    utiliMsg.cargaUpdatePriceItemActive();
//                                }
//                            } else {
//                                daoI.modificarItem(id, ic.getName(), cat, ic.getDescription(), cost, prices, stock, ic.isActiveTip());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        reset();
//    }


//    private boolean modifyPnls() {
//        boolean mod = false;
//        for (int i = 0; i < panelsN.size(); i++) {
//            String but = panelsN.get(i).getButSel().getText();
//            if (but.equals("QUITAR")) {
//                mod = true;
//                break;
//            }
//        }
//        return mod;
//    }

    public ArrayList<Itemcard> getItems() {
        return items;
    }

    public void setItems(ArrayList<Itemcard> items) {
        this.items = items;
    }

    public ArrayList<PanelNestedItem> getPanelsN() {
        return panelsN;
    }

    public void setPanelsN(ArrayList<PanelNestedItem> panelsN) {
        this.panelsN = panelsN;
    }

    public JPanel getPanelPanel() {
        return panelPanel;
    }

    public void setPanelPanel(JPanel panelPanel) {
        this.panelPanel = panelPanel;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public JButtonMetalBlu getButEnab() {
        return butEnab;
    }

    public void setButEnab(JButtonMetalBlu butEnab) {
        this.butEnab = butEnab;
    }

    public JButtonMetalBlu getButSelCat2() {
        return butSelCat2;
    }

    public void setButSelCat2(JButtonMetalBlu butSelCat2) {
        this.butSelCat2 = butSelCat2;
    }

    public JButtonMetalBlu getButSelPri() {
        return butSelPri;
    }

    public void setButSelPri(JButtonMetalBlu butSelPri) {
        this.butSelPri = butSelPri;
    }

    public JComboBox getComboCat() {
        return comboCat;
    }

    public void setComboCat(JComboBox comboCat) {
        this.comboCat = comboCat;
    }

    public JComboBox getComboCat2() {
        return comboCat2;
    }

    public void setComboCat2(JComboBox comboCat2) {
        this.comboCat2 = comboCat2;
    }

    public JComboBox getComboRound() {
        return comboRound;
    }

    public void setComboRound(JComboBox comboRound) {
        this.comboRound = comboRound;
    }

    public JSpinner getSpinnerPC() {
        return spinnerPC;
    }

    public void setSpinnerPC(JSpinner spinnerPC) {
        this.spinnerPC = spinnerPC;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean isVis() {
        return vis;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public Font getNewFont() {
        return newFont;
    }

    public void setNewFont(Font newFont) {
        this.newFont = newFont;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
    
    
    
}
