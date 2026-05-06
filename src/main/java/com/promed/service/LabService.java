package com.promed.service;

import com.promed.model.LabBooking;
import com.promed.model.LabBookingRequest;
import com.promed.model.LabTest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LabService {

    private final CatalogService catalogService;
    private final AtomicLong labBookingIdSequence = new AtomicLong(8000);
    private final List<LabBooking> labBookings = new CopyOnWriteArrayList<>();

    public LabService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public LabBooking book(LabBookingRequest request) {
        LabTest test = catalogService.getLabTestOrThrow(request.labTestId());
        if (request.homeCollection() && !test.isHomeCollectionAvailable()) {
            throw new IllegalArgumentException("Home collection is not available for selected test");
        }

        LabBooking booking = new LabBooking(
                labBookingIdSequence.incrementAndGet(),
                request.customerName(),
                request.email(),
                request.labTestId(),
                request.homeCollection(),
                request.address(),
                request.slot(),
                "BOOKED"
        );
        labBookings.add(booking);
        return booking;
    }

    public long countLabBookingsToday() {
        LocalDate today = LocalDate.now();
        return labBookings.stream().filter(b -> b.getSlot().toLocalDate().isEqual(today)).count();
    }

    public List<LabBooking> allLabBookings() {
        return labBookings;
    }
}

