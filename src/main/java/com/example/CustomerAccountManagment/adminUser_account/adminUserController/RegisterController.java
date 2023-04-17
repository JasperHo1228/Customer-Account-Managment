package com.example.CustomerAccountManagment.adminUser_account.adminUserController;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.AccountAlreadyRegistratied;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.EmptyFieldError;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.RetypePasswordNotMatch;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.TokenStillNotExpired;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.AdminRegisterService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationToken;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationTokenService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserdto.adminUserRegistrationDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Timestamp;

@Controller
public class RegisterController {

    private AdminRegisterService adminUserRegisterService;

    private ConfirmationTokenService confirmationTokenService;

    private final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    private BindingResult result;

    public RegisterController(AdminRegisterService adminUserRegisterService, ConfirmationTokenService confirmationTokenService) {
        this.adminUserRegisterService = adminUserRegisterService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @ModelAttribute("adminUser")
    public adminUserRegistrationDto adminUserRegistrationDto(){
        return new adminUserRegistrationDto();
    }

    //control which kind of error should be output
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/cms-admin-registration")
    public String showRegistration_form(){
        return "adminData/admin_login/registration";
    }

    @PostMapping("/cms-admin-registration")
    public String registerAccount(
            @Valid @ModelAttribute("adminUser")adminUserRegistrationDto registrationDto,
            BindingResult result,
            Admin_user user, Model model){

            Admin_user adminUser = adminUserRegisterService.checkUserAll(user);

        //detect duplicate account that has been activated
        if(adminUser != null && adminUser.getEnable() == 1) {
            result.addError(new FieldError("adminUser","email","This email has been used!"));
            return "adminData/admin_login/registration";
        }

        if(result.hasErrors()){
            return "adminData/admin_login/registration";
        }

        //re-type password should match to the password input box
        if(!registrationDto.getPassword().equals(registrationDto.getRe_type_password())){
            result.addError(new FieldError("adminUser","re_type_password","Password Does Not Match!"));
            return "adminData/admin_login/registration";
        }

        //if token is not expired then re-direct them back to login page
        if(adminUser!=null && adminUser.getEnable() == 0 && !adminUser.getTimestamp().before(currentTime)){
            model.addAttribute("message","Please activate your account from your email!");
            return "adminData/admin_login/login";
        }

        //delete token and account that has expired
        if(adminUser!=null && adminUser.getEnable() == 0 && adminUser.getTimestamp().before(currentTime)){
            ConfirmationToken token = confirmationTokenService.findByAdminUser(adminUser);
            confirmationTokenService.deleteToken(token.getId());
            adminUserRegisterService.deleteAdmin(adminUser.getId());
        }
        adminUserRegisterService.register(registrationDto);
        return "redirect:/cms-admin-registration?success";
    }

}
