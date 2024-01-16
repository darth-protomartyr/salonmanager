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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.ConsumerTemplate;
import salonmanager.Salon;
import salonmanager.entidades.DeliveryConsumer;
import salonmanager.entidades.CustomDialogConfirm;
import salonmanager.entidades.Delivery;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOConsumer;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class DeliveryTemplate extends FrameHalf {

    DAOConsumer daoC = new DAOConsumer();
    DAOUser daoU = new DAOUser();

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
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

    DeliveryConsumer cmrAux = new DeliveryConsumer();
    User deliAux = new User();
    Salon salon = null;
    Delivery deliFull = null;

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
            title = "Iniciar Envío";
        } else {
            title = "Datos Envío";
        }

        setTitle(title);

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(anchoFrame / 2, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1(title);
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        //---------------------------------------DeliveryConsumer
        JPanel panelConsumer = panelBacker("Cliente", anchoUnit, altoUnit * 7, (anchoFrame / 2) - anchoUnit * 3, (alturaFrame / 2 - altoUnit * 8));
        panelPpal.add(panelConsumer);

        JLabel labelSelectPhone = utiliGraf.labelTitleBacker1("Elija Cliente según teléfono:");
        labelSelectPhone.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 23, altoUnit * 4);
        panelConsumer.add(labelSelectPhone);

        consumers = daoC.getConsumersPhone();
        comboConsumers.setModel(utili.consumerComboModelReturnWNull(consumers));
        comboConsumers.setBounds(anchoUnit * 24, altoUnit * 7, anchoUnit * 13, altoUnit * 4);
        comboConsumers.setSelectedIndex(consumers.size());
        panelConsumer.add(comboConsumers);

        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consumerPhoneBacker();
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

        JButton consumerButton = utiliGraf.button2("Crear Cliente", anchoUnit * 38, altoUnit * 7, anchoUnit * 11);
        if (deliFull == null) {
            consumerButton.addActionListener(new ActionListener() {
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
            consumerButton.addActionListener(new ActionListener() {
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
            consumerButton.setLocation(anchoUnit * 20, altoUnit * 7);
            consumerButton.setText("CAMBIAR CLIENTE");
        }
        panelConsumer.add(consumerButton);
        
        if (deliFull != null) {
            JButton consumerUpdButton = utiliGraf.button3("Modificar datos Cliente", anchoUnit * 35, altoUnit * 8, anchoUnit * 14);
            consumerUpdButton.addActionListener(new ActionListener() {
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
            panelConsumer.add(consumerUpdButton);
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

        JLabel labelSelectDeli = utiliGraf.labelTitleBacker1("Elija Delivery para envíar:");
        labelSelectDeli.setBounds(anchoUnit * 3, altoUnit * 7, anchoUnit * 19, altoUnit * 4);
        panelDelivery.add(labelSelectDeli);

        deliverys = daoU.listUserByRol("DELIVERY");
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
        comboDelis.setBounds(anchoUnit * 22, altoUnit * 7, anchoUnit * 13, altoUnit * 4);
        comboDelis.setSelectedIndex(deliverys.size());
        panelDelivery.add(comboDelis);

        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryBacker();
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

        JButton deliveryButton = utiliGraf.button2("Crear Conductor", anchoUnit * 36, altoUnit * 7, anchoUnit * 14);
        if (deliFull == null) {
            deliveryButton.addActionListener(new ActionListener() {
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
            deliveryButton.addActionListener(new ActionListener() {
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
            deliveryButton.setLocation(anchoUnit * 20, altoUnit * 7);
            deliveryButton.setText("CAMBIAR CONDUCTOR");
        }
        panelDelivery.add(deliveryButton);
        
        if (deliFull != null) {
            JButton deliUserUpdButton = utiliGraf.button3("Modificar datos Conductor", anchoUnit * 35, altoUnit * 8, anchoUnit * 14);
            deliUserUpdButton.addActionListener(new ActionListener() {
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
            panelDelivery.add(deliUserUpdButton);
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

        if (deliAux != null) {
           setDeliveryFields(deliAux);
        }

        JButton createOrderButton = utiliGraf.button1("Crear Envío", anchoUnit * 17, altoUnit * 90, anchoUnit * 20);
        createOrderButton.addActionListener(new ActionListener() {
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
        if (deliAux == null) { 
            panelPpal.add(createOrderButton);
        }
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                salon.setSalonEnabled();
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
        setFndEnabled();
    }

    void getDeliUser(User user) {
        deliAux = user;
        setDeliveryFields(deliAux);
        deliverys.add(deliAux);
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
        comboDelis.setSelectedIndex(deliverys.size() - 1);
        setFndEnabled();
    }

    private void createDeliveryOrder() throws Exception {
        if (deliAux != null && cmrAux != null) {
            Delivery deliOrder = new Delivery( cmrAux.getPhone(), deliAux.getId());
            salon.getDeliOrder(deliOrder);
            salon.setSalonEnabled();
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
    
    private void updateConsumer() {

    }

    private void updateDeliveryUser() {

    }
    
    public void setFndEnabled() {
        setEnabled(true);
    }
}
