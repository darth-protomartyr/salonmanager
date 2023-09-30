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
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;

/**
 *
 * @author Gonzalo
 */
public class ServicioSalon {

    DAOUser daoU = new DAOUser();
    Utilidades utili = new Utilidades();
    Salon salon = null;

    public int itemRepeat(ItemCarta ic, ArrayList<ItemCarta> itemsTableAux) {
        int rep = -1;
        for (int i = 0; i < itemsTableAux.size(); i++) {
            if (ic.equals(itemsTableAux.get(i))) {
                rep = i;
            }
        };
        return rep;
    }

    public Table itemTableLesser(Table tab, ItemCarta ic) {
        Table tabAux = tab;
        ArrayList<ItemCarta> items = tab.getOrder();
        ArrayList<Integer> units = tab.getUnits();
        int iCIndexEmpty = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == ic.getId()) {
                int u = units.get(i);
                u = u - 1;
                if (u == 0) {
                    iCIndexEmpty = i;
                }
                units.set(i, u);
                tabAux.setUnits(units);
            }
        }
        if (iCIndexEmpty > -1) {
            items.remove(iCIndexEmpty);
            units.remove(iCIndexEmpty);
        }
        tabAux.setOrder(items);
        tabAux.setUnits(units);
        return tabAux;
    }
}