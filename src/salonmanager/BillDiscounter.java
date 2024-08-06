package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class BillDiscounter extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();

    Color bluSt = new Color(3, 166, 136);
    Color bluLg = new Color(194, 242, 206);

    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    JSpinner spinnerDiscount = null;
    Salon salon = null;
    TableAdder tAdd = null;

    public BillDiscounter(Salon sal, TableAdder ta) {
        if (sal != null) {
            salon = sal;
        }
        tAdd = ta;
        sm.addFrame(this);
        setTitle("Descuento");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        
        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, this.getWidth(), altoUnit * 5);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBacker1W("Elija un porcentaje para descontar");
        panelLabel.add(labelTit);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        spinnerDiscount = new JSpinner(spinnerModel);
        spinnerDiscount.setBounds(anchoUnit * 7, altoUnit * 12, anchoUnit * 11, altoUnit * 10);
        spinnerDiscount.setBackground(bluLg);
        spinnerDiscount.setFont(new Font("Arial", Font.PLAIN, 75)); //
        panelPpal.add(spinnerDiscount);

        JLabel labelPercentage = utiliGraf.labelTitleBackerA1("%");
        labelPercentage.setForeground(bluLg);
        labelPercentage.setBounds(anchoUnit * 18, altoUnit * 12, anchoUnit * 5, altoUnit * 10);
        panelPpal.add(labelPercentage);

        butInGift = utiliGraf.button1("Descontar", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
        butInGift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butDiscounterActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(BillDiscounter.class.getName()).log(Level.SEVERE, null, ex);
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
                    tAdd.setEnabled(true);
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
                    tAdd.setEnabled(true);
                }
                dispose();
            }
        });
    }

    private void butDiscounterActionPerformed() throws Exception {
        int u = (int) spinnerDiscount.getValue();
        if (u > 0 || u <= 100) {
            if (salon != null) {
                ss.discountBacker(u, salon);
                utiliGrafSal.setTableItems(salon);
                utiliGrafSal.jButExtSetter(salon);
            } else {
                tAdd.setDiscount(u);
                tAdd.setEnabled(true);
            }
            dispose();
        } else {
            utiliMsg.errorForbbidenValue();
        }
    }
}
