/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.utilidades.Utilidades;

public class Workshift {
    Utilidades utili = new Utilidades();
    int id;
    User cashierWs;
    Timestamp openWs;
    Timestamp closeWs;
    boolean stateWs;
    double totalMountCashWs;
    double totalMountElectronicWs;
    double totalMountTabs;
    double totalMountWs; //billing plus moneyFlow
    double errorMountTabs;
    double errorMountWs;
    double moneyFlowWsCash;
    double moneyFlowWsElec;
    String commentWs;
    boolean error;
    boolean activeWs;

    public Workshift() {
    }

    //Crear Turno
    public Workshift(User cashier) {
        this.id = 0;
        this.cashierWs = cashier;
        this.openWs = new Timestamp(new Date().getTime());
        this.closeWs = null;
        this.stateWs = true;
        this.totalMountCashWs = 0;
        this.totalMountElectronicWs = 0;
        this.errorMountTabs = 0;
        this.errorMountWs = 0;
        this.totalMountTabs = 0;
        this.totalMountWs = 0;
        this.moneyFlowWsCash = 0;
        this.moneyFlowWsElec = 0;
        this.commentWs = "";
        this.error = false;
        this.activeWs = true;
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
        this.totalMountCashWs = utili.round2Dec(totalMountCashWs);
    }

    public double getTotalMountElectronicWs() {
        return totalMountElectronicWs;
    }

    public void setTotalMountElectronicWs(double totalMountElectronicWs) {
        this.totalMountElectronicWs = utili.round2Dec(totalMountElectronicWs);
    }

    public double getTotalMountTabs() {
        return totalMountTabs;
    }

    public void setTotalMountTabs(double totalMountTabs) {
        this.totalMountTabs = utili.round2Dec(totalMountTabs);
    }

    public double getTotalMountWs() {
        return totalMountWs;
    }

    public void setTotalMountWs(double totalMountWs) {
        this.totalMountWs = utili.round2Dec(totalMountWs);
    }

    public double getErrorMountTabs() {
        return utili.round2Dec(errorMountTabs);
    }

    public void setErrorMountTabs(double errorMountTabs) {
        this.errorMountTabs = errorMountTabs;
    }

    public double getErrorMountWs() {
        return errorMountWs;
    }

    public void setErrorMountWs(double errorMountWs) {
        this.errorMountWs = utili.round2Dec(errorMountWs);
    }

    public double getMoneyFlowWsCash() {
        return moneyFlowWsCash;
    }

    public void setMoneyFlowWsCash(double moneyFlowWsCash) {
        this.moneyFlowWsCash = utili.round2Dec(moneyFlowWsCash);
    }
    
    public double getMoneyFlowWsElec() {
        return moneyFlowWsElec;
    }

    public void setMoneyFlowWsElec(double moneyFlowWsElec) {
        this.moneyFlowWsElec = utili.round2Dec(moneyFlowWsElec);
    }

    public String getCommentWs() {
        return commentWs;
    }

    public void setCommentWs(String commentWs) {
        this.commentWs = commentWs;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }    

    public boolean isActiveWs() {
        return activeWs;
    }

    public void setActiveWs(boolean activeWs) {
        this.activeWs = activeWs;
    }
}
