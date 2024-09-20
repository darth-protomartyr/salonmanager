package salonmanager;

import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.utilidades.UtilidadesGraficasAdmin;

public class TemplateUser extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesGraficasAdmin utiliGrafAdm = new UtilidadesGraficasAdmin();
    Utilidades utili = new Utilidades();
    ServicioUser su = new ServicioUser();
    DAOUser daoU = new DAOUser();
    Color bluLg = new Color(194, 242, 206);
    Font newFont = new Font("Arial", Font.BOLD, 17);

    String name = "";
    String lastName = "";
    String mail = "";
    String routeImage = "";
    String nameImage = "";
    String pass1 = "";
    String pass2 = "";
    String phone = "";
    File selectedFile = null;

    JLabel labelImage = new JLabel("Imagen: Ninguna");
    JButtonMetalBlu butSelImage = new JButtonMetalBlu();
    JTextField fieldName = new JTextField();
    JTextField fieldLastName = new JTextField();
    JTextField fieldMail = new JTextField();
    JTextField fieldPhone = new JTextField();
    JPasswordField fieldPass1 = new JPasswordField();
    JPasswordField fieldPass2 = new JPasswordField();
    JButtonMetalBlu butCreateUser;
    JFrame frame = null;
    User userMod = null;
    Admin admin = null;

    public TemplateUser(User user, Admin adm) throws Exception {
        admin = adm;
        userMod = user;
        frame = this;
        setTitle("Registrar");
        JPanel panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("REGISTRAR");
        labelTit.setBounds(anchoUnit * 2, altoUnit * 0, anchoUnit * 20, altoUnit * 4);
        panelPpal.add(labelTit);

        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelA = new JPanel();
        panelA.setLayout(null);
        panelA.setBounds(anchoUnit * 10, altoUnit * 10, anchoUnit * 32, altoUnit * 70);
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);

        JPanel panelData1 = utiliGraf.dataPanelBacker("Nombre:", 16);
        panelData1.setBounds(anchoUnit * 1, altoUnit * 3, anchoUnit * 30, altoUnit * 7);
        fieldName.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldName.setFont(newFont);
        panelData1.add(fieldName);
        panelA.add(panelData1);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Apellido:", 16);
        panelData2.setBounds(anchoUnit * 1, altoUnit * 12, anchoUnit * 30, altoUnit * 7);
        fieldLastName.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldLastName.setFont(newFont);
        panelData2.add(fieldLastName);
        panelA.add(panelData2);

        JPanel panelData3 = utiliGraf.dataPanelBacker("Mail:", 16);
        panelData3.setBounds(anchoUnit * 1, altoUnit * 21, anchoUnit * 30, altoUnit * 7);
        fieldMail.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldMail.setFont(newFont);
        panelData3.add(fieldMail);
        panelA.add(panelData3);

        JPanel panelData4 = utiliGraf.dataPanelBacker("Teléfono:", 16);
        panelData4.setBounds(anchoUnit * 1, altoUnit * 30, anchoUnit * 30, altoUnit * 7);
        fieldPhone.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldPhone.setFont(newFont);
        panelData4.add(fieldPhone);
        panelA.add(panelData4);

        JPanel panelData5 = utiliGraf.dataPanelBacker("Clave:", 16);
        panelData5.setBounds(anchoUnit * 1, altoUnit * 39, anchoUnit * 30, altoUnit * 7);
        fieldPass1.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldPass1.setFont(newFont);
        panelData5.add(fieldPass1);
        panelA.add(panelData5);

        JPanel panelData6 = utiliGraf.dataPanelBacker("Confirmar Clave:", 16);
        panelData6.setBounds(anchoUnit * 1, altoUnit * 48, anchoUnit * 30, altoUnit * 7);
        fieldPass2.setBounds(anchoUnit * 12, altoUnit * 1, anchoUnit * 17, altoUnit * 5);
        fieldPass2.setFont(newFont);
        panelData6.add(fieldPass2);
        panelA.add(panelData6);

        JPanel panelBut = new JPanel(null);
        panelBut.setBackground(bluLg);
        panelBut.setBounds(anchoUnit * 0, altoUnit * 57, anchoUnit * 32, altoUnit * 6);
        panelA.add(panelBut);

        butSelImage = utiliGraf.button2("Seleccionar Imagen", anchoUnit * 9, altoUnit * 1, anchoUnit * 14);
        butSelImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butSelImageActionPerformed();
            }
        });
        panelBut.add(butSelImage);

        JPanel panelLabelImage = new JPanel();
        panelLabelImage.setBackground(bluLg);
        panelLabelImage.setBounds(anchoUnit * 0, altoUnit * 63, anchoUnit * 32, altoUnit * 4);
        panelA.add(panelLabelImage);

        labelImage.setBounds(anchoUnit * 2, altoUnit * 61, anchoUnit * 9, altoUnit * 4);
        panelLabelImage.add(labelImage);

        butCreateUser = utiliGraf.button1("Crear Usuario", anchoUnit * 16, altoUnit * 83, anchoUnit * 21);
        butCreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCreateUserActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(TemplateUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butCreateUser);

        if (userMod != null) {
            fieldName.setText(userMod.getName());
            fieldLastName.setText(userMod.getLastName());
            fieldMail.setText(userMod.getMail());
            fieldPhone.setText(userMod.getPhone());
            fieldPass1.setText(userMod.getPassword());
            fieldPass2.setText(userMod.getPassword());
            routeImage = userMod.getRouteImage();
            nameImage = userMod.getNameImage();
            labelImage.setText("Imagen: " + nameImage);
            butCreateUser.setText("Actualizar datos");
        }

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
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
            }
        });
    }

//Buttons
    public void butCreateUserActionPerformed() throws Exception {
        name = fieldName.getText();
        lastName = fieldLastName.getText();
        mail = fieldMail.getText();
        boolean validMail = utili.isValidEmail(mail);
        phone = fieldPhone.getText();
        char[] passCh1 = fieldPass1.getPassword();
        String passString1 = new String(passCh1);
        java.util.Arrays.fill(passCh1, ' ');

        char[] passCh2 = fieldPass2.getPassword();
        String passString2 = new String(passCh2);
        java.util.Arrays.fill(passCh2, ' ');

        boolean error = false;

        if (name.length() > 30 || name.length() < 2) {
            utiliMsg.errorCantCharName();
            fieldName.setText("");
            error = true;
        }

        if (lastName.length() > 30 || name.length() < 2) {
            utiliMsg.errorCantCharName();
            fieldLastName.setText("");
            error = true;
        }

        if (mail.length() > 50 || validMail == false) {
            utiliMsg.errorMail();
            fieldMail.setText("");
            error = true;
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

        if (passString1.length() < 4 || passString2.length() > 20) {
            utiliMsg.errorPassChar();
            fieldPass1.setText("");
            fieldPass2.setText("");
            error = true;
        }

        if (!passString1.equals(passString2)) {
            utiliMsg.errorPassCoincidence();
            fieldPass1.setText("");
            fieldPass2.setText("");
            error = true;
        }

        if (routeImage == "") {
            routeImage = "resources|avatar.jpg";
            nameImage = "avatar.jpg";
        }

        if (userMod == null) {
            if (su.repeatMail(mail)) {
                utiliMsg.errorMailRepeat();
                error = true;
            }
        }

        if (error == false) {
            if (userMod == null) {
                User user = new User(name, lastName, mail, routeImage, nameImage, passString1, phone);
                user.setActiveUser(true);
                user.setRol("");
                daoU.saveUser(user);
                resetRegister();
                if (admin != null) {
                    admin.comboUsersUpdater();
                    utiliGrafAdm.enabledTrue(2, admin);
                }
                dispose();
            } else {
                String id = userMod.getId();
                daoU.updateNameUser(id, name);
                daoU.updateLastNameUser(id, lastName);
                daoU.updateMailUser(id, mail);
                daoU.updateNameImageUser(id, nameImage);
                daoU.updatePhoneUser(id, phone);
                daoU.updatePassUser(id, passString1);
                utiliMsg.successUpdateUsuario(this);
                if (admin != null) {
                    admin.comboUsersUpdater();
                    utiliGrafAdm.enabledTrue(2, admin);
                }
                dispose();
            }
        }
    }

    private void butSelImageActionPerformed() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            nameImage = selectedFile.getName();
            routeImage = utili.barrReplace(selectedFile.getAbsolutePath());
            labelImage.setText("Imagen: " + nameImage);
        }
    }

    //Resets
    private void resetRegister() {
        name = "";
        lastName = "";
        mail = "";
        pass1 = "";
        pass2 = "";
        phone = "";
        selectedFile = null;
        fieldPass1.setText("");
        fieldPass2.setText("");
        fieldName.setText("");
        fieldLastName.setText("");
        fieldPhone.setText("");
        fieldMail.setText("");
        labelImage.setText("Imagen: Ninguna");
    }
}
