package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


//The method name should be same as you entity class name
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    ConfirmationToken findByToken(String token);
    ConfirmationToken findByAdminUser(Admin_user adminUser);

    @Query(value= "Select id, expired_at, token, admin_user_id From confirmation_token " +
            "WHERE expired_at < NOW()", nativeQuery = true)
    List<ConfirmationToken> deleteToken();

}