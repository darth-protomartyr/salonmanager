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
    ArrayList<Table> shiftTables;
    ArrayList<String> registers;
    double totalShift;
    double errorShift;
    
    public Workshift() {
        
    }

    //Crear Turno
    public Workshift(User cashier, ArrayList<Table> shiftTables, ArrayList<String> registers, double totalShift, double errorShift) {
        this.cashier = cashier;
        this.openShift =  new Timestamp(new Date().getTime());
        this.openShift =  null;
        this.shiftTables = new ArrayList<Table>();
        this.registers = new ArrayList<String>();
        this.totalShift = 0;
        this.errorShift = 0;
    }
    
    //Consulta Turno

    public Workshift(int id, User cashier, Timestamp openShift, Timestamp closeShift, ArrayList<Table> shiftTables, ArrayList<String> registers, double totalShift, double errorShift) {
        this.id = id;
        this.cashier = cashier;
        this.openShift = openShift;
        this.closeShift = closeShift;
        this.shiftTables = shiftTables;
        this.registers = registers;
        this.totalShift = totalShift;
        this.errorShift = errorShift;
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

    public double getTotalShift() {
        return totalShift;
    }

    public void setTotalShift(double totalShift) {
        this.totalShift = totalShift;
    }

    public double getErrorShift() {
        return errorShift;
    }

    public void setErrorShift(double errorShift) {
        this.errorShift = errorShift;
    }
}
