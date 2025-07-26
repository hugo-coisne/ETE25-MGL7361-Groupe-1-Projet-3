package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.AddressService;

public class AddressAPIImpl implements AddressAPI {

    AddressService addressService;

    public AddressAPIImpl() {
        this.addressService = AddressService.getInstance();
    }

    @Override
    public void create(AddressDTO addressDTO) throws Exception {
        addressService.create(addressDTO);
    }

    @Override
    public AddressDTO findById(int id) throws Exception {
        return addressService.findById(id);
    }

    @Override
    public List<AddressDTO> findByAccountId(int accountId) throws Exception {
        return addressService.findByAccountId(accountId);
    }

    @Override
    public void update(AddressDTO addressDTO) throws Exception {
        addressService.update(addressDTO);
    }

    @Override
    public boolean delete(int id) throws Exception {
        return addressService.delete(id);
    }

}
