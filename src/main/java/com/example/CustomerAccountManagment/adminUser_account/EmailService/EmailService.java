package com.example.CustomerAccountManagment.adminUser_account.EmailService;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationToken;
import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationTokenService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final ConfirmationTokenService confirmationTokenService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;


    public EmailService(ConfirmationTokenService confirmationTokenService,
                        TemplateEngine templateEngine,
                        JavaMailSender javaMailSender){

        this.confirmationTokenService = confirmationTokenService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;

    }

    public void sendEmail(Admin_user admin_user) throws MessagingException {
        ConfirmationToken confirmationToken = confirmationTokenService.findByAdminUser(admin_user);
        //check if the user whether he/she has a token or not
         String user_first_name = admin_user.getFirstName();
        if(confirmationToken != null){
            String token = confirmationToken.getToken();
            Context context = new Context();
            context.setVariable("title","Verify your email address within 15 minutes");
            context.setVariable("content",user_first_name);
            context.setVariable("link","http://localhost:8080/cms-admin-activation?token="+token);

            //create and HTML template and pass the variable to it
            String body = templateEngine.process("adminData/verification_admin_user/verification",context);

            //send email
            //how to detect the email send successfully
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setTo(admin_user.getEmail());
            helper.setSubject("Welcome to join Test-Company-2027!");
            helper.setText(body,true);
            javaMailSender.send(msg);
        }
    }

    public void forgetPasswordEmail(Admin_user adminUser) throws MessagingException {
        String user_first_name = adminUser.getFirstName();

        if(adminUser != null){
            String token = adminUser.getResetPasswordToken();
            //set link in the email
            Context context = new Context();
            context.setVariable("title", "Reset Your Password" );
            context.setVariable("content",user_first_name);
            context.setVariable("link", "http://localhost:8080/cms_admin-Forgot_password-setPassword?token="+token);

            //create and HTML template and pass the variable to it
            String body = templateEngine.process("adminData/verification_admin_user/PwForgot_email",context);

            //send email
            //how to detect the email send successfully
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setTo(adminUser.getEmail());
            helper.setSubject("Reset your Password (Test-Company-2027)");
            helper.setText(body,true);
            javaMailSender.send(msg);

        }
    }
}


