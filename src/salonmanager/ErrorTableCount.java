package salonmanager;

import salonmanager.entidades.FrameWindow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import salonmanager.entidades.PanelPpal;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;

public class ErrorTableCount extends FrameWindow {

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

    double errorMount = 0;
    double total = 0;

    JTextField fieldAmount = new JTextField();
    JCheckBox checkTotalLoss = new JCheckBox("");

    JButton butInError = new JButton();
    Salon salon = null;

    public ErrorTableCount(Salon sal) {
        salon = sal;
        sm.addFrame(this);
        total = salon.getTotal();
        setTitle("Error Mesa");
        PanelPpal panelPpal = new PanelPpal(390, 300);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 40);
        panelPpal.add(panelLabel);
        JLabel labelTit = utiliGraf.labelTitleBacker1("Ingrese el monto recibido");
        panelLabel.add(labelTit);

        JLabel label$ = utiliGraf.labelTitleBackerA1("$");
        label$.setBounds(30, 70, 60, 80);
        panelPpal.add(label$);

        fieldAmount.setBounds(95, 75, 245, 70);
        fieldAmount.setFont(new Font("Arial", Font.PLAIN, 50));
        panelPpal.add(fieldAmount);

        JLabel labelLoss = utiliGraf.labelTitleBacker2("Perdida Total");
        labelLoss.setBounds(130, 160, 120, 20);
        panelPpal.add(labelLoss);

        checkTotalLoss.setBounds(250, 160, 20, 20);
        panelPpal.add(checkTotalLoss);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluSt);
        panelBut.setBounds(0, 210, 390, 40);
        panelPpal.add(panelBut);

        butInError = utiliGraf.button1("Descontar", 206, 580, 270);
        butInError.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butErrorActionPerformed();
                } catch (Exception ex) {
                    Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBut.add(butInError);
    }

    private void butErrorActionPerformed() throws Exception {
        boolean error = false;
        String err = fieldAmount.getText();
        boolean totalLoss = checkTotalLoss.isSelected();

        try {
            if (totalLoss == true) {
                errorMount = total;
                resetFrame();
            } else {   
                errorMount = parseInt(err);
            }
            
            if (errorMount > total) {
                utiliMsg.errorTotalLess();
                error = true;
                resetFrame();
            }

            if (errorMount < 1) {
                utiliMsg.errorMountNull();
                error = true;
                resetFrame();
            }

        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
            resetFrame();
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (error == false) {
            salon.ErrorBacker(errorMount);
            dispose();
        }
    }

    private void resetFrame() {
        checkTotalLoss.setSelected(false);
        fieldAmount.setText("");
    }
}