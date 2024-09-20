package salonmanager.servicios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import salonmanager.ConfigItemList;
import salonmanager.ItemcardInn;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.PanelNestedItem;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServiceConfigItemList {
    
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOItemcard daoI = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    DAOConfig daoC = new DAOConfig();
    
    
    public void select(String sel, boolean type, ConfigItemList cil) {
        ArrayList<Itemcard> itemsSel = new ArrayList<>();
        if (sel.equals("TODOS")) {
            itemsSel = cil.getItems();
        } else {
            for (int i = 0; i < cil.getItems().size(); i++) {
                if (cil.getItems().get(i).getCategory().equals(sel)) {
                    itemsSel.add(cil.getItems().get(i));
                }
            }
        }
        
        if (itemsSel.size() > 0) {
            cil.setPanelsN(new ArrayList<>());
            cil.getPanelPanel().removeAll();

            for (int i = 0; i < itemsSel.size(); i++) {
                PanelNestedItem pni = new PanelNestedItem(itemsSel.get(i), i, cil.getCategories());
                if (type) {
                    if (cil.isVis()) {
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
                cil.getPanelsN().add(pni);
                cil.getPanelPanel().add(pni);
            }

            if (cil.isVis()) {
                cil.setVis(false);
            } else {
                cil.setVis(true);
            }

            cil.getPanelPanel().revalidate();
            cil.getPanelPanel().repaint();
        } else {
            utiliMsg.errorCat();
        }   
    }
    
    public void updateCat(String cat, ConfigItemList cil) throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cil.getPanelsN().size(); i++) {
            String mod = cil.getPanelsN().get(i).getButSel().getText();
            if (mod.equals("QUITAR")) {
                String cat1 = cil.getPanelsN().get(0).getIc().getCategory();
                if (!cat.equals(cat1)) {
                    int id = cil.getPanelsN().get(i).getIc().getId();
                    daoI.updateItemCategory(id, cat);
                    ids.add(id);
                } else {
                    utiliMsg.errorCatEqual();
                }
            }
        }
        if (cil.getManager().getSalon() != null) {
            cil.getManager().getSalon().dispose();
            cil.getManager().setSalon(null);
        }
        reset(cil);
    }

    
    public ArrayList<String> listRounds() {
        ArrayList<String> rounds = new ArrayList<String>();
        rounds.add("0.01");
        rounds.add("0.05");
        rounds.add("0.1");
        rounds.add("0.5");
        rounds.add("1");
        rounds.add("5");
        rounds.add("10");
        rounds.add("50");
        rounds.add("100");
        rounds.add("500");
        rounds.add("1000");
        return rounds;
    }

    
    public void updatePrice(int p, String r, ConfigItemList cil) throws Exception {
        int pc = 100 + p;
        double round = 0;
        boolean error = false;
        boolean confirm1 = true;
        boolean confirm2 = true;
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        try {
            round = utili.toNumberD(r);
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            for (int i = 0; i < cil.getPanelsN().size(); i++) {
                String mod = cil.getPanelsN().get(i).getButSel().getText();
                int idIc = cil.getPanelsN().get(i).getIc().getId();
                if (mod.equals("QUITAR")) {
                    double oldPrice = cil.getPanelsN().get(i).getIc().getPrice().get(0);
                    double newPrice = oldPrice * pc / 100;
                    double rou = newPrice % round;
                    rou = round - rou;
                    newPrice = newPrice + rou;
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                    DecimalFormat df = new DecimalFormat("#.00", symbols);
                    String st = df.format(newPrice);
                    newPrice = Double.parseDouble(st);
                    ArrayList<Double> prices = new ArrayList<>();
                    prices.add(newPrice);
                    prices.add(oldPrice);

                    if (newPrice <= cil.getPanelsN().get(i).getIc().getCost()) {
                        confirm1 = utiliMsg.errorPriceCost();
                    }

                    ArrayList<String> tabIds = new ArrayList<>();
                    ArrayList<String> tabIdsIc = new ArrayList<>();
                    tabIds = daoT.getActiveIds();
                    for (int x = 0; x < tabIds.size(); x++) {
                        ArrayList<Integer> arrayTabIdsIc = daoT.activeTabIcMod(idIc, tabIds.get(x));
                        if (arrayTabIdsIc.size() > 0) {
                            tabIdsIc.add(tabIds.get(x));
                        }
                    }

                    if (tabIdsIc.size() > 0) {
                        if (counter1 < 1) {
                            confirm1 = utiliMsg.optionConfirmarCambioPrAct();
                            counter1 += 1;
                        }
                    }

                    if (confirm1) {
                        if (cil.getManager().getSalon() != null) {
                            if (counter2 < 1) {
                                confirm2 = utiliMsg.optionConfirmarUpdateActiveTabs();
                                counter2 += 1;
                            }
                            if (confirm2) {
                                ArrayList<String> modTabsNew = new ArrayList<>();
                                for (int y = 0; y < tabIdsIc.size(); y++) {
                                    modTabsNew.add(tabIdsIc.get(y));
                                    modTabsNew.add(idIc + "");
                                }
                                ConfigActual cfgAct = daoC.askConfigActual();
                                ArrayList<String> modTabIds = cfgAct.getArrayUnModTabs();
                                for (int y = 0; y < modTabsNew.size(); y++) {
                                    modTabIds.add(modTabsNew.get(y));
                                }
                                daoC.updateCfgActModTabs(modTabIds);

                                daoI.updateItemPrice(idIc, prices);

                                cil.getManager().getSalon().dispose();
                                cil.getManager().setSalon(null);
                                if (counter3 < 1) {
                                    utiliMsg.successUpdatePriceItemActive(cil);
                                    counter3 += 1;
                                }
                            }
                        } else {
                            daoI.updateItemPrice(idIc, prices);
                        }

                    }
                }
            }
        }
        reset(cil);
    }
    
    
    public void changeSaver(ConfigItemList cil) throws Exception {
        for (int i = 0; i < cil.getPanelsN().size(); i++) {
            boolean error = false;
            String mod = cil.getPanelsN().get(i).getButSel().getText();
            String cat = (String) cil.getPanelsN().get(i).getTextF2().getSelectedItem();
            int stock = 0;
            double cost = 0;
            double price = 0;
            if (mod.equals("QUITAR")) {
                try {
                    stock = (Integer) cil.getPanelsN().get(i).getTextF3().getValue();
                    String co = cil.getPanelsN().get(i).getTextF4().getText();
                    if (co.length() > 12) {
                        error = true;
                        utiliMsg.errorCantCharNum();
                    }

                    if (co.equals("")) {
                        cost = 0;
                    } else {
                        cost = utili.toNumberD(co);
                    }

                    String pri = cil.getPanelsN().get(i).getTextF5().getText();
                    if (pri.length() > 12) {
                        error = true;
                        utiliMsg.errorCantCharNum();
                    }

                    if (pri.equals("")) {
                        boolean confirm = utiliMsg.optionConfirmErrorPriceNull();
                        if (confirm) {
                            pri = "0";
                            price = utili.toNumberD(pri);
                        } else {
                            error = true;
                        }
                    } else {
                        price = utili.toNumberD(pri);
                    }

                } catch (NumberFormatException e) {
                    utiliMsg.errorNumerico();
                    error = true;

                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                if (error == false) {
                    int id = cil.getPanelsN().get(i).getIc().getId();
                    Itemcard ic = cil.getPanelsN().get(i).getIc();
                    daoI.updateItemCategory(id, cat);
                    daoI.updateItemStock(id, stock);
                    daoI.updateItemCost(id, cost);

                    ArrayList<Double> prices = new ArrayList<>();
                    prices.add(price);
                    prices.add(ic.getPrice().get(0));

                    boolean confirm1 = true;
                    if (price <= cost) {
                        confirm1 = utiliMsg.optionConfirmLowerPrice();
                    }

                    if (confirm1) {
                        ArrayList<String> tabIds = new ArrayList<>();
                        ArrayList<String> tabIdsIc = new ArrayList<>();
                        tabIds = daoT.getActiveIds();
                        for (int x = 0; x < tabIds.size(); x++) {
                            ArrayList<Integer> arrayTabIdsIc = daoT.activeTabIcMod(ic.getId(), tabIds.get(x));
                            if (arrayTabIdsIc.size() > 0) {
                                tabIdsIc.add(tabIds.get(x));
                            }
                        }

                        boolean confirm2 = true;
                        if (tabIdsIc.size() > 0) {
                            confirm2 = utiliMsg.optionConfirmarCambioPrAct();
                        }

                        if (confirm2) {
                            if (cil.getManager().getSalon() != null) {
                                boolean confirm3 = utiliMsg.optionConfirmarUpdateActiveTabs();
                                if (confirm3) {
                                    ArrayList<String> modTabsNew = new ArrayList<>();
                                    for (int y = 0; y < tabIdsIc.size(); y++) {
                                        modTabsNew.add(tabIdsIc.get(y));
                                        modTabsNew.add(ic.getId() + "");
                                    }
                                    ConfigActual cfgAct = daoC.askConfigActual();
                                    ArrayList<String> modTabIds = cfgAct.getArrayUnModTabs();
                                    for (int y = 0; y < modTabsNew.size(); y++) {
                                        modTabIds.add(modTabsNew.get(y));
                                    }

                                    daoC.updateCfgActModTabs(modTabIds);
                                    daoI.modificarItem(id, ic.getName(), cat, ic.getDescription(), cost, prices, stock, ic.isActiveTip());
                                    cil.getManager().getSalon().dispose();
                                    cil.getManager().setSalon(null);
                                    utiliMsg.successUpdatePriceItemActive(cil);
                                }
                            } else {
                                daoI.modificarItem(id, ic.getName(), cat, ic.getDescription(), cost, prices, stock, ic.isActiveTip());
                            }
                        }
                    }
                }
            }
        }
        reset(cil);
    }
    
    
    public void reset(ConfigItemList cil) throws Exception {
        cil.setItems(daoI.listarItemsCard(true));
        utiliMsg.successMod(cil);
        cil.getCheckBox().setVisible(false);
        cil.getCheckBox().setSelected(false);
        cil.getButEnab().setText("HABILITAR");
        cil.getComboCat().setSelectedItem("TODOS");
        cil.getComboCat2().setSelectedItem("");
        cil.getComboRound().setSelectedItem("1");
        cil.getSpinnerPC().setValue(0);
        select("TODOS", false, cil);
    }
}