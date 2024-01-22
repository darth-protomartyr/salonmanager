package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOItemcard daoIC = new DAOItemcard();
    ServicioUser si = new ServicioUser();
    SalonManager sm = new SalonManager();
    Color narSt = new Color(217, 103, 4);
    Color bluSt = new Color(1, 64, 64);
    Color narMed = new Color(255, 172, 13);
    Color bluLg = new Color(3, 166, 136);

    ArrayList<Itemcard> itemsDB = new ArrayList<Itemcard>();
    Itemcard itemAux = null;
    JLabel labelIngreso = null;
    JList listItem = new JList();
    JButton butSelItem = new JButton();
    String sel = "";

    public ItemSelector(String s) throws Exception {
        sel = s;
        sm.addFrame(this);
        itemsDB = daoIC.listarItemsCard();
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
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void butSelItemActionPerformed() throws Exception {
        String selectedItem = (String) listItem.getSelectedValue();
        if (selectedItem == null) {
            utiliMsg.errorSeleccion();
        } else {
            itemAux = utili.itemSelReturn(selectedItem, itemsDB);
            if (itemAux != null) {
                if (sel.equals("m")) {
                    new ItemcardModificacion(itemAux);
                } else {
                    new ItemcardConsulta(itemAux);
                }

            }
        }
    }
}
