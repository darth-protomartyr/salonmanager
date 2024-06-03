package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.persistencia.DAOItemcard;

public class ServicioItemcard {
    DAOItemcard daoIC = new DAOItemcard();

    public String codeCreator(String category) throws Exception {
        DAOItemcard daoIC = new DAOItemcard();
        String code = "";
        ArrayList<Itemcard> items = daoIC.listItemsByCategory(category);
        int num1 = 0;
        if (items.size() > 0) {
            for (Itemcard ic : items) {
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

//    public void ingresarItem(Itemcard itemAux) throws Exception {
//        daoIC.saveItemcard(itemAux);
//    }
//
//    public void modificarItem(Itemcard itemAux, String name, String category, String description, double cost, double price, int stock, boolean tipAlta) throws Exception {
//        daoIC.modificarItem(itemAux, name, category, description, cost, price, stock, tipAlta);
//    }
}
