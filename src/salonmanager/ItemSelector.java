package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.PanelPpal;
import salonmanager.persistencia.DAOItemCarta;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOItemCarta daoIC = new DAOItemCarta();
    ServicioUser si = new ServicioUser();
    SalonManager sm = new SalonManager();
    Color narSt = new Color(217, 103, 4);
    Color bluSt = new Color(1, 64, 64);
    Color narMed = new Color(255, 172, 13);
    Color bluLg = new Color(3, 166, 136);

    ArrayList<ItemCarta> itemsDB = new ArrayList<ItemCarta>();
    ItemCarta itemAux = null;
    JLabel labelIngreso = null;
    JList listItem = new JList();
    JButton butSelItem = new JButton();
    String sel = "";

    public ItemSelector(String s) throws Exception {
        sel = s;
        sm.addFrame(this);
        itemsDB = daoIC.listarItemsCarta();
        if (s.equals("m")) {
            setTitle("Modificar Item");
        } else {
            setTitle("Consultar Item");
        }

        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluLg);
        panelLabel.setBounds(0, 10, 390, 40);
        panelPpal.add(panelLabel);

        if (s.equals("m")) {
            labelIngreso = utiliGraf.labelTitleBacker1("Seleccione el item que desea modificar");
        } else {
            labelIngreso = utiliGraf.labelTitleBacker1("Seleccione el item que desea consultar");
        }
        panelLabel.add(labelIngreso);

        JPanel panelList = utiliGraf.panelListItemBack(30, 45, 320, 170, listItem, itemsDB, null);
        panelPpal.add(panelList);

        butSelItem = utiliGraf.button2("Seleccionar item", 100, 230, 190);
        butSelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butSelItemActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ItemSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelItem);
    }

    private void butSelItemActionPerformed() throws Exception {
        String selectedItem = (String) listItem.getSelectedValue();
        if (selectedItem == null) {
            utiliMsg.errorSeleccion();
        } else {
            itemAux = utili.itemSelReturn(selectedItem, itemsDB);
            if (itemAux != null) {
                if (sel.equals("m")) {
                    new ItemCartaModificacion(itemAux);
                } else {
                    new ItemCartaConsulta(itemAux);
                }

            }
        }
    }
}
