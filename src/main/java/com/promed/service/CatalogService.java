package com.promed.service;

import com.promed.model.Doctor;
import com.promed.model.LabTest;
import com.promed.model.Medicine;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CatalogService {

    private final Map<Long, Medicine> medicines = new ConcurrentHashMap<>();
    private final Map<Long, Doctor> doctors = new ConcurrentHashMap<>();
    private final Map<Long, LabTest> labTests = new ConcurrentHashMap<>();

    @PostConstruct
    public void seedData() {
        medicines.put(1L, new Medicine(1L, "Paracetamol 500mg", "Fever", new BigDecimal("35.00"), 120, 50, false));
        medicines.put(2L, new Medicine(2L, "Azithromycin 250mg", "Antibiotic", new BigDecimal("160.00"), 42, 40, true));
        medicines.put(3L, new Medicine(3L, "Vitamin D3", "Supplements", new BigDecimal("220.00"), 20, 30, false));
        medicines.put(4L, new Medicine(4L, "Metformin 500mg", "Diabetes", new BigDecimal("110.00"), 60, 35, true));

        List<LocalDateTime> baseSlots = List.of(
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0).withSecond(0).withNano(0)
        );
        doctors.put(1L, new Doctor(1L, "Dr. Neha Sharma", "General Physician", baseSlots));
        doctors.put(2L, new Doctor(2L, "Dr. Arjun Rao", "Cardiologist", baseSlots));

        labTests.put(1L, new LabTest(1L, "Complete Blood Count", "CBC profile", new BigDecimal("450.00"), true));
        labTests.put(2L, new LabTest(2L, "Urine Routine", "Urine microscopic and chemical test", new BigDecimal("250.00"), true));
        labTests.put(3L, new LabTest(3L, "Stool Routine", "Stool profile", new BigDecimal("300.00"), true));
    }

    public List<Medicine> searchMedicines(String query) {
        String normalized = query == null ? "" : query.trim().toLowerCase();
        return medicines.values().stream()
                .filter(m -> normalized.isBlank()
                        || m.getName().toLowerCase().contains(normalized)
                        || m.getCategory().toLowerCase().contains(normalized))
                .sorted(Comparator.comparing(Medicine::getName))
                .toList();
    }

    public Optional<Medicine> findMedicine(Long id) {
        return Optional.ofNullable(medicines.get(id));
    }

    public List<Medicine> allInventory() {
        return new ArrayList<>(medicines.values());
    }

    public List<Medicine> lowStockItems() {
        return medicines.values().stream()
                .filter(m -> m.getStock() <= m.getReorderLevel())
                .toList();
    }

    public void updateStock(Long medicineId, int stock) {
        Medicine medicine = medicines.get(medicineId);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine not found");
        }
        medicine.setStock(stock);
    }

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public Doctor getDoctorOrThrow(Long doctorId) {
        Doctor doctor = doctors.get(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found");
        }
        return doctor;
    }

    public void updateDoctorSlots(Long doctorId, List<LocalDateTime> slots) {
        Doctor doctor = getDoctorOrThrow(doctorId);
        doctor.setAvailableSlots(slots);
    }

    public List<LabTest> getLabTests() {
        return new ArrayList<>(labTests.values());
    }

    public LabTest getLabTestOrThrow(Long labTestId) {
        LabTest test = labTests.get(labTestId);
        if (test == null) {
            throw new IllegalArgumentException("Lab test not found");
        }
        return test;
    }

    public void updateLabRate(Long labTestId, BigDecimal rate) {
        LabTest test = getLabTestOrThrow(labTestId);
        test.setRate(rate);
    }
}

