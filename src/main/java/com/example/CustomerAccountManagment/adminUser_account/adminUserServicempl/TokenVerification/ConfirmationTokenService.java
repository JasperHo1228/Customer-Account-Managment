package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    //DI Bean created
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    //save token
    @Transactional
    public void save(String token, Admin_user admin_user){
        ConfirmationToken verification_token = new ConfirmationToken(token, admin_user);
        //time to judge
        verification_token.setExpiredAt(expireDate(15));
        confirmationTokenRepository.save(verification_token);
    }


    public void deleteExpiredToken(){
        confirmationTokenRepository.deleteAllInBatch(confirmationTokenRepository.deleteToken());
    }

    //Expired date calculator
    public Timestamp expireDate(int expireDateMin){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expireDateMin);
        return new Timestamp((cal.getTime().getTime()));
    }

   @Transactional
   public ConfirmationToken findByToken(String token){
       return confirmationTokenRepository.findByToken(token);
   }
//    find user
    @Transactional
    public ConfirmationToken findByAdminUser(Admin_user admin_user){
        return confirmationTokenRepository.findByAdminUser(admin_user);
    }
    @Transactional
    public void deleteToken(Long id){confirmationTokenRepository.deleteById(id);}


}
