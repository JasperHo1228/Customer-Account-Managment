package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck;

public class VerificationTokenExpiredException extends RuntimeException{
    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}
