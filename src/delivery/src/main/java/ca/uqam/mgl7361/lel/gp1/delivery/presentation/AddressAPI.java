package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;

public interface AddressAPI {
    void create(AddressDTO addressDTO) throws Exception;

    AddressDTO findById(int id) throws Exception;

    List<AddressDTO> findByAccountId(int accountId) throws Exception;

    void update(AddressDTO addressDTO) throws Exception;

    boolean delete(int id) throws Exception;
}
