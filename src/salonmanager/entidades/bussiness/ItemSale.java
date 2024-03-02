package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Gonzalo
 */
public class ItemSale {
    private int saleId;
    private int itemSaleId;
    private String itemSaleCaption;
    private String itemSaleTabPos;
    private String itemSaleWaiterId;
    private int itemSaleWorkshiftId;
    private double itemSalePrice;
    private Timestamp itemSaleDate;
    private boolean itemSaleActive;

    public ItemSale() {
    }

    public ItemSale(int itemSaleId, String itemSaleCaption, String itemSaleTabPos, String itemSaleWaiterId, int itemSaleWorkshiftId, double itemSalePrice, Timestamp itemSaleDate) {
        this.itemSaleId = itemSaleId;
        this.itemSaleCaption = itemSaleCaption;
        this.itemSaleTabPos = itemSaleTabPos;
        this.itemSaleWaiterId = itemSaleWaiterId;
        this.itemSaleWorkshiftId = itemSaleWorkshiftId;
        this.itemSalePrice = itemSalePrice;
        this.itemSaleDate = itemSaleDate;
        this.itemSaleActive = true;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getItemSaleId() {
        return itemSaleId;
    }

    public void setItemSaleId(int itemSaleId) {
        this.itemSaleId = itemSaleId;
    }

    public String getItemSaleCaption() {
        return itemSaleCaption;
    }

    public void setItemSaleCaption(String itemSaleCaption) {
        this.itemSaleCaption = itemSaleCaption;
    }
    
    public String getItemSaleTabPos() {
        return itemSaleTabPos;
    }

    public void setItemSaleTabPos(String itemSaleTabPos) {
        this.itemSaleTabPos = itemSaleTabPos;
    }
    
    
    
    
    

    public String getItemSaleWaiterId() {
        return itemSaleWaiterId;
    }

    public void setItemSaleWaiterId(String itemSaleWaiterId) {
        this.itemSaleWaiterId = itemSaleWaiterId;
    }

    public int getItemSaleWorkshiftId() {
        return itemSaleWorkshiftId;
    }

    public void setItemSaleWorkshiftId(int itemSaleWorkshiftId) {
        this.itemSaleWorkshiftId = itemSaleWorkshiftId;
    }

    public double getItemSalePrice() {
        return itemSalePrice;
    }

    public void setItemSalePrice(double itemSalePrice) {
        this.itemSalePrice = itemSalePrice;
    }

    public Timestamp getItemSaleDate() {
        return itemSaleDate;
    }

    public void setItemSaleDate(Timestamp itemSaleDate) {
        this.itemSaleDate = itemSaleDate;
    }

    public boolean isItemSaleActive() {
        return itemSaleActive;
    }

    public void setItemSaleActive(boolean itemSaleActive) {
        this.itemSaleActive = itemSaleActive;
    }

    
}