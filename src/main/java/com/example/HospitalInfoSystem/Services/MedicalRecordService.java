package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.MedicalRecord;
import com.example.HospitalInfoSystem.Repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Record not found"));
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord update(Long id, MedicalRecord medicalRecordDetails) {
        MedicalRecord medicalRecord = findById(id);
        medicalRecord.setDate(medicalRecordDetails.getDate());
        medicalRecord.setDiagnosis(medicalRecordDetails.getDiagnosis());
        medicalRecord.setTreatment(medicalRecordDetails.getTreatment());
        medicalRecord.setPatient(medicalRecordDetails.getPatient());
        return medicalRecordRepository.save(medicalRecord);
    }

    public void delete(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public List<MedicalRecord> findByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatient_PatientId(patientId);
    }
}
