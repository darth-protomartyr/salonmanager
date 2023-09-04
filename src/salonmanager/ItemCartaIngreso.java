package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    boolean tipAlta = false;
    ItemCarta itemAux = new ItemCarta();
    ArrayList<String> captionsDB = utili.captionList();

    JTextField fieldName = new JTextField();
    JComboBox comboCaption = new JComboBox();
    JTextArea areaDescription = new JTextArea();
    JTextField fieldCost = new JTextField();
    JTextField fieldPrice = new JTextField();
    JTextField fieldStock = new JTextField();
    JCheckBox checkTip = new JCheckBox("");
    JButton butCrearItem = null;

    public ItemCartaIngreso() throws Exception {
        setTitle("Ingreso Item de Carta");
        itemsCartaDB = daoIC.listarItemsCarta();
        PanelPpal panelPpal = new PanelPpal(anchoFrameHalf, alturaFrame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Ingresar Items a la Carta");
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelForm = utiliGraf.panelItemCartaForm(fieldName, comboCaption, areaDescription, fieldCost, fieldPrice, fieldStock, checkTip, captionsDB, null);
        panelPpal.add(panelForm);

        butCrearItem = utiliGraf.button1("Crear Item", 206, 600, 270);
        butCrearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCrearItemActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ItemCartaIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void butCrearItemActionPerformed() throws Exception {
        boolean error = false;
        name = fieldName.getText();
        caption = utili.selectorCaption(comboCaption.getSelectedIndex());
        description = areaDescription.getText();

        if (name.length() > 30 || name.length() < 2) {
            error = true;
            utiliMsg.errorCantCharName();
        }

        if (utili.itemCartaRepeat(name, itemsCartaDB, null)) {
            error = true;
            utiliMsg.errorNameRepeat();
        }

        if (description.length() > 149) {
            error = true;
            utiliMsg.errorCantCharDescription();
        }

        try {
            String cos = fieldCost.getText();
            if (cos.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (cos.equals("")) {
                cost = 0;
            } else {
                cost = utili.toNumberD(cos);
            }

            String pric = fieldPrice.getText();
            if (pric.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (pric.equals("")) {
                int confirm = utiliMsg.errorPriceNull();
                if (confirm == 0) {
                    pric = "0";
                    price = utili.toNumberD(pric);
                } else {
                    error = true;
                }
            } else {
                price = utili.toNumberD(pric);
            }

            String sto = fieldStock.getText();
            if (sto.length() > 12) {
                error = true;
                utiliMsg.errorCantCharNum();
            }

            if (sto.equals("")) {
                stock = 0;
            } else {
                stock = utili.toNumberI(sto);
            }

        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
            resetItemCarta();
        } catch (Exception ex) {
            Logger.getLogger(ItemCartaIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cost > price) {
            utiliMsg.errorPriceCost();
            error = true;
        }

        tipAlta = checkTip.isSelected();

        if (error == false) {
            itemAux = new ItemCarta(name, caption, description, cost, price, stock, tipAlta);
            sic.ingresarItem(itemAux);
            resetItemCarta();
        }
    }

    private void resetItemCarta() throws Exception {
        itemsCartaDB = daoIC.listarItemsCarta();
        name = "";
        caption = "";
        description = "";
        cost = 0;
        price = 0;
        stock = 0;
        tipAlta = false;
        itemAux = new ItemCarta();

        fieldName.setText("");
        areaDescription.setText("");
        fieldCost.setText("");
        fieldPrice.setText("");
        fieldStock.setText("");
        checkTip.setSelected(false);
    }
}
