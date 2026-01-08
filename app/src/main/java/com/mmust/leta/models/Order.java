package com.mmust.leta.models;

import java.util.List;

/**
 * Order Model
 */
public class Order {
    private String id;
    private String userId;
    private String vendorId;
    private String riderId;
    private List<CartItem> items;
    private double subtotal;
    private double deliveryFee;
    private double discount;
    private double total;
    private String status; // pending, preparing, ready, picking_up, delivering, completed, cancelled
    private String deliveryAddress;
    private String buildingDetails;
    private String paymentMethod;
    private String paymentStatus;
    private long createdAt;
    private long updatedAt;
    
    public Order() {
        // Required empty constructor for Firestore
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
    
    public String getRiderId() { return riderId; }
    public void setRiderId(String riderId) { this.riderId = riderId; }
    
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }
    
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    
    public String getBuildingDetails() { return buildingDetails; }
    public void setBuildingDetails(String buildingDetails) { this.buildingDetails = buildingDetails; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
