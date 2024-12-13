package Client.Api;

import com.example.HospitalInfoSystem.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientApiClient {

    private final String BASE_URL = "http://localhost:8080/patients";
    private final RestTemplate restTemplate;

    @Autowired
    public PatientApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Patient> getAllPatients() {
        ResponseEntity<Patient[]> responseEntity = restTemplate.getForEntity(BASE_URL, Patient[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public Patient getPatientById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Patient.class);
    }

    public Patient createPatient(Patient patient) {
        return restTemplate.postForObject(BASE_URL, patient, Patient.class);
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        restTemplate.put(BASE_URL + "/" + id, patientDetails);
        return getPatientById(id);
    }

    public void deletePatient(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    // Поиск пациентов по имени или фамилии
    public List<Patient> searchPatientsByNameOrSurname(String searchText) {
        ResponseEntity<Patient[]> responseEntity = restTemplate.getForEntity(BASE_URL + "/search?searchText=" + searchText, Patient[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
