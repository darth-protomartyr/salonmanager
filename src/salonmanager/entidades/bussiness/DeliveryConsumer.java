package salonmanager.entidades.bussiness;

import salonmanager.entidades.bussiness.Table;
import java.util.ArrayList;

public class DeliveryConsumer {
    int id;
    String street;
    String numSt;
    String deptFloor;
    String deptNum;
    String district;
    String area;
    String details;
    String name;
    String phone;
    String socialNetwork;
    ArrayList<Table> consumerTabs;
    boolean consumerActive;

    public DeliveryConsumer() {
    }

    public DeliveryConsumer(int id, String street, String numSt, String deptFloor, String deptNum, String district, String area, String details, String name, String phone, String socialNetwork) {
        this.id = id;
        this.street = street;
        this.numSt = numSt;
        this.deptFloor = deptFloor;
        this.deptNum = deptNum;
        this.district = district;
        this.area = area;
        this.details = details;
        this.name = name;
        this.phone = phone;
        this.socialNetwork = socialNetwork;
        this.consumerActive = true;
    }

    public DeliveryConsumer(int id, String street, String numSt, String deptFloor, String deptNum, String district, String area, String details, String name, String phone, String socialNetwork, ArrayList<Table> consumerTabs, boolean consumerActive) {
        this.id = id;
        this.street = street;
        this.numSt = numSt;
        this.deptFloor = deptFloor;
        this.deptNum = deptNum;
        this.district = district;
        this.area = area;
        this.details = details;
        this.name = name;
        this.phone = phone;
        this.socialNetwork = socialNetwork;
        this.consumerTabs = consumerTabs;
        this.consumerActive = consumerActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumSt() {
        return numSt;
    }

    public void setNumSt(String numSt) {
        this.numSt = numSt;
    }

    public String getDeptFloor() {
        return deptFloor;
    }

    public void setDeptFloor(String deptFloor) {
        this.deptFloor = deptFloor;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public ArrayList<Table> getConsumerTabs() {
        return consumerTabs;
    }

    public void setConsumerTabs(ArrayList<Table> consumerTabs) {
        this.consumerTabs = consumerTabs;
    }

    public boolean isConsumerActive() {
        return consumerActive;
    }

    public void setConsumerActive(boolean consumerActive) {
        this.consumerActive = consumerActive;
    }
}