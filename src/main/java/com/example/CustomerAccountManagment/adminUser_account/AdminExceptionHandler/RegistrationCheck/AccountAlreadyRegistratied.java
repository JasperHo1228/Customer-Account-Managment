package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck;

public class AccountAlreadyRegistratied extends RuntimeException{
    public AccountAlreadyRegistratied(String message){
        super(message);
    }
}
