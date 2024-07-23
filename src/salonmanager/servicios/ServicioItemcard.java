package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOTable;

public class ServicioItemcard {
    DAOItemCard daoIC = new DAOItemCard();
    DAOTable daoT = new DAOTable();
    public String codeCreator(String category) throws Exception {
        String code = "";
        ArrayList<ItemCard> items = daoIC.listItemsByCategory(category);
        int num1 = 0;
        if (items.size() > 0) {
            for (ItemCard ic : items) {
                String s = ic.getCode();
                s = s.substring(1);
                int num2 = Integer.parseInt(s);
                if (num2 > num1) {
                    num1 = num2;
                }
            }
        }      
        num1 = num1+1;
        if (category.equals("BEBIDAS")) {
            code = "B" + num1;
        } else if (category.equals("PLATOS")) {
            code = "P" + num1;
        } else if (category.equals("CAFETERIA")) {
            code = "C" + num1;
        } else {
            code = "O" + num1;
        }
        return code;
    }
}