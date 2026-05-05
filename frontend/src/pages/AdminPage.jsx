import { useEffect, useState } from 'react';
import { adminApi } from '../api';

export default function AdminPage() {
  const [inventory, setInventory] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [labTests, setLabTests] = useState([]);
  const [message, setMessage] = useState('');

  const load = () => {
    adminApi.getInventory().then((res) => setInventory(res.data));
    adminApi.getDoctors().then((res) => setDoctors(res.data));
    adminApi.getLabTests().then((res) => setLabTests(res.data));
  };

  useEffect(() => {
    load();
  }, []);

  const updateStock = async (medicineId) => {
    const stock = prompt('Enter new stock value');
    if (stock == null) {
      return;
    }
    await adminApi.updateStock(medicineId, Number(stock));
    setMessage('Stock updated');
    load();
  };

  const updateRate = async (labTestId) => {
    const rate = prompt('Enter new rate');
    if (rate == null) {
      return;
    }
    await adminApi.updateLabRate(labTestId, Number(rate));
    setMessage('Lab rate updated');
    load();
  };

  const replaceSlots = async (doctorId) => {
    const slotInput = prompt('Enter comma separated slots in YYYY-MM-DDTHH:mm:ss format');
    if (!slotInput) {
      return;
    }
    const slots = slotInput.split(',').map((s) => s.trim());
    await adminApi.updateDoctorSlots(doctorId, slots);
    setMessage('Doctor slots updated');
    load();
  };

  return (
    <section>
      <h2>Admin Module</h2>
      {message && <p className="status">{message}</p>}

      <div className="card">
        <h3>Inventory Management</h3>
        <ul>
          {inventory.map((i) => (
            <li key={i.id}>
              {i.name} - Stock {i.stock} - Reorder at {i.reorderLevel}
              <button onClick={() => updateStock(i.id)}>Update Stock</button>
            </li>
          ))}
        </ul>
      </div>

      <div className="card">
        <h3>Doctor Slot Availability</h3>
        <ul>
          {doctors.map((d) => (
            <li key={d.id}>
              {d.name} - {d.availableSlots.join(', ')}
              <button onClick={() => replaceSlots(d.id)}>Replace Slots</button>
            </li>
          ))}
        </ul>
      </div>

      <div className="card">
        <h3>Lab Tests and Rates</h3>
        <ul>
          {labTests.map((l) => (
            <li key={l.id}>
              {l.name} - INR {l.rate}
              <button onClick={() => updateRate(l.id)}>Update Rate</button>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}

