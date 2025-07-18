package ca.uqam.mgl7361.lel.gp1.payment.external;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.interfaces.AccountAPI;

public class AccountAPIImpl implements AccountAPI {

    @Override
    public AccountDTO signin(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signin'");
    }

    @Override
    public void signup(String firstName, String lastName, String phone, String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signup'");
    }

    @Override
    public void changePhoneFor(AccountDTO account, String newPhone) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePhoneFor'");
    }

    @Override
    public void changeEmailFor(AccountDTO account, String newEmail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeEmailFor'");
    }

    @Override
    public void changeFirstNameFor(AccountDTO account, String newFirstName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeFirstNameFor'");
    }

    @Override
    public void changeLastNameFor(AccountDTO account, String newLastName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeLastNameFor'");
    }

    @Override
    public void changePasswordFor(AccountDTO account, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePasswordFor'");
    }

    @Override
    public void delete(AccountDTO account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
