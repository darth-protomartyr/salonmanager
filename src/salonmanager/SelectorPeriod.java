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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.servicios.ServiceStatics;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class SelectorPeriod extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceStatics sStats = new ServiceStatics();
    SalonManager sm = new SalonManager();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    JButtonMetalBlu butSelect = null;

    private JSpinner yearSpinner1, monthSpinner1, daySpinner1, yearSpinner2, monthSpinner2, daySpinner2;
    private JButton selectButton;
    private int year1 = 0;
    private int month1 = 0;
    private int day1 = 0;
    private int year2 = 0;
    private int month2 = 0;
    private int day2 = 0;
    Font font = new Font("Arial", Font.BOLD, 18);

    public SelectorPeriod(int op, User user) {
        sm.addFrame(this);
        String title = "";
        switch (op) {
            case 0:
                title = "Ventas en periodo de tiempo";
                break;
            case 1:
                title = "Ventas en periodo de tiempo";
                break;
            case 2:
                title = "Ventas en periodo de tiempo";
                break;
        }
        setTitle(title);
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();
        year1 = today.getYear();
        month1 = today.getMonthValue();
        day1 = today.getDayOfMonth();

        year2 = today.getYear();
        month2 = today.getMonthValue();
        day2 = today.getDayOfMonth();

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("Seleccione los límites temporales");
        panelLabel.add(labelTit);

        JPanel panelStatsBySellInit = new JPanel(null);
        panelStatsBySellInit.setBounds(anchoUnit * 2, altoUnit * 5, anchoUnit * 12, altoUnit * 21);
        panelStatsBySellInit.setBackground(narLg);
        panelPpal.add(panelStatsBySellInit);

        JLabel labelInitBySell = utiliGraf.labelTitleBackerA4W("Inicio");
        labelInitBySell.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 32, altoUnit * 3);
        panelStatsBySellInit.add(labelInitBySell);

        JLabel labelYear = utiliGraf.labelTitleBacker1W("Año:");
        labelYear.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellInit.add(labelYear);

        yearSpinner1 = new JSpinner(new SpinnerNumberModel(year1, 1900, 2100, 1));
        yearSpinner1.setBounds(anchoUnit * 6, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        yearSpinner1.setFont(font);
        panelStatsBySellInit.add(yearSpinner1);

        yearSpinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                daySpinner1 = returnJSpinner(day1, month1, year1);
            }
        });

        JLabel labelMonth = utiliGraf.labelTitleBacker1W("Mes:");
        labelMonth.setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellInit.add(labelMonth);

        monthSpinner1 = new JSpinner(new SpinnerNumberModel(month1, 1, 12, 1));
        monthSpinner1.setBounds(anchoUnit * 6, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        monthSpinner1.setFont(font);
        panelStatsBySellInit.add(monthSpinner1);

        monthSpinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                daySpinner1 = returnJSpinner(day1, month2, year2);
            }
        });

        JLabel labelDay = utiliGraf.labelTitleBacker1W("Día:");
        labelDay.setBounds(anchoUnit * 1, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellInit.add(labelDay);

        daySpinner1 = new JSpinner(new SpinnerNumberModel(month1, 1, 12, 1));
        daySpinner1.setBounds(anchoUnit * 6, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        daySpinner1.setFont(font);
        panelStatsBySellInit.add(daySpinner1);

        JPanel panelStatsBySellEnd = new JPanel(null);
        panelStatsBySellEnd.setBounds(anchoUnit * 15, altoUnit * 5, anchoUnit * 12, altoUnit * 21);
        panelStatsBySellEnd.setBackground(narLg);
        panelPpal.add(panelStatsBySellEnd);

        JLabel labelInitBySell2 = utiliGraf.labelTitleBackerA4W("Inicio");
        labelInitBySell2.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 32, altoUnit * 3);
        panelStatsBySellEnd.add(labelInitBySell2);

        JLabel labelYear2 = utiliGraf.labelTitleBacker1W("Año:");
        labelYear2.setBounds(anchoUnit * 1, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellEnd.add(labelYear2);

        yearSpinner2 = new JSpinner(new SpinnerNumberModel(year2, 1900, 2100, 1));
        yearSpinner2.setBounds(anchoUnit * 6, altoUnit * 5, anchoUnit * 5, altoUnit * 4);
        yearSpinner2.setFont(font);
        panelStatsBySellEnd.add(yearSpinner2);

        yearSpinner2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                daySpinner2 = returnJSpinner(year2, month2, day2);
            }
        });

        JLabel labelMonth2 = utiliGraf.labelTitleBacker1W("Mes:");
        labelMonth2.setBounds(anchoUnit * 1, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellEnd.add(labelMonth2);

        monthSpinner2 = new JSpinner(new SpinnerNumberModel(month2, 1, 12, 1));
        monthSpinner2.setBounds(anchoUnit * 6, altoUnit * 10, anchoUnit * 5, altoUnit * 4);
        monthSpinner2.setFont(font);
        panelStatsBySellEnd.add(monthSpinner2);

        monthSpinner2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                daySpinner2 = returnJSpinner(year2, month2, day2);
            }
        });

        JLabel labelDay2 = utiliGraf.labelTitleBacker1W("Día:");
        labelDay.setBounds(anchoUnit * 1, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        panelStatsBySellEnd.add(labelDay);

        daySpinner2 = new JSpinner(new SpinnerNumberModel(month2, 1, 12, 1));
        daySpinner2.setBounds(anchoUnit * 6, altoUnit * 15, anchoUnit * 5, altoUnit * 4);
        daySpinner2.setFont(font);
        panelStatsBySellEnd.add(daySpinner2);

        butSelect = utiliGraf.button1("Seleccionar", anchoUnit * 5, altoUnit * 27, anchoUnit * 12);
        butSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    selectPeriod(op);
                } catch (Exception ex) {
                    Logger.getLogger(SelectorPeriod.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelect);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
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

    private void selectPeriod(int op) {
        int y1 = (int) yearSpinner1.getValue();
        int m1 = (int) monthSpinner1.getValue();
        int d1 = (int) daySpinner1.getValue();

        int y2 = (int) yearSpinner2.getValue();
        int m2 = (int) monthSpinner2.getValue();
        int d2 = (int) daySpinner2.getValue();

        String date1 = y1 + "-" + m1 + "-" + d1 + " 00:00:00.001";
        String date2 = y2 + "-" + m2 + "-" + d2 + " 23:59:59.999";

        Timestamp timestampInit = Timestamp.valueOf(date1);
        Timestamp timestampEnd = Timestamp.valueOf(date2);

        switch (op) {
            case 0:
                    sStats.listSellsByTime(timestampInit, timestampEnd);
                break;
            case 1:
                    sStats.listSellsByTime(timestampInit, timestampEnd);
                break;
            case 2:
                   sStats.listSellsByTime(timestampInit, timestampEnd);
                break;
            case 3:

                break;

        }

    }

    private JSpinner returnJSpinner(int day, int month, int year) {
        int lastDay = 31;
        if (month == 4 || month == 6 || month == 9 || month == 1) {
            lastDay = 30;
        }

        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                lastDay = 29;
            } else {
                lastDay = 28;
            }
        }

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(day, 1, lastDay, 1));
        return spinner;
    }

}
