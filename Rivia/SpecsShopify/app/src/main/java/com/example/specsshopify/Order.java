package com.example.specsshopify;

public class Order {
    String orderId;
    String paymentStatus;
    String fulfillmentStatus;
    String refundStatus;

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Order() {
        this.orderId = "NOT AVAILABLE";
        this.paymentStatus = "NOT AVAILABLE";
        this.fulfillmentStatus = "NOT AVAILABLE";
        this.refundStatus = "NOT AVAILABLE";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus == "null" ? "NOT FULFILLED" : "FULFILLED";
    }
}
