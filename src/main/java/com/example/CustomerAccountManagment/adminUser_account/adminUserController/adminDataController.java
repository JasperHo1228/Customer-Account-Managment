package com.example.CustomerAccountManagment.adminUser_account.adminUserController;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.AdminDataService;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

@Controller
public class adminDataController {

    private AdminDataService adminDataService;


    public adminDataController(AdminDataService adminuserService) {
        this.adminDataService = adminuserService;
    }


    @GetMapping("/welcome-cms_home_page")
    public String home_page(Model model){
        //I want to display admin first name
        String firstName = adminDataService.getAdminData().getFirstName();
        model.addAttribute("name",adminDataService.profileUserName());
        model.addAttribute("firstName",firstName);
        return "adminData/admin_login_successful/welcome_page";
    }

    @GetMapping("/adminUser/myProfile")
    public String myProfile(Model model){
        Admin_user admin_user = adminDataService.getAdminData();
        model.addAttribute("adminUser",admin_user);
        Optional<Role> role = adminDataService.getAdminData().getRoles().stream().findFirst();
        model.addAttribute("firstName",admin_user.getFirstName());
        model.addAttribute("lastName",admin_user.getLastName());
        model.addAttribute("email",admin_user.getEmail());
        String position_title = role != null? role.get().getName() : "";
        model.addAttribute("role",position_title);
        model.addAttribute("name",adminDataService.profileUserName());
        return "adminData/admin_login_successful/myProfile";
    }

    @GetMapping("/adminUser/myProfile/updatePersonalData")
    public String UpdateAdmin(Model model){
        Admin_user admin_user = adminDataService.getAdminData();
        model.addAttribute("adminUser",admin_user);
        model.addAttribute("name",adminDataService.profileUserName());
        return "adminData/admin_login_successful/adminUserUpdate";
    }

    @PutMapping("/adminUser/myProfile/updatePersonalData/successfully")
    public String ProcessUpdateAdmin(Admin_user admin_user,Model model, RedirectAttributes redirectAttributes) {
        Admin_user existingAdminUser = adminDataService.getAdminData();
        existingAdminUser.setId(admin_user.getId());
        existingAdminUser.setFirstName(admin_user.getFirstName());
        existingAdminUser.setLastName(admin_user.getLastName());
        existingAdminUser.setEmail(admin_user.getEmail());

        //this can update your name instantly on your profile
        model.addAttribute("name","Hi, "+existingAdminUser.getFirstName()+"!");
        model.addAttribute("adminUser", existingAdminUser);
        boolean isUpdated = adminDataService.checkIsUpdate(existingAdminUser);
        redirectAttributes.addFlashAttribute(isUpdated ? "message" : "error",
                                             isUpdated ? "Your profile has been updated successfully." : "Email existed!");

        return "redirect:/adminUser/myProfile/updatePersonalData";
    }

        @GetMapping("/myProfile/changePassword")
        public String changePassword(Model model){
            Admin_user admin_user = adminDataService.getAdminData();
            model.addAttribute("adminUser",admin_user);
            model.addAttribute("name",adminDataService.profileUserName());
            return "adminData/admin_login_successful/ChangePassword";
        }

        @PutMapping("/myProfile/changePassword/successfully")
        public String changePasswordProcess(Model model, RedirectAttributes redirectAttributes, Admin_user adminUser, HttpServletRequest request){
            model.addAttribute("name",adminDataService.profileUserName());
            Admin_user existingAdminUser = adminDataService.getAdminData();
            String oldPassword = request.getParameter("old_password");
            String newPassword = request.getParameter("new_password");
            boolean isValid = adminDataService.encodeOldPWCheck(oldPassword,existingAdminUser);

            //Check old password is valid or not
            if(!isValid){
                redirectAttributes.addFlashAttribute("errorMsg", "Old Password Not Match!");
            }
           else{
            adminDataService.updatePassword(existingAdminUser.getEmail(), newPassword);
            redirectAttributes.addFlashAttribute("messageSuccessfully","New Password Set Up Successfully");
           }
            return "redirect:/myProfile/changePassword";
        }

}
