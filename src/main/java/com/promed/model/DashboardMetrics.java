package com.promed.model;

import java.math.BigDecimal;

public record DashboardMetrics(
        BigDecimal dailySale,
        long lowStockNeedReorder,
        long ordersPlacedToday,
        long appointmentsToday,
        long labBookingsToday
) {
}

