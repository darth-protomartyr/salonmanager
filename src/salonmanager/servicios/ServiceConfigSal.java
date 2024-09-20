/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.servicios;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ListModel;
import salonmanager.ConfigSalonFrame;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.PanelNestedSpace;
import salonmanager.persistencia.DAOConfig;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServiceConfigSal {

    Utilidades utili = new Utilidades();
    DAOConfig daoC = new DAOConfig();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void selSpace(String selectedValue, int i, ConfigSalonFrame cSF) {
        if (i == 1) {
            cSF.getSpacesSel().add(selectedValue);
        }
        ListModel modeloLista1 = utili.spacesListModelReturnMono(cSF.getSpacesSel());
        cSF.getListSpacesSel().setModel(modeloLista1);

        Iterator<String> iterador = cSF.getSpaces().iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(cSF.getSpaces());
        cSF.getListSpaces().setModel(modeloLista2);

        PanelNestedSpace pn = new PanelNestedSpace(selectedValue, cSF.getPanels().size());

        panelManager(pn, selectedValue, cSF);
    }

    public void unSelSpace(String selectedValue, ConfigSalonFrame cSF) {
        cSF.getSpaces().add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(cSF.getSpaces());
        cSF.getListSpaces().setModel(modeloLista1);
        Iterator<String> iterador = cSF.getSpacesSel().iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(cSF.getSpacesSel());
        cSF.getListSpacesSel().setModel(modeloLista2);

        panelManager(null, selectedValue, cSF);
    }

    private void panelManager(PanelNestedSpace pn, String st, ConfigSalonFrame cSF) {
        cSF.getPanelPanel().removeAll();
        if (pn != null) {
            cSF.getPanels().add(pn);
        } else {
            Iterator<PanelNestedSpace> iterador = cSF.getPanels().iterator();
            while (iterador.hasNext()) {
                PanelNestedSpace p = iterador.next();
                if (p.getSt().equals(st)) {
                    iterador.remove();
                }
            }

            ArrayList<PanelNestedSpace> panels2 = new ArrayList<>();
            int counter = 0;
            for (int i = 0; i < cSF.getPanels().size(); i++) {
                PanelNestedSpace pan = new PanelNestedSpace(cSF.getPanels().get(i).getSt(), counter);
                panels2.add(pan);
                counter += 1;
            }
            cSF.setPanels(panels2);

        }

        int h = 0;
        h = 1 + 8 * cSF.getPanels().size();
        if (h < 20) {
            h = 20;
        }

        cSF.getPanelPanel().setPreferredSize(new Dimension(anchoUnit * 30, altoUnit * h));

        for (int i = 0; i < cSF.getPanels().size(); i++) {
            cSF.getPanelPanel().add(cSF.getPanels().get(i));
        }

        cSF.getPanelPanel().revalidate();
        cSF.getPanelPanel().repaint();
    }

    public void createSpace(ConfigSalonFrame cSF) throws Exception {
        String space = utiliMsg.requestNewString(1, 15);
        cSF.getSpaces().add(space);
        daoC.saveSpace(space);
        String cha = space.substring(0, 1);
        cSF.getCharsDB().add(cha);
        daoC.saveChar(cha);
        cSF.setSpaces(daoC.askSpaces());
        cSF.getListSpaces().setModel(utili.spacesListModelReturnMono(cSF.getSpaces()));
    }

    public void selCategory(String selectedValue, int i, ConfigSalonFrame cSF) {
        if (i == 1) {
            cSF.getCategoriesSel().add(selectedValue);
        }
        ListModel modeloLista1 = utili.spacesListModelReturnMono(cSF.getCategoriesSel());
        cSF.getListCatSel().setModel(modeloLista1);

        Iterator<String> iterador = cSF.getCategories().iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(cSF.getCategories());
        cSF.getListCat().setModel(modeloLista2);

        data2Updater(cSF);
    }

    public void unSelCategory(String selectedValue, ConfigSalonFrame cSF) {
        selectedValue = (String) cSF.getListCatSel().getSelectedValue();
        cSF.getCategories().add(selectedValue);
        ListModel modeloLista1 = utili.spacesListModelReturnMono(cSF.getCategories());
        cSF.getListCat().setModel(modeloLista1);
        Iterator<String> iterador = cSF.getCategoriesSel().iterator();
        while (iterador.hasNext()) {
            String st = iterador.next();
            if (st.equals(selectedValue)) {
                iterador.remove();
            }
        }
        ListModel modeloLista2 = utili.spacesListModelReturnMono(cSF.getCategoriesSel());
        cSF.getListCatSel().setModel(modeloLista2);
        data2Updater(cSF);
    }

    public void createCategory(ConfigSalonFrame cSF) throws Exception {
        String category = utiliMsg.requestNewString(2, 10);
        cSF.getCategories().add(category);
        daoC.saveCategory(category);
        cSF.setCategories(daoC.askCategories());
        cSF.getListCat().setModel(utili.spacesListModelReturnMono(cSF.getCategories()));
    }

    public void createConfig(ConfigSalonFrame cSF) throws Exception {
        int totalTab = 0;
        int tipPc = (int) cSF.getSpinnerTip().getValue();
        for (int i = 0; i < cSF.getQuants().size(); i++) {
            totalTab += cSF.getQuants().get(i);
        }
        daoC.saveConfigGeneral(totalTab, cSF.getQuants(), cSF.getSpacesSel(), cSF.getCharsSel(), cSF.getCategoriesSel(), tipPc, true, true);
        cSF.getSm().frameCloser();
        System.exit(0);
    }

    private void data1Updater(ConfigSalonFrame cSF) {
        String st = "<html><b>SECTORES</b>: ";
        for (int i = 0; i < cSF.getSpacesSel().size(); i++) {
            if (i == cSF.getSpacesSel().size() - 1) {
                st += cSF.getSpacesSel().get(i) + "(" + cSF.getQuants().get(i) + ")";
            } else {
                st += cSF.getSpacesSel().get(i) + "(" + cSF.getQuants().get(i) + ") - ";
            }
        }
        st = st + "</html>";
        cSF.getLabelData1().setText(st);
    }

    private void data2Updater(ConfigSalonFrame cSF) {
        String st = "<html><b>CATEGORÍAS</b>: ";
        for (int i = 0; i < cSF.getCategoriesSel().size(); i++) {
            if (i == cSF.getCategoriesSel().size() - 1) {
                st += cSF.getCategoriesSel().get(i);
            } else {
                st += cSF.getCategoriesSel().get(i) + " - ";
            }
        }
        st = st + "</html>";
        cSF.getLabelData2().setText(st);
    }

    public void confirmTables(ConfigSalonFrame cSF) throws Exception {
        ArrayList<Integer> tabsQ = new ArrayList<>();
        cSF.setSpacesDB(daoC.askSpaces());
        boolean zero = false;
        for (int i = 0; i < cSF.getPanels().size(); i++) {
            PanelNestedSpace paN = cSF.getPanels().get(i);
            ButtonGroup group = paN.getGroup();
            int m = 0;
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    String selection = button.getText();
                    if (selection.equals("12 Mesas")) {
                        m = 12;
                    } else if (selection.equals("24 Mesas")) {
                        m = 24;
                    } else if (selection.equals("35 Mesas")) {
                        m = 35;
                    } else if (selection.equals("48 Mesas")) {
                        m = 48;
                    }
                    break;
                }
            }
            if (m == 0) {
                zero = true;
            } else {
                tabsQ.add(m);
            }
        }

        if (zero == false) {
            cSF.setQuants(tabsQ);
            for (int i = 0; i < cSF.getSpacesSel().size(); i++) {
                String st1 = cSF.getSpacesSel().get(i);
                for (int j = 0; j < cSF.getSpacesDB().size(); j++) {
                    String st2 = cSF.getSpacesDB().get(j);
                    if (st1.equals(st2)) {
                        cSF.getCharsSel().add(cSF.getCharsDB().get(j));
                    }
                }
            }

            for (int i = 0; i < cSF.getPanels().size(); i++) {
                Enumeration<AbstractButton> buttons = cSF.getPanels().get(i).getGroup().getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    button.setEnabled(false);
                }
            }

            data1Updater(cSF);
            cSF.setSelSectors(true);
            cSF.getButConfirm1().setEnabled(false);
            cSF.getListSpacesSel().setEnabled(false);
            cSF.getListSpaces().setEnabled(false);
            cSF.getButAddSpace().setEnabled(false);
        } else {
            utiliMsg.errorNullTabsQ();
            zero = true;
        }
    }

    public void resetValues(ConfigSalonFrame cSF) throws Exception {
        cSF.setCategoriesDB(daoC.askCategories());
        cSF.setCategories(cSF.getCategoriesDB());
        cSF.setSpacesDB(daoC.askSpaces());
        cSF.setSpaces(cSF.getSpacesDB());
        cSF.setCategoriesSel(new ArrayList<>());
        cSF.setSpacesSel(new ArrayList<>());
        cSF.setCharsSel(new ArrayList<>());
        cSF.setQuants(new ArrayList<>());
        cSF.getListSpaces().setModel(utili.spacesListModelReturnMono(cSF.getSpaces()));
        cSF.getListSpacesSel().setModel(utili.spacesListModelReturnMono(cSF.getSpacesSel()));
        cSF.getListCat().setModel(utili.spacesListModelReturnMono(cSF.getCategories()));
        cSF.getListCatSel().setModel(utili.spacesListModelReturnMono(cSF.getCategoriesSel()));
        cSF.getSpinnerTip().setValue(10);
        data1Updater(cSF);
        data2Updater(cSF);
        cSF.setPanels(new ArrayList<>());
        cSF.getButConfirm1().setEnabled(true);
        cSF.getButAddSpace().setEnabled(true);
        cSF.setSelSectors(false);
        cSF.getListSpacesSel().setEnabled(true);
        cSF.getListSpaces().setEnabled(true);
        cSF.getPanelPanel().removeAll();
        cSF.getPanelPanel().revalidate();
        cSF.getPanelPanel().repaint();
    }

    public void updateValues(ConfigSalonFrame cSF) throws Exception {
        cSF.setCategoriesSel(cSF.getCfgGen().getTableItemCategories());
        cSF.setSpacesSel(cSF.getCfgGen().getTablePan());
        cSF.setQuants(cSF.getCfgGen().getTableNum());

        for (int i = 0; i < cSF.getSpacesSel().size(); i++) {
            selSpace(cSF.getSpacesSel().get(i), 0, cSF);
        }

        for (int i = 0; i < cSF.getCategoriesSel().size(); i++) {
            selCategory(cSF.getCategoriesSel().get(i), 0, cSF);
        }

        for (int i = 0; i < cSF.getQuants().size(); i++) {
            PanelNestedSpace paN = cSF.getPanels().get(i);
            ButtonGroup group = paN.getGroup();
            int m = cSF.getQuants().get(i);
            String tabs = "";
            if (m == 12) {
                tabs = "12 Mesas";
            } else if (m == 24) {
                tabs = "24 Mesas";
            } else if (m == 35) {
                tabs = "35 Mesas";
            } else {
                tabs = "48 Mesas";
            }

            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                String selection = button.getText();
                if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                } else if (selection.equals(tabs)) {
                    button.setSelected(true);
                    break;
                }
            }
        }

        cSF.getSpinnerTip().setValue(cSF.getCfgGen().getTipPc());

        data1Updater(cSF);
        data2Updater(cSF);
    }
}
