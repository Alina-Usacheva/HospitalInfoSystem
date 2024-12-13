package com.example.HospitalInfoSystem.Controllers;

import com.example.HospitalInfoSystem.Dto.LoginDto;
import com.example.HospitalInfoSystem.Dto.UserRegistrationDto;
import com.example.HospitalInfoSystem.Entities.Doctor;
import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Entities.User;
import com.example.HospitalInfoSystem.Services.DoctorService;
import com.example.HospitalInfoSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllDoctors() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // Endpoint для регистрации пользователя
    @PostMapping("/register")
    public ResponseEntity<?> registerUser (@RequestBody UserRegistrationDto registrationDto) {
        // Обращение к серверу для регистрации полььзователя
        boolean isRegistered = userService.registerUser (
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getDob(),
                registrationDto.getAddress(),
                registrationDto.getPhone(),
                registrationDto.getSpecialty(),
                registrationDto.getRoomNumber(),
                registrationDto.getUsername(),
                registrationDto.getPassword(),
                registrationDto.getRole()
        );

        if (isRegistered) {
            return ResponseEntity.ok("User  registered successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginDto loginDto) {
        Long userId = userService.authenticateUser(loginDto.getUsername(), loginDto.getPassword(), loginDto.getRole());
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.badRequest().body("Неверное имя пользователя, пароль или роль.");
        }
    }
}
