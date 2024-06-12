package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.utilidades.Utilidades;

public class CashFlow {
    Utilidades utili = new Utilidades();
    int id;
    boolean cashFwKind;
    boolean cashFwMoneyKind;
    double cashFwAmount;
    String cashFwComment;
    Timestamp cashFwTime;
    int cashFwWsId;
    boolean cashFwActive;

    public CashFlow() {
    }

    public CashFlow(boolean cashFwKind, boolean cashFwMoneyKind, double cashFwAmount, String cashFwComment, int cashFwWsId) {
        this.cashFwKind = cashFwKind;
        this.cashFwMoneyKind = cashFwMoneyKind;
        this.cashFwAmount = utili.round2Dec(cashFwAmount);
        this.cashFwComment = cashFwComment;
        this.cashFwTime = new Timestamp(new Date().getTime());
        this.cashFwWsId = cashFwWsId;
        this.cashFwActive = true;
    }

    public CashFlow(int id, boolean cashFwKind, boolean cashFwMoneyKind, double cashFwAmount, String cashFwComment, Timestamp cashFwTime, int cashFwWsId, boolean cashFwActive) {
        this.id = id;
        this.cashFwKind = cashFwKind;
        this.cashFwMoneyKind = cashFwMoneyKind;
        this.cashFwAmount = utili.round2Dec(cashFwAmount);
        this.cashFwComment = cashFwComment;
        this.cashFwTime = cashFwTime;
        this.cashFwWsId = cashFwWsId;
        this.cashFwActive = cashFwActive;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCashFwKind() {
        return cashFwKind;
    }

    public void setCashFwKind(boolean cashFwKind) {
        this.cashFwKind = cashFwKind;
    }

    public boolean isCashFwMoneyKind() {
        return cashFwMoneyKind;
    }

    public void setCashFwMoneyKind(boolean cashFwMoneyKind) {
        this.cashFwMoneyKind = cashFwMoneyKind;
    }

    public double getCashFwAmount() {
        return cashFwAmount;
    }

    public void setCashFwAmount(double cashFwAmount) {
        this.cashFwAmount = utili.round2Dec(cashFwAmount);
    }

    public String getCashFwComment() {
        return cashFwComment;
    }

    public void setCashFwComment(String cashFwComment) {
        this.cashFwComment = cashFwComment;
    }

    public Timestamp getCashFwTime() {
        return cashFwTime;
    }

    public void setCashFwTime(Timestamp cashFwTime) {
        this.cashFwTime = cashFwTime;
    }

    public int getCashFwWsId() {
        return cashFwWsId;
    }

    public void setCashFwWsId(int cashFwWsId) {
        this.cashFwWsId = cashFwWsId;
    }

    public boolean isCashFwActive() {
        return cashFwActive;
    }

    public void setCashFwActive(boolean cashFwActive) {
        this.cashFwActive = cashFwActive;
    }
}