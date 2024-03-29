package salonmanager;

import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.FrameFull;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import salonmanager.utilidades.UtilidadesUpdate;

public class StaticsManager extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    UtilidadesUpdate utiliUpd = new UtilidadesUpdate();
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    private User userIn = null;

    public StaticsManager(User user) {
        setTitle("Administrador");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        Timestamp now = new Timestamp(new Date().getTime());
        LocalDateTime today = now.toLocalDateTime();

        JLabel labelStatics = utiliGraf.labelTitleBackerA3W("Estadísticas");
        labelStatics.setBounds(anchoUnit * 3, altoUnit * 3, anchoUnit * 16, altoUnit * 4);
        panelPpal.add(labelStatics);

        JPanel panelStatsBySell = new JPanel(null);
        panelStatsBySell.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 29, altoUnit * 6);
        panelStatsBySell.setBackground(bluLg);
        panelPpal.add(panelStatsBySell);
        
        JLabel labelStats = utiliGraf.labelTitleBacker1("Ventas por período de Tiempo");
        labelStats.setBounds(anchoUnit * 1, altoUnit * 1, anchoUnit * 23, altoUnit * 4);
        panelStatsBySell.add(labelStats);
        
        JButtonMetalBlu butStatsBySell = utiliGraf.button2("VER", anchoUnit * 24, altoUnit * 1, anchoUnit * 4);
        butStatsBySell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new SelectorPeriod(0, user);
            }
        });
        panelStatsBySell.add(butStatsBySell);        
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(this);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirmation) {
                    dispose();
                }
            }
        });
    }
}