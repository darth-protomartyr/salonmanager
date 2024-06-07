package salonmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNestedItem;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigItemList extends FrameFull {

    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    Color white = new Color(255, 255, 255);

    ArrayList<Itemcard> items = new ArrayList<>();
    ArrayList<PanelNestedItem> panelsN = new ArrayList<>();
    DAOItemcard daoI = new DAOItemcard();
    DAOConfig daoC = new DAOConfig();
    SalonManager sm = new SalonManager();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
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
    
    public ConfigItemList() throws Exception {
        sm.addFrame(this);
        Font newFont = new Font("Arial", Font.PLAIN, 16);

        setTitle("Modificar Items de la carta en Lista");
        items = daoI.listarItemsCard();
        ConfigGeneral cfgGen = daoC.askConfigGeneral();
        if (cfgGen.isActiveConfig() == false) {
            cfgGen = utili.cfgBacker();
        }
        categories = cfgGen.getTableItemCategories();
        cat = "TODOS";
        PanelPpal panelPpal = new PanelPpal(this);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Lista de Items");
        panelLabel.add(labelTit);

        JPanel panelList = new JPanel(null);
        panelList.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 103, altoUnit * 89);
        panelList.setBackground(bluLg);
        panelPpal.add(panelList);

        JLabel labelCombo = utiliGraf.labelTitleBacker1("Seleccione Items por categoría");
        labelCombo.setBounds(anchoUnit, altoUnit, anchoUnit * 24, altoUnit * 4);
        panelList.add(labelCombo);

        comboCat = new JComboBox();
        comboCat.setBounds(anchoUnit * 25, altoUnit, anchoUnit * 10, altoUnit * 4);
        comboCat.setFont(newFont);
        ArrayList<String> catPlus2 = new ArrayList<String>(categories);
        catPlus2.add("TODOS");
        comboCat.setModel(utili.categoryComboModelReturn(catPlus2));
        comboCat.setSelectedItem("TODOS");
        panelList.add(comboCat);

        JButtonMetalBlu butSel = utiliGraf.button2("Seleccionar", anchoUnit * 36, altoUnit, anchoUnit * 12);
        butSel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    cat = (String) comboCat.getSelectedItem();
                    select(cat, false);
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelList.add(butSel);

        panelPanel.setLayout(null);
        panelPanel.setBackground(bluLg);
        int h = 1 + 6 * items.size();
        if (h <= 68) {
            panelPanel.setPreferredSize(new Dimension(anchoUnit * 78, altoUnit * 68));
        } else {
            panelPanel.setPreferredSize(new Dimension(anchoUnit * 78, altoUnit * h));
        }

        JScrollPane scrollPane = new JScrollPane(panelPanel);
        scrollPane.setBounds(anchoUnit, altoUnit * 6, anchoUnit * 80, altoUnit * 74);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelList.add(scrollPane);

        JPanel panelLat = new JPanel(null);
        panelLat.setBounds(anchoUnit * 82, altoUnit * 6, anchoUnit * 20, altoUnit * 74);
        panelLat.setBackground(bluSt);
        panelList.add(panelLat);

        JLabel labelLat = utiliGraf.labelTitleBacker1W("Modificaciones Globales");
        labelLat.setBounds(anchoUnit, altoUnit, anchoUnit * 20, altoUnit * 5);
        panelLat.add(labelLat);

        butEnab = utiliGraf.button1("HABILITAR", anchoUnit * 2, altoUnit * 8, anchoUnit * 16);
        butEnab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String st = butEnab.getText();
                    enablePanels(st);
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelLat.add(butEnab);

        checkBox = new JCheckBox("Seleccionar toda la lista");
        checkBox.setBackground(bluSt);
        checkBox.setBorder(null);
        checkBox.setForeground(white);
        checkBox.setBounds(anchoUnit * 2, altoUnit * 16, anchoUnit * 15, altoUnit * 3);
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBox.isSelected()) {
                    enableSelBT(true);
                } else {
                    enableSelBT(false);
                }
            }
        });
        checkBox.setVisible(false);
        panelLat.add(checkBox);

        JPanel panelSelCat = new JPanel(null);
        panelSelCat.setBounds(anchoUnit, altoUnit * 20, anchoUnit * 18, altoUnit * 23);
        panelSelCat.setBackground(bluLg);
        panelLat.add(panelSelCat);

        JLabel labelModCat = utiliGraf.labelTitleBacker2("<html>Modificar Categoría de los<br>elementos seleccionados </html>");
        labelModCat.setBounds(anchoUnit, altoUnit, anchoUnit * 18, altoUnit * 5);
        panelSelCat.add(labelModCat);

        comboCat2 = new JComboBox();
        comboCat2.setBounds(anchoUnit * 3, altoUnit * 9, anchoUnit * 12, altoUnit * 5);
        comboCat2.setFont(newFont);
        comboCat2.setModel(utili.categoryComboModelReturnWNull(categories));
        comboCat2.setSelectedItem("");
        panelSelCat.add(comboCat2);

        butSelCat2 = utiliGraf.button2("MODIFICAR CATEGORÍA", anchoUnit * 2, altoUnit * 17, anchoUnit * 14);
        butSelCat2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (butEnab.getText().equals("DESHABILITAR")) {
                        boolean confirm = utiliMsg.cargaConfirmarCambioCat();
                        if (confirm == true) {
                            boolean pMod = modifyPnls();
                            if (pMod) {
                                String cat1 = (String) comboCat2.getSelectedItem();
                                if (!cat1.equals("")) {
                                    updateCat(cat1);
                                } else {
                                    utiliMsg.errorCatSelection();
                                }
                            } else {
                                utiliMsg.errorPnlsMod();
                            }
                        }
                    } else {
                        utiliMsg.errorModDisabled();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSelCat.add(butSelCat2);

        JPanel panelModPri = new JPanel(null);
        panelModPri.setBounds(anchoUnit, altoUnit * 45, anchoUnit * 18, altoUnit * 27);
        panelModPri.setBackground(bluLg);
        panelLat.add(panelModPri);

        JLabel labelModPri = utiliGraf.labelTitleBacker2("<html>Modificar porcentualmente el precio de los items seleccionados </html>");
        labelModPri.setBounds(anchoUnit, altoUnit, anchoUnit * 17, altoUnit * 8);
        panelModPri.add(labelModPri);

        JLabel labelPC = utiliGraf.labelTitleBacker3("<html>Elija porcentaje (%)</html>");
        labelPC.setBounds(anchoUnit, altoUnit * 10, anchoUnit * 12, altoUnit * 4);
        panelModPri.add(labelPC);

        Font font = new Font("Arial", Font.BOLD, 18);

        spinnerPC = new JSpinner();
        SpinnerModel model = new SpinnerNumberModel(1, -100, 100, 1);
        spinnerPC.setModel(model);
        spinnerPC.setFont(font);
        spinnerPC.setBounds(anchoUnit * 12, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        spinnerPC.setValue(0);
        panelModPri.add(spinnerPC);

        JLabel labelRound = utiliGraf.labelTitleBacker3("<html>Elija redondeo  ($)</html>");
        labelRound.setBounds(anchoUnit, altoUnit * 15, anchoUnit * 12, altoUnit * 4);
        panelModPri.add(labelRound);

        JComboBox comboRound = new JComboBox();

        comboRound.setBounds(anchoUnit * 12, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        comboRound.setFont(font);
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
        DefaultComboBoxModel<String> modeloCombo1 = new DefaultComboBoxModel<String>();
        for (String i : rounds) {
            modeloCombo1.addElement(i);
        }
        comboRound.setModel(modeloCombo1);
        comboRound.setSelectedItem("1");
        panelModPri.add(comboRound);

        butSelPri = utiliGraf.button2("MODIFICAR PRECIOS", anchoUnit * 2, altoUnit * 21, anchoUnit * 14);
        butSelPri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (butEnab.getText().equals("DESHABILITAR")) {
                        boolean confirm = utiliMsg.cargaConfirmarCambioPrice();
                        if (confirm == true) {
                            boolean pMod = modifyPnls();
                            if (pMod) {
                                int p = (int) spinnerPC.getValue();
                                String r = (String) comboRound.getSelectedItem();
                                updatePrice(p, r);
                            } else {
                                utiliMsg.errorPnlsMod();
                            }
                        }
                    } else {
                        utiliMsg.errorModDisabled();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelModPri.add(butSelPri);

        select(cat, false);

        JButtonMetalBlu butConfirm = utiliGraf.button1("CONFIRMAR CAMBIOS", anchoUnit * 40, altoUnit * 81, anchoUnit * 18);
        butConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    boolean pMod = modifyPnls();
                    if (pMod) {
                        changeSaver();
                    } else {
                        utiliMsg.errorPnlsMod();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelList.add(butConfirm);

        JButtonMetalBlu butReset = utiliGraf.button2("RESETEAR", anchoUnit * 94, altoUnit * 84, anchoUnit * 8);
        butReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    cat = "TODOS";
                    select(cat, false);
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

    private void select(String sel, boolean type) {
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
                    boolean vis = pni.getButSel().isVisible();
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

            panelPanel.revalidate();
            panelPanel.repaint();
        } else {
            utiliMsg.errorCat();
        }
    }

    private void enablePanels(String st) {
        if (st.equals("HABILITAR")) {
            checkBox.setVisible(true);
            butEnab.setText("DESHABILITAR");
        } else {
            checkBox.setVisible(false);
            butEnab.setText("HABILITAR");
        }
        select(cat, true);
    }

    private void enableSelBT(boolean tf) {
        for (int i = 0; i < panelsN.size(); i++) {
            panelsN.get(i).selectPNB(tf);
        }
    }

    private void updateCat(String cat) throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < panelsN.size(); i++) {
            String mod = panelsN.get(i).getButSel().getText();
            if (mod.equals("QUITAR")) {
                String cat1 = panelsN.get(0).getIc().getCategory();
                if (!cat.equals(cat1)) {
                    int id = panelsN.get(i).getIc().getId();
                    daoI.updateItemCategory(id, cat);
                    ids.add(id);
                } else {
                    utiliMsg.errorCatEqual();
                }
            }
        }
        reset();
    }

    private void updatePrice(int p, String r) throws Exception {
        int pc = 100 + p;
        double round = 0;
        boolean error = false;
        try {
            round = utili.toNumberD(r);
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            for (int i = 0; i < panelsN.size(); i++) {
                String mod = panelsN.get(i).getButSel().getText();
                int id = panelsN.get(i).getIc().getId();
                if (mod.equals("QUITAR")) {
                    double price = panelsN.get(i).getIc().getPrice();
                    price = price * pc / 100;
                    double rou = price % round;
                    rou = round - rou;
                    price = price + rou;
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                    DecimalFormat df = new DecimalFormat("#.00", symbols);
                    String st  = df.format(price);
                    price = Double.parseDouble(st);
                    daoI.updateItemPrice(id, price);
                }
            }
            reset();
        }
    }

    private void changeSaver() throws Exception {
        for (int i = 0; i < panelsN.size(); i++) {
            boolean error = false;
            String mod = panelsN.get(i).getButSel().getText();
            String cat = (String) panelsN.get(i).getTextF2().getSelectedItem();
            int stock = 0;
            double cost = 0;
            double price = 0;
            if (mod.equals("QUITAR")) {
                try {
                    stock = (Integer) panelsN.get(i).getTextF3().getValue();
                    String co = panelsN.get(i).getTextF4().getText();
                    if (co.length() > 12) {
                        error = true;
                        utiliMsg.errorCantCharNum();
                    }

                    if (co.equals("")) {
                        cost = 0;
                    } else {
                        cost = utili.toNumberD(co);
                    }

                    String pri = panelsN.get(i).getTextF5().getText();
                    if (pri.length() > 12) {
                        error = true;
                        utiliMsg.errorCantCharNum();
                    }

                    if (pri.equals("")) {
                        boolean confirm = utiliMsg.cargaConfirmErrorPriceNull();
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
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (error == false) {
                    int id = panelsN.get(i).getIc().getId();
                    daoI.updateItemCategory(id, cat);
                    daoI.updateItemStock(id, stock);
                    daoI.updateItemCost(id, cost);
                    if (price <= cost) {
                        boolean confirm = utiliMsg.cargeConfirmLowerPrice();
                        if (confirm) {
                            daoI.updateItemPrice(id, price);
                        }
                    } else {
                        daoI.updateItemPrice(id, price);
                    }
                }
            }
        }
        reset();
    }
    
    private void reset() throws Exception {
        items = daoI.listarItemsCard();
        utiliMsg.cargaSuccesMod();
        checkBox.setVisible(false);
        checkBox.setSelected(false);
        butEnab.setText("HABILITAR");
        comboCat.setSelectedItem("TODOS");
        comboCat2.setSelectedItem("");
        comboRound.setSelectedItem("1");
        spinnerPC.setValue(0);
        select("TODOS", false);        
    }

    private boolean modifyPnls() {
        boolean mod = false;
        for (int i = 0; i < panelsN.size(); i++) {
            String but = panelsN.get(i).getButSel().getText();
            if (but.equals("QUITAR")) {
                mod = true;
                break;
            }
        }
        return mod;
    }
}