package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOItemCarta;
import salonmanager.servicios.ServicioItemCarta;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemCartaIngreso extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioItemCarta sic = new ServicioItemCarta();
    DAOItemCarta daoIC = new DAOItemCarta();
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User userIn = null;

    ArrayList<ItemCarta> itemsCartaDB = null;
    String name = "";
    String caption = "";
    String description = "";
    double cost = 0;
    double price = 0;
    int stock = 0;
    ItemCarta itemAux = new ItemCarta();
    ArrayList<String> captionsDB = utili.captionList();

    JTextField fieldName = new JTextField();
    JComboBox comboCaption = new JComboBox();
    JTextArea areaDescription = new JTextArea();
    JTextField fieldCost = new JTextField();
    JTextField fieldPrice = new JTextField();
    JTextField fieldStock = new JTextField();
    JButton butCrearItem = null;

    public ItemCartaIngreso() throws Exception {
        setTitle("Ingreso Item de Carta");
        itemsCartaDB = daoIC.listarItemsCarta();
        PanelPpal panelPpal = new PanelPpal(anchoFrameHalf, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Ingresar Items a la Carta");
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelForm = utiliGraf.panelItemCartaForm(fieldName, comboCaption, areaDescription, fieldCost, fieldPrice, fieldStock, captionsDB, null);
        panelPpal.add(panelForm);

        butCrearItem = utiliGraf.button1("Crear Item", 206, 580, 270);
        butCrearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                butCrearItemActionPerformed();
            }
        });
        panelPpal.add(butCrearItem);

        JButton butSalir = utiliGraf.buttonSalir(anchoFrameHalf);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
    }

    private void butCrearItemActionPerformed() {
        name = fieldName.getText();
    }

    private void butSelCaption() {
        
    }
}
