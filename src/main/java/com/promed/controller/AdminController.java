package com.promed.controller;

import com.promed.model.Doctor;
import com.promed.model.DoctorSlotsUpdateRequest;
import com.promed.model.LabTest;
import com.promed.model.Medicine;
import com.promed.service.CatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CatalogService catalogService;

    public AdminController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/inventory")
    public List<Medicine> inventory() {
        return catalogService.allInventory();
    }

    @PutMapping("/inventory/{medicineId}/stock")
    public Medicine updateStock(@PathVariable Long medicineId, @RequestParam int stock) {
        catalogService.updateStock(medicineId, stock);
        return catalogService.findMedicine(medicineId).orElseThrow();
    }

    @GetMapping("/doctors")
    public List<Doctor> doctors() {
        return catalogService.getDoctors();
    }

    @PutMapping("/doctors/{doctorId}/slots")
    public Doctor updateDoctorSlots(@PathVariable Long doctorId, @Valid @RequestBody DoctorSlotsUpdateRequest request) {
        catalogService.updateDoctorSlots(doctorId, request.slots());
        return catalogService.getDoctorOrThrow(doctorId);
    }

    @GetMapping("/lab-tests")
    public List<LabTest> labTests() {
        return catalogService.getLabTests();
    }

    @PutMapping("/lab-tests/{labTestId}/rate")
    public LabTest updateLabRate(@PathVariable Long labTestId, @RequestParam BigDecimal rate) {
        catalogService.updateLabRate(labTestId, rate);
        return catalogService.getLabTestOrThrow(labTestId);
    }
}

