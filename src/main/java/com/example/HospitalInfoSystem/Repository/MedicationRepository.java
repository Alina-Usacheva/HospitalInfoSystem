package com.example.HospitalInfoSystem.Repository;
import com.example.HospitalInfoSystem.Entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicationRepository extends JpaRepository<Medication, Long> {}
