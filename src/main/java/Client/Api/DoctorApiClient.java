package Client.Api;

import com.example.HospitalInfoSystem.Entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorApiClient {

    private final String BASE_URL = "http://localhost:8080/doctors";
    private final RestTemplate restTemplate;

    @Autowired
    public DoctorApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Doctor> getAllDoctors() {
        ResponseEntity<Doctor[]> responseEntity = restTemplate.getForEntity(BASE_URL, Doctor[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<String> getAllSpecialties() {
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(BASE_URL + "/specialties", String[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        ResponseEntity<Doctor[]> responseEntity = restTemplate.getForEntity(BASE_URL + "/specialty?specialty=" + specialty, Doctor[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public Doctor getDoctorById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Doctor.class);
    }

    public Doctor createDoctor(Doctor doctor) {
        return restTemplate.postForObject(BASE_URL, doctor, Doctor.class);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        restTemplate.put(BASE_URL + "/" + id, doctorDetails);
        return getDoctorById(id);
    }

    public void deleteDoctor(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
