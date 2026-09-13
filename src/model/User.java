package model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String role;
    private String dateOfBirth;
    private String licenseNumber;

    public User(int userId, String name, String email, String role,
                String dateOfBirth, String licenseNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.licenseNumber = licenseNumber;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getLicenseNumber() { return licenseNumber; }
}