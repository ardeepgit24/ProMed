package com.promed.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "medicines", schema = "public")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private BigDecimal price;
    private int stock;
    private int reorderLevel;
    private boolean prescriptionRequired;

    public Medicine() {}

    public Medicine(Long id, String name, String category, BigDecimal price, int stock, int reorderLevel,
                    boolean prescriptionRequired) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.reorderLevel = reorderLevel;
        this.prescriptionRequired = prescriptionRequired;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }
}
