package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.utilidades.Utilidades;

public class ServicioItemSale {
    DAOItemSale daoI = new DAOItemSale();
    Utilidades utili = new Utilidades();
    public void createItemSale(Salon salon) throws Exception {
        ArrayList<Itemcard> items = salon.getItemsTableAux();
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
        
        int idClient = 0;
        if (tabId.equals("delivery")) {
            idClient = salon.getJbdAux().getDelivery().getConsumer().getId();
        }

        int wsId = salon.getWorkshiftNow().getId();
        for(Itemcard ic : items) {
            Timestamp ts = new Timestamp(new Date().getTime());
            ItemSale is = new ItemSale(ic.getId(), ic.getCategory(), tabId, waiterId, wsId, ic.getCost(), utili.priceMod(ic, salon), idClient, ts);
            daoI.saveItemSale(is);
        }
    }
}