package salonmanager.utilidades;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import salonmanager.SalonManager;

public class UtilidadesSalon extends JFrame {

    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    Utilidades utili = new Utilidades();
    SalonManager sm = new SalonManager();

    public ArrayList<Integer> salonConfigValues(Integer tab, int anchoPane, int alturaPane) {
        ArrayList<Integer> configValues = new ArrayList<Integer>();
        int fontSize = 0;
        int wUnit = 0;
        int hUnit = 0;
        int rows = 0;
        int col = 0;

        if (tab == 12) {
            fontSize = 48;
            wUnit = (anchoPane) / 21;
            hUnit = (alturaPane) / 16;
            rows = 3;
            col = 4;
        } else if (tab == 24) {
            fontSize = 36;
            wUnit = (anchoPane) / 31;
            hUnit = (alturaPane) / 21;
            rows = 4;
            col = 6;
        } else if (tab == 35) {
            fontSize = 24;
            wUnit = (anchoPane) / 36;
            hUnit = (alturaPane) / 26;
            rows = 5;
            col = 7;
        } else if (tab == 48) {
            fontSize = 16;
            wUnit = (anchoPane) / 41;
            hUnit = (alturaPane) / 31;
            rows = 6;
            col = 8;
        }

        configValues.add(fontSize);
        configValues.add(wUnit);
        configValues.add(hUnit);
        configValues.add(rows);
        configValues.add(col);
        
        return configValues;
    }
}
