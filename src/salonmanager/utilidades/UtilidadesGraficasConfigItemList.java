package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
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
import salonmanager.ConfigItemList;
import salonmanager.ItemcardInn;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.servicios.ServiceConfigItemList;

public class UtilidadesGraficasConfigItemList {

    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice().getDefaultConfiguration());
    int taskBarHeight = insets.bottom;
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - taskBarHeight;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    DAOItemcard daoI = new DAOItemcard();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceConfigItemList sCIL = new ServiceConfigItemList();
    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    Color white = new Color(255, 255, 255);

    public JPanel panelListBacker(ConfigItemList cil) {
        JPanel panelList = new JPanel(null);
        panelList.setBounds(anchoUnit, altoUnit * 5, anchoUnit * 103, altoUnit * 89);
        panelList.setBackground(bluLg);

        JLabel labelCombo = utiliGraf.labelTitleBacker1("Seleccione Items por categoría");
        labelCombo.setBounds(anchoUnit, altoUnit, anchoUnit * 24, altoUnit * 4);
        panelList.add(labelCombo);

        cil.setComboCat(new JComboBox());
        cil.getComboCat().setBounds(anchoUnit * 25, altoUnit, anchoUnit * 10, altoUnit * 4);
        cil.getComboCat().setFont(cil.getNewFont());
        ArrayList<String> catPlus2 = new ArrayList<String>(cil.getCategories());
        catPlus2.add("TODOS");
        cil.getComboCat().setModel(utili.categoryComboModelReturn(catPlus2));
        cil.getComboCat().setSelectedItem("TODOS");
        panelList.add(cil.getComboCat());

        JButtonMetalBlu butSel = utiliGraf.button2("Seleccionar", anchoUnit * 36, altoUnit, anchoUnit * 12);
        butSel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    cil.setCat((String) cil.getComboCat().getSelectedItem());
                    sCIL.select(cil.getCat(), false, cil);
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelList.add(butSel);

        cil.getPanelPanel().setLayout(null);
        cil.getPanelPanel().setBackground(bluLg);
        int h = 1 + 6 * cil.getItems().size();
        if (h <= 68) {
            cil.getPanelPanel().setPreferredSize(new Dimension(anchoUnit * 78, altoUnit * 68));
        } else {
            cil.getPanelPanel().setPreferredSize(new Dimension(anchoUnit * 78, altoUnit * h));
        }

        JScrollPane scrollPane = new JScrollPane(cil.getPanelPanel());
        scrollPane.setBounds(anchoUnit, altoUnit * 6, anchoUnit * 80, altoUnit * 74);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelList.add(scrollPane);

        return panelList;
    }

    public JPanel panelLatBacker(ConfigItemList cil) {
        JPanel panelLat = new JPanel(null);
        panelLat.setBounds(anchoUnit * 82, altoUnit * 6, anchoUnit * 20, altoUnit * 74);
        panelLat.setBackground(bluSt);

        JLabel labelLat = utiliGraf.labelTitleBacker1W("Modificaciones Globales");
        labelLat.setBounds(anchoUnit, altoUnit, anchoUnit * 20, altoUnit * 5);
        panelLat.add(labelLat);

        cil.setButEnab(utiliGraf.button1("HABILITAR", anchoUnit * 2, altoUnit * 8, anchoUnit * 16));
        cil.getButEnab().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String st = cil.getButEnab().getText();
                    enablePanels(st, cil);
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelLat.add(cil.getButEnab());

        cil.setCheckBox(new JCheckBox("Seleccionar toda la lista"));
        cil.getCheckBox().setBackground(bluSt);
        cil.getCheckBox().setBorder(null);
        cil.getCheckBox().setForeground(white);
        cil.getCheckBox().setBounds(anchoUnit * 2, altoUnit * 16, anchoUnit * 15, altoUnit * 3);
        cil.getCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cil.getCheckBox().isSelected()) {
                    enableSelBT(true, cil);
                } else {
                    enableSelBT(false, cil);
                }
            }
        });
        cil.getCheckBox().setVisible(false);
        panelLat.add(cil.getCheckBox());

        return panelLat;
    }

    private void enablePanels(String st, ConfigItemList cil) {
        if (st.equals("HABILITAR")) {
            cil.getCheckBox().setVisible(true);
            cil.getButEnab().setText("DESHABILITAR");
        } else {
            cil.getCheckBox().setVisible(false);
            cil.getButEnab().setText("HABILITAR");
        }
        sCIL.select(cil.getCat(), true, cil);
    }

    private void enableSelBT(boolean b, ConfigItemList cil) {
        for (int i = 0; i < cil.getPanelsN().size(); i++) {
            cil.getPanelsN().get(i).selectPNB(b);
        }
    }

    public JPanel panelSelCatBacker(ConfigItemList cil) {
        JPanel panelSelCat = new JPanel(null);
        panelSelCat.setBounds(anchoUnit, altoUnit * 20, anchoUnit * 18, altoUnit * 23);
        panelSelCat.setBackground(bluLg);

        JLabel labelModCat = utiliGraf.labelTitleBacker2("<html>Modificar Categoría de los<br>elementos seleccionados </html>");
        labelModCat.setBounds(anchoUnit, altoUnit, anchoUnit * 18, altoUnit * 5);
        panelSelCat.add(labelModCat);

        cil.setComboCat2(new JComboBox());
        cil.getComboCat2().setBounds(anchoUnit * 3, altoUnit * 9, anchoUnit * 12, altoUnit * 5);
        cil.getComboCat2().setFont(cil.getNewFont());
        cil.getComboCat2().setModel(utili.categoryComboModelReturnWNull(cil.getCategories()));
        cil.getComboCat2().setSelectedItem("");
        panelSelCat.add(cil.getComboCat2());

        cil.setButSelCat2(utiliGraf.button2("MODIFICAR CATEGORÍA", anchoUnit * 2, altoUnit * 17, anchoUnit * 14));
        cil.getButSelCat2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (cil.getButEnab().getText().equals("DESHABILITAR")) {
                        boolean confirm = utiliMsg.cargaConfirmarCambioCat();
                        if (confirm == true) {
                            boolean pMod = modifyPnls(cil);
                            if (pMod) {
                                String cat1 = (String) cil.getComboCat2().getSelectedItem();
                                if (!cat1.equals("")) {
                                    sCIL.updateCat(cat1, cil);
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
        panelSelCat.add(cil.getButSelCat2());
        return panelSelCat;
    }
    
 
    private boolean modifyPnls(ConfigItemList cil) {
        boolean mod = false;
        for (int i = 0; i < cil.getPanelsN().size(); i++) {
            String but = cil.getPanelsN().get(i).getButSel().getText();
            if (but.equals("QUITAR")) {
                mod = true;
                break;
            }
        }
        return mod;
    }

    public JPanel panelPanelModPri(ConfigItemList cil) {
        JPanel panelModPri = new JPanel(null);
        panelModPri.setBounds(anchoUnit, altoUnit * 45, anchoUnit * 18, altoUnit * 27);
        panelModPri.setBackground(bluLg);

        JLabel labelModPri = utiliGraf.labelTitleBacker2("<html>Modificar porcentualmente el precio de los items seleccionados </html>");
        labelModPri.setBounds(anchoUnit, altoUnit, anchoUnit * 17, altoUnit * 8);
        panelModPri.add(labelModPri);

        JLabel labelPC = utiliGraf.labelTitleBacker3("<html>Elija porcentaje (%)</html>");
        labelPC.setBounds(anchoUnit, altoUnit * 10, anchoUnit * 12, altoUnit * 4);
        panelModPri.add(labelPC);

        cil.setSpinnerPC(new JSpinner());
        SpinnerModel model = new SpinnerNumberModel(1, -100, 100, 1);
        cil.getSpinnerPC().setModel(model);
        cil.getSpinnerPC().setFont(cil.getFont());
        cil.getSpinnerPC().setBounds(anchoUnit * 12, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        cil.getSpinnerPC().setValue(0);
        panelModPri.add(cil.getSpinnerPC());

        JLabel labelRound = utiliGraf.labelTitleBacker3("<html>Elija redondeo  ($)</html>");
        labelRound.setBounds(anchoUnit, altoUnit * 15, anchoUnit * 12, altoUnit * 4);
        panelModPri.add(labelRound);

        cil.getComboRound().setBounds(anchoUnit * 12, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        cil.getComboRound().setFont(cil.getFont());
        ArrayList<String> rounds = sCIL.listRounds();
        DefaultComboBoxModel<String> modeloCombo1 = new DefaultComboBoxModel<String>();
        for (String i : rounds) {
            modeloCombo1.addElement(i);
        }
        cil.getComboRound().setModel(modeloCombo1);
        cil.getComboRound().setSelectedItem("1");
        panelModPri.add(cil.getComboRound());

        cil.setButSelPri(utiliGraf.button2("MODIFICAR PRECIOS", anchoUnit * 2, altoUnit * 21, anchoUnit * 14));
        cil.getButSelPri().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (cil.getButEnab().getText().equals("DESHABILITAR")) {
                        boolean confirm = utiliMsg.cargaConfirmarCambioPrice();
                        if (confirm == true) {
                            boolean pMod = modifyPnls(cil);
                            if (pMod) {
                                int p = (int) cil.getSpinnerPC().getValue();
                                String r = (String) cil.getComboRound().getSelectedItem();
                                sCIL.updatePrice(p, r, cil);
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
        panelModPri.add(cil.getButSelPri());
        return panelModPri;
    }   

    public JButtonMetalBlu butChangeSaver(ConfigItemList cil) {
        JButtonMetalBlu butConfirm = utiliGraf.button1("CONFIRMAR CAMBIOS", anchoUnit * 40, altoUnit * 81, anchoUnit * 18);
        butConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    boolean pMod = modifyPnls(cil);
                    if (pMod) {
                        sCIL.changeSaver(cil);
                    } else {
                        utiliMsg.errorPnlsMod();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return butConfirm;
        
    }
    
    
    
}