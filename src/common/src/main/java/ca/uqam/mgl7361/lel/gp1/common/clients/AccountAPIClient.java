package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import feign.Headers;
import feign.RequestLine;

import java.util.Map;

public interface AccountAPIClient {

    @RequestLine("POST /account/signup")
    @Headers("Content-Type: application/json")
    void signup(AccountDTO accountDTO);

    @RequestLine("POST /account/signin")
    @Headers("Content-Type: application/json")
    AccountDTO signin(Map<String, String> credentials);

}
