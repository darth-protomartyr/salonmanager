package salonmanager.entidades.bussiness;

import java.sql.Timestamp;
import java.util.Date;
import salonmanager.utilidades.Utilidades;

public class MoneyFlow {
    Utilidades utili = new Utilidades();
    int id;
    boolean moneyFwKind; // 1(ingreso), 2(extracción)
    boolean moneyFwMoneyKind; // 1(efectivo), 2(electronico)
    double moneyFwAmount;
    String moneyFwComment;
    Timestamp moneyFwTime;
    int moneyFwWsId;
    boolean moneyFwActive;

    public MoneyFlow() {
    }

    public MoneyFlow(boolean moneyFwKind, boolean moneyFwMoneyKind, double moneyFwAmount, String moneyFwComment, int moneyFwWsId) {
        this.moneyFwKind = moneyFwKind;
        this.moneyFwMoneyKind = moneyFwMoneyKind;
        this.moneyFwAmount = utili.round2Dec(moneyFwAmount);
        this.moneyFwComment = moneyFwComment;
        this.moneyFwTime = new Timestamp(new Date().getTime());
        this.moneyFwWsId = moneyFwWsId;
        this.moneyFwActive = true;
    }
    
    public MoneyFlow(boolean moneyFwKind, boolean moneyFwMoneyKind, double moneyFwAmount, String moneyFwComment, Timestamp ts, int moneyFwWsId) {
        this.moneyFwKind = moneyFwKind;
        this.moneyFwMoneyKind = moneyFwMoneyKind;
        this.moneyFwAmount = utili.round2Dec(moneyFwAmount);
        this.moneyFwComment = moneyFwComment;
        this.moneyFwTime = ts;
        this.moneyFwWsId = moneyFwWsId;
        this.moneyFwActive = true;
    }
    

    public MoneyFlow(int id, boolean moneyFwKind, boolean moneyFwMoneyKind, double moneyFwAmount, String moneyFwComment, Timestamp moneyFwTime, int moneyFwWsId, boolean moneyFwActive) {
        this.id = id;
        this.moneyFwKind = moneyFwKind;
        this.moneyFwMoneyKind = moneyFwMoneyKind;
        this.moneyFwAmount = utili.round2Dec(moneyFwAmount);
        this.moneyFwComment = moneyFwComment;
        this.moneyFwTime = moneyFwTime;
        this.moneyFwWsId = moneyFwWsId;
        this.moneyFwActive = moneyFwActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMoneyFwKind() {
        return moneyFwKind;
    }

    public void setMoneyFwKind(boolean moneyFwKind) {
        this.moneyFwKind = moneyFwKind;
    }

    public boolean isMoneyFwMoneyKind() {
        return moneyFwMoneyKind;
    }

    public void setMoneyFwMoneyKind(boolean moneyFwMoneyKind) {
        this.moneyFwMoneyKind = moneyFwMoneyKind;
    }

    public double getMoneyFwAmount() {
        return moneyFwAmount;
    }

    public void setMoneyFwAmount(double moneyFwAmount) {
        this.moneyFwAmount = utili.round2Dec(moneyFwAmount);
    }

    public String getMoneyFwComment() {
        return moneyFwComment;
    }

    public void setMoneyFwComment(String moneyFwComment) {
        this.moneyFwComment = moneyFwComment;
    }

    public Timestamp getMoneyFwTime() {
        return moneyFwTime;
    }

    public void setMoneyFwTime(Timestamp moneyFwTime) {
        this.moneyFwTime = moneyFwTime;
    }

    public int getMoneyFwWsId() {
        return moneyFwWsId;
    }

    public void setMoneyFwWsId(int moneyFwWsId) {
        this.moneyFwWsId = moneyFwWsId;
    }

    public boolean isMoneyFwActive() {
        return moneyFwActive;
    }

    public void setMoneyFwActive(boolean moneyFwActive) {
        this.moneyFwActive = moneyFwActive;
    }
}