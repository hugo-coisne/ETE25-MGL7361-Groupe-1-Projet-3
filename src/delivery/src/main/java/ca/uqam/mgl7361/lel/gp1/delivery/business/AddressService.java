package ca.uqam.mgl7361.lel.gp1.delivery.business;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.mapper.AddressMapper;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Address;
import ca.uqam.mgl7361.lel.gp1.delivery.persistence.AddressDAO;

@Service
public class AddressService {
    
    static AddressService instance = null;
    AddressDAO addressDAO = AddressDAO.getInstance();
    Logger logger = LogManager.getLogger(AddressService.class);
    
    public static AddressService getInstance() {
        if (instance == null){
            instance = new AddressService();
        }
        return instance;
    }

    public void create(AddressDTO addressDTO) throws Exception {
        Address address = AddressMapper.toModel(addressDTO);
        addressDAO.create(address);
    }

    public AddressDTO findById(int id) throws Exception {
        Address address = addressDAO.findById(id);
        return AddressMapper.toDTO(address);
    }

    public List<AddressDTO> findByAccountId(int accountId) throws Exception {
        List<Address> addresses = addressDAO.findByAccountId(accountId);
        return AddressMapper.toDTO(addresses);
    }

    public void update(AddressDTO addressDTO) throws Exception {
        Address address = AddressMapper.toModel(addressDTO);
        addressDAO.update(address);
    }

    public boolean delete(int id) throws Exception {
        return addressDAO.delete(id);
    }

}
