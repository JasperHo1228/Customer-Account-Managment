package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck;

public class RetypePasswordNotMatch extends RuntimeException{
    public RetypePasswordNotMatch(String message){
        super(message);
    }
}
