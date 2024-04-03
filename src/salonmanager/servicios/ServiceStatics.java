/*

*/
package salonmanager.servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.persistencia.DAOItemSale;
import salonmanager.persistencia.DAOWorkshift;

public class ServiceStatics {
    DAOWorkshift daoW = new DAOWorkshift();
    DAOItemSale daoIS = new DAOItemSale();
    
//    public void listSellsByTime(Timestamp tsInit, Timestamp tsEnd) throws Exception {
//        ArrayList<ItemSale> iSales = new ArrayList<ItemSale>();
//        ArrayList<Integer> wsIds = daoW.listIdByDate(tsInit, tsEnd);
//        for (int i = 0; i < wsIds.size(); i++) {
//            ArrayList<ItemSale> iSalesWs = daoIS.askItemSaleByWorkshift(i);
//            iSales.addAll(iSalesWs);
//        }
//               
//    }
    
}
