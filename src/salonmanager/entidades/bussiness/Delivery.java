package salonmanager.entidades.bussiness;

import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Table;
import salonmanager.persistencia.DAODeliveryConsumer;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;


public class Delivery {
    DAODeliveryConsumer daoC = new DAODeliveryConsumer();
    DAOTable daoT = new DAOTable();
    DAOUser daoU = new DAOUser();
    
    int id;
    DeliveryConsumer consumer;
    Table tab;
    User deli;
    boolean open;
    boolean active;

    public Delivery() {
    }

    public Delivery(String phone,String userId) throws Exception {
        this.consumer = consumerBack(phone);
        this.deli = daoU.getUserById(userId);
        this.open = true;
        this.active = true;
    }

    public Delivery(int id, String phone, String tab, String deli, boolean open, boolean active) throws Exception {
        this.id = id;
        this.consumer = consumerBack(phone);
        this.tab = tabBack(tab);
        this.deli = deliBack(deli);
        this.open = open;
        this.active = active;
    }
    
    private DeliveryConsumer consumerBack(String phone) throws Exception {
        DeliveryConsumer cmr = daoC.getConsumerByPhone(phone);
        return cmr;
    }

    private Table tabBack(String tabId) throws Exception {
        Table tab = daoT.getTableById(tabId);
        return tab;    
    }

    private User deliBack(String deli) throws Exception {
        User user = daoU.getUserById(deli);
        return user;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeliveryConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DeliveryConsumer consumer) {
        this.consumer = consumer;
    }

    public Table getTab() {
        return tab;
    }

    public void setTab(Table tab) {
        this.tab = tab;
    }

    public User getDeli() {
        return deli;
    }

    public void setDeli(User deli) {
        this.deli = deli;
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
