package com.promed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors", schema = "public")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;

    @ElementCollection
    @CollectionTable(name = "doctor_available_slots", schema = "public",
            joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "slot")
    private List<LocalDateTime> availableSlots = new ArrayList<>();

    public Doctor() {}

    public Doctor(Long id, String name, String specialization, List<LocalDateTime> availableSlots) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableSlots = new ArrayList<>(availableSlots);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<LocalDateTime> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<LocalDateTime> availableSlots) {
        this.availableSlots = new ArrayList<>(availableSlots);
    }
}
