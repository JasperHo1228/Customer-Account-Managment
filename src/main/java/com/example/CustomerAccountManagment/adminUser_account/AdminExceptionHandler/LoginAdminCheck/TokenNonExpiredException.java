package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.LoginAdminCheck;


public class TokenNonExpiredException extends RuntimeException{
    public TokenNonExpiredException(String message){
        super(message);
    }
}

