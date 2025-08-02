package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.Printable;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Adresse complète d'un compte utilisateur")
public class AddressDTO extends Printable {
    @Schema(description = "Identifiant unique de l'adresse", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Schema(description = "Identifiant du compte utilisateur associé", example = "42")
    private int accountId;

    @Schema(description = "Prénom du destinataire", example = "Jean")
    private String firstName;

    @Schema(description = "Nom de famille du destinataire", example = "Dupont")
    private String lastName;

    @Schema(description = "Numéro de téléphone du destinataire", example = "+1-514-123-4567")
    private String phone;

    @Schema(description = "Rue et numéro civique", example = "123 rue Principale")
    private String street;

    @Schema(description = "Ville", example = "Montréal")
    private String city;

    @Schema(description = "Code postal", example = "H2X 1Y4")
    private String postalCode;

    // Constructeurs, getters/setters (non modifiés)
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
