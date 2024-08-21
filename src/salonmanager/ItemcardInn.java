package salonmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.FrameHalf;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemcardInn extends FrameHalf {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOItemCard daoIC = new DAOItemCard();
    DAOConfig daoC = new DAOConfig();
    SalonManager sm = new SalonManager();

    ArrayList<ItemCard> itemsCardDB = null;
    String name = "";
    String category = "";
    String description = "";
    double cost = 0;
    double price = 0;
    int stock = 0;
    boolean tipAlta = false;
    ItemCard itemAux = new ItemCard();
    ArrayList<String> categoriesDB = null;

    JTextField fieldName = new JTextField();
    JComboBox comboCategory = new JComboBox();
    JTextArea areaDescription = new JTextArea();
    JTextField fieldCost = new JTextField();
    JTextField fieldPrice = new JTextField();
    JTextField fieldStock = new JTextField();
    JCheckBox checkTip = new JCheckBox("");
    JButtonMetalBlu butCreateItem = null;
    Manager manager = null;
    
    public ItemcardInn(Manager man) throws Exception {
        sm.addFrame(this);
        manager = man;
        
        setTitle("Ingreso Item de Carta");
        itemsCardDB = daoIC.listarItemsCard(true);
        categoriesDB = daoC.askCategoriesConfig();
        
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("INGRESAR ITEMS AL MENÚ");
        labelTit.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 40, altoUnit * 4);
        panelPpal.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelForm = utiliGraf.panelItemcardForm(fieldName, comboCategory, areaDescription, fieldCost, fieldPrice, fieldStock, checkTip, categoriesDB, null);
        panelPpal.add(panelForm);

        butCreateItem = utiliGraf.button1("Crear Item", anchoUnit * 16, altoUnit * 86, anchoUnit * 19);
        
        butCreateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCreateItemActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butCreateItem);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
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

    private void butCreateItemActionPerformed() throws Exception {
        boolean error = false;
        name = utili.reduxName(fieldName.getText(), true);
        category = (String) comboCategory.getSelectedItem();
        description = areaDescription.getText();

        if (name.length() > 30 || name.length() < 2) {
            error = true;
            utiliMsg.errorCantCharName();
        }

        if (utili.itemcardRepeat(name, itemsCardDB)) {
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
                boolean confirm = utiliMsg.cargaConfirmErrorPriceNull();
                if (confirm) {
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
            resetItemcard();
        } catch (Exception ex) {
            Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cost >= price) {
            boolean confirm = utiliMsg.errorPriceCost();
            if (!confirm) {
                error = true;
            }
        }

        tipAlta = checkTip.isSelected();

        if (error == false) {
            ArrayList<Double> prices = new ArrayList<>();
            prices.add(price);
            prices.add(0.0);
            itemAux = new ItemCard(name, category, description, cost, prices, stock, tipAlta);
            int id = daoIC.getItemId();
            itemAux.setId(id);
            daoIC.saveItemcard(itemAux);
            resetItemcard();
        }
    }

    private void resetItemcard() throws Exception {
        itemsCardDB = daoIC.listarItemsCard(true);
        name = "";
        category = "";
        description = "";
        cost = 0;
        price = 0;
        stock = 0;
        tipAlta = false;
        itemAux = new ItemCard();

        fieldName.setText("");
        areaDescription.setText("");
        fieldCost.setText("");
        fieldPrice.setText("");
        fieldStock.setText("");
        checkTip.setSelected(true);
    }
}
