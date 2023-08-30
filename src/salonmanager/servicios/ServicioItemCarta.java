package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.entidades.ItemCarta;
import salonmanager.persistencia.DAOItemCarta;

public class ServicioItemCarta {
    public String codeCreator(String caption) throws Exception {
        DAOItemCarta daoIC = new DAOItemCarta();
        String code = "";
        ArrayList<ItemCarta> items = daoIC.listItemsByCaption(caption);
        int num1 = 1;
        if (items.size() > 0) {
            for (ItemCarta ic : items) {
                String s = ic.getCode();
                s = s.substring(1);
                int num2 = Integer.parseInt(s);
                if (num2 > num1) {
                    num1 = num2;
                }
            }
        }
        if (caption.equals("BEBIDAS")) {
            code = "B" + num1;
        } else if (caption.equals("PLATOS")) {
            code = "P" + num1;
        } else if (caption.equals("CAFETERIA")) {
            code = "C" + num1;
        } else {
            code = "O" + num1;
        }
        return code;
    }
}
