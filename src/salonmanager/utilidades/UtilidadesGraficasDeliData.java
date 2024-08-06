/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.ConsumerTemplate;
import salonmanager.DeliveryCreate;
import salonmanager.DeliveryData;
import salonmanager.UserExpressTemplate;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.persistencia.DAODeliveryClient;

/**
 *
 * @author Gonzalo
 */
public class UtilidadesGraficasDeliData {

    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    DAODeliveryClient daoC = new DAODeliveryClient();
    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color redLg = new Color(243, 103, 91);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);

//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
    public JPanel panelConsumerBacker(DeliveryData dd) {
        JPanel panelConsumer = panelBacker("Cliente", anchoUnit, altoUnit * 6, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 9));

        dd.setLabelSelectPhone(utiliGraf.labelTitleBacker1("Elija Cliente según teléfono:"));
        dd.getLabelSelectPhone().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 23, altoUnit * 4);
        dd.getLabelSelectPhone().setVisible(false);
        panelConsumer.add(dd.getLabelSelectPhone());

        dd.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dd.getConsumers()));
        dd.getComboConsumers().setFont(dd.getSalon().getFont4());
        dd.getComboConsumers().setBounds(anchoUnit * 24, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        dd.getComboConsumers().setSelectedIndex(dd.getConsumers().size());
        dd.getComboConsumers().setVisible(false);
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consumerPhoneBacker(dd);
                    changeConsumer(dd, false);
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        dd.getComboConsumers().addActionListener(actionListener1);
        panelConsumer.add(dd.getComboConsumers());

        dd.setButOpConsumer(utiliGraf.button2("CAMBIAR CLIENTE", anchoUnit * 18, altoUnit * 7, anchoUnit * 15));
        dd.getButOpConsumer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    changeConsumer(dd, true);
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelConsumer.add(dd.getButOpConsumer());

        dd.setButUpdateConsumer(utiliGraf.button3("MODIFICAR DATOS", anchoUnit * 38, altoUnit * 8, anchoUnit * 10));
        dd.getButUpdateConsumer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    updateConsumer(dd);
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelConsumer.add(dd.getButUpdateConsumer());

        dd.setLabelName(utiliGraf.labelTitleBacker2("Nombre: " + dd.getCmrAux().getName().toUpperCase(Locale.ITALY)));
        dd.getLabelName().setBounds(anchoUnit * 5, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelName());

        dd.setLabelPhone(utiliGraf.labelTitleBacker2("Teléfono: " + dd.getCmrAux().getPhone().toUpperCase(Locale.ITALY)));
        dd.getLabelPhone().setBounds(anchoUnit * 5, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelPhone());

        dd.setLabelSn(utiliGraf.labelTitleBacker2("Red Social: " + dd.getCmrAux().getSocialNetwork().toUpperCase(Locale.ITALY)));
        dd.getLabelSn().setBounds(anchoUnit * 5, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelSn());

        dd.setLabelStreet(utiliGraf.labelTitleBacker2("Calle: " + dd.getCmrAux().getStreet().toUpperCase(Locale.ITALY)));
        dd.getLabelStreet().setBounds(anchoUnit * 5, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelStreet());

        dd.setLabelNumSt(utiliGraf.labelTitleBacker2("Numéro: " + dd.getCmrAux().getNumSt().toUpperCase(Locale.ITALY)));
        dd.getLabelNumSt().setBounds(anchoUnit * 5, altoUnit * 33, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelNumSt());

        dd.setLabelFloorD(utiliGraf.labelTitleBacker2("Piso: " + dd.getCmrAux().getDeptFloor().toUpperCase(Locale.ITALY)));
        dd.getLabelFloorD().setBounds(anchoUnit * 5, altoUnit * 38, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelFloorD());

        dd.setLabelNumD(utiliGraf.labelTitleBacker2("Nro departamento: " + dd.getCmrAux().getDeptNum().toUpperCase(Locale.ITALY)));
        dd.getLabelNumD().setBounds(anchoUnit * 30, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelNumD());

        dd.setLabelDistrict(utiliGraf.labelTitleBacker2("Barrio: " + dd.getCmrAux().getArea().toUpperCase(Locale.ITALY)));
        dd.getLabelDistrict().setBounds(anchoUnit * 30, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelDistrict());

        dd.setLabelArea(utiliGraf.labelTitleBacker2("Localidad: " + dd.getCmrAux().getDistrict().toUpperCase(Locale.ITALY)));
        dd.getLabelArea().setBounds(anchoUnit * 30, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelArea());

        JLabel labelDetailsTit = utiliGraf.labelTitleBacker2("Detalles Domicilio:");
        labelDetailsTit.setBounds(anchoUnit * 30, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelDetailsTit);

        dd.getPanelDetails().setBackground(narLg);

        String details = "<html>" + dd.getCmrAux().getDetails() + "</html>";
        dd.setLabelDetails(utiliGraf.labelTitleBacker3(details));
        dd.getPanelDetails().add(dd.getLabelDetails());

        JScrollPane scrollPaneConsumer = new JScrollPane(dd.getPanelDetails());
        scrollPaneConsumer.setBounds(anchoUnit * 30, altoUnit * 31, anchoUnit * 17, altoUnit * 10);
        panelConsumer.add(scrollPaneConsumer);

        return panelConsumer;
    }

    private JPanel panelBacker(String tit, int i, int i0, int i1, int i2) {
        JPanel panel = new JPanel(null);
        panel.setBounds(i, i0, i1, i2);
        panel.setBackground(narLg);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(narLg);
        panelLabel.setBounds(0, 0, anchoFrame / 2, altoUnit * 5);
        panel.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBackerA4(tit);
        panelLabel.add(labelTit);

        return panel;
    }


    private void changeConsumer(DeliveryData dd, boolean b) {
        boolean bool1 = true;
        boolean bool2 = false;
        
        if (!b) {
            bool1 = false;
            bool2 = true;
        }
        
        dd.getLabelSelectPhone().setVisible(bool1);
        dd.getComboConsumers().setVisible(bool1);
        dd.getButOpConsumer().setVisible(bool2);
        dd.getButUpdateConsumer().setVisible(bool2);
    }
    
        private void consumerPhoneBacker(DeliveryData dd) throws Exception {
        String st = (String) dd.getComboConsumers().getSelectedItem();
        DeliveryClient cmr = daoC.getConsumerByPhone(st);
        dd.setCmrAux(cmr);
        setConsumerFields(dd.getCmrAux(), dd);
    }

    public void setConsumerFields(DeliveryClient cmr, DeliveryData dd) {
        dd.getLabelName().setText("Nombre: " + cmr.getName());
        dd.getLabelPhone().setText("Teléfono: " + cmr.getPhone());
        dd.getLabelSn().setText("Red Social: " + cmr.getSocialNetwork());
        dd.getLabelStreet().setText("Calle: " + cmr.getStreet());
        dd.getLabelNumSt().setText("Número: " + cmr.getNumSt());
        dd.getLabelFloorD().setText("Piso: " + cmr.getDeptFloor());
        dd.getLabelNumD().setText("Nro de Departamento: " + cmr.getDeptNum());
        dd.getLabelDistrict().setText("Barrio: " + cmr.getArea());
        dd.getLabelArea().setText("Localidad: " + cmr.getDistrict());
        String details = utili.stringMsgFrd(cmr.getDetails(), 25, 1);
        dd.getLabelDetails().setText(details);
        dd.revalidate();
        dd.repaint();
    }

    private void updateConsumer(DeliveryData dd) throws Exception {
        new ConsumerTemplate(null, dd, dd.getCmrAux());
        dd.setEnabled(false);
    }

    public void getConsumer(DeliveryClient cmr, DeliveryData dd) {
        dd.setCmrAux(cmr);
        setConsumerFields(cmr, dd);
        dd.getConsumers().add(dd.getCmrAux().getPhone());
        dd.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dd.getConsumers()));
        dd.getComboConsumers().setSelectedIndex(dd.getConsumers().size() - 1);
        if (dd.getDeliFull() != null) {
            dd.getButOpDelivery().setVisible(true);
        }
        dd.setFndEnabled();
    }

//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
    public JPanel panelDeliveryBacker(DeliveryData dd) {
        JPanel panelDelivery = panelBacker("Delivery", anchoUnit, alturaFrame / 2, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 15));

        dd.setLabelSelectDeli(utiliGraf.labelTitleBacker1("Elija Delivery para envíar:"));
        dd.getLabelSelectDeli().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
        panelDelivery.add(dd.getLabelSelectDeli());

        dd.getComboDelis().setModel(utili.userComboModelReturnWNull(dd.getDeliverys()));
        dd.getComboDelis().setFont(dd.getSalon().getFont4());
        dd.getComboDelis().setBounds(anchoUnit * 22, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        dd.getComboDelis().setSelectedIndex(dd.getDeliverys().size());
        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryBacker(dd);
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        // Agregar el ActionListener al JComboBox
        dd.getComboDelis().addActionListener(actionListener2);
        panelDelivery.add(dd.getComboDelis());

        if (dd.getDeliFull() != null) {
            if (dd.getDeliAux() != null) {
                dd.getLabelSelectDeli().setVisible(false);
                dd.getComboDelis().setVisible(false);
            } else {
                dd.getLabelSelectDeli().setVisible(true);
                dd.getComboDelis().setVisible(true);
            }
        }

        dd.setButOpDeliUser(utiliGraf.button2("CAMBIAR CONDUCTOR", anchoUnit * 18, altoUnit * 7, anchoUnit * 15));
        dd.getButOpDeliUser().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    changeDeliveryUser(dd);
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelDelivery.add(dd.getButOpDeliUser());

        if (dd.getDeliFull() != null) {
            dd.setButUpdateDeliUser(utiliGraf.button3("MODIFICAR DATOS", anchoUnit * 38, altoUnit * 8, anchoUnit * 10));
            dd.getButUpdateDeliUser().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        updateDeliveryUser(dd);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            panelDelivery.add(dd.getButUpdateDeliUser());
        }
        
        User deliU = dd.getDeliAux();

        dd.setLabelNameDeli(utiliGraf.labelTitleBacker1("Nombre: " + deliU.getName()));
        dd.getLabelNameDeli().setBounds(anchoUnit * 8, altoUnit * 11, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelNameDeli());

        dd.setLabelLastNameDeli(utiliGraf.labelTitleBacker1("Apellido: " + deliU.getLastName()));
        dd.getLabelLastNameDeli().setBounds(anchoUnit * 8, altoUnit * 16, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelLastNameDeli());

        dd.setLabelPhoneDeli(utiliGraf.labelTitleBacker1("Teléfono: " + deliU.getPhone()));
        dd.getLabelPhoneDeli().setBounds(anchoUnit * 8, altoUnit * 21, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelPhoneDeli());

        dd.setLabelMailDeli(utiliGraf.labelTitleBacker1("Mail: " + deliU.getMail()));
        dd.getLabelMailDeli().setBounds(anchoUnit * 8, altoUnit * 26, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelMailDeli());

        dd.setLabelRolDeli(utiliGraf.labelTitleBacker1("Ocupación: " + deliU.getRol()));
        dd.getLabelRolDeli().setBounds(anchoUnit * 8, altoUnit * 31, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelRolDeli());

//        setDeliveryUserFields(dd.getDeliAux(), dd);

        return panelDelivery;
    }

    private void deliveryBacker(DeliveryData dd) {
        String st = (String) dd.getComboDelis().getSelectedItem();
        dd.setDeliAux(utili.userSelReturn(st, dd.getDeliverys()));
        setDeliveryFields(dd.getDeliAux(), dd);
    }

    private void changeDeliveryUser(DeliveryData dd) {
        dd.getLabelSelectDeli().setVisible(true);
        dd.getComboDelis().setVisible(true);
        dd.getButOpDeliUser().setVisible(false);
        dd.getButUpdateDeliUser().setVisible(false);
    }

    private void updateDeliveryUser(DeliveryData dd) throws Exception {
        new UserExpressTemplate(null, dd, "DELIVERY", dd.getDeliAux());
        dd.setEnabled(false);
    }

//    private void setConsumerFields(DeliveryData dd, DeliveryClient dc) {
//        dd.setLabelName(utiliGraf.labelTitleBacker2("Nombre: " + dc.getName().toUpperCase(Locale.ITALY)));
//        dd.setLabelPhone(utiliGraf.labelTitleBacker2("Teléfono: " + dc.getPhone().toUpperCase(Locale.ITALY)));
//        dd.setLabelSn(utiliGraf.labelTitleBacker2("Red Social: " + dc.getSocialNetwork().toUpperCase(Locale.ITALY)));
//        dd.setLabelStreet(utiliGraf.labelTitleBacker2("Calle: " + dc.getStreet().toUpperCase(Locale.ITALY)));
//        dd.setLabelNumSt(utiliGraf.labelTitleBacker2("Numéro: " + dc.getNumSt().toUpperCase(Locale.ITALY)));
//        dd.setLabelFloorD(utiliGraf.labelTitleBacker2("Piso: " + dc.getDeptFloor().toUpperCase(Locale.ITALY)));
//        dd.setLabelNumD(utiliGraf.labelTitleBacker2("Nro departamento: " + dc.getDeptNum().toUpperCase(Locale.ITALY)));
//        dd.setLabelDistrict(utiliGraf.labelTitleBacker2("Barrio: " + dc.getArea().toUpperCase(Locale.ITALY)));
//        dd.setLabelArea(utiliGraf.labelTitleBacker2("Localidad: " + dc.getDistrict().toUpperCase(Locale.ITALY)));
//        String details = "<html>" + dc.getDetails() + "</html>";
//        dd.setLabelDetails(utiliGraf.labelTitleBacker3(details));
//        dd.getPanelConsumer().revalidate();
//        dd.getPanelConsumer().repaint();
//    }
    
    public void setDeliveryFields(User deli, DeliveryData dd) {
        dd.getLabelNameDeli().setText("Nombre: " + deli.getName());
        dd.getLabelLastNameDeli().setText("Apellido: " + deli.getLastName());
        dd.getLabelPhoneDeli().setText("Teléfono: " + deli.getPhone());
        dd.getLabelMailDeli().setText("Mail: " + deli.getMail());
        dd.getLabelRolDeli().setText("Ocupación: " + deli.getRol());
    }

//    public void setDeliveryUserField(User user, DeliveryData dd) {
//        dd.setLabelNameDeli(utiliGraf.labelTitleBacker1("Nombre:" + user.getName()));
//        dd.setLabelLastNameDeli(utiliGraf.labelTitleBacker1("Apellido: " + user.getLastName()));
//        dd.setLabelPhoneDeli(utiliGraf.labelTitleBacker1("Teléfono: " + user.getPhone()));
//        dd.setLabelMailDeli(utiliGraf.labelTitleBacker1("Numéro: " + user.getPhone()));
//        dd.setLabelRolDeli(utiliGraf.labelTitleBacker1("Ocupación: " + user.getRol()));
//        dd.getPanelDelivery().revalidate();
//        dd.getPanelDelivery().repaint();    
//    }
}
