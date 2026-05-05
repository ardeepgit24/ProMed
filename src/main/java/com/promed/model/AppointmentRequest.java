package com.promed.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotBlank String customerName,
        @Email String email,
        @NotNull Long doctorId,
        @NotNull LocalDateTime slot
) {
}

