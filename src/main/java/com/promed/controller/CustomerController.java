package com.promed.controller;

import com.promed.model.Appointment;
import com.promed.model.AppointmentRequest;
import com.promed.model.LabBooking;
import com.promed.model.LabBookingRequest;
import com.promed.model.LabTest;
import com.promed.model.Medicine;
import com.promed.model.Order;
import com.promed.model.OrderRequest;
import com.promed.service.AppointmentService;
import com.promed.service.CatalogService;
import com.promed.service.LabService;
import com.promed.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CatalogService catalogService;
    private final OrderService orderService;
    private final AppointmentService appointmentService;
    private final LabService labService;

    public CustomerController(CatalogService catalogService,
                              OrderService orderService,
                              AppointmentService appointmentService,
                              LabService labService) {
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.appointmentService = appointmentService;
        this.labService = labService;
    }

    @GetMapping("/medicines")
    public List<Medicine> searchMedicines(@RequestParam(required = false) String query) {
        return catalogService.searchMedicines(query);
    }

    @PostMapping("/orders")
    public Order placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping("/doctors")
    public List<com.promed.model.Doctor> doctors() {
        return catalogService.getDoctors();
    }

    @PostMapping("/appointments")
    public Appointment bookAppointment(@Valid @RequestBody AppointmentRequest request) {
        return appointmentService.book(request);
    }

    @GetMapping("/lab-tests")
    public List<LabTest> labTests() {
        return catalogService.getLabTests();
    }

    @PostMapping("/lab-bookings")
    public LabBooking bookLab(@Valid @RequestBody LabBookingRequest request) {
        return labService.book(request);
    }
}

