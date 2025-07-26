package ca.uqam.mgl7361.lel.gp1.delivery.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Address;

public class AddressMapper {
    public static AddressDTO toDTO(Address address) {
        AddressDTO result = new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode());

        result.setAccountId(address.getAccountId());
        result.setFirstName(address.getFirstName());
        result.setLastName(address.getLastName());
        result.setPhone(address.getPhone());

        return result;
    }

    public static Address toModel(AddressDTO addressDTO) {
        Address result = new Address();
        result.setAccountId(addressDTO.getAccountId());
        result.setFirstName(addressDTO.getFirstName());
        result.setLastName(addressDTO.getLastName());
        result.setPhone(addressDTO.getPhone());
        result.setStreet(addressDTO.getStreet());
        result.setCity(addressDTO.getCity());
        result.setPostalCode(addressDTO.getPostalCode());
        return result;
    }

    public static List<Address> toModel(List<AddressDTO> addressDTOs) {
        return addressDTOs.stream().map(addressDTO -> AddressMapper.toModel(addressDTO))
                .collect(Collectors.toList());
    }

    public static List<AddressDTO> toDTO(List<Address> addresses) {
        return addresses.stream().map(address -> AddressMapper.toDTO(address)).collect(Collectors.toList());
    }
}
