package com.example.CustomerAccountManagment.adminUser_account.adminUserController;

import com.example.CustomerAccountManagment.adminUser_account.adminUserService.CheckActivateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class adminLoginController {
    private CheckActivateService checkActivateService;

    public adminLoginController(CheckActivateService checkActivateService) {
        this.checkActivateService = checkActivateService;
    }

    @GetMapping("/cms-home")
    public String index(){
        checkActivateService.inactiveAdminExpired();
        return "adminData/admin_login/home";
    }

    @GetMapping("/cms_admin-sign-in")
    public String login(Model model){
        checkActivateService.inactiveAdminExpired();
        return "adminData/admin_login/login";
    }


    @GetMapping("/logout")
    public String logout(){
        return "adminData/admin_login/home";
    }

}
