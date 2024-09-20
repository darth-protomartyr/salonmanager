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
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.servicios.ServiceMoneyFlow;
import salonmanager.servicios.ServicioSalon;
import salonmanager.servicios.ServicioTable;
import salonmanager.utilidades.UtilidadesGraficasSalon;

public class MoneyFlowManager extends FrameWindow {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasSalon utiliGrafSal = new UtilidadesGraficasSalon();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    ServicioSalon ss = new ServicioSalon();
    ServiceMoneyFlow smf = new ServiceMoneyFlow();
    SalonManager sm = new SalonManager();
    

    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);

    JTextField fieldMoneyFLow = new JTextField();
    JTextArea textArea = new JTextArea();
    JButtonMetalBlu butMoneyFlow = new JButtonMetalBlu();
    Salon salon = null;
    JRadioButton optionCash = new JRadioButton("Efectivo");
    JRadioButton optionElec = new JRadioButton("Transferencia");
    int flowKind;
    boolean moneyKind = true;

    public MoneyFlowManager(Salon sal, int kind) {
        salon = sal;
        sal.setEnabled(false);
        sm.addFrame(this);
        flowKind = kind;
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(bluSt);
        panelLabel.setBounds(0, 0, anchoUnit * 29, altoUnit * 5);
        panelPpal.add(panelLabel);

        JLabel labelTit = utiliGraf.labelTitleBacker1W("");
        panelLabel.add(labelTit);

        JLabel label$ = utiliGraf.labelTitleBackerA3W("$");
        label$.setBounds(anchoUnit * 3, altoUnit * 10, anchoUnit * 2, altoUnit * 5);
        panelPpal.add(label$);

        fieldMoneyFLow.setBounds(anchoUnit * 5, altoUnit * 10, anchoUnit * 12, altoUnit * 6);
        fieldMoneyFLow.setFont(salon.getFont2());

        optionCash.setBounds(anchoUnit * 19, altoUnit * 10, anchoUnit * 17, altoUnit * 3);
        optionElec.setBounds(anchoUnit * 19, altoUnit * 13, anchoUnit * 17, altoUnit * 3);

        optionCash.setBackground(bluSt);
        optionElec.setBackground(bluSt);

        optionCash.setForeground(Color.WHITE);
        optionElec.setForeground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(optionCash);
        group.add(optionElec);
        optionCash.setSelected(true);
        optionElec.setSelected(false);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                if (selectedButton.getText().equals("Efectivo")) {
                    optionElec.setSelected(false);
                    moneyKind = true;
                } else {
                    optionCash.setSelected(false);
                    moneyKind = false;
                }
            }
        };

        optionCash.addActionListener(listener);
        optionElec.addActionListener(listener);

        panelPpal.add(optionCash);
        panelPpal.add(optionElec);

        JLabel labelComment = utiliGraf.labelTitleBacker3W("");
        labelComment.setBounds(anchoUnit * 4, altoUnit * 17, anchoUnit * 22, altoUnit * 4);
        panelPpal.add(labelComment);

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
        panelPpal.add(fieldMoneyFLow);

        butMoneyFlow = utiliGraf.button1("", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
        butMoneyFlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    butMoneyFlowAction();
                } catch (Exception ex) {
                    Logger.getLogger(MoneyFlowManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelPpal.add(butMoneyFlow);

        if (flowKind == 0) {
            setTitle("Caja Inicial");
            labelTit.setText(utili.stringMsgFrd("Introduzca la caja inicial", 20, 1));
            labelComment.setText("Introduzca un comentario de ser necesario:");
            fieldMoneyFLow.setText("0");
            butMoneyFlow.setText("INGRESAR");
        } else if (flowKind == 1) {
            setTitle("Ingresos a Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a ingresar", 20, 1));
            labelComment.setText("Introduzca el motivo del ingreso:");
            butMoneyFlow.setText("INGRESAR");
        } else {
            setTitle("Salidas de Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a extraer", 20, 1));
            labelComment.setText("Introduzca el motivo de la extracción:");
            butMoneyFlow.setText("EXTRAER");
        }

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

    private void butMoneyFlowAction() throws Exception {
//        boolean kind = moneyKind;
        String moneyFlowSt = fieldMoneyFLow.getText();
        double moneyFlow = 0;
        String comment = textArea.getText();
        boolean error = false;

        if (flowKind == 0) {
            comment = "Dinero ingresado en caja inicial.<br>" + comment;
        }

        if (moneyFlowSt.equals("") || moneyFlowSt.equals("0")) {
            salon.setEnabled(true);
            dispose();
        } else {
            try {
                moneyFlow = parseDouble(moneyFlowSt);
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
                smf.moneyFlowAdd(flowKind, moneyKind, moneyFlow, comment, salon);
                salon.setEnabled(true);
                dispose();
            } else {
                resetValues();
            }
        }
    }

    private void resetValues() {
        fieldMoneyFLow.setText("");
        textArea.setText("");
    }
}
