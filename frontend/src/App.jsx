import { Link, Route, Routes } from 'react-router-dom';
import DashboardPage from './pages/DashboardPage';
import CustomerPage from './pages/CustomerPage';
import AdminPage from './pages/AdminPage';

export default function App() {
  return (
    <div className="app-shell">
      <header>
        <h1>ProMed Platform</h1>
        <nav>
          <Link to="/">Dashboard</Link>
          <Link to="/customer">Customer</Link>
          <Link to="/admin">Admin</Link>
        </nav>
      </header>

      <main>
        <Routes>
          <Route path="/" element={<DashboardPage />} />
          <Route path="/customer" element={<CustomerPage />} />
          <Route path="/admin" element={<AdminPage />} />
        </Routes>
      </main>
    </div>
  );
}

