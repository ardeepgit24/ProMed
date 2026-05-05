import { useEffect, useState } from 'react';
import { dashboardApi } from '../api';

export default function DashboardPage() {
  const [metrics, setMetrics] = useState(null);
  const [lowStock, setLowStock] = useState([]);
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    Promise.all([
      dashboardApi.metrics(),
      dashboardApi.lowStock(),
      dashboardApi.ordersToday()
    ]).then(([m, s, o]) => {
      setMetrics(m.data);
      setLowStock(s.data);
      setOrders(o.data);
    }).catch(() => {
      setMetrics(null);
    });
  }, []);

  return (
    <section>
      <h2>Business Dashboard</h2>
      {metrics ? (
        <div className="grid">
          <div className="card">Daily Sale: INR {metrics.dailySale}</div>
          <div className="card">Low Stock Need Reorder: {metrics.lowStockNeedReorder}</div>
          <div className="card">Orders Placed Today: {metrics.ordersPlacedToday}</div>
          <div className="card">Appointments Today: {metrics.appointmentsToday}</div>
          <div className="card">Lab Bookings Today: {metrics.labBookingsToday}</div>
        </div>
      ) : (
        <p>Backend is not reachable. Start Spring Boot backend on port 8080.</p>
      )}

      <h3>Low Stock Items</h3>
      <ul>
        {lowStock.map((item) => (
          <li key={item.id}>{item.name} - Stock {item.stock}, Reorder Level {item.reorderLevel}</li>
        ))}
      </ul>

      <h3>Orders Placed Today</h3>
      <ul>
        {orders.map((o) => (
          <li key={o.id}>#{o.id} - {o.customerName} - INR {o.totalAmount}</li>
        ))}
      </ul>
    </section>
  );
}

