package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.LoginAdminCheck;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.ui.Model;

public class NullAdminUser extends InternalAuthenticationServiceException {

    public NullAdminUser(String message) {
        super(message);
    }

}
