package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class CustomDialogTextInAlt extends JDialog {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    ImageIcon icono = new ImageIcon("menu.png");

    Color narUlg = new Color(255, 255, 176);
    Color narLg = new Color(252, 203, 5);

    String text = "";
    JTextField textField = new JTextField();

    public CustomDialogTextInAlt(String tit, String message, int large) {
        setIconImage(icono.getImage());
        setModal(true);
        setTitle(tit);

        setSize(anchoUnit * 30, altoUnit * 40);
        setLocationRelativeTo(null); // Centrar en la pantalla

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(narLg);
        setContentPane(contentPane);

        JPanel panelText = new JPanel(new GridBagLayout());
        panelText.setBounds(anchoUnit, altoUnit, anchoUnit * 27, altoUnit * 12);
        Border bordeInterno = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        panelText.setBorder(bordeInterno);
        panelText.setBackground(narLg);
        contentPane.add(panelText);

        
        JLabel labelIText = utiliGraf.labelTitleBacker1W(utili.stringMsgFrd(message, 25, 2));
        labelIText.setBounds(anchoUnit * 2, altoUnit * 2, anchoUnit * 27, altoUnit * 7);
        panelText.add(labelIText);

        Font newFont = new Font("Arial", Font.PLAIN, 16);

        textField.setFont(newFont);
        textField.setBounds(anchoUnit * 6, altoUnit * 18, anchoUnit * 15, altoUnit * 4);
        textField.setBackground(narUlg);
        contentPane.add(textField);

        JButtonMetalBlu butInText = utiliGraf.button2("Ingresar", anchoUnit * 10, altoUnit * 30, anchoUnit * 10);
        butInText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String st = textField.getText();
                    if (st.length() <= large) {
                        text = st;
                        dispose();
                    } else {
                        utiliMsg.errorNameLength(large);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogTextInAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        contentPane.add(butInText);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        }
        );
    }

    public String getText() {
        return text;
    }
}