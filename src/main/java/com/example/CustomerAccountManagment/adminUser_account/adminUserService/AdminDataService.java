package com.example.CustomerAccountManagment.adminUser_account.adminUserService;


import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.AccountAlreadyRegistratied;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.RetypePasswordNotMatch;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.TokenStillNotExpired;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserdto.adminUserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface AdminDataService extends UserDetailsService {
    Admin_user getAdminData();
    Admin_user checkUserAll (Admin_user admin_user);
    Admin_user updateAdmin(Admin_user admin_user);
    void updateSecurity(Admin_user admin_user);
    String profileUserName();
    void updatePassword(String email, String newPassword);
    boolean encodeOldPWCheck(String oldPassword, Admin_user adminUser);
    boolean checkIsUpdate(Admin_user existingAdminUser);

}
