package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient patientDetails) {
        Patient patient = findById(id);
        patient.setName(patientDetails.getName());
        patient.setSurname(patientDetails.getSurname());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setAddress(patientDetails.getAddress());
        patient.setPhoneNumber(patientDetails.getPhoneNumber());
        return patientRepository.save(patient);
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    // Метод для поиска пациентов
    public List<Patient> searchPatientsByNameOrSurname(String searchText) {
        return patientRepository.searchByNameOrSurname(searchText);
    }
}
