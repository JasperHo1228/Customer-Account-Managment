package com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler;

import org.springframework.security.access.method.P;

public class PhoneNoDuplicated extends RuntimeException{
    public PhoneNoDuplicated(String message){
        super(message);
    }
}
