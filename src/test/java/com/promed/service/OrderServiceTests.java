package com.promed.service;

import com.promed.model.OrderItemRequest;
import com.promed.model.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CatalogService catalogService;

    @Test
    void placeOrderReducesStockAndReturnsOrder() {
        int before = catalogService.findMedicine(1L).orElseThrow().getStock();

        OrderRequest request = new OrderRequest(
                "Test Customer",
                "test@example.com",
                "9999999999",
                "Sample Address",
                List.of(new OrderItemRequest(1L, 2))
        );

        var order = orderService.placeOrder(request);

        assertNotNull(order);
        assertEquals(before - 2, catalogService.findMedicine(1L).orElseThrow().getStock());
    }
}

