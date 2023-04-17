package com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler;

public class EmailPhoneNoDuplicated extends RuntimeException{
    public EmailPhoneNoDuplicated(String message) {
        super(message);
    }
}
