package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck;

public class AccountAlreadyActivatedException extends RuntimeException{
    public AccountAlreadyActivatedException(String message) {
        super(message);
    }
}
