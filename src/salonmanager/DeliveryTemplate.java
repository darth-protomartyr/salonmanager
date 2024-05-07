package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.accessibility.AccessibleContext;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.entidades.bussiness.DeliveryConsumer;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAODeliveryConsumer;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasDeliTemplate;
import salonmanager.utilidades.UtilidadesGraficasSalon;
import salonmanager.utilidades.UtilidadesMensajes;

public class DeliveryTemplate extends FrameHalf {

    DAODeliveryConsumer daoC = new DAODeliveryConsumer();
    DAOUser daoU = new DAOUser();

    
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasDeliTemplate utiliGrafDT = new UtilidadesGraficasDeliTemplate();

    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();

    Utilidades utili = new Utilidades();
    ImageIcon icono = new ImageIcon("menu.png");

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

    JComboBox comboConsumers = new JComboBox();
    JComboBox comboDelis = new JComboBox();
    JPanel panelDetails = new JPanel();
    JLabel labelSelectPhone = new JLabel();
    JLabel labelSelectDeli = new JLabel();
    JLabel labelName = new JLabel();
    JLabel labelPhone = new JLabel();
    JLabel labelSn = new JLabel();
    JLabel labelStreet = new JLabel();
    JLabel labelNumSt = new JLabel();
    JLabel labelFloorD = new JLabel();
    JLabel labelNumD = new JLabel();
    JLabel labelDistrict = new JLabel();
    JLabel labelArea = new JLabel();
    JLabel labelDetails = new JLabel();

    JLabel labelNameDeli = new JLabel();
    JLabel labelLastNameDeli = new JLabel();
    JLabel labelPhoneDeli = new JLabel();
    JLabel labelRolDeli = new JLabel();
    JLabel labelMailDeli = new JLabel();

    ArrayList<String> consumers = new ArrayList<String>();
    ArrayList<User> deliverys = new ArrayList<User>();

    JButtonMetalBlu butOpConsumer = null;
    JButtonMetalBlu butOpDeliUser = null;
    JButtonMetalBlu butUpdateConsumer = null;
    JButtonMetalBlu butUpdateDeliUser = null;

    JButtonMetalBlu butOpDelivery = null;

    DeliveryConsumer cmrAux = null;
    User deliAux = null;
    Salon salon = null;
    Delivery deliFull = null;
    boolean change = false;

    public DeliveryTemplate(Salon sal, Delivery deli) throws Exception {
        salon = sal;
        deliFull = deli;
        if (deliFull != null) {
            if (deliFull.getDeli() != null) {
                deliAux = deli.getDeli();
            }
            cmrAux = deli.getConsumer();
        }
        setIconImage(icono.getImage());

        String title = "";
        if (deliFull == null) {
            title = "INICIAR ENVIO";
        } else {
            title = "DATOS ENVIO";
        }

        consumers = daoC.getConsumersPhone();
        
        setTitle(title);

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W(title);
        labelTit.setBounds(anchoUnit, altoUnit * 2, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelTit);

        //---------------------------------------DeliveryConsumer
        JPanel panelConsumer = utiliGrafDT.panelConsumerBacker(this);
        panelPpal.add(panelConsumer);

        //---------------------------------------Delivery
        JPanel panelDelivery = panelBacker("Delivery", anchoUnit, alturaFrame / 2, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 16));
        panelPpal.add(panelDelivery);

        labelSelectDeli = utiliGraf.labelTitleBacker1("Elija Delivery para envíar:");
        labelSelectDeli.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
        panelDelivery.add(labelSelectDeli);

        deliverys = daoU.listUserByRol("DELIVERY");
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
        comboDelis.setFont(salon.getFont4());
        comboDelis.setBounds(anchoUnit * 22, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        comboDelis.setSelectedIndex(deliverys.size());
        panelDelivery.add(comboDelis);

        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryBacker();
                    if (deliFull != null) {
                        butOpDelivery.setVisible(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        // Agregar el ActionListener al JComboBox
        comboDelis.addActionListener(actionListener2);

        if (deliFull != null) {
            if (deliAux != null) {
                labelSelectDeli.setVisible(false);
                comboDelis.setVisible(false);
            } else {
                labelSelectDeli.setVisible(true);
                comboDelis.setVisible(true);
            }
        }

        butOpDeliUser = utiliGraf.button2("CREAR CONDUCTOR", anchoUnit * 35, altoUnit * 7, anchoUnit * 14);
        if (deliFull == null) {
            butOpDeliUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        createDeliveryUser();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        } else {
            butOpDeliUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        changeDeliveryUser();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            butOpDeliUser.setLocation(anchoUnit * 20, altoUnit * 7);
            butOpDeliUser.setText("CAMBIAR CONDUCTOR");
        }
        panelDelivery.add(butOpDeliUser);

        if (deliFull != null) {
            butUpdateDeliUser = utiliGraf.button3("MODIFICAR DATOS", anchoUnit * 38, altoUnit * 8, anchoUnit * 10);
            butUpdateDeliUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        updateDeliveryUser();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            panelDelivery.add(butUpdateDeliUser);
        }

        labelNameDeli = utiliGraf.labelTitleBacker1("Nombre:");
        labelNameDeli.setBounds(anchoUnit * 8, altoUnit * 11, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(labelNameDeli);

        labelLastNameDeli = utiliGraf.labelTitleBacker1("Apellido:");
        labelLastNameDeli.setBounds(anchoUnit * 8, altoUnit * 16, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(labelLastNameDeli);

        labelPhoneDeli = utiliGraf.labelTitleBacker1("Teléfono:");
        labelPhoneDeli.setBounds(anchoUnit * 8, altoUnit * 21, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(labelPhoneDeli);

        labelMailDeli = utiliGraf.labelTitleBacker1("Numéro:");
        labelMailDeli.setBounds(anchoUnit * 8, altoUnit * 26, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(labelMailDeli);

        labelRolDeli = utiliGraf.labelTitleBacker1("Ocupación:");
        labelRolDeli.setBounds(anchoUnit * 8, altoUnit * 31, anchoUnit * 22, altoUnit * 4);
        panelDelivery.add(labelRolDeli);

        if (deliFull != null) {
            setDeliveryFields(deliAux);
        }

        butOpDelivery = utiliGraf.button1("CREAR ENVÏO", anchoUnit * 17, altoUnit * 90, anchoUnit * 16);
        if (deliFull == null) {
            butOpDelivery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        createDeliveryOrder();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        } else {
            butOpDelivery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        updateDeliveryOrder();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        }
        panelPpal.add(butOpDelivery);
        if (deliFull != null) {
            butOpDelivery.setVisible(false);
            butOpDelivery.setText("CONFIRMAR CAMBIOS");
        }

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                salon.setEnabled(true);
            }
        });
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

    private void deliveryBacker() {
        String st = (String) comboDelis.getSelectedItem();
        deliAux = utili.userSelReturn(st, deliverys);
        setDeliveryFields(deliAux);
    }

    private void createDeliveryUser() throws Exception {
        DeliveryTemplate fnd = this;
        new UserExpressTemplate(fnd, "DELIVERY", null);
        fnd.setEnabled(false);
    }

    private void setDeliveryFields(User deli) {
        labelNameDeli.setText("Nombre: " + deli.getName());
        labelLastNameDeli.setText("Apellido: " + deli.getLastName());
        labelPhoneDeli.setText("Teléfono: " + deli.getPhone());
        labelMailDeli.setText("Mail: " + deli.getMail());
        labelRolDeli.setText("Ocupación: " + deli.getRol());
    }



    void getDeliUser(User user) {
        deliAux = user;
        setDeliveryFields(deliAux);
        deliverys.add(deliAux);
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
        comboDelis.setSelectedIndex(deliverys.size() - 1);
        if (deliFull != null) {
            butOpDelivery.setVisible(true);
        }
        setFndEnabled();
    }

    private void createDeliveryOrder() throws Exception {
//        if (deliAux != null && cmrAux != null) {
        if (cmrAux != null) {
            String id = "";
            if (deliAux != null) {
                id = deliAux.getId();
            }

            Delivery deliOrder = new Delivery(cmrAux.getPhone(), id);
            utiliGrafSal.getDeliOrder(deliOrder, salon);
            salon.setEnabled(true);
            dispose();

        } else {
//            if (deliAux == null) {
//                utiliMsg.errorDeliveryNull();
//            }

            if (cmrAux == null) {
                utiliMsg.errorConsumerNull();
            }
        }
    }

    private void updateDeliveryOrder() throws Exception {
        deliFull.setConsumer(cmrAux);
        deliFull.setDeli(deliAux);
        utiliGrafSal.setDeliOrder(deliFull, salon);
        salon.setEnabled(true);
        dispose();
    }

    private void changeConsumer() {
        labelSelectPhone.setVisible(true);
        comboConsumers.setVisible(true);
        butOpConsumer.setVisible(false);
        butUpdateConsumer.setVisible(false);
    }

    private void changeDeliveryUser() {
        labelSelectDeli.setVisible(true);
        comboDelis.setVisible(true);
        butOpDeliUser.setVisible(false);
        butUpdateDeliUser.setVisible(false);
    }

    private void updateConsumer() throws Exception {
        DeliveryTemplate fnd = this;
        new ConsumerTemplate(fnd, cmrAux);
        fnd.setEnabled(false);
    }

    private void updateDeliveryUser() throws Exception {
        DeliveryTemplate fnd = this;
        new UserExpressTemplate(fnd, "DELIVERY", deliAux);
        fnd.setEnabled(false);
    }

    
    
    public void setFndEnabled() {
        setEnabled(true);
    }

    public DAODeliveryConsumer getDaoC() {
        return daoC;
    }

    public void setDaoC(DAODeliveryConsumer daoC) {
        this.daoC = daoC;
    }

    public DAOUser getDaoU() {
        return daoU;
    }

    public void setDaoU(DAOUser daoU) {
        this.daoU = daoU;
    }

    public ImageIcon getIcono() {
        return icono;
    }

    public void setIcono(ImageIcon icono) {
        this.icono = icono;
    }

    public JComboBox getComboConsumers() {
        return comboConsumers;
    }

    public void setComboConsumers(JComboBox comboConsumers) {
        this.comboConsumers = comboConsumers;
    }

    public JComboBox getComboDelis() {
        return comboDelis;
    }

    public void setComboDelis(JComboBox comboDelis) {
        this.comboDelis = comboDelis;
    }

    public JPanel getPanelDetails() {
        return panelDetails;
    }

    public void setPanelDetails(JPanel panelDetails) {
        this.panelDetails = panelDetails;
    }

    public JLabel getLabelSelectPhone() {
        return labelSelectPhone;
    }

    public void setLabelSelectPhone(JLabel labelSelectPhone) {
        this.labelSelectPhone = labelSelectPhone;
    }

    public JLabel getLabelSelectDeli() {
        return labelSelectDeli;
    }

    public void setLabelSelectDeli(JLabel labelSelectDeli) {
        this.labelSelectDeli = labelSelectDeli;
    }

    public JLabel getLabelName() {
        return labelName;
    }

    public void setLabelName(JLabel labelName) {
        this.labelName = labelName;
    }

    public JLabel getLabelPhone() {
        return labelPhone;
    }

    public void setLabelPhone(JLabel labelPhone) {
        this.labelPhone = labelPhone;
    }

    public JLabel getLabelSn() {
        return labelSn;
    }

    public void setLabelSn(JLabel labelSn) {
        this.labelSn = labelSn;
    }

    public JLabel getLabelStreet() {
        return labelStreet;
    }

    public void setLabelStreet(JLabel labelStreet) {
        this.labelStreet = labelStreet;
    }

    public JLabel getLabelNumSt() {
        return labelNumSt;
    }

    public void setLabelNumSt(JLabel labelNumSt) {
        this.labelNumSt = labelNumSt;
    }

    public JLabel getLabelFloorD() {
        return labelFloorD;
    }

    public void setLabelFloorD(JLabel labelFloorD) {
        this.labelFloorD = labelFloorD;
    }

    public JLabel getLabelNumD() {
        return labelNumD;
    }

    public void setLabelNumD(JLabel labelNumD) {
        this.labelNumD = labelNumD;
    }

    public JLabel getLabelDistrict() {
        return labelDistrict;
    }

    public void setLabelDistrict(JLabel labelDistrict) {
        this.labelDistrict = labelDistrict;
    }

    public JLabel getLabelArea() {
        return labelArea;
    }

    public void setLabelArea(JLabel labelArea) {
        this.labelArea = labelArea;
    }

    public JLabel getLabelDetails() {
        return labelDetails;
    }

    public void setLabelDetails(JLabel labelDetails) {
        this.labelDetails = labelDetails;
    }

    public JLabel getLabelNameDeli() {
        return labelNameDeli;
    }

    public void setLabelNameDeli(JLabel labelNameDeli) {
        this.labelNameDeli = labelNameDeli;
    }

    public JLabel getLabelLastNameDeli() {
        return labelLastNameDeli;
    }

    public void setLabelLastNameDeli(JLabel labelLastNameDeli) {
        this.labelLastNameDeli = labelLastNameDeli;
    }

    public JLabel getLabelPhoneDeli() {
        return labelPhoneDeli;
    }

    public void setLabelPhoneDeli(JLabel labelPhoneDeli) {
        this.labelPhoneDeli = labelPhoneDeli;
    }

    public JLabel getLabelRolDeli() {
        return labelRolDeli;
    }

    public void setLabelRolDeli(JLabel labelRolDeli) {
        this.labelRolDeli = labelRolDeli;
    }

    public JLabel getLabelMailDeli() {
        return labelMailDeli;
    }

    public void setLabelMailDeli(JLabel labelMailDeli) {
        this.labelMailDeli = labelMailDeli;
    }

    public ArrayList<String> getConsumers() {
        return consumers;
    }

    public void setConsumers(ArrayList<String> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<User> getDeliverys() {
        return deliverys;
    }

    public void setDeliverys(ArrayList<User> deliverys) {
        this.deliverys = deliverys;
    }

    public JButtonMetalBlu getButOpConsumer() {
        return butOpConsumer;
    }

    public void setButOpConsumer(JButtonMetalBlu butOpConsumer) {
        this.butOpConsumer = butOpConsumer;
    }

    public JButtonMetalBlu getButOpDeliUser() {
        return butOpDeliUser;
    }

    public void setButOpDeliUser(JButtonMetalBlu butOpDeliUser) {
        this.butOpDeliUser = butOpDeliUser;
    }

    public JButtonMetalBlu getButUpdateConsumer() {
        return butUpdateConsumer;
    }

    public void setButUpdateConsumer(JButtonMetalBlu butUpdateConsumer) {
        this.butUpdateConsumer = butUpdateConsumer;
    }

    public JButtonMetalBlu getButUpdateDeliUser() {
        return butUpdateDeliUser;
    }

    public void setButUpdateDeliUser(JButtonMetalBlu butUpdateDeliUser) {
        this.butUpdateDeliUser = butUpdateDeliUser;
    }

    public JButtonMetalBlu getButOpDelivery() {
        return butOpDelivery;
    }

    public void setButOpDelivery(JButtonMetalBlu butOpDelivery) {
        this.butOpDelivery = butOpDelivery;
    }

    public DeliveryConsumer getCmrAux() {
        return cmrAux;
    }

    public void setCmrAux(DeliveryConsumer cmrAux) {
        this.cmrAux = cmrAux;
    }

    public User getDeliAux() {
        return deliAux;
    }

    public void setDeliAux(User deliAux) {
        this.deliAux = deliAux;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Delivery getDeliFull() {
        return deliFull;
    }

    public void setDeliFull(Delivery deliFull) {
        this.deliFull = deliFull;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }
    
    
    
    
    
    

    
}
