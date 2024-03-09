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
import static java.lang.Double.parseDouble;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class CashFlowManager extends FrameWindow {
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

    JTextField fieldCashFlow = new JTextField();
    JTextArea textArea = new JTextArea();
    JButtonMetalBlu butCashFlow = new JButtonMetalBlu();
    Salon salon = null;
    int flowKind;

    public CashFlowManager(Salon sal, int kind) {
        salon = sal;
        sm.addFrame(this);
        flowKind = kind;
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, 390, 70);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("");
        panelLabel.add(labelTit);

        JLabel label$ = utiliGraf.labelTitleBackerA3W("$");
        label$.setBounds(anchoUnit * 4, altoUnit * 9, anchoUnit * 3, altoUnit * 8);
        panelPpal.add(label$);

        fieldCashFlow.setBounds(anchoUnit * 7, altoUnit * 10, anchoUnit * 17, altoUnit * 6);
        fieldCashFlow.setFont(salon.getFont2());

        textArea.setRows(3);
        textArea.setColumns(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Font newFont = new Font("Arial", Font.PLAIN, 16);
        textArea.setFont(newFont);
        textArea.setBackground(narUlg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(anchoUnit * 4, altoUnit * 20, anchoUnit * 21, altoUnit * 8);
        panelPpal.add(scrollPane);
        panelPpal.add(fieldCashFlow);

        JLabel labelComment = utiliGraf.labelTitleBacker3W("");
        panelPpal.add(labelComment);

        JPanel panelBut = new JPanel();
        panelBut.setBackground(bluSt);
        panelBut.setBounds(0, 210, 390, 50);
        panelPpal.add(panelBut);
        butCashFlow = utiliGraf.button1("", 206, 580, 270);
        butCashFlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butCashFlowAction();
                } catch (Exception ex) {
                    Logger.getLogger(CashFlowManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelBut.add(butCashFlow);       
        
        if (flowKind == 0) {
            setTitle("Caja Inicial");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto de la caja inicial", 20, 1));
            fieldCashFlow.setBounds(anchoUnit * 7, altoUnit * 15, anchoUnit * 17, altoUnit * 6);
            label$.setBounds(anchoUnit * 4, altoUnit * 15, anchoUnit * 3, altoUnit * 8);
            labelComment.setVisible(false);
            textArea.setVisible(false);
            butCashFlow.setText("Ingresar");
        } else if (flowKind == 1) {
            setTitle("Ingresos a Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a ingresar en la caja", 20, 1));
            labelComment.setText("Introduzca el motivo del ingreso:");
            labelComment.setBounds(anchoUnit * 4, altoUnit * 17, anchoUnit * 22, altoUnit * 4);
            butCashFlow.setText("Ingresar");
        } else {
            setTitle("Salidas de Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a extraer de la caja", 20, 1));
            labelComment.setText("Introduzca el motivo de la extracción:");
            labelComment.setBounds(anchoUnit * 4, altoUnit * 17, anchoUnit * 22, altoUnit * 4);
            butCashFlow.setText("Extraer");
        }


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                salon.setEnabled(true);
                dispose();
            }
        });
    }

    private void butCashFlowAction() throws Exception {
        String cashFlowSt = fieldCashFlow.getText();
        double cashFlow = 0;
        String comment = textArea.getText();
        boolean error = false;
        
        if (flowKind == 0) {
            comment = "Dinero ingresado en caja inicial.";
        }
        
        try {
            cashFlow = parseDouble(cashFlowSt);
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (comment.equals("")) {
            utiliMsg.errorEmptyCause();
            error = true;
        }
        
        if (error == false) {
            ss.cashFlowAdd(cashFlow, comment, flowKind, salon);
            dispose();
        } else {
            resetValues();
        }
    }

    private void resetValues() {
        fieldCashFlow.setText("");
        textArea.setText("");
    }
}