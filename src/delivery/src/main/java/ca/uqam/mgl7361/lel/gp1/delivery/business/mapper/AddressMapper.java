package ca.uqam.mgl7361.lel.gp1.delivery.business.mapper;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Address;

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
