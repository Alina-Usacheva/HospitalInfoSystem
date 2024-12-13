package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.Doctor;
import com.example.HospitalInfoSystem.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<String> findAllSpecialties() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getSpecialty)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Doctor> findBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor update(Long id, Doctor doctorDetails) {
        Doctor doctor = findById(id);
        doctor.setName(doctorDetails.getName());
        doctor.setSurname(doctorDetails.getSurname());
        doctor.setSpecialty(doctorDetails.getSpecialty());
        doctor.setRoomNumber(doctorDetails.getRoomNumber());
        return doctorRepository.save(doctor);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }
}
