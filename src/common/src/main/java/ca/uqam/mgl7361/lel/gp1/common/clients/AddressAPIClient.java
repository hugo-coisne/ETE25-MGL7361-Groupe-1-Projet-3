package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AddressAPIClient {

    @RequestLine("POST /addresses")
    @Headers("Content-Type: application/json")
    void create(AddressDTO addressDTO);

    @RequestLine("GET /addresses/byId/{id}")
    @Headers("Accept: application/json")
    AddressDTO findById(@Param("id") int id);

    @RequestLine("GET /addresses/byAccountId/{id}")
    @Headers("Accept: application/json")
    AddressDTO findByAccountId(@Param("id") int id);

    @RequestLine("PATCH /addresses")
    @Headers("Accept: application/json")
    AddressDTO update(AddressDTO addressDTO);

    @RequestLine("DELETE /addresses/{id}")
    @Headers("Accept: application/json")
    AddressDTO delete(@Param("id") int id);
}
