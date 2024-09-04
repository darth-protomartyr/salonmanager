package salonmanager.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.ConsumerTemplate;
import salonmanager.DeliveryData;
import salonmanager.UserExpressTemplate;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.persistencia.DAODeliveryClient;

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
    Color narLg = new Color(252, 203, 5);

//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
//--------------------------------CONSUMER--------------------------------------
    public JPanel panelConsumerBacker(DeliveryData dd) {
        JPanel panelConsumer = panelBacker("Cliente", altoUnit * 6, (alturaFrame / 2 - altoUnit * 9));

        dd.setLabelSelectPhone(utiliGraf.labelTitleBacker1("Seleccione Cliente:"));
        dd.getLabelSelectPhone().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 14, altoUnit * 4);
        dd.getLabelSelectPhone().setVisible(false);
        panelConsumer.add(dd.getLabelSelectPhone());        

        dd.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dd.getConsumers(), null, dd));
        dd.getComboConsumers().setFont(dd.getSalon().getFont4());
        dd.getComboConsumers().setBounds(anchoUnit * 17, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
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

        dd.setLabelName(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Nombre: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getName().toUpperCase() + "</span></html>"));
        dd.getLabelName().setBounds(anchoUnit * 2, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelName());

        dd.setLabelLastname(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Apellido: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getLastname().toUpperCase() + "</span></html>"));
        dd.getLabelLastname().setBounds(anchoUnit * 2, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelLastname());

        dd.setLabelPhone(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Teléfono: </span> <span style='font-weight: 900;'>" + dd.getCmrAux().getPhone().toUpperCase() + "</span></html>"));
        dd.getLabelPhone().setBounds(anchoUnit * 2, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelPhone());

        dd.setLabelSn(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Red Social: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getSocialNetwork() + "</span></html>"));
        dd.getLabelSn().setBounds(anchoUnit * 2, altoUnit * 28, anchoUnit * 30, altoUnit * 3);
        panelConsumer.add(dd.getLabelSn());

        dd.setLabelStreet(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Calle: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getStreet().toUpperCase() + "</span></html>"));
        dd.getLabelStreet().setBounds(anchoUnit * 2, altoUnit * 33, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelStreet());

        dd.setLabelNumSt(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Numéro: </span> <span style='font-weight: 900;'>" + dd.getCmrAux().getNumSt().toUpperCase() + "</span></html>"));
        dd.getLabelNumSt().setBounds(anchoUnit * 2, altoUnit * 38, anchoUnit * 10, altoUnit * 3);
        panelConsumer.add(dd.getLabelNumSt());

        dd.setLabelFloorD(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Piso: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getDeptFloor().toUpperCase() + "</span></html>"));
        dd.getLabelFloorD().setBounds(anchoUnit * 15, altoUnit * 38, anchoUnit * 8, altoUnit * 3);
        panelConsumer.add(dd.getLabelFloorD());

        dd.setLabelNumD(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Nro departamento: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getDeptNum().toUpperCase() + "</span></html>"));
        dd.getLabelNumD().setBounds(anchoUnit * 30, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelNumD());

        dd.setLabelDistrict(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Barrio: </span></span> <span style='font-weight: 900;'>" + dd.getCmrAux().getDistrict().toUpperCase() + "</span></html>"));
        dd.getLabelDistrict().setBounds(anchoUnit * 30, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelDistrict());

        dd.setLabelArea(utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'>Localidad: </span><span style='font-weight: 900;'>" + dd.getCmrAux().getArea().toUpperCase() + "</span></html>"));
        dd.getLabelArea().setBounds(anchoUnit * 30, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dd.getLabelArea());

        JLabel labelDetailsTit = utiliGraf.labelTitleBacker2("<html><span style='font-weight: 400;'> Detalles Domicilio:</span></html>");
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

    public JPanel panelBacker(String tit, int hPos, int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(anchoUnit, hPos, (anchoFrame / 2) - anchoUnit * 3, h);
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
        int i = (int) dd.getComboConsumers().getSelectedIndex();
        i = dd.getIdsCmr().get(i);
        DeliveryClient cmr = daoC.getConsumerById(i);
        dd.setCmrAux(cmr);
        setConsumerFields(dd.getCmrAux(), dd);
    }

    public void setConsumerFields(DeliveryClient cmr, DeliveryData dd) {
        dd.getLabelName().setText("<html><span style='font-weight: 400;'>Nombre:</span> <span style='font-weight: 900;'>" + cmr.getName().toUpperCase() + "</span></html>");
        dd.getLabelLastname().setText("<html><span style='font-weight: 400;'>Apellido:</span> <span style='font-weight: 900;'>" + cmr.getLastname().toUpperCase() + "</span></html>");
        dd.getLabelPhone().setText("<html><span style='font-weight: 400;'>Teléfono:</span> <span style='font-weight: 900;'>" + cmr.getPhone().toUpperCase() + "</span></html>");
        dd.getLabelSn().setText("<html><span style='font-weight: 400;'>Red Social:</span> <span style='font-weight: 900;'>" + cmr.getSocialNetwork() + "</span></html>");
        dd.getLabelStreet().setText("<html><span style='font-weight: 400;'>Calle:</span> <span style='font-weight: 900;'>" + cmr.getStreet().toUpperCase() + "</span></html>");
        dd.getLabelNumSt().setText("<html><span style='font-weight: 400;'>Número:</span> <span style='font-weight: 900;'>" + cmr.getNumSt().toUpperCase() + "</span></html>");
        dd.getLabelFloorD().setText("<html><span style='font-weight: 400;'>Piso:</span> <span style='font-weight: 900;'>" + cmr.getDeptFloor().toUpperCase() + "</span></html>");
        dd.getLabelNumD().setText("<html><span style='font-weight: 400;'>Nro de Departamento:</span> <span style='font-weight: 900;'>" + cmr.getDeptNum().toUpperCase() + "</span></html>");
        dd.getLabelDistrict().setText("<html><span style='font-weight: 400;'>Barrio:</span> <span style='font-weight: 900;'>" + cmr.getDistrict().toUpperCase() + "</span></html>");
        dd.getLabelArea().setText("<html><span style='font-weight: 400;'>Localidad:</span> <span style='font-weight: 900;'>" + cmr.getArea().toUpperCase() + "</span></html>");
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
        dd.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dd.getConsumers(), null, dd));
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
        JPanel panelDelivery = panelBacker("Delivery", alturaFrame / 2, (alturaFrame / 2 - altoUnit * 14));

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

        dd.setLabelNameDeli(utiliGraf.labelTitleBacker1("<html><span style='font-weight: 400;'>Nombre:</span><span style='font-weight: 900;'>" + deliU.getName().toUpperCase() + "</span></html>"));
        dd.getLabelNameDeli().setBounds(anchoUnit * 8, altoUnit * 11, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelNameDeli());

        dd.setLabelLastNameDeli(utiliGraf.labelTitleBacker1("<html><span style='font-weight: 400;'>Apellido:</span><span style='font-weight: 900;'>" + deliU.getLastName().toUpperCase() + "</span></html>"));
        dd.getLabelLastNameDeli().setBounds(anchoUnit * 8, altoUnit * 16, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelLastNameDeli());

        dd.setLabelPhoneDeli(utiliGraf.labelTitleBacker1("<html><span style='font-weight: 400;'>Teléfono:</span><span style='font-weight: 900;'>" + deliU.getPhone().toUpperCase() + "</span></html>"));
        dd.getLabelPhoneDeli().setBounds(anchoUnit * 8, altoUnit * 21, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelPhoneDeli());

        dd.setLabelMailDeli(utiliGraf.labelTitleBacker1("<html><span style='font-weight: 400;'>Mail:</span><span style='font-weight: 900;'>" + deliU.getMail().toUpperCase() + "</span></html>"));
        dd.getLabelMailDeli().setBounds(anchoUnit * 8, altoUnit * 26, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelMailDeli());

        dd.setLabelRolDeli(utiliGraf.labelTitleBacker1("<html><span style='font-weight: 400;'>Puesto:</span><span style='font-weight: 900;'>" + deliU.getRol().toUpperCase() + "</span></html>"));
        dd.getLabelRolDeli().setBounds(anchoUnit * 8, altoUnit * 31, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dd.getLabelRolDeli());

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

    public void setDeliveryFields(User deli, DeliveryData dd) {
        dd.getLabelNameDeli().setText("<html><span style='font-weight: 400;'>Nombre:</span> <span style='font-weight: 900;'>" + deli.getName().toUpperCase() + "</span></html>");
        dd.getLabelLastNameDeli().setText("<html><span style='font-weight: 400;'>Apellido:</span> <span style='font-weight: 900;'>" + deli.getLastName().toUpperCase() + "</span></html>");
        dd.getLabelPhoneDeli().setText("<html><span style='font-weight: 400;'>Teléfono:</span> <span style='font-weight: 900;'>" + deli.getPhone().toUpperCase() + "</span></html>");
        dd.getLabelMailDeli().setText("<html><span style='font-weight: 400;'>Mail:</span> <span style='font-weight: 900;'>" + deli.getMail().toUpperCase() + "</span></html>");
        dd.getLabelRolDeli().setText("<html><span style='font-weight: 400;'>Ocupación:</span> <span style='font-weight: 900;'>" + deli.getRol().toUpperCase() + "</span></html>");

    }
}
