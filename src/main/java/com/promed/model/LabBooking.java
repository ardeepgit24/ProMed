package com.promed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_bookings", schema = "public")
public class LabBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;
    private Long labTestId;
    private boolean homeCollection;
    private String address;
    private LocalDateTime slot;
    private String status;

    public LabBooking() {}

    public LabBooking(Long id, String customerName, String email, Long labTestId,
                      boolean homeCollection, String address, LocalDateTime slot, String status) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.labTestId = labTestId;
        this.homeCollection = homeCollection;
        this.address = address;
        this.slot = slot;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public Long getLabTestId() { return labTestId; }
    public boolean isHomeCollection() { return homeCollection; }
    public String getAddress() { return address; }
    public LocalDateTime getSlot() { return slot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setId(Long id) { this.id = id; }
    public void setCustomerName(String c) { this.customerName = c; }
    public void setEmail(String e) { this.email = e; }
    public void setLabTestId(Long l) { this.labTestId = l; }
    public void setHomeCollection(boolean h) { this.homeCollection = h; }
    public void setAddress(String a) { this.address = a; }
    public void setSlot(LocalDateTime s) { this.slot = s; }
}
