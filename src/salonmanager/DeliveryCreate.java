package salonmanager;

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
import salonmanager.entidades.bussiness.DeliveryClient;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.entidades.bussiness.Delivery;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasDeliCreate;
import salonmanager.utilidades.UtilidadesGraficasSalon;
import salonmanager.utilidades.UtilidadesMensajes;

public class DeliveryCreate extends FrameHalf {

    DAODeliveryClient daoC = new DAODeliveryClient();
    DAOUser daoU = new DAOUser();

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasDeliCreate utiliGrafDT = new UtilidadesGraficasDeliCreate();

    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();

    Utilidades utili = new Utilidades();
    ImageIcon icono = new ImageIcon("menu.png");

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

    ArrayList<String> consumers = new ArrayList<>();
    ArrayList<User> deliverys = new ArrayList<>();

    JButtonMetalBlu butOpConsumer = null;
    JButtonMetalBlu butOpDeliUser = null;
    JButtonMetalBlu butUpdateConsumer = null;
    JButtonMetalBlu butUpdateDeliUser = null;

    JButtonMetalBlu butOpDelivery = null;

    DeliveryCreate dc = null;
    DeliveryClient cmrAux = null;
    User deliUserAux = null;
    Salon salon = null;
    boolean change = false;

    public DeliveryCreate(Salon sal) throws Exception {
        salon = sal;
        setIconImage(icono.getImage());
        dc = this;

        String title = "";
        title = "INICIAR ENVIO";
        

        consumers = daoC.getConsumersPhone();
        deliverys = daoU.listUserByRol("DELIVERY");

        setTitle(title);

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W(title);
        labelTit.setBounds(anchoUnit, altoUnit * 2, anchoUnit * 12, altoUnit * 3);
        panelPpal.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        //---------------------------------------DeliveryConsumer
        JPanel panelConsumer = utiliGrafDT.panelConsumerBacker(this);
        panelPpal.add(panelConsumer);

        //---------------------------------------Delivery
        JPanel panelDelivery = utiliGrafDT.panelDeliveryBacker(this);
        panelPpal.add(panelDelivery);
        
        butOpDelivery = utiliGraf.button1("CREAR ENVÍO", anchoUnit * 17, altoUnit * 90, anchoUnit * 16);
 
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

        panelPpal.add(butOpDelivery);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                salon.setEnabled(true);
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

    void getDeliUser(User user) {
        deliUserAux = user;
        DeliveryCreate dt = this;
        utiliGrafDT.setDeliveryFields(deliUserAux, dt);
        deliverys.add(deliUserAux);
        comboDelis.setModel(utili.userComboModelReturnWNull(deliverys));
        comboDelis.setSelectedIndex(deliverys.size() - 1);

        setFndEnabled();
    }

    private void createDeliveryOrder() throws Exception {
        if (cmrAux != null) {
            String id = "";
            if (deliUserAux != null) {
                id = deliUserAux.getId();
            }

            Delivery deliOrder = new Delivery(cmrAux.getPhone(), id);
            utiliGrafSal.getDeliOrder(deliOrder, salon);
            salon.setEnabled(true);
            dispose();

        } else {
            utiliMsg.errorConsumerNull();
        }
    }

    public void setFndEnabled() {
        setEnabled(true);
    }

    public DAODeliveryClient getDaoC() {
        return daoC;
    }

    public void setDaoC(DAODeliveryClient daoC) {
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

    public DeliveryClient getCmrAux() {
        return cmrAux;
    }

    public void setCmrAux(DeliveryClient cmrAux) {
        this.cmrAux = cmrAux;
    }

    public User getDeliAux() {
        return deliUserAux;
    }

    public void setDeliAux(User deliAux) {
        this.deliUserAux = deliAux;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
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

    public DeliveryCreate getDc() {
        return dc;
    }

    public void setDc(DeliveryCreate dc) {
        this.dc = dc;
    }
}
