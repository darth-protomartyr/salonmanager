package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
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
import javax.swing.JPanel;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.bussiness.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class GiftSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);
    
    int f4 = (int) Math.round(anchoUnit * 1.3);
    Font font4 = new Font("Arial", Font.BOLD, f4);
    
    JComboBox comboItems = new JComboBox();
    Table tab = null;
    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    Salon salon = null;
    TableAdder tadd = null;
    ArrayList<ItemCard> aIC = null;

    public GiftSelector(Salon sal, Table tabAux, TableAdder ta, ArrayList<ItemCard> order) {
        if (sal != null) {
            salon = sal;
            tab = salon.getTableAux();
        } else {
            tadd = ta;
        }
        sm.addFrame(this);
        
        setTitle("Selector de Obsequios");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Selecione un item para obsequiar");
        panelLabel.add(labelTit);

        if ( salon != null) {
            aIC = tab.getOrder();
        } else {
            aIC = order;
        }
        comboItems.setModel(utili.itemsComboModelReturn(aIC));
        comboItems.setFont(font4);
        comboItems.setBounds(anchoUnit * 5, altoUnit * 15, anchoUnit * 19, altoUnit * 5);
        panelPpal.add(comboItems);
        
        butInGift = utiliGraf.button1("Obsequiar", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
        butInGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butInGiftActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(GiftSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butInGift);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (salon != null) {
                    salon.setEnabled(true);
                } else {
                    tadd.setEnabled(true);
                }
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (salon != null) {
                    salon.setEnabled(true);
                } else {
                    tadd.setEnabled(true);
                }
                dispose();
            }
        });
    }

    private void butInGiftActionPerformed() throws Exception {
        int i = comboItems.getSelectedIndex();
        ItemCard ic = aIC.get(i);
        if (salon != null) {
            ss.giftBacker(ic, salon);
            utiliGrafSal.setTableItems(salon);
            utiliGrafSal.jButExtSetter(salon);
        } else {
            tadd.addGift(ic);
        }
        dispose();
    }
}
