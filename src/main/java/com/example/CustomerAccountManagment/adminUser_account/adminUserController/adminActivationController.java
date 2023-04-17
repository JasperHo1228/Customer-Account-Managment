package com.example.CustomerAccountManagment.adminUser_account.adminUserController;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.AccountAlreadyActivatedException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.InvalidVerificationTokenException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.VerificationTokenExpiredException;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.CheckActivateService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class adminActivationController {

    private final CheckActivateService checkActivateService;

    private final ConfirmationTokenService confirmationTokenService;


    public adminActivationController(CheckActivateService checkActivateService, ConfirmationTokenService confirmationTokenService) {
        this.checkActivateService = checkActivateService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("/cms-admin-activation")
    public String activation_account(@RequestParam("token") String token, Model model){

        try{
        checkActivateService.activateAccount(token);
        model.addAttribute("pop_up_message","Your account is activated");
        }
        //There is no Token in your account
        catch (InvalidVerificationTokenException e){
            model.addAttribute("pop_up_message","Your verification token is invalid");
        }
        //Your account has been expired
        catch (VerificationTokenExpiredException e){
            model.addAttribute("pop_up_message","Your verification has Expired");
        }
        //Your account has been activated
        catch(AccountAlreadyActivatedException e){
            model.addAttribute("pop_up_message","This account is activated already");
        }

        return "adminData/verification_admin_user/activation";
    }

}
