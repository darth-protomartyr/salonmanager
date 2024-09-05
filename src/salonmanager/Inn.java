package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOInit;

public class Inn extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOUser daoU = new DAOUser();
    DAOConfig daoC = new DAOConfig();
    DAOInit daoI = new DAOInit();
    SalonManager sm = new SalonManager();
    Color bluLg = new Color(3, 166, 136);

    String mail = "";
    String pass = "";
    User userAux = new User();

    JButtonMetalBlu butInUser = new JButtonMetalBlu();
    JTextField fieldMail = new JTextField();
    JPasswordField fieldPass = new JPasswordField();
    JFrame landing = null;

    public Inn(JFrame landing1) throws Exception {
        landing = landing1;
        setTitle("Registrar");
        int f = (int) Math.round(anchoUnit * 1.3);
        Font font = new Font("Arial", Font.PLAIN, f);

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluLg);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelIngreso = utiliGraf.labelTitleBacker1W("Log In");
        panelLabel.add(labelIngreso);

        JPanel panelData1 = utiliGraf.dataPanelBacker("Mail:", 16);
        panelData1.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 23, altoUnit * 7);
        fieldMail.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 15, altoUnit * 5);
        fieldMail.setFont(font);
        fieldMail.setText("mbastias382@gmail.com");
        panelData1.add(fieldMail);
        panelPpal.add(panelData1);

        JPanel panelData2 = utiliGraf.dataPanelBacker("Clave:", 16);
        panelData2.setBounds(anchoUnit * 3, altoUnit * 20, anchoUnit * 23, altoUnit * 7);
        fieldPass.setBounds(anchoUnit * 7, altoUnit * 1, anchoUnit * 15, altoUnit * 5);
        fieldPass.setFont(font);
        fieldPass.setText("lana");
        panelData2.add(fieldPass);
        panelPpal.add(panelData2);

        butInUser = utiliGraf.button1("Ingresar", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
        butInUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butInUserActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(Inn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butInUser);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
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

    private void butInUserActionPerformed() throws Exception {
        mail = fieldMail.getText();        
        char[] pass = fieldPass.getPassword();  
        String passString = new String(pass);
        java.util.Arrays.fill(pass, ' ');
        
        
        boolean error = false;

        if (mail.equals("") || pass.equals("")) {
            utiliMsg.errorDataNull();
            error = true;
        }

        userAux = daoU.consultaUser(mail);
        String id = userAux.getId();

        if (id == null) {
            error = true;
            utiliMsg.errorUserNull();
        }

        if (error == false && id != null) {
            if (userAux.getPassword().equals(passString)) {
                try {
                    sm.setUserIn(userAux);
                    sm.setPassIn(passString);
                    landing.dispose();
                    dispose();
                    new Manager(userAux, passString);
                } catch (Exception ex) {
                    Logger.getLogger(Inn.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                utiliMsg.errorAccessDenied();
                fieldMail.setText("");
                fieldPass.setText("");
            }
        }
    }
}
