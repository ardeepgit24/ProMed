package com.promed.service;

import com.promed.model.Appointment;
import com.promed.model.AppointmentRequest;
import com.promed.model.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AppointmentService {

    private final CatalogService catalogService;
    private final AtomicLong appointmentIdSequence = new AtomicLong(5000);
    private final List<Appointment> appointments = new CopyOnWriteArrayList<>();

    public AppointmentService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public Appointment book(AppointmentRequest request) {
        Doctor doctor = catalogService.getDoctorOrThrow(request.doctorId());
        LocalDateTime slot = request.slot();

        if (!doctor.getAvailableSlots().contains(slot)) {
            throw new IllegalArgumentException("Requested slot is not available");
        }
        doctor.getAvailableSlots().remove(slot);

        Appointment appointment = new Appointment(
                appointmentIdSequence.incrementAndGet(),
                request.customerName(),
                request.email(),
                request.doctorId(),
                slot,
                "BOOKED"
        );
        appointments.add(appointment);
        return appointment;
    }

    public long countAppointmentsToday() {
        LocalDate today = LocalDate.now();
        return appointments.stream().filter(a -> a.getSlot().toLocalDate().isEqual(today)).count();
    }

    public List<Appointment> allAppointments() {
        return appointments;
    }
}

