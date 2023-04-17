package com.example.CustomerAccountManagment.adminUser_account.adminUserService;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.AccountAlreadyRegistratied;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.EmptyFieldError;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.RetypePasswordNotMatch;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.TokenStillNotExpired;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserdto.adminUserRegistrationDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

public interface AdminRegisterService {

    Admin_user register(adminUserRegistrationDto registrationDto);

    Admin_user save(Admin_user admin_user);

    Admin_user checkUserAll (Admin_user admin_user);

    void deleteAdmin(Long id);

}
