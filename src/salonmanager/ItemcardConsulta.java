package salonmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import salonmanager.servicios.ServicioItemcard;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class ItemcardConsulta extends FrameThird {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioItemcard sic = new ServicioItemcard();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User userIn = null;
    Itemcard itemAux = null;

    public ItemcardConsulta(Itemcard ic) {
        sm.addFrame(this);
        itemAux = ic;
        setTitle("Consulta Item Carta");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Consulta Item de la Carta");
        labelTit.setBounds(10, 20, 300, 30);
        panelPpal.add(labelTit);

        JPanel panelB = new JPanel();
        panelB.setLayout(null);
        panelB.setBounds(40, 100, 365, 510);
        panelB.setBackground(bluLg);
        panelPpal.add(panelB);

        JLabel labelNameItem = utiliGraf.labelTitleBacker2("Nombre: " + itemAux.getName());
        labelNameItem.setBounds(10, 10, 345, 40);
        panelB.add(labelNameItem);

        JLabel labelCaptionItem = utiliGraf.labelTitleBacker2("Rubro: " + itemAux.getCaption());
        labelCaptionItem.setBounds(10, 60, 345, 40);
        panelB.add(labelCaptionItem);

        JLabel labelCodeItem = utiliGraf.labelTitleBacker2("Código del Item: " + itemAux.getCode());
        labelCodeItem.setBounds(10, 110, 345, 40);
        panelB.add(labelCodeItem);

        JLabel labelDescriptionItem = utiliGraf.labelTitleBacker2("Descripcion: ");
        labelDescriptionItem.setBounds(10, 160, 345, 40);
        panelB.add(labelDescriptionItem);

        String descr = "<html>" + itemAux.getDescription() + "</html>";
        JLabel labelDescriptionTextItem = utiliGraf.labelTitleBacker3(descr);
//        labelDescriptionTextItem.setToolTipText(descr);
        labelDescriptionTextItem.setBounds(20, 180, 345, 80);
        panelB.add(labelDescriptionTextItem);

        JLabel labelCostItem = utiliGraf.labelTitleBacker2("Costo: " + itemAux.getCost());
        labelCostItem.setBounds(10, 280, 345, 40);
        panelB.add(labelCostItem);

        JLabel labelPriceItem = utiliGraf.labelTitleBacker2("Precio: " + itemAux.getPrice());
        labelPriceItem.setBounds(10, 330, 345, 40);
        panelB.add(labelPriceItem);

        JLabel labelStockItem = utiliGraf.labelTitleBacker2("Stock: " + itemAux.getStock());
        labelStockItem.setBounds(10, 380, 345, 40);
        panelB.add(labelStockItem);

        String tip = utili.booleanStringBack(itemAux.isActiveTip());
        JLabel labelTipItem = utiliGraf.labelTitleBacker2("Con propina deducible: " + tip);
        labelTipItem.setBounds(10, 430, 345, 40);
        panelB.add(labelTipItem);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panelPpal.add(butSalir);
    }
}
