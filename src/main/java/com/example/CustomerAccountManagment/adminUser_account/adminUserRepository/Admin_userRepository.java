package com.example.CustomerAccountManagment.adminUser_account.adminUserRepository;

import com.example.CustomerAccountManagment.Customer_Database.CustomerEntity.Customer;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface Admin_userRepository extends JpaRepository<Admin_user,Long> {
   Admin_user findByEmail(String email);

   Admin_user findByTimestamp(Timestamp timestamp);
   @Query(value= "SELECT id, first_name, last_name, email, password, activate_account, " +
           "Verification_Expired_Time FROM admin_user WHERE " +
           "activate_account = 0 AND Verification_Expired_Time < NOW()", nativeQuery = true)
   List<Admin_user> findByenableAndTimestamp();
//   Admin_user findByResetPasswordToken(String token);

   List<Admin_user> findByResetPasswordToken(String token);
}
