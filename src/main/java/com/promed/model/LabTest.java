package com.promed.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lab_tests", schema = "public")
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal rate;
    private boolean homeCollectionAvailable;

    public LabTest() {}

    public LabTest(Long id, String name, String description, BigDecimal rate, boolean homeCollectionAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.homeCollectionAvailable = homeCollectionAvailable;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public boolean isHomeCollectionAvailable() {
        return homeCollectionAvailable;
    }
}
