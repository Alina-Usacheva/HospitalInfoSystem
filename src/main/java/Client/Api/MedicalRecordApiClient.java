package Client.Api;

import com.example.HospitalInfoSystem.Entities.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MedicalRecordApiClient {

    private final String BASE_URL = "http://localhost:8080/medical-records";
    private final RestTemplate restTemplate;

    @Autowired
    public MedicalRecordApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        MedicalRecord[] records = restTemplate.getForObject(BASE_URL, MedicalRecord[].class);
        return Arrays.asList(records);
    }

    public MedicalRecord getMedicalRecordById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, MedicalRecord.class);
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return restTemplate.postForObject(BASE_URL, medicalRecord, MedicalRecord.class);
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecordDetails) {
        restTemplate.put(BASE_URL + "/" + id, medicalRecordDetails);
        return getMedicalRecordById(id);
    }

    public void deleteMedicalRecord(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        MedicalRecord[] records = restTemplate.getForObject(BASE_URL + "/patient/" + patientId, MedicalRecord[].class);
        return Arrays.asList(records);
    }
}
