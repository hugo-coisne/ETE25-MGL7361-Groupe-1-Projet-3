package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;

public class AddressDTO extends DTO {
    private int id;
    private int accountId;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String postalCode;

    public AddressDTO(String firstName, String lastName, String phone, String street, String city, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO(int id, int accountId, String firstName, String lastName, String phone, String street,
            String city, String postalCode) {
        this.id = id;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO(int id, String street, String city, String postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

}
