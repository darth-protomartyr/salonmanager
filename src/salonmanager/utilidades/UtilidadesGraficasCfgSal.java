package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import salonmanager.Admin;
import salonmanager.ConfigSalonFrame;
import salonmanager.SalonManager;
import salonmanager.entidades.graphics.FrameGeneral;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.servicios.ServiceConfigSal;

public class UtilidadesGraficasCfgSal {

    int anchoUnit = FrameGeneral.anchoUnit;
    int altoUnit = FrameGeneral.altoUnit;
    int anchoFrameThird = FrameThird.anchoFrameThird;
    DAOConfig daoC = new DAOConfig();
    ServiceConfigSal scs = new ServiceConfigSal();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    SalonManager sm = new SalonManager();

    public JPanel panelSpacesBacker(ConfigSalonFrame cSF) {
        JPanel panelSpaces = new JPanel(null);
        panelSpaces.setBounds(anchoUnit * 0, altoUnit * 5, anchoFrameThird, altoUnit * 14);
        panelSpaces.setBackground(bluSt);

        JLabel labelIndications1 = utiliGraf.labelTitleBacker2W("Seleccione el nombre de los sectores del local");
        labelIndications1.setBounds(anchoUnit * 3, altoUnit * 0, anchoUnit * 28, altoUnit * 3);
        panelSpaces.add(labelIndications1);

        JLabel labelItemsTP = utiliGraf.labelTitleBacker3W("Sectores a Seleccionar");
        labelItemsTP.setBounds(anchoUnit * 2, altoUnit * 3, anchoUnit * 12, altoUnit * 3);
        panelSpaces.add(labelItemsTP);

        cSF.getListSpaces().setModel(utili.spacesListModelReturnMono(cSF.getSpaces()));
        cSF.getListSpaces().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (cSF.getSpacesSel().size() < 8) {
                        String selectedValue = (String) cSF.getListSpaces().getSelectedValue();
                        scs.selSpace(selectedValue, 1, cSF);
                    } else {
                        utiliMsg.errorSpacesExcess();
                    }
                }
            }
        });
        JScrollPane jSSpaces = new JScrollPane(cSF.getListSpaces());
        jSSpaces.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 8, altoUnit * 8);
        panelSpaces.add(jSSpaces);

        JLabel labelItemsSel = utiliGraf.labelTitleBacker3W("Sectores Seleccionados");
        labelItemsSel.setBounds(anchoUnit * 15, altoUnit * 3, anchoUnit * 12, altoUnit * 3);
        panelSpaces.add(labelItemsSel);

        cSF.getListSpacesSel().setModel(utili.spacesListModelReturnMono(cSF.getSpacesSel()));
        cSF.getListSpacesSel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedValue = (String) cSF.getListSpacesSel().getSelectedValue();
                    scs.unSelSpace(selectedValue, cSF);
                }
            }
        });

        JScrollPane jSSelected = new JScrollPane(cSF.getListSpacesSel());
        jSSelected.setBounds(anchoUnit * 15, altoUnit * 6, anchoUnit * 8, altoUnit * 8);
        panelSpaces.add(jSSelected);

        cSF.setButAddSpace(utiliGraf.button3("Crear Espacio", anchoUnit * 24, altoUnit * 11, anchoUnit * 9));
        cSF.getButAddSpace().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    scs.createSpace(cSF);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSpaces.add(cSF.getButAddSpace());
        return panelSpaces;
    }

    public JPanel panelSpacesPanelBacker(ConfigSalonFrame cSF) {
        JPanel panelSpaces = new JPanel(null);
        panelSpaces.setBounds(anchoUnit * 0, altoUnit * 20, anchoFrameThird, altoUnit * 25);
        panelSpaces.setBackground(bluSt);

        JLabel labelIndications2 = utiliGraf.labelTitleBacker2W("Seleccione la cantidad de mesas por sector:");
        labelIndications2.setBounds(anchoUnit * 3, altoUnit * 0, anchoUnit * 28, altoUnit * 3);
        panelSpaces.add(labelIndications2);

        cSF.getPanelPanel().setLayout(null);
        cSF.getPanelPanel().setBackground(bluLg);
        cSF.getPanelPanel().setPreferredSize(new Dimension(anchoUnit * 30, altoUnit * 20));

        JScrollPane scrollPane = new JScrollPane(cSF.getPanelPanel());
        scrollPane.setBounds(anchoUnit * 1, altoUnit * 3, anchoUnit * 32, altoUnit * 17);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelSpaces.add(scrollPane);

        cSF.setButConfirm1(utiliGraf.button2("Confirmar Sectores", anchoUnit * 9, altoUnit * 20 + 4, anchoUnit * 16));
        cSF.getButConfirm1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cSF.getPanels().size() > 0) {
                    try {
                        scs.confirmTables(cSF);
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        panelSpaces.add(cSF.getButConfirm1());
        return panelSpaces;
    }

    public JPanel panelCatBacker(ConfigSalonFrame cSF) {
        JPanel panelSpaces = new JPanel(null);
        panelSpaces.setBounds(anchoUnit * 0, altoUnit * 46, anchoFrameThird, altoUnit * 14);
        panelSpaces.setBackground(bluSt);
        
        JLabel labelIndications3 = utiliGraf.labelTitleBacker2W("Seleccione hasta 6 categorías");
        labelIndications3.setBounds(anchoUnit * 8, altoUnit * 0, anchoUnit * 18, altoUnit * 3);
        panelSpaces.add(labelIndications3);

        JLabel labelCat = utiliGraf.labelTitleBacker3W("Categ. a Seleccionar");
        labelCat.setBounds(anchoUnit * 2, altoUnit * 3, anchoUnit * 13, altoUnit * 3);
        panelSpaces.add(labelCat);

        cSF.getListCat().setModel(utili.spacesListModelReturnMono(cSF.getCategories()));
        cSF.getListCat().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (cSF.getCategoriesSel().size() < 6) {
                        String selectedValue = (String) cSF.getListCat().getSelectedValue();
                        scs.selCategory(selectedValue, 1, cSF);
                    } else {
                        utiliMsg.errorCatExcess();
                    }
                }
            }
        });
        JScrollPane jSCat = new JScrollPane(cSF.getListCat());
        jSCat.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 8, altoUnit * 8);
        panelSpaces.add(jSCat);

        JLabel labelCatSel = utiliGraf.labelTitleBacker3W("Categ. Seleccionadas");
        labelCatSel.setBounds(anchoUnit * 15, altoUnit * 3, anchoUnit * 13, altoUnit * 3);
        panelSpaces.add(labelCatSel);

        cSF.getListCatSel().setModel(utili.spacesListModelReturnMono(cSF.getCategoriesSel()));
        cSF.getListCatSel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedValue = (String) cSF.getListCatSel().getSelectedValue();
                    scs.unSelCategory(selectedValue, cSF);
                }
            }
        });

        JScrollPane jSCatSel = new JScrollPane(cSF.getListCatSel());
        jSCatSel.setBounds(anchoUnit * 15, altoUnit * 6, anchoUnit * 8, altoUnit * 8);
        panelSpaces.add(jSCatSel);

        cSF.setButAddCategory(utiliGraf.button3("Crear Categoría", anchoUnit * 24, altoUnit * 11, anchoUnit * 9));
        cSF.getButAddCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    scs.createCategory(cSF);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelSpaces.add(cSF.getButAddCategory());

        return panelSpaces;
    }

    public JPanel panelTipBacker(ConfigSalonFrame cSF) {
        
        JPanel panelSpaces = new JPanel(null);
        panelSpaces.setBounds(anchoUnit * 0, altoUnit * 61, anchoFrameThird, altoUnit * 22);
        panelSpaces.setBackground(bluSt);
        
        JLabel labelIndications4 = utiliGraf.labelTitleBacker2W("Seleccione el porcentaje de las Propinas:");
        labelIndications4.setBounds(anchoUnit * 4, altoUnit * 0, anchoUnit * 25, altoUnit * 3);
        panelSpaces.add(labelIndications4);
        
        JLabel labelPorcentaje = utiliGraf.labelTitleBacker1W("Porcentaje %:");
        labelPorcentaje.setBounds(anchoUnit * 8, altoUnit * 3, anchoUnit * 11, altoUnit * 4);
        panelSpaces.add(labelPorcentaje);        

        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
        cSF.getSpinnerTip().setModel(model);
        cSF.getSpinnerTip().setBounds(anchoUnit * 19, altoUnit * 3, anchoUnit * 4, altoUnit * 4);
        cSF.getSpinnerTip().setFont(cSF.getFont1());
        cSF.getSpinnerTip().setValue(10);
        panelSpaces.add(cSF.getSpinnerTip());
        
        JSeparator separator4 = new JSeparator(SwingConstants.HORIZONTAL);
        separator4.setBounds(anchoUnit * 1, altoUnit * 7 + 3, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelSpaces.add(separator4);
        
        cSF.setData1("<html><b>SECTORES</b>:</html>");
        cSF.setLabelData1(utiliGraf.labelTitleBacker2W(cSF.getData1()));
        cSF.getLabelData1().setText(cSF.getData1());
        cSF.getLabelData1().setBounds(anchoUnit * 1, altoUnit * 8, anchoUnit * 32, altoUnit * 8);
        cSF.getLabelData1().setFont(cSF.getFont2());
        cSF.getLabelData1().setBackground(bluLg);
        panelSpaces.add(cSF.getLabelData1());

        cSF.setData2("<html><b>CATEGORÍAS</b>:</html>");
        cSF.setLabelData2(utiliGraf.labelTitleBacker2W(cSF.getData2()));
        cSF.getLabelData2().setText(cSF.getData2());
        cSF.getLabelData2().setBounds(anchoUnit * 1, altoUnit * 16, anchoUnit * 32, altoUnit * 6);
        cSF.getLabelData2().setFont(cSF.getFont2());
        cSF.getLabelData2().setBackground(bluLg);
        panelSpaces.add(cSF.getLabelData2());
                
        return panelSpaces;
    }

    public JButtonMetalBlu butConf2Backer(ConfigSalonFrame cSF) {
        cSF.setButConfirm2(utiliGraf.button1("Confirmar Configuración", anchoUnit, altoUnit * 85, anchoUnit * 21));
        cSF.getButConfirm2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cSF.getCategoriesSel().size() > 0) {
                    try {
                        if (cSF.isSelSectors()) {
                            boolean confirm = utiliMsg.optionConfirmRestart();
                            if (confirm) {
                                String pass = utiliMsg.requestPass();
                                if (pass.equals(cSF.getUser().getPassword())) {
                                    scs.createConfig(cSF);
                                } else {
                                    utiliMsg.errorAccessDenied();
                                }
                            }
                        }
                        utiliMsg.errorUnconfirmTable();
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (cSF.getQuants().size() == 0) {
                    utiliMsg.errorNullSector();
                } else {
                    utiliMsg.errorCategoriesNull();
                }
            }
        });
        
        return cSF.getButConfirm2();
    }

    public JButtonMetalBlu butResetBacker(ConfigSalonFrame cSF) {
        cSF.setButReset(utiliGraf.button1("Resetear", anchoUnit * 23, altoUnit * 85, anchoUnit * 10));
        cSF.getButReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cSF.getPanels().size() > 0) {
                    try {
                        scs.resetValues(cSF);
                    } catch (Exception ex) {
                        Logger.getLogger(ConfigSalonFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    utiliMsg.errorNullSector();
                }
            }
        });
        return cSF.getButReset();
    }
}
