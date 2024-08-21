package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.persistencia.DAODeliveryClient;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;


public class Delivery {
    DAODeliveryClient daoC = new DAODeliveryClient();
    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
    Utilidades utili = new Utilidades();
    String id;
    DeliveryClient consumer;
    Table tab;
    User deliUser;
    boolean open;
    boolean active;

    public Delivery() {
    }

    public Delivery(String phone,String userId) throws Exception {
        Timestamp ts = new Timestamp(new Date().getTime());
        this.id = utili.emptyToStr(ts + "");
        this.consumer = consumerBack(phone);
        this.deliUser = daoU.getUserById(userId, true);
        this.open = true;
        this.active = true;
    }

    public Delivery(String id, String phone, String tab, String deli, boolean open, boolean active) throws Exception {
        this.id = id;
        this.consumer = consumerBack(phone);
        this.tab = tabBack(tab);
        this.deliUser = deliBack(deli);
        this.open = open;
        this.active = active;
    }
    
    private DeliveryClient consumerBack(String phone) throws Exception {
        DeliveryClient cmr = daoC.getConsumerByPhone(phone);
        return cmr;
    }

    private Table tabBack(String tabId) throws Exception {
        Table tab = daoT.getTableById(tabId);
        return tab;    
    }

    private User deliBack(String deli) throws Exception {
        User user = daoU.getUserById(deli, true);
        return user;        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeliveryClient getConsumer() {
        return consumer;
    }

    public void setConsumer(DeliveryClient consumer) {
        this.consumer = consumer;
    }

    public Table getTab() {
        return tab;
    }

    public void setTab(Table tab) {
        this.tab = tab;
    }

    public User getDeliUser() {
        return deliUser;
    }

    public void setDeliUser(User deliUser) {
        this.deliUser = deliUser;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
