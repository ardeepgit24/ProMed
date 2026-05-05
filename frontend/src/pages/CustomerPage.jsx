import { useEffect, useState } from 'react';
import { customerApi } from '../api';

const initialOrder = {
  customerName: '',
  email: '',
  phone: '',
  address: '',
  items: [{ medicineId: 1, quantity: 1 }]
};

export default function CustomerPage() {
  const [query, setQuery] = useState('');
  const [medicines, setMedicines] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [labTests, setLabTests] = useState([]);
  const [orderForm, setOrderForm] = useState(initialOrder);
  const [message, setMessage] = useState('');

  const [appointment, setAppointment] = useState({ customerName: '', email: '', doctorId: 1, slot: '' });
  const [lab, setLab] = useState({ customerName: '', email: '', labTestId: 1, homeCollection: true, address: '', slot: '' });

  useEffect(() => {
    customerApi.searchMedicines('').then((res) => setMedicines(res.data));
    customerApi.getDoctors().then((res) => setDoctors(res.data));
    customerApi.getLabTests().then((res) => setLabTests(res.data));
  }, []);

  const search = () => {
    customerApi.searchMedicines(query).then((res) => setMedicines(res.data));
  };

  const placeOrder = async (e) => {
    e.preventDefault();
    try {
      const res = await customerApi.placeOrder({
        ...orderForm,
        items: [{ medicineId: Number(orderForm.items[0].medicineId), quantity: Number(orderForm.items[0].quantity) }]
      });
      setMessage(`Order #${res.data.id} placed. Mail notification sent to ardeep24@gmail.com.`);
      setOrderForm(initialOrder);
    } catch (err) {
      setMessage(err?.response?.data?.error || 'Order failed');
    }
  };

  const bookAppointment = async (e) => {
    e.preventDefault();
    try {
      const payload = { ...appointment, doctorId: Number(appointment.doctorId) };
      await customerApi.bookAppointment(payload);
      setMessage('Doctor appointment booked successfully.');
    } catch (err) {
      setMessage(err?.response?.data?.error || 'Appointment failed');
    }
  };

  const bookLab = async (e) => {
    e.preventDefault();
    try {
      const payload = { ...lab, labTestId: Number(lab.labTestId) };
      await customerApi.bookLab(payload);
      setMessage('Lab test booked successfully.');
    } catch (err) {
      setMessage(err?.response?.data?.error || 'Lab booking failed');
    }
  };

  return (
    <section>
      <h2>Customer Module</h2>
      {message && <p className="status">{message}</p>}

      <div className="card">
        <h3>Search and Order Medicines</h3>
        <div className="inline-row">
          <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search medicine or category" />
          <button onClick={search}>Search</button>
        </div>
        <ul>
          {medicines.map((m) => (
            <li key={m.id}>{m.name} - INR {m.price} - Stock {m.stock}</li>
          ))}
        </ul>

        <form onSubmit={placeOrder} className="form-grid">
          <input placeholder="Customer name" value={orderForm.customerName} onChange={(e) => setOrderForm({ ...orderForm, customerName: e.target.value })} required />
          <input placeholder="Email" type="email" value={orderForm.email} onChange={(e) => setOrderForm({ ...orderForm, email: e.target.value })} required />
          <input placeholder="Phone" value={orderForm.phone} onChange={(e) => setOrderForm({ ...orderForm, phone: e.target.value })} required />
          <input placeholder="Address" value={orderForm.address} onChange={(e) => setOrderForm({ ...orderForm, address: e.target.value })} required />
          <input placeholder="Medicine ID" value={orderForm.items[0].medicineId} onChange={(e) => setOrderForm({ ...orderForm, items: [{ ...orderForm.items[0], medicineId: e.target.value }] })} required />
          <input placeholder="Quantity" value={orderForm.items[0].quantity} onChange={(e) => setOrderForm({ ...orderForm, items: [{ ...orderForm.items[0], quantity: e.target.value }] })} required />
          <button type="submit">Place Order</button>
        </form>
      </div>

      <div className="card">
        <h3>Find Slot and Book Doctor Appointment</h3>
        <ul>
          {doctors.map((d) => (
            <li key={d.id}>{d.name} ({d.specialization}) - Slots: {d.availableSlots.join(', ')}</li>
          ))}
        </ul>
        <form onSubmit={bookAppointment} className="form-grid">
          <input placeholder="Customer name" value={appointment.customerName} onChange={(e) => setAppointment({ ...appointment, customerName: e.target.value })} required />
          <input placeholder="Email" type="email" value={appointment.email} onChange={(e) => setAppointment({ ...appointment, email: e.target.value })} required />
          <input placeholder="Doctor ID" value={appointment.doctorId} onChange={(e) => setAppointment({ ...appointment, doctorId: e.target.value })} required />
          <input placeholder="Slot (YYYY-MM-DDTHH:mm:ss)" value={appointment.slot} onChange={(e) => setAppointment({ ...appointment, slot: e.target.value })} required />
          <button type="submit">Book Appointment</button>
        </form>
      </div>

      <div className="card">
        <h3>Book Lab Test</h3>
        <ul>
          {labTests.map((t) => (
            <li key={t.id}>{t.name} - INR {t.rate} - Home Collection: {String(t.homeCollectionAvailable)}</li>
          ))}
        </ul>
        <form onSubmit={bookLab} className="form-grid">
          <input placeholder="Customer name" value={lab.customerName} onChange={(e) => setLab({ ...lab, customerName: e.target.value })} required />
          <input placeholder="Email" type="email" value={lab.email} onChange={(e) => setLab({ ...lab, email: e.target.value })} required />
          <input placeholder="Lab Test ID" value={lab.labTestId} onChange={(e) => setLab({ ...lab, labTestId: e.target.value })} required />
          <input placeholder="Address" value={lab.address} onChange={(e) => setLab({ ...lab, address: e.target.value })} required />
          <input placeholder="Slot (YYYY-MM-DDTHH:mm:ss)" value={lab.slot} onChange={(e) => setLab({ ...lab, slot: e.target.value })} required />
          <label>
            <input type="checkbox" checked={lab.homeCollection} onChange={(e) => setLab({ ...lab, homeCollection: e.target.checked })} />
            Home Collection
          </label>
          <button type="submit">Book Lab Test</button>
        </form>
      </div>
    </section>
  );
}

