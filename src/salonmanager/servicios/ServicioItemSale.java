package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.utilidades.Utilidades;

public class ServicioItemSale {
    DAOItemSale daoI = new DAOItemSale();
    Utilidades utili = new Utilidades();
    public void createItemSale(Salon salon) throws Exception {
        ArrayList<ItemCard> items = salon.getItemsTableAux();
        String tabId = salon.getTableAux().getPos();
        if (!tabId.equals("barra") && !tabId.equals("delivery")) {
            tabId = "tab";
        }

        String waiterId = "";
        if (tabId.equals("barra") || tabId.equals("delivery")) {
            waiterId = salon.getUser().getId();
        } else {
           waiterId = salon.getWaiterAux().getId();
        }

        int wsId = salon.getWorkshiftNow().getId();
        for(ItemCard ic : items) {
            Timestamp ts = new Timestamp(new Date().getTime());
            ItemSale is = new ItemSale(ic.getId(), ic.getCategory(), tabId, waiterId, wsId, utili.priceMod(ic, salon), ts);
            daoI.saveItemSale(is);
        }
    }
}