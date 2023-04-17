package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl;


import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.LoginAdminCheck.NullAdminUser;
import com.example.CustomerAccountManagment.adminUser_account.AdminExceptionHandler.LoginAdminCheck.TokenNonExpiredException;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Role;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.AdminDataService;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import javax.transaction.Transactional;
import java.sql.Timestamp;

import java.util.Collection;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class AdminDatampl implements AdminDataService{

    private final Admin_userRepository adminUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws NullAdminUser,TokenNonExpiredException {
        Admin_user adminUser = adminUserRepository.findByEmail(username);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        //you still not register or password get wrong
        if(adminUser == null){
            throw new NullAdminUser("Invalid username or password");
        }

       //you still have time to verify your token in email
       else if(adminUser != null && adminUser.getEnable() == 0 && !adminUser.getTimestamp().before(currentTime)){
            throw new TokenNonExpiredException("Please go to email to activate your account!");
        }

        return new User(adminUser.getEmail(),
                adminUser.getPassword(),
                mapRolesToAuthorities(adminUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Admin_user getAdminData(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Admin_user adminUser = adminUserRepository.findByEmail(email);
        return adminUser;
    }


    //Because Login is strongly related Security Spring boot therefore,
    // you should also update the SecuritySpring part
    public void updateSecurity(Admin_user admin_user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(admin_user.getEmail(),
                                                                          auth.getCredentials(),
                                                                          loadUserByUsername(admin_user.getEmail())
                                                                                  .getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
    @Override
    public String profileUserName() {
        return "Hi, "+getAdminData().getFirstName()+"!";
    }

    //check is it existed or not
    @Override
    public Admin_user checkUserAll(Admin_user admin_user){
        return adminUserRepository.findByEmail(admin_user.getEmail());
    }
    //update Admin data
    @Override
    public Admin_user updateAdmin(Admin_user admin_user){
        return adminUserRepository.save(admin_user);
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword){
        Admin_user admin_user = adminUserRepository.findByEmail(email);
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        admin_user.setPassword(encodePassword);
        adminUserRepository.save(admin_user);
    }
    //by using bCryPasswordEncoder.matches it can help you to match the hash in database
    @Override
    public boolean encodeOldPWCheck(String oldPassword,Admin_user adminUser){
        return bCryptPasswordEncoder.matches(oldPassword, adminUser.getPassword());
    }

    @Override
    public boolean checkIsUpdate(Admin_user existingAdminUser) {
        boolean isValid;
            Admin_user adminUser = checkUserAll(existingAdminUser);
            if (adminUser != null && !adminUser.getId().equals(existingAdminUser.getId())) {
                isValid = false;
            }
            else {
                isValid = true;
                updateAdmin(existingAdminUser);
                updateSecurity(existingAdminUser);
            }

        return isValid;
    }
}
