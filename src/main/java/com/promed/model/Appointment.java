package com.promed.model;

import java.time.LocalDateTime;

public record Appointment(
        Long id,
        String customerName,
        String email,
        Long doctorId,
        LocalDateTime slot,
        String status
) {
}

