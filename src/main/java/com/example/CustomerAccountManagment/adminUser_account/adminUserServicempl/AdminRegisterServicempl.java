package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.AccountAlreadyRegistratied;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.EmptyFieldError;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.RetypePasswordNotMatch;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.RegistrationCheck.TokenStillNotExpired;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.AdminRegisterService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationToken;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationTokenService;
import com.example.CustomerAccountManagment.adminUser_account.EmailService.EmailService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserdto.adminUserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminRegisterServicempl implements AdminRegisterService {
   private final Admin_userRepository adminUserRepository;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;

   private final ConfirmationTokenService confirmationTokenService;

   private final EmailService emailService;

   private final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    @Override
    public Admin_user save(Admin_user admin_user){
        return adminUserRepository.save(admin_user);
    }

    @Override
    public Admin_user checkUserAll(Admin_user admin_user){
        return adminUserRepository.findByEmail(admin_user.getEmail());
    }
    //If toke is expired delete the token
    @Transactional
    public void deleteAdmin(Long id){
        adminUserRepository.deleteById(id);
    }

    @Override
    public Admin_user register(adminUserRegistrationDto registrarDto){
        Admin_user adminUser = new Admin_user(registrarDto.getFirstName(),registrarDto.getLastName(),
                registrarDto.getEmail(),
                Arrays.asList(new Role("Admin")));
        String encodePassword = bCryptPasswordEncoder.encode(registrarDto.getPassword());
        adminUser.setPassword(encodePassword);
        adminUser.isEnable(0);
        adminUser.setTimestamp(confirmationTokenService.expireDate(15));
        Optional<Admin_user> saved = Optional.of(save(adminUser));

        saved.ifPresent(admin_user -> {

            try{
                // TODO: Send confirmation token
                String token = UUID.randomUUID().toString();

                confirmationTokenService.save(token,saved.get());

                //TODO: Send email
                emailService.sendEmail(admin_user);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        return saved.get();
    }

}
