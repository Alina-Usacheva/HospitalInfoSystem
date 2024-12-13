package com.example.HospitalInfoSystem.Repository;

import com.example.HospitalInfoSystem.Entities.Doctor;
import com.example.HospitalInfoSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
