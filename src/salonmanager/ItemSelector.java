package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.servicios.ServicioUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

    ArrayList<Itemcard> itemsDB = new ArrayList<>();
    Itemcard itemAux = null;
    JLabel labelIngreso = null;
    int f4 = (int) Math.round(anchoUnit * 1.2);
    Font font4 = new Font("Arial", Font.BOLD, f4);
    JComboBox comboItems = new JComboBox();
    JButtonMetalBlu butSelItem = new JButtonMetalBlu();
    String sel = "";
    Manager manager = null;

    public ItemSelector(String s, Manager man) throws Exception {
        manager = man;
        sel = s;
        sm.addFrame(this);
        itemsDB = daoIC.listarItemsCard(true);
        if (s.equals("m")) {
            setTitle("Modificar Item");
        } else {
            setTitle("Consultar Item");
        }

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        if (s.equals("m")) {
            labelIngreso = utiliGraf.labelTitleBacker1W("<html>Seleccione un item<br>para modificar</html>");
        } else {
            labelIngreso = utiliGraf.labelTitleBacker1W("<html>Seleccione un item<br>para consultar</html>");
        }
        labelIngreso.setBounds(anchoUnit * 8, altoUnit * 2, anchoUnit * 15, altoUnit * 7);
        panelPpal.add(labelIngreso);

        comboItems.setBounds(anchoUnit * 7, altoUnit * 14, anchoUnit * 15, altoUnit * 5);
        comboItems.setFont(font4);
        comboItems.setModel(utili.itemsComboModelReturnWNull(itemsDB));
        comboItems.setSelectedItem("");
        panelPpal.add(comboItems);

        butSelItem = utiliGraf.button1("Seleccionar item", anchoUnit * 5, altoUnit * 24, anchoUnit * 19);
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
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
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

    private void butSelItemActionPerformed() throws Exception {
        String selectedItem = (String) comboItems.getSelectedItem();
        if (selectedItem == null) {
            utiliMsg.errorSeleccion();
        } else {
            itemAux = utili.itemSelReturn(selectedItem, itemsDB);
            if (itemAux != null) {
                if (sel.equals("m")) {
                    new ItemcardModificacion(itemAux, manager);
                    dispose();
                } else {
                    new ItemcardConsulta(itemAux);
                }
            }
        }
    }
}
