package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.servicios.ServicioItemcard;

public class Itemcard {

    ServicioItemcard sic = new ServicioItemcard();
    int id;
    String code;
    String name;
    String caption;
    String description;
    double cost;
    double price;
    int stock;
    Timestamp dateCreation;
    Timestamp dateUpdate;
    boolean activeTip;
    boolean activeItem;

    public Itemcard() {
        
    }

    public Itemcard(String name, String caption, String description, double cost, double price, int stock, boolean activeTip) throws Exception {
        this.code = sic.codeCreator(caption);
        this.name = name;
        this.caption = caption;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.activeTip = activeTip;
        this.dateCreation = new Timestamp(new Date().getTime());
        this.activeItem = true;
    }

    public Itemcard(int id, String code, String name, String caption, String description, double cost, double price, int stock, Timestamp dateCreation, Timestamp dateUpdate, boolean activeTip, boolean activeItem) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.caption = caption;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateUpdate;
        this.activeTip = activeTip;
        this.activeItem = activeItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public boolean isActiveTip() {
        return activeTip;
    }

    public void setActiveTip(boolean activeTip) {
        this.activeTip = activeTip;
    }
    
    

    public boolean isActiveItem() {
        return activeItem;
    }

    public void setActiveItem(boolean activeItem) {
        this.activeItem = activeItem;
    }

    @Override
    public String toString() {
        return "Itemcard{" + ", name=" + name + '}';
    }
    
    
    
    
    
    
}
