package salonmanager;

import salonmanager.entidades.FrameWindow;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import salonmanager.entidades.Itemcard;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.Table;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class GiftSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
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
    JButton butInGift = new JButton();
    Salon salon = null;
    ArrayList<Itemcard> aIC = null;

    GiftSelector(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        tab = salon.getTable();
        setTitle("Selector de Obsequios");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1("Selecione un item para obsequiar");
        panelLabel.add(labelTit);

        aIC = tab.getOrder();
//        HashSet<Itemcard> hsaIC = new HashSet<Itemcard>(aIC1);
//        aIC2 = new ArrayList<Itemcard>(hsaIC);

        comboItems.setModel(utili.itemsComboModelReturn(aIC));
        comboItems.setBounds(30, 50, 150, 40);
        panelPpal.add(comboItems);

        textAreaGifts.setBackground(bluLg);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textAreaGifts.setFont(newFont);
        textAreaGifts.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textAreaGifts);
        scrollPane.setBounds(200, 50, 150, 140);
        panelPpal.add(scrollPane);

        String txt = st.listarGifts(tab.getGifts());
        textAreaGifts.setText(txt);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluSt);
        panelBut.setBounds(0, 210, 390, 40);
        panelPpal.add(panelBut);

        butInGift = utiliGraf.button1("Obsequiar", 206, 580, 270);
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
        panelBut.add(butInGift);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sal.enableSalon();
                dispose();
            }
        });
    }

    private void butInGiftActionPerformed() throws Exception {
        int i = comboItems.getSelectedIndex();
        Itemcard ic = aIC.get(i);
        salon.giftBacker(ic);
        dispose();
    }
}
