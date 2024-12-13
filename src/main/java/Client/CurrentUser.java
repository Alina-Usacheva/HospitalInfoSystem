package Client;

public class CurrentUser  {
    private Long id;
    private static CurrentUser  instance;
    private String username;
    private String role;

    private CurrentUser () {
        // Приватный конструктор для предотвращения создания экземпляров извне
    }

    public static CurrentUser  getInstance() {
        if (instance == null) {
            instance = new CurrentUser ();
        }
        return instance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void clear() {
        username = null;
        role = null;
    }
}
