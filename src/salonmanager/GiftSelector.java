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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import salonmanager.entidades.bussiness.Itemcard;
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

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);

    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    JComboBox comboItems = new JComboBox();
    JTextArea textAreaGifts = new JTextArea();
    Table tab = new Table();
    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    Salon salon = null;
    ArrayList<Itemcard> aIC = null;

    public GiftSelector(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        tab = salon.getTableAux();
        setTitle("Selector de Obsequios");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Selecione un item para obsequiar");
        panelLabel.add(labelTit);

        aIC = tab.getOrder();

        comboItems.setModel(utili.itemsComboModelReturn(aIC));
        comboItems.setFont(salon.getFont5());
        comboItems.setBounds( anchoUnit * 1, altoUnit * 8, anchoUnit * 13, altoUnit * 4);
        panelPpal.add(comboItems);

        textAreaGifts.setBackground(bluLg);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textAreaGifts.setFont(newFont);
        textAreaGifts.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textAreaGifts);
        scrollPane.setBounds(anchoUnit * 15, altoUnit * 8, anchoUnit * 13, altoUnit * 20);
        panelPpal.add(scrollPane);

        String txt = st.listarGifts(tab.getGifts());
        textAreaGifts.setText(txt);

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
                salon.setEnabled(true);
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                salon.setEnabled(true);
                dispose();
            }
        });
    }

    private void butInGiftActionPerformed() throws Exception {
        int i = comboItems.getSelectedIndex();
        Itemcard ic = aIC.get(i);
        ss.giftBacker(ic, salon);
        utiliGrafSal.setTableItems(salon);
        utiliGrafSal.jButExtSetter(salon);
        dispose();
    }
}
