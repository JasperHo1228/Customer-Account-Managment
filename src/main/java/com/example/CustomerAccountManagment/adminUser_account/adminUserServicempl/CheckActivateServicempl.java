package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl;

import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.AccountAlreadyActivatedException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.InvalidVerificationTokenException;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.ActivationACTokenCheck.VerificationTokenExpiredException;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Role_Repository;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.CheckActivateService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationToken;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
@Service
@AllArgsConstructor
public class CheckActivateServicempl implements CheckActivateService {
    private final Admin_userRepository adminUserRepository;

    private final Role_Repository role_repository;

    private final ConfirmationTokenService confirmationTokenService;

    private final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    //delete admin user account
    @Transactional
    public void deleteAdmin(Long id){
        adminUserRepository.deleteById(id);
    }


    //check the database and delete all the person who still not activate their account after 15 mintues
    @Override
    public void inactiveAdminExpired() {
        List<Role> savetmp = role_repository.deleteRolesByAdminIn();
        confirmationTokenService.deleteExpiredToken();
        adminUserRepository.deleteAllInBatch(adminUserRepository.findByenableAndTimestamp());
        role_repository.deleteAllInBatch(savetmp);
    }

    //save adminUser
    @Override
    public Admin_user save(Admin_user admin_user){
        return adminUserRepository.save(admin_user);
    }

    //check does the account activated or not
    @Override
    public void activateAccount(String token) throws InvalidVerificationTokenException, VerificationTokenExpiredException, AccountAlreadyActivatedException {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken == null){
            throw new InvalidVerificationTokenException("Your verification token is invalid");
        }
        else{
            Admin_user admin_user = confirmationToken.getAdmin_user();
            //still not activate
            if(admin_user.getEnable()==0){
                //check expired or not
                if(confirmationToken.getExpiredAt().before(currentTime)){
                    throw new VerificationTokenExpiredException("Your verification has Expired");
                }

                else{
                    admin_user.isEnable(1);
                    save(admin_user);
                }
            }
            else{
                throw new AccountAlreadyActivatedException("This account is activated already");
            }
        }
    }
}
