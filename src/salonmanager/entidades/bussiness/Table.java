package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import salonmanager.utilidades.Utilidades;

public class Table {
    Utilidades utili = new Utilidades();
    int num;
    String pos;
    Timestamp openTime;
    Timestamp closeTime;
    String id;
    boolean open; //open/close tab
    boolean bill;
    boolean toPay;// indicate tha table has been partial paid 
    ArrayList<Itemcard> order;
    ArrayList<Itemcard> gifts;
    ArrayList<Itemcard> partialPayed; //items partial paid
    ArrayList<Itemcard> partialPayedND; //items partial paid no discount
    User waiter;
    int discount;
    double error;
    double priceCorrection;
    double amountCash;
    double amountElectronic;
    double total;
    String comments;
    boolean activeTable; //active/inactive tab, to be over watched

    public Table() {
    }

    //Creacion
    public Table(int num, String pos, User waiter) {
        this.num = num;
        this.pos = pos;
        this.openTime = new Timestamp(new Date().getTime());
        this.closeTime = null;
        this.id = emptyEraser(num + pos + "_" + openTime);
        this.open = true;
        this.bill = false;
        this.toPay = false;
        this.order = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.toPay = false;
        this.partialPayed = new ArrayList<>();
        this.partialPayedND = new ArrayList<>();
        this.waiter = waiter;
        this.discount = 0;
        this.error = 0;
        this.priceCorrection = 0;
        this.amountCash = 0;
        this.amountElectronic = 0;
        this.total = 0;
        this.comments = "";
        this.activeTable = true;
    }

    //Consulta
    public Table(int num, String pos, Timestamp openTime, Timestamp closeTime, String id, boolean open, boolean bill, boolean toPay,  boolean activeTable, ArrayList<Itemcard> order, ArrayList<Itemcard> gifts, ArrayList<Itemcard> partialPayed, ArrayList<Itemcard> partialPayedNoDiscount, ArrayList<Itemcard> errorItems, User waiter, int discount, double error, double priceCorrection, double amountCash, double amountElectronic, double total, String comments) {
        this.num = num;
        this.pos = pos;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.id = id;
        this.open = open;
        this.bill = bill;
        this.toPay = toPay;
        this.order = order;
        this.gifts = gifts;
        this.partialPayed = partialPayed;
        this.partialPayedND = partialPayedNoDiscount;
        this.waiter = waiter;
        this.discount = discount;
        this.error = utili.round2Dec(error);
        this.priceCorrection = utili.round2Dec(priceCorrection);
        this.amountCash = utili.round2Dec(amountCash);
        this.amountElectronic = utili.round2Dec(amountElectronic);
        this.total = utili.round2Dec(total);
        this.comments = comments;
        this.activeTable = activeTable;
    }
    
    //crear table pendiente para cambio de turno
    public Table(int num, String pos, boolean bill, boolean activeTable, ArrayList<Itemcard> order, User waiter, int discount, double total, String comments) {
        this.num = num;
        this.pos = pos;
        this.openTime = new Timestamp(new Date().getTime());
        this.closeTime = new Timestamp(new Date().getTime());
        this.id = emptyEraser(num + pos + "_" + openTime);
        this.open = true;
        this.bill = bill;
        this.toPay = false;
        this.order = order;
        this.gifts = new ArrayList();    
        this.partialPayed = new ArrayList<>();
        this.partialPayedND = new ArrayList<>();
        this.waiter = waiter;
        this.discount = discount;
        this.error = 0;
        this.priceCorrection = 0;
        this.total = utili.round2Dec(total);
        this.comments = comments;
        this.activeTable = activeTable;
    }

    //Clonar
    public Table(Table tab) {
        this.num = tab.getNum();
        this.pos = tab.getPos();
        this.openTime = tab.getOpenTime();
        this.closeTime = tab.getCloseTime();
        this.id = tab.getId();
        this.open = tab.isOpen();
        this.toPay = tab.isToPay();
        this.bill = tab.isBill();
        this.order = tab.getOrder();
        this.gifts = tab.getGifts();
        this.partialPayed = tab.getPartialPayed();
        this.partialPayedND = tab.getPartialPayedND();
        this.waiter = tab.getWaiter();
        this.discount = tab.getDiscount();
        this.error = utili.round2Dec(tab.getError());
        this.priceCorrection = utili.round2Dec(tab.getPriceCorrection());
        this.amountCash = utili.round2Dec(tab.getAmountCash());
        this.amountElectronic = utili.round2Dec(tab.getAmountElectronic());
        this.total = utili.round2Dec(tab.getTotal());
        this.comments = tab.getComments();
        this.activeTable = tab.isActiveTable();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Itemcard itemcard = (Itemcard) obj;
        return id.equals(itemcard.id); // Comparar basándose en el campo que identifica unívocamente al objeto
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Usar el mismo campo que en equals
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
    
    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
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

    public ArrayList<Itemcard> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Itemcard> order) {
        this.order = order;
    }

    public void setGifts(ArrayList<Itemcard> gifts) {
        this.gifts = gifts;
    }

    public ArrayList<Itemcard> getGifts() {
        return gifts;
    }

    public void setToPay(boolean toPay) {
        this.toPay = toPay;
    }

    public boolean isToPay() {
        return toPay;
    }
    
    public void setActiveTable(boolean activeTable) {
        this.activeTable = activeTable;
    }

    public boolean isActiveTable() {
        return activeTable;
    }
    
    public void setPartialPayed(ArrayList<Itemcard> partialPayed) {
        this.partialPayed = partialPayed;
    }

    public ArrayList<Itemcard> getPartialPayed() {
        return partialPayed;
    }
 
    public void setPartialPayedND(ArrayList<Itemcard> partialPayedNoDiscount) {
        this.partialPayedND = partialPayedNoDiscount;
    }

    public ArrayList<Itemcard> getPartialPayedND() {
        return partialPayedND;
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
        this.error = utili.round2Dec(error);
    }

    public double getPriceCorrection() {
        return priceCorrection;
    }

    public void setPriceCorrection(double priceCorrection) {
        this.priceCorrection = utili.round2Dec(priceCorrection);
    }

    public double getAmountCash() {
        return amountCash;
    }

    public void setAmountCash(double amountCash) {
        this.amountCash = utili.round2Dec(amountCash);
    }

    public double getAmountElectronic() {
        return amountElectronic;
    }

    public void setAmountElectronic(double amountElectronic) {
        this.amountElectronic = utili.round2Dec(amountElectronic);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = utili.round2Dec(total);
    }

    private String emptyEraser(String s) {
        s = s.replace(" ", "_");
        return s;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Table{" + "num=" + num + ", pos=" + pos + ", openTime=" + openTime + ", id=" + id + ", open=" + open + ", bill=" + bill + ", toPay=" + toPay + ", discount=" + discount + ", error=" + utili.round2Dec(error) + ", priceCorrection=" + utili.round2Dec(priceCorrection) + ", amountCash=" + utili.round2Dec(amountCash) + ", amountElectronic=" + utili.round2Dec(amountElectronic) + ", total=" + utili.round2Dec(total) + ", comments=" + comments + ", activeTable=" + activeTable + '}';
    }
}
