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
    boolean stateWorkshift;
    double totalMountShiftCash;
    double totalMountShiftElectronic;
    double totalMountShift;
    double totalMountShiftReal;
    double errorMountShift;
    double errorMountShiftReal;


    public Workshift() {
    }

    //Crear Turno
    public Workshift(User cashier) {
        this.cashier = cashier;
        this.openShift =  new Timestamp(new Date().getTime());
        this.closeShift =  null;
        this.stateWorkshift = true;
        this.totalMountShiftCash = 0;
        this.totalMountShiftElectronic = 0;
        this.errorMountShift = 0;
        this.errorMountShiftReal = 0;
        this.totalMountShift = 0;
        this.totalMountShiftReal = 0;

    }

    //Consulta Turno
    public Workshift(int id, User cashier, Timestamp openShift, Timestamp closeShift, boolean stateWorkshift, double totalMountShiftCash, double totalMountShiftElectronic, double giftShift, double errorShift, double errorShiftReal, double totalShift, double totalShiftReal) {
        this.id = id;
        this.cashier = cashier;
        this.openShift = openShift;
        this.closeShift = closeShift;
        this.stateWorkshift = stateWorkshift;
        this.totalMountShiftCash = totalMountShiftCash;
        this.totalMountShiftElectronic = totalMountShiftElectronic;        
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

    public boolean isStateWorkshift() {
        return stateWorkshift;
    }

    public void setStateWorkshift(boolean stateWorkshift) {
        this.stateWorkshift = stateWorkshift;
    }

    public double getTotalMountShiftCash() {
        return totalMountShiftCash;
    }

    public void setTotalMountShiftCash(double totalMountShiftCash) {
        this.totalMountShiftCash = totalMountShiftCash;
    }

    public double getTotalMountShiftElectronic() {
        return totalMountShiftElectronic;
    }

    public void setTotalMountShiftElectronic(double totalMountShiftElectronic) {
        this.totalMountShiftElectronic = totalMountShiftElectronic;
    }

    public double getTotalMountShift() {
        return totalMountShift;
    }

    public void setTotalMountShift(double totalMountShift) {
        this.totalMountShift = totalMountShift;
    }

    public double getTotalMountShiftReal() {
        return totalMountShiftReal;
    }

    public void setTotalMountShiftReal(double totalMountShiftReal) {
        this.totalMountShiftReal = totalMountShiftReal;
    }

    public double getErrorMountShift() {
        return errorMountShift;
    }

    public void setErrorMountShift(double errorMountShift) {
        this.errorMountShift = errorMountShift;
    }

    public double getErrorMountShiftReal() {
        return errorMountShiftReal;
    }

    public void setErrorMountShiftReal(double errorMountShiftReal) {
        this.errorMountShiftReal = errorMountShiftReal;
    }
}