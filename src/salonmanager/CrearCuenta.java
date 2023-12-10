package salonmanager;

import salonmanager.entidades.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.PanelPpal;

public class CrearCuenta extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioUser su = new ServicioUser();
    DAOUser daoU = new DAOUser();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    String name = "";
    String lastName = "";
    String mail = "";
    String routeImage = "";
    String nameImage = "";
    String pass1 = "";
    String pass2 = "";
    File selectedFile = null;

    JLabel labelImage = new JLabel("Imagen: Ninguna");
    JButton butSelImage = new JButton();
    JTextField fieldName = new JTextField();
    JTextField fieldLastName = new JTextField();
    JTextField fieldMail = new JTextField();
    JPasswordField fieldPass1 = new JPasswordField();
    JPasswordField fieldPass2 = new JPasswordField();
    JButton butCrearUser;
    JFrame frame = null;
    public CrearCuenta() throws Exception {
        frame = this;
        setTitle("Registrar");
        JPanel panelPpal = new PanelPpal(anchoFrame, alturaFrame);
        add(panelPpal);
        
        JLabel labelTit = utiliGraf.labelTitleBacker1("Registrar");
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelA = new JPanel();
        panelA.setLayout(null);
        panelA.setBounds(134, 70, 416, 490);
        panelA.setBackground(bluLg);
        panelPpal.add(panelA);

        JPanel panelData1 = utiliGraf.dataPanelBacker("Nombre:", 16);
        panelData1.setBounds(20, 20, 375, 50);
        panelData1.add(fieldName);
        panelA.add(panelData1);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Apellido:", 16);
        panelData2.setBounds(20, 100, 375, 50);
        panelData2.add(fieldLastName);
        panelA.add(panelData2);

        JPanel panelData3 = utiliGraf.dataPanelBacker("Mail:", 16);
        panelData3.setBounds(20, 180, 375, 50);
        panelData3.add(fieldMail);
        panelA.add(panelData3);

        JPanel panelData4 = utiliGraf.dataPanelBacker("Password:", 16);
        panelData4.setBounds(20, 260, 375, 50);
        panelData4.add(fieldPass1);
        panelA.add(panelData4);

        JPanel panelData5 = utiliGraf.dataPanelBacker("Password Confirmar:", 16);
        panelData5.setBounds(20, 340, 375, 50);
        panelData5.add(fieldPass2);
        panelA.add(panelData5);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluLg);
        panelBut.setBounds(0, 400, 416, 40);
        panelA.add(panelBut);

        butSelImage = utiliGraf.button2("Seleccionar Imagen", 20, 450, 150);
        butSelImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butSelImageActionPerformed();
            }
        });
        panelBut.add(butSelImage);

        JPanel panelLabelImage = new JPanel();
        panelLabelImage.setBackground(bluLg);
        panelLabelImage.setBounds(0, 440, 416, 25);
        panelA.add(panelLabelImage);

        labelImage.setBounds(20, 430, 60, 30);
        panelLabelImage.add(labelImage);

        butCrearUser = utiliGraf.button1("Crear Usuario", 206, 580, 270);
        butCrearUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCrearUserActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butCrearUser);

        JButton butSalir = utiliGraf.buttonSalir(anchoFrame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
    }

//Buttons
    public void butCrearUserActionPerformed() throws Exception {
        name = fieldName.getText();
        lastName = fieldLastName.getText();
        mail = fieldMail.getText();
        boolean validMail = utili.isValidEmail(mail);
        pass1 = fieldPass1.getText();
        pass2 = fieldPass2.getText();
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

        if (pass1.length() < 8 || pass1.length() > 30) {
            utiliMsg.errorPassChar();
            fieldPass1.setText("");
            fieldPass2.setText("");
            error = true;
        }

        if (!pass1.equals(pass2)) {
            utiliMsg.errorPassCoincidence();
            fieldPass1.setText("");
            fieldPass2.setText("");
            error = true;
        }

        if (routeImage == "") {
            routeImage = "C:|Users|ferlo|Documents|imagenes_salon|avatar.jpg";
            nameImage = "avatar.jpg";
        }

        if (su.repeatMail(mail)) {
            utiliMsg.errorMailRepeat();
            error = true;
        }

        if (error == false) {
            User user = new User(name, lastName, mail, routeImage, nameImage, pass1);
            daoU.saveTablaUsers(user);
            resetRegister();
            dispose();
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
        selectedFile = null;
        fieldPass1.setText("");
        fieldPass2.setText("");
        fieldName.setText("");
        fieldLastName.setText("");
        fieldMail.setText("");
        labelImage.setText("Imagen: Ninguna");
    }
}
