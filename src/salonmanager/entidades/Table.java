/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gonzalo
 */
public class Table {

    int num;
    String pos;
    Timestamp openTime;
    String id;
    boolean open;
    boolean bill;
    ArrayList<ItemCarta> order;
    ArrayList<ItemCarta> gifts;
    ArrayList<Integer> units;
    User waiter;
    int discount;
    double error;
    double total;
    

    public Table() {
    }

    //Creacion
    public Table(int num, String pos, User waiter) {
        this.num = num;
        this.pos = pos;
        this.openTime = new Timestamp(new Date().getTime());
        this.id = num + pos + "-" + openTime;
        this.open = true;
        this.bill = false;
        this.order = new ArrayList<ItemCarta>();
        this.gifts = new ArrayList<ItemCarta>();
        this.units = new ArrayList<Integer>();
        this.waiter = waiter;
        this.discount = 0;
        this.error = 0;
        this.total = 0;
    }

    //Consulta
    public Table(int num, String pos, Timestamp openTime, String id, boolean open, boolean bill, ArrayList<ItemCarta> order, ArrayList<ItemCarta> gifts, ArrayList<Integer> units, User waiter, int discount, double error, double total) {
        this.num = num;
        this.pos = pos;
        this.openTime = openTime;
        this.id = id;
        this.open = open;
        this.bill = bill;
        this.order = order;
        this.gifts = gifts;
        this.units = units;
        this.waiter = waiter;
        this.discount = discount;
        this.error  = error;
        this.total = total;
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

    public ArrayList<Integer> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Integer> units) {
        this.units = units;
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
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}