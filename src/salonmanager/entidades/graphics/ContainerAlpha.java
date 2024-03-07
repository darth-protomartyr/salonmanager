/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.graphics;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;

/**
 *
 * @author Gonzalo
 */
public class ContainerAlpha {

    ArrayList<Itemcard> itemsTableAux;
    ArrayList<Itemcard> itemsGift;
    ArrayList<Itemcard> itemsPartialPaid;
    ArrayList<Itemcard> itemsPartialPaidNoDiscount;
    User waiterAux;
    Table tableAux;
    double total;
    double error;
    double discount;
    double priceCorrection;
    JCheckBox checkBoxIndic;
    JSpinner spinnerUnitsItem;
    JComboBox comboItems;
    JLabel labelTotalParcial;
    JLabel labelCuenta;
    JLabel labelTip;
    JLabel labelTotal;
    JLabel labelPartialPay;
    JLabel labelOrder;
    JLabel labelWaiter;
    JButtonMetalBlu butCloseTable;
    JButtonTable jbtAux;

    public ContainerAlpha(ArrayList<Itemcard> itemsTableAux, ArrayList<Itemcard> itemsGift, ArrayList<Itemcard> itemsPartialPaid, ArrayList<Itemcard> itemsPartialPaidNoDiscount, User waiterAux, Table tableAux, double total, double error, double discount, double priceCorrection, JCheckBox checkBoxIndic, JSpinner spinnerUnitsItem, JComboBox comboItems, JLabel labelTotalParcial, JLabel labelCuenta, JLabel labelTip, JLabel labelTotal, JLabel labelPartialPay, JLabel labelOrder, JLabel labelWaiter, JButtonMetalBlu butCloseTable, JButtonTable jbtAux) {
        this.itemsTableAux = itemsTableAux;
        this.itemsGift = itemsGift;
        this.itemsPartialPaid = itemsPartialPaid;
        this.itemsPartialPaidNoDiscount = itemsPartialPaidNoDiscount;
        this.waiterAux = waiterAux;
        this.tableAux = tableAux;
        this.total = total;
        this.error = error;
        this.discount = discount;
        this.priceCorrection = priceCorrection;
        this.checkBoxIndic = checkBoxIndic;
        this.spinnerUnitsItem = spinnerUnitsItem;
        this.comboItems = comboItems;
        this.labelTotalParcial = labelTotalParcial;
        this.labelCuenta = labelCuenta;
        this.labelTip = labelTip;
        this.labelTotal = labelTotal;
        this.labelPartialPay = labelPartialPay;
        this.labelOrder = labelOrder;
        this.labelWaiter = labelWaiter;
        this.butCloseTable = butCloseTable;
        this.jbtAux = jbtAux;
    }

    public ArrayList<Itemcard> getItemsTableAux() {
        return itemsTableAux;
    }

    public ArrayList<Itemcard> getItemsGift() {
        return itemsGift;
    }

    public ArrayList<Itemcard> getItemsPartialPaid() {
        return itemsPartialPaid;
    }

    public ArrayList<Itemcard> getItemsPartialPaidNoDiscount() {
        return itemsPartialPaidNoDiscount;
    }

    public User getWaiterAux() {
        return waiterAux;
    }

    public Table getTableAux() {
        return tableAux;
    }

    public double getTotal() {
        return total;
    }

    public double getError() {
        return error;
    }

    public double getDiscount() {
        return discount;
    }

    public double getPriceCorrection() {
        return priceCorrection;
    }

    public JCheckBox getCheckBoxIndic() {
        return checkBoxIndic;
    }

    public JSpinner getSpinnerUnitsItem() {
        return spinnerUnitsItem;
    }

    public JComboBox getComboItems() {
        return comboItems;
    }

    public JLabel getLabelTotalParcial() {
        return labelTotalParcial;
    }

    public JLabel getLabelCuenta() {
        return labelCuenta;
    }

    public JLabel getLabelTip() {
        return labelTip;
    }

    public JLabel getLabelTotal() {
        return labelTotal;
    }

    public JLabel getLabelPartialPay() {
        return labelPartialPay;
    }

    public JLabel getLabelOrder() {
        return labelOrder;
    }

    public JLabel getLabelWaiter() {
        return labelWaiter;
    }

    public JButtonMetalBlu getButCloseTable() {
        return butCloseTable;
    }

    public JButtonTable getJbtAux() {
        return jbtAux;
    }
    
    


}
