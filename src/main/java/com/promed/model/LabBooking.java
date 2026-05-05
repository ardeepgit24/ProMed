package com.promed.model;

import java.time.LocalDateTime;

public record LabBooking(
        Long id,
        String customerName,
        String email,
        Long labTestId,
        boolean homeCollection,
        String address,
        LocalDateTime slot,
        String status
) {
}

