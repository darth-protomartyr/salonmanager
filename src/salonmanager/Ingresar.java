package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import salonmanager.entidades.PanelPpal;                                                                                                                                                                                                                                                                                                                                                                            

public class Ingresar extends FrameWindow {
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOUser daoU = new DAOUser();
    ServicioUser si = new ServicioUser();
    SalonManager sm = new SalonManager();
    Color narSt = new Color(217, 103, 4);
    Color bluSt = new Color(1, 64, 64);
    Color narMed = new Color(255, 172, 13);
    Color bluLg = new Color(3, 166, 136);

    String mail = "";
    String pass = "";
    User userAux = new User();

    JButton butInUser = new JButton();
    JTextField fieldMail = new JTextField();
    JPasswordField fieldPass = new JPasswordField();
    JFrame landing = null;

    public Ingresar(JFrame landing1) throws Exception {
        landing = landing1;
        setTitle("Registrar");

        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluLg);
        panelLabel.setBounds(0, 0, 390, 50);
        panelPpal.add(panelLabel);

        JLabel labelIngreso = utiliGraf.labelTitleBacker1("Log In");
        panelLabel.add(labelIngreso);

        JPanel panelData1 = utiliGraf.dataPanelBacker("Mail:", 16);
        panelData1.setBounds(12, 70, 360, 40);
        panelData1.add(fieldMail);
        panelPpal.add(panelData1);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Password:", 16);
        panelData2.setBounds(12, 140, 360, 40);
        panelData2.add(fieldPass);
        panelPpal.add(panelData2);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluLg);
        panelBut.setBounds(0, 210, 390, 40);
        panelPpal.add(panelBut);
        
        butInUser = utiliGraf.button1("Ingresar", 206, 580, 270);
        butInUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {             
                    butInUserActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(Ingresar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBut.add(butInUser);
    }

    private void butInUserActionPerformed() throws Exception {
//        mail = fieldMail.getText();
//        pass = fieldPass.getText();        
        mail = "gon@gmail.com";
        pass = "27949874";    
        boolean error = false;
        userAux = daoU.consultaUser(mail);


        
        if (mail == "" || pass == "") {
            utiliMsg.errorDataNull();
            error = true;
        }

        if (!userAux.getPassword().equals(pass)) {
            utiliMsg.errorUserNull();
            error = true;
        }

        
        
        if (error == false) {            
            if (userAux.getPassword().equals(pass)) {
                try {
//                    sm.setUserIn(userAux);
//                    sm.setPassIn(pass);
                    landing.dispose();
                    dispose();
                    new Manager(userAux, pass);

                } catch (Exception ex) {
                    Logger.getLogger(Ingresar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                utiliMsg.errorAccessDenied();
                fieldMail.setText("");
                fieldPass.setText("");
            }
        }
    }
}