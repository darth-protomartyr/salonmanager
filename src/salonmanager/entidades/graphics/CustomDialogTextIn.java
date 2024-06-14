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
    JPasswordField textField = new JPasswordField();

    public CustomDialogTextIn(String tit, String message, int size) {
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
        labelIText.setBounds(anchoUnit * 2, altoUnit * 2, anchoUnit * 27, altoUnit * 5);
        panelText.add(labelIText);

        JScrollPane scrollPane = null;
        Font newFont = new Font("Arial", Font.PLAIN, 16);

        if (size == 1) {
            textArea.setRows(3);
            textArea.setColumns(5);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(newFont);
            textArea.setBackground(narUlg);
            scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 21, altoUnit * 15);
            scrollPane.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 21, altoUnit * 15);
            panelText.add(scrollPane);
            panelText.add(scrollPane);
        } else {
            textField.setFont(newFont);
            textField.setBounds(anchoUnit * 6, altoUnit * 10, anchoUnit * 15, altoUnit * 4);
            textField.setBackground(narUlg);
            panelText.add(textField);
        }

        JButtonMetalBlu butInText = utiliGraf.button2("Ingresar", anchoUnit * 10, altoUnit * 30, anchoUnit * 10);
        butInText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (size == 1) {
                        text = textArea.getText();
                    } else {
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
}
