package ca.uqam.mgl7361.lel.gp1.user.model;

import ca.uqam.mgl7361.lel.gp1.user.dto.AccountDTO;

public class Account {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;

    private int id;

    public Account(String firstName, String lastName, String phone, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Account(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public AccountDTO toDto() {
        String firstName = this.getFirstName();
        String lastName = this.getLastName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String password = this.getPassword();
        int id = this.getId();
        AccountDTO accountDto = new AccountDTO(firstName, lastName, phone, email, password);
        accountDto.setId(id);
        return accountDto;
    }

    public String toString() {
        String result = "Account(";
        if (id > 0) {
            result += "id=" + id + ", ";
        }
        result += "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ')';

        return result;
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
