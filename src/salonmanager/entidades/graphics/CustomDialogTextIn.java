package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.Border;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class CustomDialogTextIn extends JDialog {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
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
    JTextArea textArea = new JTextArea();
    JPasswordField textFieldPass = new JPasswordField();
    JTextField textField = new JTextField();


    public CustomDialogTextIn(String tit, String message, int kind) {
        setIconImage(icono.getImage());
        setModal(true);
        setTitle(tit);

        setSize(anchoUnit * 30, altoUnit * 40);
        setLocationRelativeTo(null); // Centrar en la pantalla

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(narLg);
        setContentPane(contentPane);

        JPanel panelText = new JPanel(null);
        panelText.setBounds(anchoUnit, altoUnit, anchoUnit * 27, altoUnit * 26);
        Border bordeInterno = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        panelText.setBorder(bordeInterno);
        panelText.setBackground(narLg);
        contentPane.add(panelText);

//        String mess = "Ingrese indicaciónes del cliente";
        JLabel labelIText = utiliGraf.labelTitleBacker1W(message);
        labelIText.setBounds(anchoUnit * 0, altoUnit * 2, anchoUnit * 30, altoUnit * 5);
        labelIText.setHorizontalAlignment(CENTER);
        panelText.add(labelIText);

        JScrollPane scrollPane = null;
        Font newFont1 = new Font("Arial", Font.PLAIN, 16);
        Font newFont2 = new Font("Arial", Font.BOLD, 30);
        if (kind == 1) {
            textArea.setRows(3);
            textArea.setColumns(5);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(newFont1);
            textArea.setBackground(narUlg);
            scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 21, altoUnit * 15);
            scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 21, altoUnit * 15);
            panelText.add(scrollPane);
            panelText.add(scrollPane);
        } else if (kind == 2){
            textFieldPass.setFont(newFont2);
            textFieldPass.setBounds(anchoUnit * 6, altoUnit * 13, anchoUnit * 15, altoUnit * 6);
            textFieldPass.setBackground(narUlg);
            panelText.add(textFieldPass);
        } else if (kind == 3) {
            textField.setFont(newFont2);
            textField.setBounds(anchoUnit * 6, altoUnit * 13, anchoUnit * 15, altoUnit * 6);
            textField.setBackground(narUlg);
            panelText.add(textField);            
        }

        JButtonMetalBlu butInText = utiliGraf.button2("Ingresar", anchoUnit * 10, altoUnit * 30, anchoUnit * 10);
        butInText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (kind == 1) {
                        text = textArea.getText();
                    } else if (kind == 2){
                        char[] passCh1 = textFieldPass.getPassword();
                        String passString1 = new String(passCh1);
                        text = passString1;
                        java.util.Arrays.fill(passCh1, ' ');
                    } else if (kind == 3) {
                        text = textField.getText();
                    }
                    dispose();
                } catch (Exception ex) {
                    Logger.getLogger(CustomDialogTextIn.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void setText(String st) {
        this.text = st;
    }
}
