package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import salonmanager.utilidades.UtilidadesGraficasSalon;
import salonmanager.utilidades.UtilidadesMensajes;

public class DeliveryTemplate extends FrameHalf {

    DAODeliveryConsumer daoC = new DAODeliveryConsumer();
    DAOUser daoU = new DAOUser();

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
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
            deliAux = deli.getDeli();
            cmrAux = deli.getConsumer();
        }
        setIconImage(icono.getImage());

        String title = "";
        if (deliFull == null) {
            title = "INICIAR ENVIO";
        } else {
            title = "DATOS ENVIO";
        }

        setTitle(title);

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W(title);
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        //---------------------------------------DeliveryConsumer
        JPanel panelConsumer = panelBacker("Cliente", anchoUnit, altoUnit * 7, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 8));
        panelPpal.add(panelConsumer);

        labelSelectPhone = utiliGraf.labelTitleBacker1("Elija Cliente según teléfono:");
        labelSelectPhone.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 23, altoUnit * 4);
        panelConsumer.add(labelSelectPhone);

        consumers = daoC.getConsumersPhone();
        comboConsumers.setModel(utili.consumerComboModelReturnWNull(consumers));
        comboConsumers.setBounds(anchoUnit * 24, altoUnit * 7, anchoUnit * 12, altoUnit * 4);
        comboConsumers.setSelectedIndex(consumers.size());
        panelConsumer.add(comboConsumers);

        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consumerPhoneBacker();
                    if (deliFull != null) {
                        butOpDelivery.setVisible(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(DeliveryTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        comboConsumers.addActionListener(actionListener1);
        if (deliFull != null) {
            labelSelectPhone.setVisible(false);
            comboConsumers.setVisible(false);
        }

        butOpConsumer = utiliGraf.button2("CREAR CLIENTE", anchoUnit * 38, altoUnit * 7, anchoUnit * 11);
        if (deliFull == null) {
            butOpConsumer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        createConsumer();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        } else {
            butOpConsumer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        changeConsumer();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            butOpConsumer.setLocation(anchoUnit * 20, altoUnit * 7);
            butOpConsumer.setText("CAMBIAR CLIENTE");
        }
        panelConsumer.add(butOpConsumer);

        if (deliFull != null) {
            butUpdateConsumer = utiliGraf.button3("MODIFICAR DATOS", anchoUnit * 38, altoUnit * 8, anchoUnit * 10);
            butUpdateConsumer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        updateConsumer();
                    } catch (Exception ex) {
                        Logger.getLogger(CustomDialogConfirm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            panelConsumer.add(butUpdateConsumer);
        }

        labelName = utiliGraf.labelTitleBacker2("Nombre:");
        labelName.setBounds(anchoUnit * 5, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelName);

        labelPhone = utiliGraf.labelTitleBacker2("Teléfono:");
        labelPhone.setBounds(anchoUnit * 5, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelPhone);

        labelSn = utiliGraf.labelTitleBacker2("Red Social:");
        labelSn.setBounds(anchoUnit * 5, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelSn);

        labelStreet = utiliGraf.labelTitleBacker2("Calle:");
        labelStreet.setBounds(anchoUnit * 5, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelStreet);

        labelNumSt = utiliGraf.labelTitleBacker2("Numéro:");
        labelNumSt.setBounds(anchoUnit * 5, altoUnit * 33, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelNumSt);

        labelFloorD = utiliGraf.labelTitleBacker2("Piso:");
        labelFloorD.setBounds(anchoUnit * 5, altoUnit * 38, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelFloorD);

        labelNumD = utiliGraf.labelTitleBacker2("Nro departamento:");
        labelNumD.setBounds(anchoUnit * 30, altoUnit * 13, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelNumD);

        labelDistrict = utiliGraf.labelTitleBacker2("Barrio:");
        labelDistrict.setBounds(anchoUnit * 30, altoUnit * 18, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelDistrict);

        labelArea = utiliGraf.labelTitleBacker2("Localidad:");
        labelArea.setBounds(anchoUnit * 30, altoUnit * 23, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelArea);

        JLabel labelDetailsTit = utiliGraf.labelTitleBacker2("Detalles Domicilio:");
        labelDetailsTit.setBounds(anchoUnit * 30, altoUnit * 28, anchoUnit * 22, altoUnit * 3);
        panelConsumer.add(labelDetailsTit);

        panelDetails.setBackground(narLg);

//        String details = utili.stringMsgFrd("", 25, 1);
        labelDetails = utiliGraf.labelTitleBacker3("");
        panelDetails.add(labelDetails);

        JScrollPane scrollPaneConsumer = new JScrollPane(panelDetails);
        scrollPaneConsumer.setBounds(anchoUnit * 30, altoUnit * 31, anchoUnit * 17, altoUnit * 10);
        panelConsumer.add(scrollPaneConsumer);

        if (deliFull != null) {
            setConsumerFields(cmrAux);
        }

        //---------------------------------------Delivery
        JPanel panelDelivery = panelBacker("Delivery", anchoUnit, alturaFrame / 2 + altoUnit, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 15));
        panelPpal.add(panelDelivery);

        labelSelectDeli = utiliGraf.labelTitleBacker1("Elija Delivery para envíar:");
        labelSelectDeli.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
        panelDelivery.add(labelSelectDeli);

        deliverys = daoU.listUserByRol("DELIVERY");
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
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
            labelSelectDeli.setVisible(false);
            comboDelis.setVisible(false);
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

        butOpDelivery = utiliGraf.button1("CREAR ENVÏO", anchoUnit * 17, altoUnit * 90, anchoUnit * 20);
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

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                salon.setEnabled(true);
            }
        });
    }

//    public static void main(String[] args) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    DeliveryTemplate fnd = new DeliveryTemplate();
//                    fnd.setVisible(true);
//                } catch (Exception ex) {
//                    Logger.getLogger(DeliveryTemplate.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }
    private JPanel panelBacker(String tit, int i, int i0, int i1, int i2) {
        JPanel panel = new JPanel(null);
        panel.setBounds(i, i0, i1, i2);
        panel.setBackground(narLg);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(narLg);
        panelLabel.setBounds(0, 0, anchoFrame / 2, 40);
        panel.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBackerA4(tit);
        panelLabel.add(labelTit);

        return panel;
    }

    private void consumerPhoneBacker() throws Exception {
        String st = (String) comboConsumers.getSelectedItem();
        cmrAux = daoC.getConsumerByPhone(st);
        setConsumerFields(cmrAux);
    }

    private void deliveryBacker() {
        String st = (String) comboDelis.getSelectedItem();
        deliAux = utili.userSelReturn(st, deliverys);
        setDeliveryFields(deliAux);
    }

    private void createConsumer() throws Exception {
        DeliveryTemplate fnd = this;
        new ConsumerTemplate(fnd, null);
        fnd.setEnabled(false);
    }

    private void createDeliveryUser() throws Exception {
        DeliveryTemplate fnd = this;
        new UserExpressTemplate(fnd, "DELIVERY", null);
        fnd.setEnabled(false);
    }

    private void setConsumerFields(DeliveryConsumer cmr) {
        labelName.setText("Nombre: " + cmr.getName());
        labelPhone.setText("Teléfono: " + cmr.getPhone());
        labelSn.setText("Red Social: " + cmr.getSocialNetwork());
        labelStreet.setText("Calle: " + cmr.getStreet());
        labelNumSt.setText("Número: " + cmr.getNumSt());
        labelFloorD.setText("Piso: " + cmr.getDeptFloor());
        labelNumD.setText("Nro de Departamento: " + cmr.getDeptNum());
        labelDistrict.setText("Barrio: " + cmr.getDistrict());
        labelArea.setText("Localidad: " + cmr.getArea());
        String details = utili.stringMsgFrd(cmr.getDetails(), 25, 1);
        labelDetails.setText(details);
        revalidate();
        repaint();
    }

    private void setDeliveryFields(User deli) {
        labelNameDeli.setText("Nombre: " + deli.getName());
        labelLastNameDeli.setText("Apellido: " + deli.getLastName());
        labelPhoneDeli.setText("Teléfono: " + deli.getPhone());
        labelMailDeli.setText("Mail: " + deli.getMail());
        labelRolDeli.setText("Ocupación: " + deli.getRol());
    }

    public void getConsumer(DeliveryConsumer cmr) {
        cmrAux = cmr;
        setConsumerFields(cmrAux);
        consumers.add(cmrAux.getPhone());
        comboConsumers.setModel(utili.consumerComboModelReturnWNull(consumers));
        comboConsumers.setSelectedIndex(consumers.size() - 1);
        if (deliFull != null) {
            butOpDelivery.setVisible(true);
        }
        setFndEnabled();
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
        if (deliAux != null && cmrAux != null) {
            Delivery deliOrder = new Delivery(cmrAux.getPhone(), deliAux.getId());
            utiliGrafSal.getDeliOrder(deliOrder, salon);
            salon.setEnabled(true);
            dispose();

        } else {
            if (deliAux == null) {
                utiliMsg.errorDeliveryNull();
            }

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
}
