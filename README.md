# ProMed Base Application

This is a base full-stack application for a medical business that supports:

- Medicine ordering and customer search
- Doctor appointment slot booking
- Lab test booking with home collection option
- Admin inventory, doctor slot, and lab rate management
- Dashboard for daily sale, low stock reorder needs, and orders placed today

## Backend (Java / Spring Boot)

Backend path: `src/main/java/com/promed`

Key APIs:

- `GET /api/customer/medicines?query=`
- `POST /api/customer/orders` (order summary mailed to `ardeep24@gmail.com`)
- `GET /api/customer/doctors`
- `POST /api/customer/appointments`
- `GET /api/customer/lab-tests`
- `POST /api/customer/lab-bookings`
- `GET /api/admin/inventory`
- `PUT /api/admin/inventory/{medicineId}/stock?stock=NN`
- `PUT /api/admin/doctors/{doctorId}/slots`
- `PUT /api/admin/lab-tests/{labTestId}/rate?rate=NN`
- `GET /api/dashboard/metrics`
- `GET /api/dashboard/low-stock`
- `GET /api/dashboard/orders-today`

## Frontend (React / Vite)

Frontend path: `frontend`

Pages:

- Dashboard
- Customer module
- Admin module

## Quick Start

### 1) Run backend

```bash
cd /Users/I059625/ProMed
mvn spring-boot:run
```

### 2) Run frontend

```bash
cd /Users/I059625/ProMed/frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

## Test

```bash
cd /Users/I059625/ProMed
mvn test
```

## Component Test (End-to-End Backend Flow)

The project now includes a component/integration test that validates a full feature flow across customer, admin, and dashboard APIs:

- Test file: `src/test/java/com/promed/integration/ProMedFeatureFlowIT.java`
- Covers medicine order, doctor appointment booking, lab booking, admin updates, and dashboard aggregation checks.

Run only this test:

```bash
cd /Users/I059625/ProMed
mvn -Dtest=ProMedFeatureFlowIT test
```

## Notes

- Data is currently in-memory for base setup (easy to extend to PostgreSQL/MySQL).
- If SMTP is not configured in `src/main/resources/application.properties`, order mail falls back to logging.
- Base design includes common features from pharmacy platforms: search, availability, ordering, appointment and diagnostics booking, and operational dashboard metrics.

