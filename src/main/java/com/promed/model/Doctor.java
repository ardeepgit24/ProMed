package com.promed.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private Long id;
    private String name;
    private String specialization;
    private List<LocalDateTime> availableSlots;

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

