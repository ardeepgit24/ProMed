package com.promed.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders", schema = "public")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(Long id, String customerName, String email, String phone, String address,
                 BigDecimal totalAmount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setId(Long id) { this.id = id; }
    public void setCustomerName(String c) { this.customerName = c; }
    public void setEmail(String e) { this.email = e; }
    public void setPhone(String p) { this.phone = p; }
    public void setAddress(String a) { this.address = a; }
    public void setTotalAmount(BigDecimal t) { this.totalAmount = t; }
    public void setStatus(String s) { this.status = s; }
    public void setCreatedAt(LocalDateTime c) { this.createdAt = c; }
}
