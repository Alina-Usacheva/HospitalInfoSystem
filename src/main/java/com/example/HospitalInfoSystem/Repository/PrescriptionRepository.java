package com.example.HospitalInfoSystem.Repository;
import com.example.HospitalInfoSystem.Entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByMedicalRecord_MedicalRecordId(Long medicalRecordId);
}
