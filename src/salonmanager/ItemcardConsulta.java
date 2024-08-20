package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class ItemcardConsulta extends FrameThird {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    Color bluLg = new Color(194, 242, 206);
    Color narUlg = new Color(255, 255, 176);
    SalonManager sm = new SalonManager();
    ItemCard itemAux = null;

    public ItemcardConsulta(ItemCard ic) {
        sm.addFrame(this);
        itemAux = ic;
        setTitle("Consulta Item Carta");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Consulta Item de la Carta");
        labelTit.setBounds(anchoUnit * 5, altoUnit * 0, anchoUnit * 23, altoUnit * 5);
        panelPpal.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelB = new JPanel();
        panelB.setLayout(null);
        panelB.setBounds(anchoUnit * 2, altoUnit * 6, anchoUnit * 30, altoUnit * 80);
        panelB.setBackground(bluLg);
        panelPpal.add(panelB);

        JLabel labelNameItem = utiliGraf.labelTitleBacker1("Nombre: " + itemAux.getName());
        labelNameItem.setBounds(anchoUnit * 2, altoUnit * 2, anchoUnit * 26, altoUnit * 5);
        panelB.add(labelNameItem);

        JLabel labelCategoryItem = utiliGraf.labelTitleBacker1("Categoría: " + itemAux.getCategory());
        labelCategoryItem.setBounds(anchoUnit * 2, altoUnit * 9, anchoUnit * 26, altoUnit * 5);
        panelB.add(labelCategoryItem);

        JLabel labelDescriptionItem = utiliGraf.labelTitleBacker1("Descripcion: ");
        labelDescriptionItem.setBounds(anchoUnit * 2, altoUnit * 16, anchoUnit * 26, altoUnit * 5);
        panelB.add(labelDescriptionItem);
                
        String descr = itemAux.getDescription();

        JLabel labelDescriptionTextItem = utiliGraf.labelTitleBacker2(utili.stringMsgFrd(descr, 22, 1));                

        JScrollPane scrollPane = new JScrollPane(labelDescriptionTextItem);
        scrollPane.setBounds(anchoUnit * 4, altoUnit * 21, anchoUnit * 21, altoUnit * 17);
        scrollPane.setBackground(narUlg);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelB.add(scrollPane);
        
        JLabel labelCostItem = utiliGraf.labelTitleBacker1("Costo $: " + itemAux.getCost());
        labelCostItem.setBounds(anchoUnit * 2, altoUnit * 42, anchoUnit * 21, altoUnit * 5);
        panelB.add(labelCostItem);

        JLabel labelPriceItem = utiliGraf.labelTitleBacker1("Precio $: " + itemAux.getPrice().get(0));
        labelPriceItem.setBounds(anchoUnit * 2, altoUnit * 49, anchoUnit * 21, altoUnit * 5);
        panelB.add(labelPriceItem);

        JLabel labelStockItem = utiliGraf.labelTitleBacker1("Stock: " + itemAux.getStock() + " unidades");
        labelStockItem.setBounds(anchoUnit * 2, altoUnit * 56, anchoUnit * 21, altoUnit * 5);
        panelB.add(labelStockItem);

        String tip = utili.booleanStringBack(itemAux.isActiveTip());
        JLabel labelTipItem = utiliGraf.labelTitleBacker1("Propina deducible: " + tip);
        labelTipItem.setBounds(anchoUnit * 2, altoUnit * 63, anchoUnit * 21, altoUnit * 5);
        panelB.add(labelTipItem);

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
}