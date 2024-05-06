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
    JRadioButton optionCash = new JRadioButton("Efectivo");
    JRadioButton optionElec = new JRadioButton("Transferencia");
    int flowKind;
    boolean cashKind = true;

    public CashFlowManager(Salon sal, int kind) {
        salon = sal;
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

        fieldCashFlow.setBounds(anchoUnit * 5, altoUnit * 10, anchoUnit * 12, altoUnit * 6);
        fieldCashFlow.setFont(salon.getFont2());

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
                    cashKind = true;
                } else {
                    optionCash.setSelected(false);
                    cashKind = false;                    
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
        panelPpal.add(fieldCashFlow);


        butCashFlow = utiliGraf.button1("", anchoUnit * 8, altoUnit * 29, anchoUnit * 12);
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
        panelPpal.add(butCashFlow);       
        
        if (flowKind == 0) {
            setTitle("Caja Inicial");
            labelTit.setText(utili.stringMsgFrd("Introduzca la caja inicial", 20, 1));
            labelComment.setText("Introduzca un comentario de ser necesario:");
            butCashFlow.setText("INGRESAR");
        } else if (flowKind == 1) {
            setTitle("Ingresos a Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a ingresar", 20, 1));
            labelComment.setText("Introduzca el motivo del ingreso:");
            butCashFlow.setText("INGERSar");
        } else {
            setTitle("Salidas de Caja");
            labelTit.setText(utili.stringMsgFrd("Introduzca el monto a extraer", 20, 1));
            labelComment.setText("Introduzca el motivo de la extracción:");
            butCashFlow.setText("EXTRAER");
        }
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalirRedux(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
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

    private void butCashFlowAction() throws Exception {
//        boolean kind = cashKind;
        String cashFlowSt = fieldCashFlow.getText();
        double cashFlow = 0;
        String comment = textArea.getText();
        boolean error = false;
        
        if (flowKind == 0) {
            comment = "Dinero ingresado en caja inicial. \n" + comment;
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
            ss.cashFlowAdd(flowKind, cashKind, cashFlow, comment, salon);
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