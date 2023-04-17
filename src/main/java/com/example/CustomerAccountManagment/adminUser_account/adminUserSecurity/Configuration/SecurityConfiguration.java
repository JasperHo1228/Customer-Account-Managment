package com.example.CustomerAccountManagment.adminUser_account.adminUserSecurity.Configuration;



import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.example.CustomerAccountManagment.adminUser_account.adminUserService.AdminDataService;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private AdminDataService AdminUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(bCryptPasswordEncoder);
        auth.setUserDetailsService(AdminUserService);
        return auth;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                        "/cms-admin-registration**",
                        "/js/**",
                        "/cms_admin-forgot-password",
                        "/stylecss/login_css/**",
                        "/stylecss/**",
                        "/customerFunction-js/**","/cms_admin-sign-in","/login",
                        "/img/**","/cms-home**","/cms-admin-activation**","/cms_admin-Forgot_password-setPassword**").permitAll().antMatchers("/customerData")
                .hasRole("Admin").anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/cms_admin-sign-in").permitAll().defaultSuccessUrl("/welcome-cms_home_page")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/cms-home")
                .permitAll();
   }

}

