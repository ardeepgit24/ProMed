package com.promed.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Order(
        Long id,
        String customerName,
        String email,
        String phone,
        String address,
        BigDecimal totalAmount,
        String status,
        LocalDateTime createdAt
) {
}


