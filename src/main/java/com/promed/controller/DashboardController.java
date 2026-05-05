package com.promed.controller;

import com.promed.model.DashboardMetrics;
import com.promed.model.Medicine;
import com.promed.model.Order;
import com.promed.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    public DashboardMetrics metrics() {
        return dashboardService.getMetrics();
    }

    @GetMapping("/low-stock")
    public List<Medicine> lowStock() {
        return dashboardService.lowStockItems();
    }

    @GetMapping("/orders-today")
    public List<Order> ordersToday() {
        return dashboardService.ordersToday();
    }
}

