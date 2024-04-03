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
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class BillDiscounter extends FrameWindow {

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

    JButtonMetalBlu butInGift = new JButtonMetalBlu();
    JSpinner spinnerDiscount = null;
    Salon salon = null;

    int year = 0;
    int month = 0;
    int day = 0;
    
    public BillDiscounter(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        setTitle("Descuento");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();
        year = today.getYear();
        month = today.getMonthValue();
        day = today.getDayOfMonth();
        
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Elija un porcentaje para descontar");
        panelLabel.add(labelTit);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 5);
        spinnerDiscount = new JSpinner(spinnerModel);
        spinnerDiscount.setBounds(90, 90, 140, 80);
        spinnerDiscount.setBackground(bluLg);
        spinnerDiscount.setFont(new Font("Arial", Font.PLAIN, 75)); //
        panelPpal.add(spinnerDiscount);
        JLabel labelPercentage = utiliGraf.labelTitleBackerA1("%");
        labelPercentage.setForeground(bluLg);
        labelPercentage.setBounds(240, 90, 80, 80);
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

    private void butDiscounterActionPerformed() throws Exception {
        int u = (int) spinnerDiscount.getValue();
        ss.discountBacker(u, salon);
        utiliGrafSal.setTableItems(salon);
        utiliGrafSal.jButExtSetter(salon);
        dispose();
    }
}
