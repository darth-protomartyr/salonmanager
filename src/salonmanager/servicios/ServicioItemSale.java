package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.persistencia.DAOItemSale;

public class ServicioItemSale {
    DAOItemSale daoI = new DAOItemSale();

    public void createItemSale(Salon salon) throws Exception {
        ArrayList<Itemcard> items = salon.getItemsTableAux();
        String tabId = salon.getTableAux().getPos();
        if (!tabId.equals("barr") && !tabId.equals("delivery")) {
            tabId = "tab";
        }
        String waiterId = salon.getWaiterAux().getId();
        int wsId = salon.getWorkshiftNow().getId();
        for(Itemcard ic : items) {
            Timestamp ts = new Timestamp(new Date().getTime());
            ItemSale is = new ItemSale(ic.getId(), ic.getCaption(), tabId, waiterId, wsId, ic.getPrice(), ts);
            daoI.saveItemSale(is);
        }
    }
}