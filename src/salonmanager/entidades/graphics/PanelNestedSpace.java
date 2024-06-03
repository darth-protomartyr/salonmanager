package salonmanager.entidades.graphics;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.utilidades.UtilidadesGraficas;

/**
 *
 * @author Gonzalo
 */
public class PanelNestedSpace extends JPanel {

    String st = "";
    int y = 0;
    Color bluSt = new Color(3, 166, 136);
    Color white = new Color(255, 255, 255);
    ButtonGroup group = new ButtonGroup();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();

    public PanelNestedSpace(String st, int i) {
        this.st = st;
        y = 1 + i * 8;

        setLayout(null);
        setBounds(anchoUnit * 1, altoUnit * y, anchoUnit * 28, altoUnit * 7);
        setBackground(bluSt);

        JLabel label = utiliGraf.labelTitleBacker2W(st);
        label.setBounds(anchoUnit, altoUnit * 0, anchoUnit * 12, altoUnit * 3);
        add(label);

        Font font = new Font("Arial", Font.PLAIN, 12);

        JRadioButton radioButton1 = new JRadioButton("12 Mesas");
        JRadioButton radioButton2 = new JRadioButton("24 Mesas");
        JRadioButton radioButton3 = new JRadioButton("35 Mesas");
        JRadioButton radioButton4 = new JRadioButton("48 Mesas");

        radioButton1.setBounds(anchoUnit * 0, altoUnit * 3, anchoUnit * 7, altoUnit * 3);
        radioButton2.setBounds(anchoUnit * 7, altoUnit * 3, anchoUnit * 7, altoUnit * 3);
        radioButton3.setBounds(anchoUnit * 14, altoUnit * 3, anchoUnit * 7, altoUnit * 3);
        radioButton4.setBounds(anchoUnit * 21, altoUnit * 3, anchoUnit * 7, altoUnit * 3);

        radioButton1.setFont(font);
        radioButton2.setFont(font);
        radioButton3.setFont(font);
        radioButton4.setFont(font);

        radioButton1.setForeground(white);
        radioButton2.setForeground(white);
        radioButton3.setForeground(white);
        radioButton4.setForeground(white);

        radioButton1.setBackground(bluSt);
        radioButton2.setBackground(bluSt);
        radioButton3.setBackground(bluSt);
        radioButton4.setBackground(bluSt);
                
        group.add(radioButton1);
        group.add(radioButton2);
        group.add(radioButton3);
        group.add(radioButton4);

        add(radioButton1);
        add(radioButton2);
        add(radioButton3);
        add(radioButton4);

    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getSt() {
        return st;
    }

    public ButtonGroup getGroup() {
        return group;
    }

    public void setGroup(ButtonGroup group) {
        this.group = group;
    }
}