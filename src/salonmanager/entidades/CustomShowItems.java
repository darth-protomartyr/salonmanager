package salonmanager.entidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import static salonmanager.entidades.FrameGeneral.alturaFrame;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class CustomShowItems extends JDialog {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Toolkit pantalla = Toolkit.getDefaultToolkit();
    Dimension tamanioPantalla = pantalla.getScreenSize();
    int anchoFrame = tamanioPantalla.width;
    int alturaFrame = tamanioPantalla.height - tamanioPantalla.height / 14;
    int anchoUnit = anchoFrame / 100;
    int altoUnit = alturaFrame / 100;
    ImageIcon icono = new ImageIcon("menu.png");

    Color black = new Color(50, 50, 50);
    Color red = new Color(240, 82, 7);
    Color redLg = new Color(243, 103, 91);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color viol = new Color(205, 128, 255);
    Color bluLg = new Color(194, 242, 206);

    Component component = null;
    TextArea textAreaList = new TextArea();
    

    public CustomShowItems(ArrayList<Itemcard> items, int type) {
        setIconImage(icono.getImage());
        setModal(true);
        setTitle("Consulta items de la mesa");
        setSize(anchoUnit * 30, altoUnit * 40);
        setLocationRelativeTo(null); // Centrar en la pantalla

        PanelPpal panelPpal = new PanelPpal(anchoUnit * 30, altoUnit * 40);
        panelPpal.setBackground(viol);
        add(panelPpal);

        String st = "";

        switch (type) {
            case 1:
                st = "Items Pedidos en la mesa";
                break;
            case 2:
                st = "Items Obsequiados en la mesa";
                break;
            case 3:
                st = "Items Pagados sin descuento";
                break;
            case 4:
                st = "Items no abonados";
                break;
        }


        JLabel labelTit = utiliGraf.labelTitleBacker3(st);
        labelTit.setBounds(20, 20, 300, 30);
        panelPpal.add(labelTit);

        


//        JPanel panelText = new JPanel();
//        panelText.setBounds(anchoUnit, altoUnit, anchoUnit * 27, altoUnit * 26);
//        Border bordeInterno = BorderFactory.createEmptyBorder(20, 20, 20, 20);
//        panelText.setBorder(bordeInterno);
//        contentPane.add(panelText);

//        textAreaRecList.setBounds(10, 60, 280, 150);



        JButton closeButton = utiliGraf.button2("Cerrar", anchoUnit * 10, altoUnit * 28, anchoUnit * 10);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    dispose();
                } catch (Exception ex) {
                    Logger.getLogger(CustomShowItems.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panelPpal.add(closeButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        }
        );
    }

//    public CustomShowItems(ArrayList<Itemcard> items, int i) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
