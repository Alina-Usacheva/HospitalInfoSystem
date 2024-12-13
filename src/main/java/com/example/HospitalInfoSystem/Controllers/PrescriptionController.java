package com.example.HospitalInfoSystem.Controllers;

import com.example.HospitalInfoSystem.Entities.Prescription;
import com.example.HospitalInfoSystem.Services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.findById(id));
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.save(prescription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescriptionDetails) {
        return ResponseEntity.ok(prescriptionService.update(id, prescriptionDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/medical-record/{medicalRecordId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByMedicalRecordId(@PathVariable Long medicalRecordId) {
        List<Prescription> prescriptions = prescriptionService.findByMedicalRecordId(medicalRecordId);
        return ResponseEntity.ok(prescriptions);
    }
}
