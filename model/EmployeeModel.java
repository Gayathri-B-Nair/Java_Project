package opps.model;

public class EmployeeModel {
    private int id;
    private String username;
    private String password;
    private String role;

    // --- Constructor ---
    public EmployeeModel() {}

    public EmployeeModel(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // --- Getters & Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- Optional: for debugging or logs ---
    @Override
    public String toString() {
        return "Employee [id=" + id + ", username=" + username + ", role=" + role + "]";
    }
}
