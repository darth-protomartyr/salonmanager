/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.Salon;
import salonmanager.entidades.ItemCarta;
import salonmanager.entidades.Table;
import salonmanager.persistencia.DAOItemCarta;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;

/**
 *
 * @author Gonzalo
 */
public class ServicioSalon {

    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    DAOItemCarta daoIC = new DAOItemCarta();
    Utilidades utili = new Utilidades();
    Salon salon = null;

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

    public int itemRepeat(ItemCarta ic, ArrayList<ItemCarta> itemsTableAux) {
        int rep = -1;
        for (int i = 0; i < itemsTableAux.size(); i++) {
            if (ic.equals(itemsTableAux.get(i))) {
                rep = i;
            }
        };
        return rep;
    }

    public ArrayList<ItemCarta> itemTableLesser(ArrayList<ItemCarta> items, ItemCarta ic) {
        ArrayList<ItemCarta> itemsLesser = new ArrayList<ItemCarta>(items);
        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            if (itemsLesser.get(i).getId() == ic.getId()) {
                index = i;
            }
        }
        itemsLesser.remove(index);
        return itemsLesser;
    }

//Resumen de Cuenta Total
    public double countBill(Table tableAux) {
        double bill = 0;
        int discount = tableAux.getDiscount();
        ArrayList<ItemCarta> itemsTable = tableAux.getOrder();

        for (int i = 0; i < itemsTable.size(); i++) {
            bill = bill + (itemsTable.get(i).getPrice());
        }

        if (discount > 0) {
            bill = bill - Math.round(bill * discount / 100);
        }

//      bill += tableAux.getErrorMod;
        return bill;
    }

//resumen de cuenta abonada en pago parcial  
    public double partialBillPayed(Table tableAux) {
        double partial = 0;
        int discount = tableAux.getDiscount();
        ArrayList<ItemCarta> itemsPartial = new ArrayList<ItemCarta>(tableAux.getPartialPayed());
        for (int i = 0; i < itemsPartial.size(); i++) {
            partial += itemsPartial.get(i).getPrice();
        }

        double disc = (double) discount;
        partial = partial * (1 - disc / 100);

        ArrayList<ItemCarta> itemsPartialAux = new ArrayList<ItemCarta>(tableAux.getAuxiliarPartialPayedNoDiscount());
        double partialND = 0;
        for (int i = 0; i < itemsPartialAux.size(); i++) {
            partialND += itemsPartialAux.get(i).getPrice();
        }

        partial += partialND /* + tableAux.getErrorMod*/;
        return partial;
    }

//Resumen de cuenta aplicada a ventana de pago parcial
    public double billPartial(ArrayList<ItemCarta> items, int discount) {
        double bill = 0;
        for (ItemCarta ic : items) {
            bill += ic.getPrice();
        }
        double disc = discount;
        bill = bill * (1 - disc / 100);
        return bill;
    }

    public void createTable(Table tableAux) throws Exception {
        daoT.guardarTable(tableAux);
        daoU.guardarWaiterTable(tableAux);
    }

    public ArrayList<ItemCarta> itemDeployer(ItemCarta ic, int num) {
        ArrayList<ItemCarta> al = new ArrayList<ItemCarta>();
        int count = 0;
        while (count < num) {
            al.add(ic);
            count += 1;
        }
        return al;
    }

    public void addItemOrder(Table tableAux, ArrayList<ItemCarta> arrayAux) throws Exception {
        for (int i = 0; i < arrayAux.size(); i++) {
            daoIC.guardarItemOrderTable(arrayAux.get(i), tableAux);
        }
    }
}
