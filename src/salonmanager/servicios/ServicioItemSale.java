package salonmanager.servicios;

import static java.lang.Integer.parseInt;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        for (Itemcard ic : items) {
            Timestamp ts = new Timestamp(new Date().getTime());
            ItemSale is = new ItemSale(ic.getId(), ic.getCategory(), tabId, waiterId, wsId, ic.getCost(), utili.priceMod(ic, salon), idClient, ts);
            daoI.saveItemSale(is);
        }
    }

    public ArrayList<ItemSale> listarItemsSale(ArrayList<String> salesSt, Timestamp ts1, Timestamp ts2) throws ParseException, Exception {
        ArrayList<ItemSale> iSales = new ArrayList<>();
        ArrayList<Timestamp> tsS = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        for (int i = 0; i < salesSt.size(); i++) {
            String di = salesSt.get(i);

            String[] words = di.split("/");
            
            String d = words[0];
            Date parsedDate = dateFormat.parse(d);
            Timestamp ts = new Timestamp(parsedDate.getTime());
            tsS.add(ts);
            
            String idd = words[1];
            int id = (int) parseInt(idd);
            ids.add(id);
        }
        
        for (int i = 0; i < tsS.size(); i++) {
            Timestamp ts = tsS.get(i);
            if (ts.after(ts1) && ts.before(ts2)) {
                int id = ids.get(i);
                ItemSale is = daoI.askItemSaleById(id);
                iSales.add(is);
            }
        }
        return iSales;
    }
}
