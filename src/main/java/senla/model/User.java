package senla.model;

public class User {
    private Integer id;
    private String username;

    private String password;

    private String email;
    private UserRole role;

    public User() {
    }

    public User(Integer id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = UserRole.valueOf(role);
    }

    public User( String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = UserRole.valueOf(role);
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return  "Пользователь:" +
                " логин='" + username + '\'' +
                ", пароль='" + password + '\'' +
                ", email='" + email + '\'' +
                ", роль=" + role ;
    }
}
