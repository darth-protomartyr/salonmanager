/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.bussiness;

import salonmanager.entidades.bussiness.User;
import java.sql.Timestamp;
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
    boolean activeWorkshift;

    public Workshift() {
    }

    //Crear Turno
    public Workshift(User cashier) {
        this.cashier = cashier;
        this.openShift = new Timestamp(new Date().getTime());
        this.closeShift = null;
        this.stateWorkshift = true;
        this.totalMountShiftCash = 0;
        this.totalMountShiftElectronic = 0;
        this.errorMountShift = 0;
        this.errorMountShiftReal = 0;
        this.totalMountShift = 0;
        this.totalMountShiftReal = 0;
        this.activeWorkshift = true;

    }

    //Consulta Turno
    public Workshift(int id, User cashier, Timestamp openShift, Timestamp closeShift, boolean stateWorkshift, double totalMountShiftCash, double totalMountShiftElectronic, double giftShift, double errorShift, double errorShiftReal, double totalShift, double totalShiftReal, boolean activeWorkshift) {
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
        this.activeWorkshift = activeWorkshift;
    }

    public int getWsId() {
        return id;
    }

    public void setWsId(int id) {
        this.id = id;
    }

    public User getWsCashier() {
        return cashier;
    }

    public void setWsCashier(User cashier) {
        this.cashier = cashier;
    }

    public Timestamp getWsOpen() {
        return openShift;
    }

    public void setWsOpen(Timestamp openShift) {
        this.openShift = openShift;
    }

    public Timestamp getWsClose() {
        return closeShift;
    }

    public void setWsClose(Timestamp closeShift) {
        this.closeShift = closeShift;
    }

    public boolean isWsState() {
        return stateWorkshift;
    }

    public void setWsState(boolean stateWorkshift) {
        this.stateWorkshift = stateWorkshift;
    }

    public boolean isActiveWs() {
        return activeWorkshift;
    }

    public void setActiveWs(boolean activeWorkshift) {
        this.activeWorkshift = activeWorkshift;
    }

    public double getWsTotalMountCash() {
        return totalMountShiftCash;
    }

    public void setWsTotalMountCash(double totalMountShiftCash) {
        this.totalMountShiftCash = totalMountShiftCash;
    }

    public double getWsTotalMountElectronic() {
        return totalMountShiftElectronic;
    }

    public void setWsTotalMountElectronic(double totalMountShiftElectronic) {
        this.totalMountShiftElectronic = totalMountShiftElectronic;
    }

    public double getWsTotalMount() {
        return totalMountShift;
    }

    public void setWsTotalMount(double totalMountShift) {
        this.totalMountShift = totalMountShift;
    }

    public double getWsTotalMountReal() {
        return totalMountShiftReal;
    }

    public void setWsTotalMountReal(double totalMountShiftReal) {
        this.totalMountShiftReal = totalMountShiftReal;
    }

    public double getWsErrorMount() {
        return errorMountShift;
    }

    public void setWsErrorMount(double errorMountShift) {
        this.errorMountShift = errorMountShift;
    }

    public double getWsErrorMountReal() {
        return errorMountShiftReal;
    }

    public void setWsErrorMountReal(double errorMountShiftReal) {
        this.errorMountShiftReal = errorMountShiftReal;
    }
}
