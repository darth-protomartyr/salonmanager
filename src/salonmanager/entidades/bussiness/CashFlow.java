package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;

public class CashFlow {
    boolean cashfwKind;
    int cashFwWsId;
    double cashFwAmount;
    String cashFwComment;
    Timestamp cashFwTime;
    boolean cashFlowActive;

    public CashFlow() {
    }

    public CashFlow(boolean cashfwKind, int cashFwWsId, double cashFwAmount, String cashFwComment, boolean cashFlowActive) {
        this.cashfwKind = cashfwKind;
        this.cashFwWsId = cashFwWsId;
        this.cashFwAmount = cashFwAmount;
        this.cashFwComment = cashFwComment;
        this.cashFwTime =new Timestamp(new Date().getTime());
        this.cashFlowActive = cashFlowActive;
    }

    public boolean isCashfwKind() {
        return cashfwKind;
    }

    public void setCashfwKind(boolean cashfwKind) {
        this.cashfwKind = cashfwKind;
    }

    public int getCashFwWsId() {
        return cashFwWsId;
    }

    public void setCashFwWsId(int cashFwWsId) {
        this.cashFwWsId = cashFwWsId;
    }

    public double getCashFwAmount() {
        return cashFwAmount;
    }

    public void setCashFwAmount(double cashFwIn) {
        this.cashFwAmount = cashFwAmount;
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

    public boolean isCashFlowActive() {
        return cashFlowActive;
    }

    public void setCashFlowActive(boolean cashFlowActive) {
        this.cashFlowActive = cashFlowActive;
    }
}