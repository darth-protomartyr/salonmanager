package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.ItemcardInn;
import salonmanager.entidades.bussiness.Itemcard;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;

public class PanelNestedItem extends JPanel {

    Itemcard ic = null;
    int y = 0;
    Color bluSt = new Color(3, 166, 136);
    Color bluStt = new Color(2, 76, 46);
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    JComboBox textF2 = new JComboBox();
    JSpinner textF3 = new JSpinner();
    JTextField textF4 = new JTextField();
    JTextField textF5 = new JTextField();
    JButtonMetalBlu butSel = null;

    public PanelNestedItem(Itemcard iCard, int i, ArrayList<String> cat) {
        ic = iCard;
        y = 1 + i * 6;

        JLabel labelItem = utiliGraf.labelTitleBacker2(utili.reduxSt(ic.getName(), i));
        Font font = new Font("Arial", Font.PLAIN, 12);

        setLayout(null);
        setBounds(anchoUnit * 1, altoUnit * y, anchoUnit * 77, altoUnit * 5);
        setBackground(bluSt);

        JLabel label1 = utiliGraf.labelTitleBacker2W(ic.getName());
        label1.setBounds(anchoUnit, altoUnit, anchoUnit * 12, altoUnit * 3);
        add(label1);

        JLabel label2 = utiliGraf.labelTitleBacker3W("Categoría:");
        label2.setBounds(anchoUnit * 15, altoUnit, anchoUnit * 6, altoUnit * 3);
        add(label2);

        textF2.setBounds(anchoUnit * 21, altoUnit, anchoUnit * 8, altoUnit * 3);
        textF2.setFont(font);
        textF2.setModel(utili.categoryComboModelReturn(cat));
        textF2.setSelectedItem(ic.getCategory());
        textF2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fieldModified();
            }
        });
        add(textF2);

        JLabel label3 = utiliGraf.labelTitleBacker3W("Cant. Stock:");
        label3.setBounds(anchoUnit * 32, altoUnit, anchoUnit * 6, altoUnit * 3);
        add(label3);

        SpinnerModel model = new SpinnerNumberModel(1, 0, 10000, 1);
        textF3.setModel(model);
        textF3.setBounds(anchoUnit * 39, altoUnit, anchoUnit * 4, altoUnit * 3);
        textF3.setValue(ic.getStock());
        textF3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fieldModified();
            }
        });
        add(textF3);

        JLabel label4 = utiliGraf.labelTitleBacker3W("Costo:");
        label4.setBounds(anchoUnit * 46, altoUnit, anchoUnit * 3, altoUnit * 3);
        add(label4);

        textF4.setBounds(anchoUnit * 50, altoUnit, anchoUnit * 5, altoUnit * 3);
        textF4.setFont(font);
        textF4.setText(ic.getCost() + "");
        textF4.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                fieldModified();
            }

            public void removeUpdate(DocumentEvent e) {
                fieldModified();
            }

            public void changedUpdate(DocumentEvent e) {
                fieldModified();
            }
        });
        add(textF4);

        JLabel label5 = utiliGraf.labelTitleBacker3W("Precio Venta:");
        label5.setBounds(anchoUnit * 58, altoUnit, anchoUnit * 7, altoUnit * 3);
        add(label5);

        textF5.setBounds(anchoUnit * 65, altoUnit, anchoUnit * 5, altoUnit * 3);
        textF5.setFont(font);
        textF5.setText(ic.getPrice() + "");
        textF5.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                fieldModified();
            }

            public void removeUpdate(DocumentEvent e) {
                fieldModified();
            }

            public void changedUpdate(DocumentEvent e) {
                fieldModified();
            }
        });
        add(textF5);

        butSel = utiliGraf.button3("ELEGIR", anchoUnit * 72, altoUnit, anchoUnit * 4);
        butSel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (butSel.getText().equals("ELEGIR")) {
                        selectPNB(true);
                    } else {
                        selectPNB(false);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ItemcardInn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        butSel.setVisible(false);
        add(butSel);
    }

    public void selectPNB(boolean tf) {
        if (tf == true) {
            fieldModified();
        } else {
            fieldReset();
        }
    }

    private void fieldModified() {
        setBackground(bluStt);
        butSel.setText("QUITAR");
    }

    private void fieldReset() {
        textF2.setSelectedItem(ic.getCategory());
        textF3.setValue(ic.getStock());
        textF4.setText(ic.getCost() + "");
        textF5.setText(ic.getPrice() + "");
        setBackground(bluSt);
        butSel.setText("ELEGIR");
    }

    public Itemcard getIc() {
        return ic;
    }

    public void setIc(Itemcard ic) {
        this.ic = ic;
    }

    public JComboBox getTextF2() {
        return textF2;
    }

    public void setTextF2(JComboBox textF2) {
        this.textF2 = textF2;
    }

    public JSpinner getTextF3() {
        return textF3;
    }

    public void setTextF3(JSpinner textF3) {
        this.textF3 = textF3;
    }

    public JTextField getTextF4() {
        return textF4;
    }

    public void setTextF4(JTextField textF4) {
        this.textF4 = textF4;
    }

    public JTextField getTextF5() {
        return textF5;
    }

    public void setTextF5(JTextField textF5) {
        this.textF5 = textF5;
    }

    public JButtonMetalBlu getButSel() {
        return butSel;
    }

    public void setButSel(JButtonMetalBlu butSel) {
        this.butSel = butSel;
    }
}
