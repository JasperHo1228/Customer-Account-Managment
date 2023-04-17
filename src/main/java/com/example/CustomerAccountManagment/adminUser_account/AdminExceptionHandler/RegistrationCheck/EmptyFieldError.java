package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck;

public class EmptyFieldError extends RuntimeException{
    public EmptyFieldError(String message){
        super(message);
    }
}
