package com.mmust.leta.models;

/**
 * User Model
 */
public class User {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String role; // student, vendor, rider
    private String photoUrl;
    private double rating;
    private int totalOrders;
    private long createdAt;
    
    // Rider specific
    private String riderStatus; // pending_approval, active, inactive
    private double totalEarnings;
    
    // Vendor specific
    private String vendorName;
    private String vendorDescription;
    private double totalRevenue;
    
    public User() {
        // Required empty constructor for Firestore
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
    
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    
    // Rider specific
    public String getRiderStatus() { return riderStatus; }
    public void setRiderStatus(String riderStatus) { this.riderStatus = riderStatus; }
    
    public double getTotalEarnings() { return totalEarnings; }
    public void setTotalEarnings(double totalEarnings) { this.totalEarnings = totalEarnings; }
    
    // Vendor specific
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    
    public String getVendorDescription() { return vendorDescription; }
    public void setVendorDescription(String vendorDescription) { this.vendorDescription = vendorDescription; }
    
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}
