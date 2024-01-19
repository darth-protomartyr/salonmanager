package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class UserExpressTemplate extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOUser daoU = new DAOUser();

    DeliveryTemplate fnd = null;

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color narUlgX = new Color(255, 255, 210);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);
    User userIn = null;

    String name = "";
    String lastName = "";
    String phone = "";
    String mail = "";
    String rol = "";

    User userAux = new User();
    User userFull = new User();
    ArrayList<String> mailUsers = null;

    JTextField fieldName = new JTextField();
    JTextField fieldLastName = new JTextField();
    JTextField fieldPhone = new JTextField();
    JTextField fieldMail = new JTextField();
    JTextField fieldRol = new JTextField();

    JButton butCreateUserExpress = null;

    public UserExpressTemplate(DeliveryTemplate f, String r, User u) throws Exception {
        rol = r;
        String tit = "";
        if (u != null) {
            userFull = u;
            tit = "Modificar " + r;
        } else {
            tit = "Alta " + r;
        }

        setTitle(tit);
        fnd = f;
        mailUsers = daoU.listarUserMails();

        setBounds(0, 0, anchoFrame / 2, alturaFrame);

        PanelPpal panelPpal = new PanelPpal(anchoFrame / 2, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1(tit);
        labelTit.setBounds(10, 15, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelForm = new JPanel();

        panelForm.setLayout(null);
        panelForm.setBounds(anchoUnit * 8, altoUnit * 8, anchoUnit * 37, altoUnit * 77);
        panelForm.setBackground(narLg);

        JPanel panelData1 = utiliGraf.dataPanelBacker("Nombre: ", 14);
        panelData1.setBounds(anchoUnit * 2, altoUnit * 5, anchoUnit * 33, altoUnit * 7);
        fieldName.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 25, altoUnit * 5);
        panelData1.add(fieldName);
        panelForm.add(panelData1);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Apellido: ", 14);
        panelData2.setBounds(anchoUnit * 2, altoUnit * 20, anchoUnit * 33, altoUnit * 7);
        fieldLastName.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 25, altoUnit * 5);
        panelData2.add(fieldLastName);
        panelForm.add(panelData2);

        JPanel panelData3 = utiliGraf.dataPanelBacker("Teléfono: ", 14);
        panelData3.setBounds(anchoUnit * 2, altoUnit * 35, anchoUnit * 33, altoUnit * 7);
        fieldPhone.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 25, altoUnit * 5);
        panelData3.add(fieldPhone);
        panelForm.add(panelData3);

        JPanel panelData4 = utiliGraf.dataPanelBacker("Mail: ", 14);
        panelData4.setBounds(anchoUnit * 2, altoUnit * 50, anchoUnit * 33, altoUnit * 7);
        fieldMail.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 25, altoUnit * 5);
        panelData4.add(fieldMail);
        panelForm.add(panelData4);

        JPanel panelData5 = utiliGraf.dataPanelBacker("Rol: ", 14);
        panelData5.setBounds(anchoUnit * 2, altoUnit * 65, anchoUnit * 33, altoUnit * 7);
        fieldRol.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 25, altoUnit * 5);
        panelData5.add(fieldRol);
        fieldRol.setText(rol);
        fieldRol.setEditable(false);
        panelForm.add(panelData5);

        panelPpal.add(panelForm);

        butCreateUserExpress = utiliGraf.button1("Crear Usuario " + r, anchoUnit * 14, altoUnit * 88, anchoUnit * 25);

        if (userFull != null) {
            butCreateUserExpress.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        userExpressOp(2);
                    } catch (Exception ex) {
                        Logger.getLogger(ConsumerTemplate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            butCreateUserExpress.setText("Modificar Usuario" + r);
        } else {
            butCreateUserExpress.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        userExpressOp(1);
                    } catch (Exception ex) {
                        Logger.getLogger(ConsumerTemplate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        panelPpal.add(butCreateUserExpress);

        JButton butSalir = utiliGraf.buttonSalir(anchoFrame / 2);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fnd.setFndEnabled();
                dispose();
            }
        });
        panelPpal.add(butSalir);

        if (userFull != null) {
            fieldName.setText(userFull.getName());
            fieldLastName.setText(userFull.getLastName());
            fieldPhone.setText(userFull.getPhone());
            fieldMail.setText(userFull.getMail());
            fieldRol.setText(userFull.getRol());
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fnd.setFndEnabled();
                dispose();
            }
        });
    }

    private void userExpressOp(int i) throws Exception {
        boolean error = false;
        name = fieldName.getText();
        lastName = fieldLastName.getText();
        phone = fieldPhone.getText();
        mail = fieldMail.getText();

        if (name.length() > 30) {
            error = true;
            utiliMsg.errorCantCharNameUser();
        }

        if (lastName.length() > 30) {
            error = true;
            utiliMsg.errorCantCharNameUser();
        }

        if (phone.length() > 30 || phone.length() < 5) {
            utiliMsg.errorPhoneNumber();
            fieldPhone.setText("");
            error = true;
        } else {
            boolean charBool = false;
            for (char c : phone.toCharArray()) {
                if (!(Character.isDigit(c) || c == ' ' || c == '+' || c == '-')) {
                    charBool = true;
                }
            }

            if (charBool) {
                utiliMsg.errorPhoneNumber();
                fieldPhone.setText("");
                error = true;
            }
        }

        if (i == 1) {
            if (utili.stringRepeat(mail, mailUsers)) {
                error = true;
                utiliMsg.errorMailRepeat();
            }
        }

        if (name.length() > 60 || name.length() < 2 || lastName.length() > 60 || lastName.length() < 2 || phone.length() > 19 || phone.length() < 7 || mail.length() > 60 || mail.length() < 6) {
            error = true;
            utiliMsg.errorCantCharDescription();
        }

        if (name.equals("") || lastName.equals("") || phone.equals("") || mail.equals("")) {
            error = true;
            utiliMsg.errorEmptyFields();
        }

        if (error == false) {
            userAux = new User(name, lastName, phone, mail, rol);
            if (i == 1) {
                daoU.saveUser(userAux);
            } else {
                daoU.updateUser(userAux, userFull.getId());
            }
            fnd.getDeliUser(userAux);
            fnd.setFndEnabled();
            dispose();
        } else {
            resetFields();
        }
    }

    private void resetFields() throws Exception {
        fieldName.setText("");
        fieldPhone.setText("");
        fieldLastName.setText("");
        fieldMail.setText("");
    }
}
