package com.navjot.decemberapplication_seller.model;

public class FinalOrder {


    public String finalOrderID;
    public String finalOrderPrice;
    public String finalOrderPaymentMethod;
    public String finalOrderDeliveryName;
    public String finalOrderDeliveryPhone;
    public String finalOrderDeliveryAddr;
    public String finalOrderDeliveryTown;
    public String finalOrderDeliveryCity;
    public String finalOrderDate;
    public String finalOrderTime;
    public String finalOrderedItems;

    public FinalOrder(){}

    public FinalOrder(String finalOrderID, String finalOrderPrice, String finalOrderPaymentMethod,
                      String finalOrderDeliveryName, String finalOrderDeliveryPhone, String finalOrderDeliveryAddr,
                      String finalOrderDeliveryTown, String finalOrderDeliveryCity, String finalOrderDate,
                      String finalOrderTime, String finalOrderedItems) {
        this.finalOrderID = finalOrderID;
        this.finalOrderPrice = finalOrderPrice;
        this.finalOrderPaymentMethod = finalOrderPaymentMethod;
        this.finalOrderDeliveryName = finalOrderDeliveryName;
        this.finalOrderDeliveryPhone = finalOrderDeliveryPhone;
        this.finalOrderDeliveryAddr = finalOrderDeliveryAddr;
        this.finalOrderDeliveryTown = finalOrderDeliveryTown;
        this.finalOrderDeliveryCity = finalOrderDeliveryCity;
        this.finalOrderDate = finalOrderDate;
        this.finalOrderTime = finalOrderTime;
        this.finalOrderedItems = finalOrderedItems;

    }




    public String getFinalOrderID() {
        return finalOrderID;
    }

    public void setFinalOrderID(String finalOrderID) {
        this.finalOrderID = finalOrderID;
    }

    public String getFinalOrderPrice() {
        return finalOrderPrice;
    }

    public void setFinalOrderPrice(String finalOrderPrice) {
        this.finalOrderPrice = finalOrderPrice;
    }

    public String getFinalOrderPaymentMethod() {
        return finalOrderPaymentMethod;
    }

    public void setFinalOrderPaymentMethod(String finalOrderPaymentMethod) {
        this.finalOrderPaymentMethod = finalOrderPaymentMethod;
    }

    public String getFinalOrderDeliveryName() {
        return finalOrderDeliveryName;
    }

    public void setFinalOrderDeliveryName(String finalOrderDeliveryName) {
        this.finalOrderDeliveryName = finalOrderDeliveryName;
    }

    public String getFinalOrderDeliveryPhone() {
        return finalOrderDeliveryPhone;
    }

    public void setFinalOrderDeliveryPhone(String finalOrderDeliveryPhone) {
        this.finalOrderDeliveryPhone = finalOrderDeliveryPhone;
    }

    public String getFinalOrderDeliveryAddr() {
        return finalOrderDeliveryAddr;
    }

    public void setFinalOrderDeliveryAddr(String finalOrderDeliveryAddr) {
        this.finalOrderDeliveryAddr = finalOrderDeliveryAddr;
    }

    public String getFinalOrderDeliveryTown() {
        return finalOrderDeliveryTown;
    }

    public void setFinalOrderDeliveryTown(String finalOrderDeliveryTown) {
        this.finalOrderDeliveryTown = finalOrderDeliveryTown;
    }

    public String getFinalOrderDeliveryCity() {
        return finalOrderDeliveryCity;
    }

    public void setFinalOrderDeliveryCity(String finalOrderDeliveryCity) {
        this.finalOrderDeliveryCity = finalOrderDeliveryCity;
    }

    public String getFinalOrderDate() {
        return finalOrderDate;
    }

    public void setFinalOrderDate(String finalOrderDate) {
        this.finalOrderDate = finalOrderDate;
    }

    public String getFinalOrderTime() {
        return finalOrderTime;
    }

    public void setFinalOrderTime(String finalOrderTime) {
        this.finalOrderTime = finalOrderTime;
    }

    public String getFinalOrderedItems() {
        return finalOrderedItems;
    }

    public void setFinalOrderedItems(String finalOrderedItems) {
        this.finalOrderedItems = finalOrderedItems;
    }


}
