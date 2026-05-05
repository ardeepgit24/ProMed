package com.promed.model;

import java.math.BigDecimal;

public class LabTest {
    private Long id;
    private String name;
    private String description;
    private BigDecimal rate;
    private boolean homeCollectionAvailable;

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

