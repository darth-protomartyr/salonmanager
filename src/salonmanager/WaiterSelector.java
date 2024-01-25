package salonmanager;

import salonmanager.entidades.graphics.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.persistencia.DAOUser;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class WaiterSelector extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    SalonManager sm = new SalonManager();
    DAOUser daoU = new DAOUser();

    Color red = new Color(240, 82, 7);
    Color green = new Color(31, 240, 100);
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);

    Table tableAux = new Table();
    JComboBox comboWaiters = new JComboBox();
    JButton butInWaiter = new JButton();
    Salon salon = null;
    ArrayList<User> waiters = null;
    User waiterAux = null;

    WaiterSelector(Salon sal, int i) throws Exception {
        salon = sal;
        tableAux = salon.getTableAux();
        sm.addFrame(this);
        setTitle("Selector de Mozos");

        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);
        waiters = daoU.listUserByRol("MOZO");

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBackerA4("");
        panelLabel.add(labelTit);
        if (i == 0) {
            labelTit.setText("Seleccione un mozo");
        } else {
            labelTit.setText("Modifique el mozo");
        }

        comboWaiters.setModel(utili.userComboModelReturnWNull(waiters));
        comboWaiters.setBounds(90, 80, 200, 40);
        comboWaiters.setSelectedIndex(waiters.size());
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setFont(new Font("Arial", Font.PLAIN, 50));
        comboWaiters.setRenderer(renderer);
        panelPpal.add(comboWaiters);
        JButton butSelWaiter = utiliGraf.button1("Elija mozo", 90, 180, 200);
        butSelWaiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (tableAux == null) {
                        if (waiterAux == null) {
                            getWaiter();
                        }
                    } else {
                        utiliMsg.errorBillSend();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butSelWaiter);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sal.enableSalon();
                dispose();
            }
        });
    }

    public void getWaiter() {
        User waiter = new User();
        String selection = (String) comboWaiters.getSelectedItem();
        if(!selection.equals("")) {
            waiter = utili.userSelReturn(selection, waiters);
            salon.waiterBacker(waiter);
            dispose();
        }
    }
}
