import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'
});

export const customerApi = {
  searchMedicines: (query) => api.get('/customer/medicines', { params: { query } }),
  placeOrder: (payload) => api.post('/customer/orders', payload),
  getDoctors: () => api.get('/customer/doctors'),
  bookAppointment: (payload) => api.post('/customer/appointments', payload),
  getLabTests: () => api.get('/customer/lab-tests'),
  bookLab: (payload) => api.post('/customer/lab-bookings', payload)
};

export const adminApi = {
  getInventory: () => api.get('/admin/inventory'),
  updateStock: (medicineId, stock) => api.put(`/admin/inventory/${medicineId}/stock`, null, { params: { stock } }),
  getDoctors: () => api.get('/admin/doctors'),
  updateDoctorSlots: (doctorId, slots) => api.put(`/admin/doctors/${doctorId}/slots`, { slots }),
  getLabTests: () => api.get('/admin/lab-tests'),
  updateLabRate: (labTestId, rate) => api.put(`/admin/lab-tests/${labTestId}/rate`, null, { params: { rate } })
};

export const dashboardApi = {
  metrics: () => api.get('/dashboard/metrics'),
  lowStock: () => api.get('/dashboard/low-stock'),
  ordersToday: () => api.get('/dashboard/orders-today')
};

