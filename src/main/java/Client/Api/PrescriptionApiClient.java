package Client.Api;

import com.example.HospitalInfoSystem.Entities.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PrescriptionApiClient {

    private final String BASE_URL = "http://localhost:8080/prescriptions";
    private final RestTemplate restTemplate;

    @Autowired
    public PrescriptionApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Prescription> getAllPrescriptions() {
        Prescription[] prescriptions = restTemplate.getForObject(BASE_URL, Prescription[].class);
        return Arrays.asList(prescriptions);
    }

    public Prescription getPrescriptionById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Prescription.class);
    }

    public Prescription createPrescription(Prescription prescription) {
        return restTemplate.postForObject(BASE_URL, prescription, Prescription.class);
    }

    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        restTemplate.put(BASE_URL + "/" + id, prescriptionDetails);
        return getPrescriptionById(id);
    }

    public void deletePrescription(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public List<Prescription> getPrescriptionsByMedicalRecordId(Long medicalRecordId) {
        Prescription[] prescriptions = restTemplate.getForObject(BASE_URL + "/medical-record/" + medicalRecordId, Prescription[].class);
        return List.of(prescriptions);
    }
}
