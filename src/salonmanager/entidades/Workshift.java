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
public class Workshift {
    int id;
    User cashier;
    Timestamp openShift;
    Timestamp closeShift;
    boolean openWorkshift;
    ArrayList<Table> shiftTables;
    ArrayList<String> registers;
    double giftMountShift;
    double errorMountShift;
    double errorMountShiftReal;
    double totalMountShift;
    double totalMountShiftReal;


    public Workshift() {
        
    }

    //Crear Turno
    public Workshift(User cashier, ArrayList<Table> shiftTables, ArrayList<String> registers, double giftShift, double errorShift, double totalShift) {
        this.cashier = cashier;
        this.openShift =  new Timestamp(new Date().getTime());
        this.closeShift =  null;
        this.openWorkshift = true;
        this.shiftTables = new ArrayList<Table>();
        this.registers = new ArrayList<String>();
        this.giftMountShift = 0;
        this.errorMountShift = 0;
        this.errorMountShiftReal = 0;
        this.totalMountShift = 0;
        this.totalMountShiftReal = 0;
    }
    
    //Consulta Turno

    public Workshift(int id, User cashier, Timestamp openShift, Timestamp closeShift, boolean openWorkshift, ArrayList<Table> shiftTables, ArrayList<String> registers, double giftShift, double errorShift, double errorShiftReal, double totalShift, double totalShiftReal) {
        this.id = id;
        this.cashier = cashier;
        this.openShift = openShift;
        this.closeShift = closeShift;
        this.openWorkshift = openWorkshift;
        this.shiftTables = shiftTables;
        this.registers = registers;
        this.giftMountShift = giftShift;
        this.errorMountShift = errorShift;
        this.errorMountShiftReal = errorShiftReal;
        this.totalMountShift = totalShift;
        this.totalMountShiftReal = totalShiftReal;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCashier() {
        return cashier;
    }

    public void setCashier(User cashier) {
        this.cashier = cashier;
    }

    public Timestamp getOpenShift() {
        return openShift;
    }

    public void setOpenShift(Timestamp openShift) {
        this.openShift = openShift;
    }

    public Timestamp getCloseShift() {
        return closeShift;
    }

    public void setCloseShift(Timestamp closeShift) {
        this.closeShift = closeShift;
    }
    
    public boolean isOpenWorkshift() {
        return openWorkshift;
    }
    
    public void setOpenWorkshift(boolean openWorkshift) {
        this.openWorkshift = openWorkshift;
    }

    public ArrayList<Table> getShiftTables() {
        return shiftTables;
    }

    public void setShiftTables(ArrayList<Table> shiftTables) {
        this.shiftTables = shiftTables;
    }

    public ArrayList<String> getRegisters() {
        return registers;
    }

    public void setRegisters(ArrayList<String> registers) {
        this.registers = registers;
    }

    public double getGiftMountShift() {
        return giftMountShift;
    }

    public void setGiftMountShift(double giftMountShift) {
        this.giftMountShift = giftMountShift;
    }
    
    public double getErrorMountShift() {
        return errorMountShift;
    }

    public void setErrorMountShift(double errorMountShift) {
        this.errorMountShift = errorMountShift;
    }

    public double getTotalMountShift() {
        return totalMountShift;
    }

    public void setTotalMountShift(double totalMountShift) {
        this.totalMountShift = totalMountShift;
    }
}
