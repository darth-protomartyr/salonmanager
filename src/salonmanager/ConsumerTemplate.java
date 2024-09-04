package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import salonmanager.entidades.bussiness.DeliveryClient;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.alturaFrame;
import static salonmanager.entidades.graphics.FrameGeneral.anchoFrame;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasDeliCreate;
import salonmanager.utilidades.UtilidadesGraficasDeliData;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConsumerTemplate extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasDeliCreate utiliGrafDC = new UtilidadesGraficasDeliCreate();
    UtilidadesGraficasDeliData utiliGrafDD = new UtilidadesGraficasDeliData();

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAODeliveryClient daoC = new DAODeliveryClient();

    DeliveryData dd = null;
    DeliveryCreate dc = null;

    Color narLg = new Color(252, 203, 5);

    String street = "";
    String streetNum = "";
    String deptFloor = "";
    String deptNum = "";
    String district = "";
    String area = "";
    String details = "";
    String name = "";
    String lastname = "";
    String phone = "";
    String socialNetwork = "";

    DeliveryClient cmrAux = new DeliveryClient();
    ArrayList<String> phonesCmrs = null;

    JTextField fieldStreet = new JTextField();
    JTextField fieldNumStreet = new JTextField();
    JTextField fieldDeptFloor = new JTextField();
    JTextField fieldDeptNum = new JTextField();
    JTextField fieldDistrict = new JTextField();
    JTextField fieldArea = new JTextField();
    JTextArea areaDetails = new JTextArea();
    JTextField fieldName = new JTextField();
    JTextField fieldLastName = new JTextField();
    JTextField fieldPhone = new JTextField();
    JTextField fieldSN = new JTextField();

    JButtonMetalBlu butCreateConsumer = null;
    DeliveryClient cmrFull = null;

    public ConsumerTemplate(DeliveryCreate dCr, DeliveryData dDa, DeliveryClient cmr) throws Exception {

        String tit = "";
        if (cmr != null) {
            cmrFull = cmr;
            tit = "Modificar Cliente";
        } else {
            tit = "Alta Cliente";
        }

        if (dCr != null) {
            dc = dCr;
        }

        if (dDa != null) {
            dd = dDa;
        }

        setTitle(tit);

        phonesCmrs = daoC.getConsumersPhone();

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W(tit.toUpperCase());
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 40, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(null);
        panelForm.setBounds(anchoUnit * 3, altoUnit * 8, anchoUnit * 45, altoUnit * 77);
        panelForm.setBackground(narLg);

        JPanel panelData1a = utiliGraf.dataPanelBacker("Nombre: ", 14);
        panelData1a.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 21, altoUnit * 7);
        fieldName.setBounds(anchoUnit * 6, altoUnit * 1, anchoUnit * 14, altoUnit * 5);
        panelData1a.add(fieldName);
        panelForm.add(panelData1a);

        JPanel panelData1b = utiliGraf.dataPanelBacker("Apellido: ", 14);
        panelData1b.setBounds(anchoUnit * 23, altoUnit * 5, anchoUnit * 21, altoUnit * 7);
        fieldLastName.setBounds(anchoUnit * 6, altoUnit * 1, anchoUnit * 14, altoUnit * 5);
        panelData1b.add(fieldLastName);
        panelForm.add(panelData1b);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Teléfono: ", 14);
        panelData2.setBounds(anchoUnit * 1, altoUnit * 18, anchoUnit * 17, altoUnit * 7);
        fieldPhone.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 9, altoUnit * 5);
        panelData2.add(fieldPhone);
        panelForm.add(panelData2);

        JPanel panelData3 = utiliGraf.dataPanelBacker("Red Social:", 14);
        panelData3.setBounds(anchoUnit * 19, altoUnit * 18, anchoUnit * 25, altoUnit * 7);
        fieldSN.setBounds(anchoUnit * 8, altoUnit * 1, anchoUnit * 16, altoUnit * 5);
        panelData3.add(fieldSN);
        panelForm.add(panelData3);

        JPanel panelData4 = utiliGraf.dataPanelBacker("Calle:", 14);
        panelData4.setBounds(anchoUnit * 1, altoUnit * 31, anchoUnit * 18, altoUnit * 7);
        fieldStreet.setBounds(anchoUnit * 5, altoUnit * 1, anchoUnit * 12, altoUnit * 5);
        panelData4.add(fieldStreet);
        panelForm.add(panelData4);

        JPanel panelData5 = utiliGraf.dataPanelBacker("Nro:", 14);
        panelData5.setBounds(anchoUnit * 20, altoUnit * 31, anchoUnit * 8, altoUnit * 7);
        fieldNumStreet.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 3, altoUnit * 5);
        panelData5.add(fieldNumStreet);
        panelForm.add(panelData5);

        JPanel panelData6 = utiliGraf.dataPanelBacker("Piso:", 14);
        panelData6.setBounds(anchoUnit * 29, altoUnit * 31, anchoUnit * 7, altoUnit * 7);
        fieldDeptFloor.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 2, altoUnit * 5);
        panelData6.add(fieldDeptFloor);
        panelForm.add(panelData6);

        JPanel panelData7 = utiliGraf.dataPanelBacker("Dpto: ", 14);
        panelData7.setBounds(anchoUnit * 37, altoUnit * 31, anchoUnit * 7, altoUnit * 7);
        fieldDeptNum.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 2, altoUnit * 5);
        panelData7.add(fieldDeptNum);
        panelForm.add(panelData7);

        JPanel panelData8 = utiliGraf.dataPanelBacker("Barrio:", 14);
        panelData8.setBounds(anchoUnit * 1, altoUnit * 44, anchoUnit * 20, altoUnit * 7);
        fieldDistrict.setBounds(anchoUnit * 5, altoUnit * 1, anchoUnit * 14, altoUnit * 5);
        panelData8.add(fieldDistrict);
        panelForm.add(panelData8);

        JPanel panelData9 = utiliGraf.dataPanelBacker("Localidad: ", 14);
        panelData9.setBounds(anchoUnit * 22, altoUnit * 44, anchoUnit * 22, altoUnit * 7);
        fieldArea.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 14, altoUnit * 5);
        panelData9.add(fieldArea);
        panelForm.add(panelData9);

        JPanel panelData10 = utiliGraf.dataPanelBacker("Ingrese detalles del domicilio: ", 14);
        panelData10.setBounds(anchoUnit * 1, altoUnit * 57, anchoUnit * 43, altoUnit * 16);
        areaDetails.setColumns(8);
        areaDetails.setLineWrap(true);
        areaDetails.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        areaDetails.setFont(newFont);
        JScrollPane scrollPane = new JScrollPane(areaDetails);
        scrollPane.setBounds(anchoUnit * 18, altoUnit * 1, anchoUnit * 24, altoUnit * 14);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelData10.add(scrollPane);
        panelForm.add(panelData10);

        panelPpal.add(panelForm);

        butCreateConsumer = utiliGraf.button1("Crear Cliente", 206, 600, 270);
        if (cmrFull != null) {
            butCreateConsumer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        consumerOp(2);
                    } catch (Exception ex) {
                        Logger.getLogger(ConsumerTemplate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            butCreateConsumer.setText("Modificar Cliente");
        } else {
            butCreateConsumer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        consumerOp(1);
                    } catch (Exception ex) {
                        Logger.getLogger(ConsumerTemplate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        panelPpal.add(butCreateConsumer);

        if (cmrFull != null) {
            fieldStreet.setText(cmrFull.getStreet());
            fieldNumStreet.setText(cmrFull.getNumSt());
            fieldDeptFloor.setText(cmrFull.getDeptFloor());
            fieldDeptNum.setText(cmrFull.getDeptNum());
            fieldDistrict.setText(cmrFull.getDistrict());
            fieldArea.setText(cmrFull.getArea());
            areaDetails.setText(cmrFull.getDetails());
            fieldName.setText(cmrFull.getName());
            fieldLastName.setText(cmrFull.getLastname());
            fieldPhone.setText(cmrFull.getPhone());
            fieldSN.setText(cmrFull.getSocialNetwork());
        }

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (dd != null) {
                    dd.setFndEnabled();
                } else {
                    dc.setFndEnabled();
                }
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (dd != null) {
                    dd.setFndEnabled();
                } else {
                    dc.setFndEnabled();
                }
                dispose();
            }
        });
    }

    private void consumerOp(int i) throws Exception {
        boolean error = false;
        name = fieldName.getText();
        lastname = fieldLastName.getText();
        phone = fieldPhone.getText();
        street = fieldStreet.getText();
        streetNum = fieldNumStreet.getText();
        deptFloor = fieldDeptFloor.getText();
        deptNum = fieldDeptNum.getText();
        district = fieldDistrict.getText();
        area = fieldArea.getText();
        details = areaDetails.getText();
        socialNetwork = fieldSN.getText();
        int id = daoC.getConsumerId();

        if (name.length() > 60 || name.length() < 2) {
            error = true;
            utiliMsg.errorCantCharName();
        }

        if (lastname.length() > 60 || lastname.length() < 2) {
            error = true;
            utiliMsg.errorCantCharName();
        }
        
        if (socialNetwork.length() > 60 ) {
            error = true;
            utiliMsg.errorCantCharName();
        }
        

        if (i == 0) {
            if (utili.stringRepeat(phone, phonesCmrs)) {
                error = true;
                utiliMsg.errorNameRepeat();
            }
        }

        if (details.length() > 150 || name.length() > 60 || name.length() < 2 || lastname.length() > 60 || lastname.length() < 2
                || phone.length() > 19 || phone.length() < 7 || street.length() > 60
                || streetNum.length() > 6 || deptFloor.length() > 3 || deptNum.length() > 6 || district.length() > 40
                || area.length() > 40 || socialNetwork.length() > 50) {
            error = true;
            utiliMsg.errorCantCharDescription();
        }

        if (name.equals("") || lastname.equals("") || phone.equals("")) {
            error = true;
            utiliMsg.errorEmptyFieldsCmr();
        }

        if (details.equals("")) {
            details = "--";
        }

        if (deptFloor.equals("")) {
            deptFloor = "--";
        }

        if (deptNum.equals("")) {
            deptNum = "--";
        }

        if (district.equals("")) {
            district = "--";
        }

        if (area.equals("")) {
            area = "--";
        }

        if (socialNetwork.equals("")) {
            socialNetwork = "--";
        }

        if (error == false) {
            cmrAux = new DeliveryClient(id, street, streetNum, deptFloor, deptNum, district, area, details, name, lastname, phone, socialNetwork);
            if (i == 1) {
                daoC.saveConsumer(cmrAux);
            } else {
                daoC.updateConsumer(cmrAux, cmrFull.getId());
            }

            if (dc != null) {
                utiliGrafDC.getConsumer(cmrAux, dc);
                dc.setFndEnabled();
            }

            if (dd != null) {
                utiliGrafDD.getConsumer(cmrAux, dd);
                dd.setFndEnabled();
            }
            dispose();
        } else {
            resetFields();
        }
    }

    private void resetFields() throws Exception {
        fieldName.setText("");
        fieldLastName.setText("");
        fieldPhone.setText("");
        fieldStreet.setText("");
        fieldNumStreet.setText("");
        fieldDeptFloor.setText("");
        fieldDeptNum.setText("");
        fieldDistrict.setText("");
        fieldArea.setText("");
        areaDetails.setText("");
        fieldSN.setText("");
    }
}