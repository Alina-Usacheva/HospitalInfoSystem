package Client.Api;

import com.example.HospitalInfoSystem.Entities.User;
import com.example.HospitalInfoSystem.Dto.UserRegistrationDto;
import com.example.HospitalInfoSystem.Dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class UserApiClient {

    private final String BASE_URL = "http://localhost:8080/users";
    private final RestTemplate restTemplate;

    @Autowired
    public UserApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Метод для регистрации пользователя
    public String registerUser (UserRegistrationDto registrationDto) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + "/register", registrationDto, String.class);
        return responseEntity.getBody();
    }

    // Метод для аутентификации пользователя
    public Long loginUser (LoginDto loginDto) {
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(BASE_URL + "/login", loginDto, Long.class);
        return responseEntity.getBody();
    }

    // Метод для получения всех пользователей (если необходимо)
    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(BASE_URL, User[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    // Метод для получения пользователя по ID
    public User getUserById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, User.class);
    }

    // Метод для обновления информации о пользователе
    public User updateUser(Long id, User userDetails) {
        restTemplate.put(BASE_URL + "/" + id, userDetails);
        return getUserById(id);
    }

    // Метод для удаления пользователя
    public void deleteUser (Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
