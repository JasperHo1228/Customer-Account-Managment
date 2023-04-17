package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.CannotMapUniqueAdminData;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ForgotPasswordException.NoValidTokenFound;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.ForgotPwService;
import com.example.CustomerAccountManagment.adminUser_account.EmailService.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ForgotPwServicempl implements ForgotPwService {

   private final Admin_userRepository adminUserRepository;

   private final EmailService emailService;

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void forgotPassword(String email){
        Admin_user adminUser = adminUserRepository.findByEmail(email);
        try{
            String token = UUID.randomUUID().toString();
            String crytoToken = generateUniqueToken(email, token);
            adminUser.setResetPasswordToken(crytoToken);
            adminUserRepository.save(adminUser);
            emailService.forgetPasswordEmail(adminUser);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Helper method to generate a unique token using SHA-256 cryptographic hash function
    public String generateUniqueToken(String admin,String token) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String uniqueString = token + admin + System.currentTimeMillis() + Math.random();
        byte[] hashBytes = md.digest(uniqueString.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    }

//    @Override
//    public Admin_user getToken(String resetPasswordToken){
//        return adminUserRepository.findByResetPasswordToken(resetPasswordToken);
//    }

    @Override
    public List<Admin_user> getListOfAdminUser(String resetPasswordToken) {
        return adminUserRepository.findByResetPasswordToken(resetPasswordToken);
    }

    @Transactional
    public void updatePassword(String email, String newPassword){
        Admin_user admin_user = adminUserRepository.findByEmail(email);
        admin_user.setResetPasswordToken(null);
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        admin_user.setPassword(encodePassword);
        adminUserRepository.save(admin_user);
    }

    public Admin_user checkUserAll(Admin_user admin_user){
        return adminUserRepository.findByEmail(admin_user.getEmail());
    }

    @Override
    public void ProcessForgotPWFormException(String token) throws CannotMapUniqueAdminData, NoValidTokenFound {
        List<Admin_user> admin_users = getListOfAdminUser(token);

        if(admin_users == null || admin_users.isEmpty()){
            throw new NoValidTokenFound("The token cannot be found in admin table");
        }
        if(admin_users != null && admin_users.size()>1){
            throw new CannotMapUniqueAdminData("More then one null data are found from admin table",1);
        }
    }

}
