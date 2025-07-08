package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto;

public class AddressDTO {
    private int id;
    private String street;
    private String city;
    private String postalCode;

    public AddressDTO(int id, String street, String city, String postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO() {}

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

    public String toString() {
        return "AdressDTO{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

}


