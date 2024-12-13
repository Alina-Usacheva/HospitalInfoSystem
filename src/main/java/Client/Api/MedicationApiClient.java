package Client.Api;

import com.example.HospitalInfoSystem.Entities.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MedicationApiClient {

    private final String BASE_URL = "http://localhost:8080/medications";
    private final RestTemplate restTemplate;

    @Autowired
    public MedicationApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Medication> getAllMedications() {
        Medication[] medications = restTemplate.getForObject(BASE_URL, Medication[].class);
        return Arrays.asList(medications);
    }

    public Medication getMedicationById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Medication.class);
    }

    public Medication createMedication(Medication medication) {
        return restTemplate.postForObject(BASE_URL, medication, Medication.class);
    }

    public Medication updateMedication(Long id, Medication medicationDetails) {
        restTemplate.put(BASE_URL + "/" + id, medicationDetails);
        return getMedicationById(id);
    }

    public void deleteMedication(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
