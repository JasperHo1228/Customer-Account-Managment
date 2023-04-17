package com.example.CustomerAccountManagment.adminUser_account.adminUserService;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.CannotMapUniqueAdminData;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.NoValidTokenFound;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ForgotPwService {

    void forgotPassword(String email);

    void updatePassword(String email, String newPassword);

    String generateUniqueToken(String admin,String token) throws NoSuchAlgorithmException;

    Admin_user checkUserAll(Admin_user admin_user);

    void ProcessForgotPWFormException(String token) throws CannotMapUniqueAdminData, NoValidTokenFound;

    List<Admin_user> getListOfAdminUser(String  resetPasswordToken);
}
