package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.Salon;
import salonmanager.entidades.bussiness.ItemMonitor;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.persistencia.DAOItemMonitor;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServicioItemMonitor {

    DAOItemMonitor daoim = new DAOItemMonitor();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    
    void createItemMonitor(Salon sal, Table tableAux, Itemcard ic, boolean indi) throws Exception {
        String indication = "";
        if (indi == true) {
            indication = utiliMsg.requestIndication();
        }
        ItemMonitor im = new ItemMonitor(tableAux, ic, indication);
        daoim.saveIMon(im);
        sal.addItemMonitorList(im);
    }

    public ArrayList<ItemMonitor> downOpenIMon(ArrayList<ItemMonitor> itemsMntr, Table tableAux) throws Exception {
        ArrayList<ItemMonitor> ims = new ArrayList<>();
        for (int i = 0; i < itemsMntr.size(); i++) {
            if (itemsMntr.get(i).getTableIMon().getId().equals(tableAux.getId())) {
                itemsMntr.get(i).setOpenItemMonitor(false);
                daoim.downOpenImon(itemsMntr.get(i));
            }
            ims.add(itemsMntr.get(i));
        }
        return ims;
    }

}
