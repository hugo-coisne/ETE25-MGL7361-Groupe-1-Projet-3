package ca.uqam.mgl7361.lel.gp1.common.dtos.user;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User account data")
public class AccountDTO extends DTO {

    @Schema(description = "First name", example = "Alice")
    private String firstName;

    @Schema(description = "Last name", example = "Smith")
    private String lastName;

    @Schema(description = "Phone number", example = "+1-555-1234")
    private String phone;

    @Schema(description = "Email address", example = "alice@example.com")
    private String email;

    @Schema(description = "Account password", example = "securePassword123")
    private String password;

    @Schema(description = "Unique account ID", example = "42")
    private int id;

    public AccountDTO(String firstName, String lastName, String phone, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public AccountDTO(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public AccountDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
