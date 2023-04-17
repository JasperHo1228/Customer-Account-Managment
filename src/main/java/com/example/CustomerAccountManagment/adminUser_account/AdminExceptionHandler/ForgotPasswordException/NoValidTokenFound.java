package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException;

public class NoValidTokenFound extends RuntimeException{
    public NoValidTokenFound(String message){
        super(message);
    }
}
