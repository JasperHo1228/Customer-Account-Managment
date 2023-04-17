package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck;

public class TokenStillNotExpired extends RuntimeException{
    public TokenStillNotExpired(String message){
        super(message);
    }
}
