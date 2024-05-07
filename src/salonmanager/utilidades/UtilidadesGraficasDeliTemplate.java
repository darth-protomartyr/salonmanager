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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.ConsumerTemplate;
import salonmanager.DeliveryTemplate;
import salonmanager.entidades.bussiness.DeliveryConsumer;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.persistencia.DAODeliveryConsumer;

/**
 *
 * @author Gonzalo
 */
public class UtilidadesGraficasDeliTemplate {

    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    DAODeliveryConsumer daoC = new DAODeliveryConsumer();
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
    

    
    public JPanel panelConsumerBacker(DeliveryTemplate dt) {
        
        JPanel panelConsumer = panelBacker("Cliente", anchoUnit, altoUnit * 6, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 9));

        dt.setLabelSelectPhone(utiliGraf.labelTitleBacker1("Elija Cliente según teléfono:"));
        dt.getLabelSelectPhone().setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 23, altoUnit * 4);
        panelConsumer.add(dt.getLabelSelectPhone());

        dt.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dt.getConsumers()));
        dt.getComboConsumers().setFont(dt.getSalon().getFont4());
        dt.getComboConsumers().setBounds(anchoUnit * 24, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        dt.getComboConsumers().setSelectedIndex(dt.getConsumers().size());
        panelConsumer.add(dt.getComboConsumers());

        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consumerPhoneBacker(dt);
                    if (dt.getDeliFull() != null) {
                        dt.getButOpDelivery().setVisible(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        dt.getComboConsumers().addActionListener(actionListener1);
        if (dt.getDeliFull() != null) {
            dt.getLabelSelectPhone().setVisible(false);
            dt.getComboConsumers().setVisible(false);
        }

        dt.setButOpConsumer(utiliGraf.button2("CREAR CLIENTE", anchoUnit * 38, altoUnit * 7, anchoUnit * 11));
        if (dt.getDeliFull() == null) {
            dt.getButOpConsumer().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        createConsumer(dt);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        } else {
            dt.getButOpConsumer().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        changeConsumer(dt);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            dt.getButOpConsumer().setLocation(anchoUnit * 20, altoUnit * 7);
            dt.getButOpConsumer().setText("CAMBIAR CLIENTE");
        }
        panelConsumer.add(dt.getButOpConsumer());

        if (dt.getDeliFull() != null) {
            dt.setButUpdateConsumer(utiliGraf.button3("MODIFICAR DATOS", anchoUnit * 38, altoUnit * 8, anchoUnit * 10));
            dt.getButUpdateConsumer().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        updateConsumer(dt);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            panelConsumer.add(dt.getButUpdateConsumer());
        }

        dt.setLabelName(utiliGraf.labelTitleBacker2("Nombre:"));
        dt.getLabelName().setBounds(anchoUnit * 5, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelName());

        dt.setLabelPhone(utiliGraf.labelTitleBacker2("Teléfono:"));
        dt.getLabelPhone().setBounds(anchoUnit * 5, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelPhone());

        dt.setLabelSn(utiliGraf.labelTitleBacker2("Red Social:"));
        dt.getLabelSn().setBounds(anchoUnit * 5, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelSn());

        dt.setLabelStreet(utiliGraf.labelTitleBacker2("Calle:"));
        dt.getLabelStreet().setBounds(anchoUnit * 5, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelStreet());

        dt.setLabelNumSt(utiliGraf.labelTitleBacker2("Numéro:"));
        dt.getLabelNumSt().setBounds(anchoUnit * 5, altoUnit * 33, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelNumSt());

        dt.setLabelFloorD(utiliGraf.labelTitleBacker2("Piso:"));
        dt.getLabelFloorD().setBounds(anchoUnit * 5, altoUnit * 38, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelFloorD());

        dt.setLabelNumD(utiliGraf.labelTitleBacker2("Nro departamento:"));
        dt.getLabelNumD().setBounds(anchoUnit * 30, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelNumD());

        dt.setLabelDistrict(utiliGraf.labelTitleBacker2("Barrio:"));
        dt.getLabelDistrict().setBounds(anchoUnit * 30, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelDistrict());

        dt.setLabelArea(utiliGraf.labelTitleBacker2("Localidad:"));
        dt.getLabelArea().setBounds(anchoUnit * 30, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(dt.getLabelArea());

        JLabel labelDetailsTit = utiliGraf.labelTitleBacker2("Detalles Domicilio:");
        labelDetailsTit.setBounds(anchoUnit * 30, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelDetailsTit);

        dt.getPanelDetails().setBackground(narLg);

        dt.setLabelDetails(utiliGraf.labelTitleBacker3(""));
        dt.getPanelDetails().add(dt.getLabelDetails());

        JScrollPane scrollPaneConsumer = new JScrollPane(dt.getPanelDetails());
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
    
    private void consumerPhoneBacker(DeliveryTemplate dt) throws Exception {
        String st = (String) dt.getComboConsumers().getSelectedItem();
        DeliveryConsumer dc = daoC.getConsumerByPhone(st); 
        dt.setCmrAux(dc);
        setConsumerFields(dt.getCmrAux(), dt);
    }
    
    public void setConsumerFields(DeliveryConsumer cmr, DeliveryTemplate dt) {
        dt.getLabelName().setText("Nombre: " + cmr.getName());
        dt.getLabelPhone().setText("Teléfono: " + cmr.getPhone());
        dt.getLabelSn().setText("Red Social: " + cmr.getSocialNetwork());
        dt.getLabelStreet().setText("Calle: " + cmr.getStreet());
        dt.getLabelNumSt().setText("Número: " + cmr.getNumSt());
        dt.getLabelFloorD().setText("Piso: " + cmr.getDeptFloor());
        dt.getLabelNumD().setText("Nro de Departamento: " + cmr.getDeptNum());
        dt.getLabelDistrict().setText("Barrio: " + cmr.getDistrict());
        dt.getLabelArea().setText("Localidad: " + cmr.getArea());
        String details = utili.stringMsgFrd(cmr.getDetails(), 25, 1);
        dt.getLabelDetails().setText(details);
        dt.revalidate();
        dt.repaint();
    }
    
    private void createConsumer(DeliveryTemplate dt) throws Exception {
        new ConsumerTemplate(dt, null);
        dt.setEnabled(false);
    }
    
    private void changeConsumer(DeliveryTemplate dt) {
        dt.getLabelSelectPhone().setVisible(true);
        dt.getComboConsumers().setVisible(true);
        dt.getButOpConsumer().setVisible(false);
        dt.getButUpdateConsumer().setVisible(false);
    }


    private void updateConsumer(DeliveryTemplate dt) throws Exception {
        new ConsumerTemplate(dt, dt.getCmrAux());
        dt.setEnabled(false);
    }
    
    
    public void getConsumer(DeliveryConsumer cmr, DeliveryTemplate dt) {
        dt.setCmrAux(cmr);
        setConsumerFields(dt.getCmrAux(), dt);
        dt.getConsumers().add(dt.getCmrAux().getPhone());
        dt.getComboConsumers().setModel(utili.consumerComboModelReturnWNull(dt.getConsumers()));
        dt.getComboConsumers().setSelectedIndex(dt.getConsumers().size() - 1);
        if (dt.getDeliFull() != null) {
            dt.getButOpDelivery().setVisible(true);
        }
        dt.setFndEnabled();
    }
    
    
    
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------
//--------------------------------DRIVER----------------------------------------

    
    
    
    
}
