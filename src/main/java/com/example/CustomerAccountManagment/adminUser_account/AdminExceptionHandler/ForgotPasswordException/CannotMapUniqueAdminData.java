package com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class CannotMapUniqueAdminData extends IncorrectResultSizeDataAccessException {

    public CannotMapUniqueAdminData(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
