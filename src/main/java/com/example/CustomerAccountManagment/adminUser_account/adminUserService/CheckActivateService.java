package com.example.CustomerAccountManagment.adminUser_account.adminUserService;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.AccountAlreadyActivatedException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.InvalidVerificationTokenException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.VerificationTokenExpiredException;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;

public interface CheckActivateService {

    void deleteAdmin(Long id);
    void inactiveAdminExpired();
    Admin_user save(Admin_user admin_user);

    void activateAccount(String token) throws InvalidVerificationTokenException, VerificationTokenExpiredException, AccountAlreadyActivatedException;
}
