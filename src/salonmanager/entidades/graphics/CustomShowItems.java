package salonmanager.entidades.graphics;

import salonmanager.entidades.bussiness.ItemCard;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    

    public CustomShowItems(ArrayList<ItemCard> items, int type) {
        setIconImage(icono.getImage());
        setModal(true);
        setTitle("Consulta items de la mesa");
        setSize(anchoUnit * 30, altoUnit * 40);
        setLocationRelativeTo(null); // Centrar en la pantalla

        JPanel panelPpal = new JPanel();
        panelPpal.setBounds(0, 0, anchoUnit*30, altoUnit*40);
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
        labelTit.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(labelTit);

        JButtonMetalBlu closeButton = utiliGraf.button2("Cerrar", anchoUnit * 10, altoUnit * 30, anchoUnit * 10);
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
}
