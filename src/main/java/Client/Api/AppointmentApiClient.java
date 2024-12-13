package Client.Api;

import com.example.HospitalInfoSystem.Entities.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class AppointmentApiClient {

    private final String BASE_URL = "http://localhost:8080/appointments";
    private final RestTemplate restTemplate;

    @Autowired
    public AppointmentApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Appointment> getAllAppointments() {
        ResponseEntity<Appointment[]> responseEntity = restTemplate.getForEntity(BASE_URL, Appointment[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public Appointment getAppointmentById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Appointment.class);
    }

    public Appointment createAppointment(Appointment appointment) {
        return restTemplate.postForObject(BASE_URL, appointment, Appointment.class);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        restTemplate.put(BASE_URL + "/" + id, appointmentDetails);
        return getAppointmentById(id);
    }

    public void deleteAppointment(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        ResponseEntity<Appointment[]> response = restTemplate.getForEntity(BASE_URL + "/patient/" + patientId, Appointment[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        ResponseEntity<Appointment[]> response = restTemplate.getForEntity(BASE_URL + "/doctor/" + doctorId, Appointment[].class);
        return Arrays.asList(response.getBody());
    }
}
