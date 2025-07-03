package delivery.business.mapper;

import delivery.dto.AddressDTO;
import delivery.model.Address;

public class AddressMapper {
    public static AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode()
        );
    }
}
