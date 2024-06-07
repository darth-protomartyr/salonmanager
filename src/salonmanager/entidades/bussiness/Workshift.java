/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.bussiness;

import salonmanager.entidades.bussiness.User;
import java.sql.Timestamp;
import java.util.Date;

public class Workshift {
    int id;
    User cashierWs;
    Timestamp openWs;
    Timestamp closeWs;
    boolean stateWs;
    double totalMountCashWs;
    double totalMountElectronicWs;
    double totalMountWs;
    double totalMountRealWs; //billing plus cashFlow
    double errorMountWs;
    double errorMountRealWs;
    double cashFlowWsCash;
    double cashFlowWsElec;
    String commentWs;
    boolean activeWs;

    public Workshift() {
    }

    //Crear Turno
    public Workshift(User cashier) {
        this.cashierWs = cashier;
        this.openWs = new Timestamp(new Date().getTime());
        this.closeWs = null;
        this.stateWs = true;
        this.totalMountCashWs = 0;
        this.totalMountElectronicWs = 0;
        this.errorMountWs = 0;
        this.errorMountRealWs = 0;
        this.totalMountWs = 0;
        this.totalMountRealWs = 0;
        this.cashFlowWsCash = 0;
        this.cashFlowWsElec = 0;
        this.commentWs = "";
        this.activeWs = true;
    }

    //Consulta Turno
    public Workshift(int id, User cashierWs, Timestamp openWs, Timestamp closeWs, boolean stateWs, double totalMountCashWs, double totalMountElectronicWs, double errorWs, double errorRealWs, double totalWs, double totalRealWs, double cashFlowWsCash, double cashFlowWsElec, String commentWs, boolean activeWs) {
        this.id = id;
        this.cashierWs = cashierWs;
        this.openWs = openWs;
        this.closeWs = closeWs;
        this.stateWs = stateWs;
        this.totalMountCashWs = totalMountCashWs;
        this.totalMountElectronicWs = totalMountElectronicWs;
        this.errorMountWs = errorWs;
        this.errorMountRealWs = errorRealWs;
        this.totalMountWs = totalWs;
        this.totalMountRealWs = totalRealWs;
        this.cashFlowWsCash = cashFlowWsCash;
        this.cashFlowWsElec = cashFlowWsElec;
        this.commentWs = commentWs;
        this.activeWs = activeWs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCashierWs() {
        return cashierWs;
    }

    public void setCashierWs(User cashierWs) {
        this.cashierWs = cashierWs;
    }

    public Timestamp getOpenWs() {
        return openWs;
    }

    public void setOpenWs(Timestamp openWs) {
        this.openWs = openWs;
    }

    public Timestamp getCloseWs() {
        return closeWs;
    }

    public void setCloseWs(Timestamp closeWs) {
        this.closeWs = closeWs;
    }

    public boolean isStateWs() {
        return stateWs;
    }

    public void setStateWs(boolean stateWs) {
        this.stateWs = stateWs;
    }

    public double getTotalMountCashWs() {
        return totalMountCashWs;
    }

    public void setTotalMountCashWs(double totalMountCashWs) {
        this.totalMountCashWs = totalMountCashWs;
    }

    public double getTotalMountElectronicWs() {
        return totalMountElectronicWs;
    }

    public void setTotalMountElectronicWs(double totalMountElectronicWs) {
        this.totalMountElectronicWs = totalMountElectronicWs;
    }

    public double getTotalMountWs() {
        return totalMountWs;
    }

    public void setTotalMountWs(double totalMountWs) {
        this.totalMountWs = totalMountWs;
    }

    public double getTotalMountRealWs() {
        return totalMountRealWs;
    }

    public void setTotalMountRealWs(double totalMountRealWs) {
        this.totalMountRealWs = totalMountRealWs;
    }

    public double getErrorMountWs() {
        return errorMountWs;
    }

    public void setErrorMountWs(double errorMountWs) {
        this.errorMountWs = errorMountWs;
    }

    public double getErrorMountRealWs() {
        return errorMountRealWs;
    }

    public void setErrorMountRealWs(double errorMountRealWs) {
        this.errorMountRealWs = errorMountRealWs;
    }

    public double getCashFlowWsCash() {
        return cashFlowWsCash;
    }

    public void setCashFlowWsCash(double cashFlowWsCash) {
        this.cashFlowWsCash = cashFlowWsCash;
    }
    
    public double getCashFlowWsElec() {
        return cashFlowWsElec;
    }

    public void setCashFlowWsElec(double cashFlowWsElec) {
        this.cashFlowWsElec = cashFlowWsElec;
    }

    public String getCommentWs() {
        return commentWs;
    }

    public void setCommentWs(String commentWs) {
        this.commentWs = commentWs;
    }

    public boolean isActiveWs() {
        return activeWs;
    }

    public void setActiveWs(boolean activeWs) {
        this.activeWs = activeWs;
    }
}
