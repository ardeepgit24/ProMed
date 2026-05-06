package com.promed.service;

import com.promed.model.Medicine;
import com.promed.model.Order;
import com.promed.model.OrderItemRequest;
import com.promed.model.OrderRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final CatalogService catalogService;
    private final EmailService emailService;
    private final AtomicLong orderIdSequence = new AtomicLong(1000);
    private final List<Order> orders = new CopyOnWriteArrayList<>();

    public OrderService(CatalogService catalogService, EmailService emailService) {
        this.catalogService = catalogService;
        this.emailService = emailService;
    }

    public Order placeOrder(OrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<String> lines = new ArrayList<>();

        for (OrderItemRequest item : request.items()) {
            Medicine medicine = catalogService.findMedicine(item.medicineId())
                    .orElseThrow(() -> new IllegalArgumentException("Medicine id " + item.medicineId() + " not found"));

            if (medicine.getStock() < item.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for " + medicine.getName());
            }

            medicine.setStock(medicine.getStock() - item.quantity());
            BigDecimal lineAmount = medicine.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            total = total.add(lineAmount);
            lines.add(medicine.getName() + " x " + item.quantity() + " = " + lineAmount);
        }

        Order order = new Order(
                orderIdSequence.incrementAndGet(),
                request.customerName(),
                request.email(),
                request.phone(),
                request.address(),
                total,
                "PLACED",
                LocalDateTime.now()
        );
        orders.add(order);

        String summary = "Order ID: " + order.getId() + "\nCustomer: " + request.customerName() +
                "\nItems:\n" + String.join("\n", lines) +
                "\nTotal: " + total + "\nDelivery Address: " + request.address();
        emailService.sendOrderPlacedMail(summary);

        return order;
    }

    public List<Order> allOrders() {
        return orders;
    }

    public long countOrdersToday() {
        LocalDate today = LocalDate.now();
        return orders.stream().filter(o -> o.getCreatedAt().toLocalDate().isEqual(today)).count();
    }

    public BigDecimal dailySale() {
        LocalDate today = LocalDate.now();
        return orders.stream()
                .filter(o -> o.getCreatedAt().toLocalDate().isEqual(today))
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Order> ordersToday() {
        LocalDate today = LocalDate.now();
        return orders.stream().filter(o -> o.getCreatedAt().toLocalDate().isEqual(today)).toList();
    }
}

