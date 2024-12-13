package com.example.HospitalInfoSystem.Services;

import com.example.HospitalInfoSystem.Entities.Doctor;
import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Entities.User;
import com.example.HospitalInfoSystem.Repository.DoctorRepository;
import com.example.HospitalInfoSystem.Repository.PatientRepository;
import com.example.HospitalInfoSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Long authenticateUser (String username, String password, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            return null;
        }

        if (role.equals("Пациент") && user.getPatient() != null) {
            return user.getUserId();
        } else if (role.equals("Врач") && user.getDoctor() != null) {
            return user.getUserId();
        }

        return null;
    }

    public boolean registerUser (String firstName, String lastName, String dob, String address, String phone, String specialty, String roomNumber, String username, String password, String role) {

        if (userRepository.findByUsername(username) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        if ("Пациент".equals(role)) {
            Patient patient = new Patient();
            patient.setName(firstName);
            patient.setSurname(lastName);
            patient.setDateOfBirth(dob);
            patient.setAddress(address);
            patient.setPhoneNumber(phone);
            patient.setUser(user); // Устанавливаем связь с пользователем
            patientRepository.save(patient);
        } else if ("Врач".equals(role)) {
            Doctor doctor = new Doctor();
            doctor.setName(firstName);
            doctor.setSurname(lastName);
            doctor.setSpecialty(specialty);
            doctor.setRoomNumber(roomNumber);
            doctor.setUser(user); // Устанавливаем связь с пользователем
            doctorRepository.save(doctor);
        }

        return true; // Возвращаем успешный результат
    }
}
