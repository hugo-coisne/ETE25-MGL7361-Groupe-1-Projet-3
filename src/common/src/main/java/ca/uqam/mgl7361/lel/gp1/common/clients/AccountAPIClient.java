package ca.uqam.mgl7361.lel.gp1.common.clients;

import feign.Headers;
import feign.RequestLine;

import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public interface AccountAPIClient {

    @RequestLine("POST /account/signup")
    @Headers("Content-Type: application/json")
    void signup(AccountDTO accountDTO);

    @RequestLine("POST /account/signin")
    @Headers("Content-Type: application/json")
    AccountDTO signin(Map<String, String> credentials);

}
