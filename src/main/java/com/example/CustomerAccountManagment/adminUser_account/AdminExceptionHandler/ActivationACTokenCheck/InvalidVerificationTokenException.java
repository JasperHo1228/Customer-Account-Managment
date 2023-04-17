package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck;

public class InvalidVerificationTokenException extends RuntimeException{
    public InvalidVerificationTokenException(String message) {
        super(message);
    }
}
