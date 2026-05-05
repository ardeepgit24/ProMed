package com.promed.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LabBookingRequest(
        @NotBlank String customerName,
        @Email String email,
        @NotNull Long labTestId,
        boolean homeCollection,
        @NotBlank String address,
        @NotNull LocalDateTime slot
) {
}

