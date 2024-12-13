package com.example.HospitalInfoSystem.Repository;
import com.example.HospitalInfoSystem.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameAndSurname(String name, String surname);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(p.surname) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Patient> searchByNameOrSurname(@Param("searchText") String searchText);
}
