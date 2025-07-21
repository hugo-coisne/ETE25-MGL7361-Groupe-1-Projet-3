package ca.uqam.mgl7361.lel.gp1.user.mapper;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.user.model.Account;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPassword(account.getPassword());
        return accountDTO;
    }

    public static Account toModel(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setPhone(accountDTO.getPhone());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        return account;
    }
    
}
