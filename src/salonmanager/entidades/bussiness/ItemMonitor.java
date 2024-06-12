package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;

public class ItemMonitor {
    DAOItemcard daoI = new DAOItemcard();
    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();

    String idIMon;
    Table tableIMon; //alt 1
    Itemcard itemIMon;// alt 1
    String posIMon;
    boolean initIMon;
    Timestamp dateInitIMon;
    boolean cookIMon;
    Timestamp dateCookIMon;
    boolean readyIMon;
    Timestamp dateReadyIMon;
    boolean otwIMon;
    Timestamp dateOtwIMon;
    boolean openItemMonitor;
    boolean activeItemMonitor;
    String indications;
            
    public ItemMonitor() {

    }

    public ItemMonitor(Table tableIMon, Itemcard itemIMon, String indications) {
        Timestamp ts = new Timestamp(new Date().getTime());
        String orderKind = okBacker(tableIMon);
        this.idIMon = tableIMon.getId() + "_" + itemIMon.getId();
        this.tableIMon = tableIMon;
        this.itemIMon = itemIMon;
        this.posIMon = orderKind;
        this.initIMon = true;
        this.dateInitIMon = ts;
        this.cookIMon = false;
        this.dateCookIMon = null;
        this.readyIMon = false;
        this.dateReadyIMon = null;
        this.otwIMon = false;
        this.dateOtwIMon = null;
        this.openItemMonitor = true;
        this.activeItemMonitor = true;
        this.indications = indications;
    }

    public ItemMonitor(String idIMon, Table tableIMon, Itemcard itemIMon, String posIMon, boolean initIMon, Timestamp dateInitIMon, boolean cookIMon, Timestamp dateCookIMon, boolean readyIMon, Timestamp dateReadyIMon, boolean otwIMon, Timestamp dateOtwIMon, boolean openItemMonitor, boolean activeItemMonitor, String indications) {
        this.idIMon = idIMon;
        this.tableIMon = tableIMon;
        this.itemIMon = itemIMon;
        this.posIMon = posIMon;
        this.initIMon = initIMon;
        this.dateInitIMon = dateInitIMon;
        this.cookIMon = cookIMon;
        this.dateCookIMon = dateCookIMon;
        this.readyIMon = readyIMon;
        this.dateReadyIMon = dateReadyIMon;
        this.otwIMon = otwIMon;
        this.dateOtwIMon = dateOtwIMon;
        this.openItemMonitor = openItemMonitor;
        this.activeItemMonitor = activeItemMonitor;
        this.indications = indications;
    }
    
    public ItemMonitor(String idIMon, String tableIMon, int itemIMon, String posIMon, boolean initIMon, Timestamp dateInitIMon, boolean cookIMon, Timestamp dateCookIMon, boolean readyIMon, Timestamp dateReadyIMon, boolean otwIMon, Timestamp dateOtwIMon, boolean openItemMonitor, boolean activeItemMonitor, String indications) throws Exception {
        this.idIMon = idIMon;
//        this.tableIMon = daoT.getTableById(tableIMon);
                this.tableIMon = completeTable(tableIMon);
        this.itemIMon = daoI.getItemById(itemIMon);
        this.posIMon = posIMon;
        this.initIMon = initIMon;
        this.dateInitIMon = dateInitIMon;
        this.cookIMon = cookIMon;
        this.dateCookIMon = dateCookIMon;
        this.readyIMon = readyIMon;
        this.dateReadyIMon = dateReadyIMon;
        this.otwIMon = otwIMon;
        this.dateOtwIMon = dateOtwIMon;
        this.openItemMonitor = openItemMonitor;
        this.activeItemMonitor = activeItemMonitor;
        this.indications = indications;
    }
    

    public String getIdIMon() {
        return idIMon;
    }

    public void setIdIMon(String idIMon) {
        this.idIMon = idIMon;
    }

    public Table getTableIMon() {
        return tableIMon;
    }

    public void setTableIMon(Table tableIMon) {
        this.tableIMon = tableIMon;
    }

    public Itemcard getItemIMon() {
        return itemIMon;
    }

    public void setItemIMon(Itemcard itemIMon) {
        this.itemIMon = itemIMon;
    }

    public String getPosIMon() {
        return posIMon;
    }

    public void setPosIMon(String posIMon) {
        this.posIMon = posIMon;
    }

    public boolean isInitIMon() {
        return initIMon;
    }

    public void setInitIMon(boolean initIMon) {
        this.initIMon = initIMon;
    }

    public Timestamp getDateInitIMon() {
        return dateInitIMon;
    }

    public void setDateInitIMon(Timestamp dateInitIMon) {
        this.dateInitIMon = dateInitIMon;
    }

    public boolean isCookIMon() {
        return cookIMon;
    }

    public void setCookIMon(boolean cookIMon) {
        this.cookIMon = cookIMon;
    }

    public Timestamp getDateCookIMon() {
        return dateCookIMon;
    }

    public void setDateCookIMon(Timestamp dateCookIMon) {
        this.dateCookIMon = dateCookIMon;
    }

    public boolean isReadyIMon() {
        return readyIMon;
    }

    public void setReadyIMon(boolean readyIMon) {
        this.readyIMon = readyIMon;
    }

    public Timestamp getDateReadyIMon() {
        return dateReadyIMon;
    }

    public void setDateReadyIMon(Timestamp dateReadyIMon) {
        this.dateReadyIMon = dateReadyIMon;
    }

    public boolean isOtwIMon() {
        return otwIMon;
    }

    public void setOtwIMon(boolean otwIMon) {
        this.otwIMon = otwIMon;
    }

    public Timestamp getDateOtwIMon() {
        return dateOtwIMon;
    }

    public void setDateOtwIMon(Timestamp dateOtwIMon) {
        this.dateOtwIMon = dateOtwIMon;
    }

    public boolean isOpenItemMonitor() {
        return openItemMonitor;
    }

    public void setOpenItemMonitor(boolean openItemMonitor) {
        this.openItemMonitor = openItemMonitor;
    }

    public boolean isActiveItemMonitor() {
        return activeItemMonitor;
    }

    public void setActiveItemMonitor(boolean activeItemMonitor) {
        this.activeItemMonitor = activeItemMonitor;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }
    
    

    private String okBacker(Table tab) {
        String ok = "";
        if (tab.getPos().equals("barra")) {
            ok = "barra";
        } else if (tab.getPos().equals("delivery")) {
            ok = "delivery";
        } else {
            ok = "mesa";
        }
        return ok;
    }

    private Table completeTable(String tableIMon) throws Exception {
        Table tab = daoT.getTableById(tableIMon);
        tab.setWaiter(daoU.getWaiterByTable(tableIMon));
        return tab;
    }
}
