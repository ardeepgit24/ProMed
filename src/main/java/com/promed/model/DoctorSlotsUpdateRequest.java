package com.promed.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DoctorSlotsUpdateRequest(
        @NotEmpty List<@NotNull LocalDateTime> slots
) {
}

