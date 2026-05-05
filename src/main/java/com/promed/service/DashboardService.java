package com.promed.service;

import com.promed.model.DashboardMetrics;
import com.promed.model.Medicine;
import com.promed.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final CatalogService catalogService;
    private final OrderService orderService;
    private final AppointmentService appointmentService;
    private final LabService labService;

    public DashboardService(CatalogService catalogService,
                            OrderService orderService,
                            AppointmentService appointmentService,
                            LabService labService) {
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.appointmentService = appointmentService;
        this.labService = labService;
    }

    public DashboardMetrics getMetrics() {
        return new DashboardMetrics(
                orderService.dailySale(),
                catalogService.lowStockItems().size(),
                orderService.countOrdersToday(),
                appointmentService.countAppointmentsToday(),
                labService.countLabBookingsToday()
        );
    }

    public List<Medicine> lowStockItems() {
        return catalogService.lowStockItems();
    }

    public List<Order> ordersToday() {
        return orderService.ordersToday();
    }
}

