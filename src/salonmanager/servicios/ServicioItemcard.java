package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;

public class ServicioItemcard {
    DAOItemcard daoIC = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    public String codeCreator(String category) throws Exception {
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
    

    public void priceUpdater(Itemcard ic, double newPrice) throws Exception {
        ArrayList<String> tabIds = daoT.getActiveIds();
        ArrayList<String> tabIdsIc = daoT.getActiveIds();
        for (int i = 0; i < tabIds.size(); i++) {
            tabIdsIc = daoT.activeTabIcMod(ic.getId());
        }
        
        
        
        
    }


}