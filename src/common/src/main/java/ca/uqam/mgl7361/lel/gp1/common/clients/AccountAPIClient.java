package ca.uqam.mgl7361.lel.gp1.common.clients;

@FeignClient(name = "account", url = "${account.url}")
public interface AccountAPIClient {

}
