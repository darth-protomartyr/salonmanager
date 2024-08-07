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
import salonmanager.DeliveryCreate;
import salonmanager.UserExpressTemplate;
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.persistencia.DAODeliveryClient;

public class UtilidadesGraficasDeliCreate {

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
    public JPanel panelConsumerBacker(DeliveryCreate dc) {
        JPanel panelConsumer = panelBacker("Cliente", altoUnit * 6, (alturaFrame / 2 - altoUnit * 9));

        dc.setLabelSelectPhone(utiliGraf.labelTitleBacker1("Elija Cliente según teléfono:"));
        dc.getLabelSelectPhone().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 23, altoUnit * 4);
        panelConsumer.add(dc.getLabelSelectPhone());

        dc.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dc.getConsumers()));
        dc.getComboConsumers().setFont(dc.getSalon().getFont4());
        dc.getComboConsumers().setBounds(anchoUnit * 24, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        dc.getComboConsumers().setSelectedIndex(dc.getConsumers().size());
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consumerPhoneBacker(dc);
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryCreate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        dc.getComboConsumers().addActionListener(actionListener1);
        panelConsumer.add(dc.getComboConsumers());

        
        dc.setButOpConsumer(utiliGraf.button2("CREAR CLIENTE", anchoUnit * 37, altoUnit * 7, anchoUnit * 12));
        dc.getButOpConsumer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createConsumer(dc.getDc());
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelConsumer.add(dc.getButOpConsumer());

        dc.setLabelName(utiliGraf.labelTitleBacker2("Nombre:"));
        dc.getLabelName().setBounds(anchoUnit * 5, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelName());

        dc.setLabelPhone(utiliGraf.labelTitleBacker2("Teléfono:"));
        dc.getLabelPhone().setBounds(anchoUnit * 5, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelPhone());

        dc.setLabelSn(utiliGraf.labelTitleBacker2("Red Social:"));
        dc.getLabelSn().setBounds(anchoUnit * 5, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelSn());

        dc.setLabelStreet(utiliGraf.labelTitleBacker2("Calle:"));
        dc.getLabelStreet().setBounds(anchoUnit * 5, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelStreet());

        dc.setLabelNumSt(utiliGraf.labelTitleBacker2("Numéro:"));
        dc.getLabelNumSt().setBounds(anchoUnit * 5, altoUnit * 33, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelNumSt());

        dc.setLabelFloorD(utiliGraf.labelTitleBacker2("Piso:"));
        dc.getLabelFloorD().setBounds(anchoUnit * 5, altoUnit * 38, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelFloorD());

        dc.setLabelNumD(utiliGraf.labelTitleBacker2("Nro departamento:"));
        dc.getLabelNumD().setBounds(anchoUnit * 30, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelNumD());

        dc.setLabelDistrict(utiliGraf.labelTitleBacker2("Barrio:"));
        dc.getLabelDistrict().setBounds(anchoUnit * 30, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelDistrict());

        dc.setLabelArea(utiliGraf.labelTitleBacker2("Localidad:"));
        dc.getLabelArea().setBounds(anchoUnit * 30, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dc.getLabelArea());

        JLabel labelDetailsTit = utiliGraf.labelTitleBacker2("Detalles Domicilio:");
        labelDetailsTit.setBounds(anchoUnit * 30, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelDetailsTit);

        dc.getPanelDetails().setBackground(narLg);

        dc.setLabelDetails(utiliGraf.labelTitleBacker3(""));
        dc.getPanelDetails().add(dc.getLabelDetails());

        JScrollPane scrollPaneConsumer = new JScrollPane(dc.getPanelDetails());
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

    private void consumerPhoneBacker(DeliveryCreate dc) throws Exception {
        String st = (String) dc.getComboConsumers().getSelectedItem();
        DeliveryClient cmr = daoC.getConsumerByPhone(st);
        dc.setCmrAux(cmr);
        setConsumerFields(dc.getCmrAux(), dc);
    }

    public void setConsumerFields(DeliveryClient cmr, DeliveryCreate dc) {
        dc.getLabelName().setText("Nombre: " + cmr.getName());
        dc.getLabelPhone().setText("Teléfono: " + cmr.getPhone());
        dc.getLabelSn().setText("Red Social: " + cmr.getSocialNetwork());
        dc.getLabelStreet().setText("Calle: " + cmr.getStreet());
        dc.getLabelNumSt().setText("Número: " + cmr.getNumSt());
        dc.getLabelFloorD().setText("Piso: " + cmr.getDeptFloor());
        dc.getLabelNumD().setText("Nro de Departamento: " + cmr.getDeptNum());
        dc.getLabelDistrict().setText("Barrio: " + cmr.getDistrict());
        dc.getLabelArea().setText("Localidad: " + cmr.getArea());
        String details = utili.stringMsgFrd(cmr.getDetails(), 25, 1);
        dc.getLabelDetails().setText(details);
        dc.revalidate();
        dc.repaint();
    }

    private void createConsumer(DeliveryCreate dc) throws Exception {
        new ConsumerTemplate(dc, null, null);
        dc.setEnabled(false);
    }

    public void getConsumer(DeliveryClient cmr, DeliveryCreate dc) {
        dc.setCmrAux(cmr);
        setConsumerFields(dc.getCmrAux(), dc);
        dc.getConsumers().add(dc.getCmrAux().getPhone());
        dc.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dc.getConsumers()));
        dc.getComboConsumers().setSelectedIndex(dc.getConsumers().size() - 1);
        dc.setFndEnabled();
    }

//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
    public JPanel panelDeliveryBacker(DeliveryCreate dc) {
        JPanel panelDelivery = panelBacker("Delivery", alturaFrame / 2, (alturaFrame / 2 - altoUnit * 14));

        dc.setLabelSelectDeli(utiliGraf.labelTitleBacker1("Elija Delivery para envíar:"));
        dc.getLabelSelectDeli().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
        panelDelivery.add(dc.getLabelSelectDeli());

        dc.getComboDelis().setModel(utili.userComboModelReturnWNull(dc.getDeliverys()));
        dc.getComboDelis().setFont(dc.getSalon().getFont4());
        dc.getComboDelis().setBounds(anchoUnit * 22, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        dc.getComboDelis().setSelectedIndex(dc.getDeliverys().size());
        panelDelivery.add(dc.getComboDelis());

        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryBacker(dc);
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryCreate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        dc.getComboDelis().addActionListener(actionListener2);

        dc.setButOpDeliUser(utiliGraf.button2("CREAR CONDUCTOR", anchoUnit * 35, altoUnit * 7, anchoUnit * 14));
        dc.getButOpDeliUser().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    createDeliveryUser(dc);
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelDelivery.add(dc.getButOpDeliUser());

        dc.setLabelNameDeli(utiliGraf.labelTitleBacker1("Nombre:"));
        dc.getLabelNameDeli().setBounds(anchoUnit * 8, altoUnit * 11, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dc.getLabelNameDeli());

        dc.setLabelLastNameDeli(utiliGraf.labelTitleBacker1("Apellido:"));
        dc.getLabelLastNameDeli().setBounds(anchoUnit * 8, altoUnit * 16, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dc.getLabelLastNameDeli());

        dc.setLabelPhoneDeli(utiliGraf.labelTitleBacker1("Teléfono:"));
        dc.getLabelPhoneDeli().setBounds(anchoUnit * 8, altoUnit * 21, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dc.getLabelPhoneDeli());

        dc.setLabelMailDeli(utiliGraf.labelTitleBacker1("Numéro:"));
        dc.getLabelMailDeli().setBounds(anchoUnit * 8, altoUnit * 26, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dc.getLabelMailDeli());

        dc.setLabelRolDeli(utiliGraf.labelTitleBacker1("Ocupación:"));
        dc.getLabelRolDeli().setBounds(anchoUnit * 8, altoUnit * 31, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(dc.getLabelRolDeli());

        return panelDelivery;

    }

    private void deliveryBacker(DeliveryCreate dc) {
        String st = (String) dc.getComboDelis().getSelectedItem();
        dc.setDeliAux(utili.userSelReturn(st, dc.getDeliverys()));
        setDeliveryFields(dc.getDeliAux(), dc);
    }

    public void setDeliveryFields(User deli, DeliveryCreate dc) {
        dc.getLabelNameDeli().setText("Nombre: " + deli.getName());
        dc.getLabelLastNameDeli().setText("Apellido: " + deli.getLastName());
        dc.getLabelPhoneDeli().setText("Teléfono: " + deli.getPhone());
        dc.getLabelMailDeli().setText("Mail: " + deli.getMail());
        dc.getLabelRolDeli().setText("Ocupación: " + deli.getRol());
    }

    private void createDeliveryUser(DeliveryCreate dc) throws Exception {
        new UserExpressTemplate(dc, null, "DELIVERY", null);
        dc.setEnabled(false);
    }
}
