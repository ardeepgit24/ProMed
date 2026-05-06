package com.promed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", schema = "public")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;
    private Long doctorId;
    private LocalDateTime slot;
    private String status;

    public Appointment() {}

    public Appointment(Long id, String customerName, String email, Long doctorId, LocalDateTime slot, String status) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.doctorId = doctorId;
        this.slot = slot;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public Long getDoctorId() { return doctorId; }
    public LocalDateTime getSlot() { return slot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setId(Long id) { this.id = id; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setEmail(String email) { this.email = email; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setSlot(LocalDateTime slot) { this.slot = slot; }
}
