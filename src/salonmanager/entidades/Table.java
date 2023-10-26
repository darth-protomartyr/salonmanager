package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Table {

    int num;
    String pos;
    Timestamp openTime;
    String id;
    boolean open;
    boolean bill;
    ArrayList<ItemCarta> order;
    ArrayList<ItemCarta> gifts;
    ArrayList<ItemCarta> toPay;
    ArrayList<ItemCarta> partialPayed;
    ArrayList<ItemCarta> auxiliarPartialPayedNoDiscount;
    ArrayList<ItemCarta> errorItems;
    User waiter;
    int discount;
    double error;
    double priceCorrection;
    double amountCash;
    double amountElectronic;
    double total;

    public Table() {
    }

    //Creacion
    public Table(int num, String pos, User waiter) {
        this.num = num;
        this.pos = pos;
        this.openTime = new Timestamp(new Date().getTime());
        this.id = emptyEraser(num + pos + "_" + openTime);
        this.open = true;
        this.bill = false;
        this.order = new ArrayList<ItemCarta>();
        this.gifts = new ArrayList<ItemCarta>();
        this.toPay = new ArrayList<ItemCarta>();
        this.partialPayed = new ArrayList<ItemCarta>();
        this.auxiliarPartialPayedNoDiscount = new ArrayList<ItemCarta>();
        this.errorItems = new ArrayList<ItemCarta>();
        this.waiter = waiter;
        this.discount = 0;
        this.error = 0;
        this.priceCorrection = 0;
        this.total = 0;
    }

    //Consulta
    public Table(int num, String pos, Timestamp openTime, String id, boolean open, boolean bill, ArrayList<ItemCarta> order, ArrayList<ItemCarta> gifts, ArrayList<ItemCarta> toPay, ArrayList<ItemCarta> partialPayed, ArrayList<ItemCarta> partialPayedNoDiscount, ArrayList<ItemCarta> errorItems, User waiter, int discount, double error, double priceCorrection, double amountCash, double amountElectronic, double total) {
        this.num = num;
        this.pos = pos;
        this.openTime = openTime;
        this.id = id;
        this.open = open;
        this.bill = bill;
        this.order = order;
        this.gifts = gifts;
        this.toPay = toPay;
        this.partialPayed = partialPayed;
        this.auxiliarPartialPayedNoDiscount = partialPayedNoDiscount;
        this.errorItems = errorItems;
        this.waiter = waiter;
        this.discount = discount;
        this.error = error;
        this.priceCorrection = priceCorrection;
        this.amountCash = amountCash;
        this.amountElectronic = amountElectronic;
        this.total = total;
    }
    
    //crear table pendiente para cambio de turno
    public Table(int num, String pos, boolean bill, ArrayList<ItemCarta> order, ArrayList<ItemCarta> gifts, User waiter, int discount, double total) {
        this.num = num;
        this.pos = pos;
        this.openTime = new Timestamp(new Date().getTime());
        this.id = emptyEraser(num + pos + "_" + openTime);
        this.open = true;
        this.bill = bill;
        this.order = order;
        this.gifts = gifts;    
        this.toPay = new ArrayList<ItemCarta>();
        this.partialPayed = new ArrayList<ItemCarta>();
        this.auxiliarPartialPayedNoDiscount = new ArrayList<ItemCarta>();
        this.errorItems = new ArrayList<ItemCarta>();
        this.waiter = waiter;
        this.discount = discount;
        this.error = 0;
        this.priceCorrection = 0;
        this.total = total;
    }
    

    //Clonar
    public Table(Table tab) {
        this.num = tab.getNum();
        this.pos = tab.getPos();
        this.openTime = tab.getOpenTime();
        this.id = tab.getId();
        this.open = tab.isOpen();
        this.bill = tab.isBill();
        this.order = tab.getOrder();
        this.gifts = tab.getGifts();
        this.toPay = tab.getToPay();
        this.partialPayed = tab.getPartialPayed();
        this.auxiliarPartialPayedNoDiscount = tab.getAuxiliarPartialPayedNoDiscount();
        this.waiter = tab.getWaiter();
        this.discount = tab.getDiscount();
        this.error = tab.getError();
        this.errorItems = tab.getErrorItems();
        this.priceCorrection = tab.getPriceCorrection();
        this.amountCash = tab.getAmountCash();
        this.amountElectronic = tab.getAmountElectronic();
        this.total = tab.getTotal();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isBill() {
        return bill;
    }

    public void setBill(boolean bill) {
        this.bill = bill;
    }

    public ArrayList<ItemCarta> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<ItemCarta> order) {
        this.order = order;
    }

    public void setGifts(ArrayList<ItemCarta> gifts) {
        this.gifts = gifts;
    }

    public ArrayList<ItemCarta> getGifts() {
        return gifts;
    }

    public void setToPay(ArrayList<ItemCarta> toPay) {
        this.toPay = toPay;
    }

    public ArrayList<ItemCarta> getToPay() {
        return gifts;
    }

    public void setPartialPayed(ArrayList<ItemCarta> partialPayed) {
        this.partialPayed = partialPayed;
    }

    public ArrayList<ItemCarta> getPartialPayed() {
        return partialPayed;
    }

    public void setAuxiliarPartialPayedNoDiscount(ArrayList<ItemCarta> partialPayedNoDiscount) {
        this.auxiliarPartialPayedNoDiscount = partialPayedNoDiscount;
    }

    public ArrayList<ItemCarta> getAuxiliarPartialPayedNoDiscount() {
        return auxiliarPartialPayedNoDiscount;
    }

    public void setErrorItems(ArrayList<ItemCarta> errorItems) {
        this.errorItems = errorItems;
    }

    public ArrayList<ItemCarta> getErrorItems() {
        return errorItems;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waiter) {
        this.waiter = waiter;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.total = error;
    }

    public double getPriceCorrection() {
        return priceCorrection;
    }

    public void setPriceCorrection(double priceCorrection) {
        this.priceCorrection = priceCorrection;
    }

    public double getAmountCash() {
        return amountCash;
    }

    public void setAmountCash(double amountCash) {
        this.amountCash = amountCash;
    }

    public double getAmountElectronic() {
        return amountElectronic;
    }

    public void setAmountElectronic(double amountElectronic) {
        this.amountElectronic = amountElectronic;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private String emptyEraser(String s) {
        s = s.replace(" ", "_");
        return s;
    }

    @Override
    public String toString() {
        return "Table{" + "num=" + num + ", pos=" + pos + ", openTime=" + openTime + ", id=" + id + ", open=" + open + ", bill=" + bill + ", order=" + order + ", gifts=" + gifts + ", toPay=" + toPay + ", partialPayed=" + partialPayed + ", auxiliarPartialPayedNoDiscount=" + auxiliarPartialPayedNoDiscount + ", errorItems=" + errorItems + ", waiter=" + waiter + ", discount=" + discount + ", error=" + error + ", priceCorrection=" + priceCorrection + ", amountCash=" + amountCash + ", amountElectronic=" + amountElectronic + ", total=" + total + '}';
    }
}
