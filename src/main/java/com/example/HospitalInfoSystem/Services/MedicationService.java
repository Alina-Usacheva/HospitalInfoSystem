package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.Medication;
import com.example.HospitalInfoSystem.Repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    public Medication findById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
    }

    public Medication save(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Medication update(Long id, Medication medicationDetails) {
        Medication medication = findById(id);
        medication.setName(medicationDetails.getName());
        medication.setDescription(medicationDetails.getDescription());
        return medicationRepository.save(medication);
    }

    public void delete(Long id) {
        medicationRepository.deleteById(id);
    }
}
