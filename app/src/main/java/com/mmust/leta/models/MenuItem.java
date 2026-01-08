package com.mmust.leta.models;

/**
 * Menu Item Model (for vendors)
 */
public class MenuItem {
    private String id;
    private String vendorId;
    private String name;
    private String description;
    private double price;
    private String category; // snacks, drinks, meals
    private String imageUrl;
    private boolean available;
    private int preparationTime; // in minutes
    private long createdAt;
    
    public MenuItem() {
        // Required empty constructor for Firestore
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }
    
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
