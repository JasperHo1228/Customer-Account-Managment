package com.example.CustomerAccountManagment.adminUser_account.adminUserController;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.CannotMapUniqueAdminData;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.NoValidTokenFound;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.ForgotPwService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ForgotPWController {
    private ForgotPwService forgotPwService;
    private String email;

    public ForgotPWController(ForgotPwService forgotPwService) {
        this.forgotPwService= forgotPwService;
    }

    @GetMapping("/cms_admin-forgot-password")
    public String forgot_password_form(){
        return "adminData/admin_login/ForgetPassword";
    }

    @PostMapping("/cms_admin-forgot-password")
    public String forgot_password_form_process(@ModelAttribute Admin_user adminUser, Model model) {
          Admin_user admin_user = forgotPwService.checkUserAll(adminUser);
        if (admin_user == null) {
            model.addAttribute("message", "User does not exist");
            return "adminData/admin_login/ForgetPassword";
        }
        else{
            String admin = adminUser.getEmail();
            forgotPwService.forgotPassword(admin);
        }
        return "redirect:/cms_admin-forgot-password?success";
    }

    //setPasswordPage
    @GetMapping("/cms_admin-Forgot_password-setPassword")
    public String forget_setPassword(@Param("token") String token, Model model){
        model.addAttribute("token",token);
        try {
            forgotPwService.ProcessForgotPWFormException(token);
        }
        catch (CannotMapUniqueAdminData e){
            model.addAttribute("message", "You cannot reset the Password by using this link");
        }
        catch (NoValidTokenFound e){
            model.addAttribute("message", "You cannot reset the Password by using this link");
        }
        return "adminData/admin_login/setPassword";
    }

    //Progress Set Password this is creating
    @PostMapping("/cms_admin-Forgot_password-setPassword")
    public String send_forget_pw(Model model, HttpServletRequest request){

        //get it from thymeleaf name = token
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        List<Admin_user> admin_user = forgotPwService.getListOfAdminUser(token);

        //get email
        for(Admin_user adminUser:admin_user){
                 email = adminUser.getEmail();
                 break;
        }
        //check adminUser is existed or not
        if(!admin_user.isEmpty()) {
            forgotPwService.updatePassword(email, password);
            model.addAttribute("messageSuccessfully", "New Password Has Been Setup Successfully");
                 return "adminData/admin_login/setPassword";
        }
        else{
            model.addAttribute("message",
                               "You cannot reset the Password by using this link");}

        return "adminData/admin_login/setPassword";
    }

}
