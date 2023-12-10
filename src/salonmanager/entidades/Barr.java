package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Barr {

    Timestamp openTime;
    String id;
    boolean open;
    ArrayList<Itemcard> order;
    ArrayList<Itemcard> gifts;
    User cashier;
    int discount;
    double amountCash;
    double amountElectronic;
    double total;
    String comments;
    boolean activeBarr;

    public Barr() {
    }

    //Creacion
    public Barr(User waiter) {
        this.openTime = new Timestamp(new Date().getTime());
        this.id = emptyEraser( "_" + openTime);
        this.open = true;
        this.order = new ArrayList<Itemcard>();
        this.gifts = new ArrayList<Itemcard>();
        this.cashier = cashier;
        this.discount = 0;
        this.total = 0;
        this.comments = "";
        this.activeBarr = true;
    }

    //Consulta
    public Barr( Timestamp openTime, String id, boolean open, boolean activeTable, ArrayList<Itemcard> order, ArrayList<Itemcard> gifts, User waiter, int discount, double error, double priceCorrection, double amountCash, double amountElectronic, double total, String comments) {
        this.openTime = openTime;
        this.id = id;
        this.open = open;
        this.order = order;
        this.gifts = gifts;
        this.cashier = cashier;
        this.discount = discount;
        this.amountCash = amountCash;
        this.amountElectronic = amountElectronic;
        this.total = total;
        this.comments = comments;
        this.activeBarr = activeBarr;
    }


    private String emptyEraser(String s) {
        s = s.replace(" ", "_");
        return s;
    }

}