package com.example.CustomerAccountManagment.adminUser_account.adminUserRepository;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Role_Repository extends JpaRepository<Role, Long> {

    @Query(value = "Select id, name FROM role WHERE id IN" +
            "(SELECT role_id FROM admin_roles WHERE admin_id IN " +
            "(SELECT id FROM admin_user WHERE activate_account = 0 AND Verification_Expired_Time < NOW()))",
              nativeQuery = true)
    List<Role> deleteRolesByAdminIn();

}
