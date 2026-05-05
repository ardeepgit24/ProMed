package com.promed.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProMedFeatureFlowIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void endToEndFeatureFlowWorksAcrossCustomerAdminAndDashboard() throws Exception {
        JsonNode medicines = getJson("/api/customer/medicines");
        assertTrue(medicines.isArray());
        assertTrue(medicines.size() >= 4);

        JsonNode stockUpdate = putJson("/api/admin/inventory/1/stock?stock=130", null);
        assertEquals(130, stockUpdate.get("stock").asInt());

        String todaysSlot = LocalDateTime.now().plusMinutes(20).withSecond(0).withNano(0).toString();
        JsonNode doctorUpdate = putJson("/api/admin/doctors/1/slots", Map.of("slots", List.of(todaysSlot)));
        assertTrue(doctorUpdate.get("availableSlots").isArray());
        assertEquals(1, doctorUpdate.get("availableSlots").size());

        JsonNode appointment = postJson("/api/customer/appointments", Map.of(
                "customerName", "Ravi Kumar",
                "email", "ravi@example.com",
                "doctorId", 1,
                "slot", todaysSlot
        ));
        assertEquals("BOOKED", appointment.get("status").asText());

        JsonNode labRate = putJson("/api/admin/lab-tests/1/rate?rate=500", null);
        assertEquals(0, new BigDecimal("500").compareTo(labRate.get("rate").decimalValue()));

        JsonNode labBooking = postJson("/api/customer/lab-bookings", Map.of(
                "customerName", "Ravi Kumar",
                "email", "ravi@example.com",
                "labTestId", 1,
                "homeCollection", true,
                "address", "Bangalore",
                "slot", todaysSlot
        ));
        assertEquals("BOOKED", labBooking.get("status").asText());

        JsonNode order = postJson("/api/customer/orders", Map.of(
                "customerName", "Ravi Kumar",
                "email", "ravi@example.com",
                "phone", "9999999999",
                "address", "Bangalore",
                "items", List.of(Map.of("medicineId", 2, "quantity", 3))
        ));
        assertEquals("PLACED", order.get("status").asText());
        assertEquals(0, new BigDecimal("480.00").compareTo(order.get("totalAmount").decimalValue()));

        JsonNode inventory = getJson("/api/admin/inventory");
        assertEquals(39, findById(inventory, 2).get("stock").asInt());

        JsonNode metrics = getJson("/api/dashboard/metrics");
        assertEquals(0, new BigDecimal("480.00").compareTo(metrics.get("dailySale").decimalValue()));
        assertEquals(2, metrics.get("lowStockNeedReorder").asInt());
        assertEquals(1, metrics.get("ordersPlacedToday").asInt());
        assertEquals(1, metrics.get("appointmentsToday").asInt());
        assertEquals(1, metrics.get("labBookingsToday").asInt());

        JsonNode todaysOrders = getJson("/api/dashboard/orders-today");
        assertEquals(1, todaysOrders.size());
        assertEquals(order.get("id").asLong(), todaysOrders.get(0).get("id").asLong());

        JsonNode lowStock = getJson("/api/dashboard/low-stock");
        assertNotNull(findById(lowStock, 2));
        assertNotNull(findById(lowStock, 3));
    }

    private JsonNode getJson(String url) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        return objectMapper.readTree(response.getContentAsString());
    }

    private JsonNode postJson(String url, Object payload) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        return objectMapper.readTree(response.getContentAsString());
    }

    private JsonNode putJson(String url, Object payload) throws Exception {
        var request = put(url).contentType(MediaType.APPLICATION_JSON);
        if (payload != null) {
            request.content(objectMapper.writeValueAsString(payload));
        }

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        return objectMapper.readTree(response.getContentAsString());
    }

    private JsonNode findById(JsonNode array, long id) {
        Iterator<JsonNode> iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            if (node.has("id") && node.get("id").asLong() == id) {
                return node;
            }
        }
        return null;
    }
}


