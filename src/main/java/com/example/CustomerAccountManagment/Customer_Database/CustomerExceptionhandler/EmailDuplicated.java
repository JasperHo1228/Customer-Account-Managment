package com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler;

public class EmailDuplicated extends RuntimeException{
    public EmailDuplicated(String message){
        super(message);
    }
}
