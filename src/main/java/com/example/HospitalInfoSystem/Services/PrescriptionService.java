package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.Prescription;
import com.example.HospitalInfoSystem.Repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    public Prescription findById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    public Prescription save(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription update(Long id, Prescription prescriptionDetails) {
        Prescription prescription = findById(id);
        prescription.setDosage(prescriptionDetails.getDosage());
        prescription.setDuration(prescriptionDetails.getDuration());
        prescription.setMedicalRecord(prescriptionDetails.getMedicalRecord());
        prescription.setMedication(prescriptionDetails.getMedication());
        return prescriptionRepository.save(prescription);
    }

    public void delete(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public List<Prescription> findByMedicalRecordId(Long medicalRecordId) {
        return prescriptionRepository.findByMedicalRecord_MedicalRecordId(medicalRecordId);
    }
}
