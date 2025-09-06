package org.example.ecommercebackend.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
    private String customerEmail;
    private String customerName;
    private BigDecimal totalAmount;
    private List<OrderItemRequest> items;

    // Constructors
    public OrderRequest() {}

    // Getters and Setters
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
