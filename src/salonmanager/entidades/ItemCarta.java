package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.servicios.ServicioItemCarta;

public class ItemCarta {

    ServicioItemCarta sic = new ServicioItemCarta();

    int id;
    String code;
    String name;
    String caption;
    String description;
    double cost;
    double price;
    int stock;
    Timestamp dateCreation;
    Timestamp dateCostUpdate;
    boolean altaItem;

    public ItemCarta() {

    }

    public ItemCarta(String name, String caption, String description, double cost, double price, int stock) throws Exception {
        this.code = sic.codeCreator(caption);
        this.name = name;
        this.caption = caption;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.dateCreation = new Timestamp(new Date().getTime());
        this.dateCostUpdate = null;
        this.altaItem = true;
    }

    public ItemCarta(int id, String code, String name, String caption, String description, double cost, double price, int stock, Timestamp dateCreation, Timestamp dateCostUpdate, boolean altaItem) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.caption = caption;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
        this.dateCreation = dateCreation;
        this.dateCostUpdate = dateCostUpdate;
        this.altaItem = altaItem;
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

    public Timestamp getDateCostUpdate() {
        return dateCostUpdate;
    }

    public void setDateCostUpdate(Timestamp dateCostUpdate) {
        this.dateCostUpdate = dateCostUpdate;
    }

    public boolean isAltaItem() {
        return altaItem;
    }

    public void setAltaItem(boolean altaItem) {
        this.altaItem = altaItem;
    }
}
