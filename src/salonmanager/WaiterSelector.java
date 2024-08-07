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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class WaiterSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();
    DAOUser daoU = new DAOUser();

    Color bluSt = new Color(3, 166, 136);

    Table tableAux = new Table();
    JComboBox comboWaiters = new JComboBox();
    Salon salon = null;
    ArrayList<User> waiters = null;
    User waiterAux = null;

    public WaiterSelector(Salon sal, int i) throws Exception {
        salon = sal;
        tableAux = salon.getTableAux();
        sm.addFrame(this);
        setTitle("Selector de Mozos");

        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);
        waiters = daoU.listUserByRol("MOZO");

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("");
        panelLabel.add(labelTit);
        if (i == 0) {
            labelTit.setText("SELECCIONE UN MOZO");
        } else {
            labelTit.setText("MODIFIQUE EL MOZO");
        }

        comboWaiters.setModel(utili.userComboModelReturnWNull(waiters));
        comboWaiters.setBounds(anchoUnit * 6, altoUnit * 16, anchoUnit * 17, altoUnit * 6);
        comboWaiters.setFont(salon.getFont3());
        comboWaiters.setSelectedIndex(waiters.size());
        comboWaiters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableAux == null) {
                    if (waiterAux == null) {
                        getWaiter();
                    }
                } else {
                    utiliMsg.errorBillSend();
                }
            }
        });
        panelPpal.add(comboWaiters);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(this);
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

    public void getWaiter() {
        User waiter = new User();
        String selection = (String) comboWaiters.getSelectedItem();
        if (!selection.equals("")) {
            waiter = utili.userSelReturn(selection, waiters);
            utiliGrafSal.waiterBacker(waiter, salon);
            dispose();
        }
    }
}
