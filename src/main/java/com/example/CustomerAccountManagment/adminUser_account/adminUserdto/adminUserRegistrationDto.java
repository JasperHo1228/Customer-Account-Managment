package com.example.CustomerAccountManagment.adminUser_account.adminUserdto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


//This class is for registration page
public class adminUserRegistrationDto {
    @Pattern(regexp ="^[a-zA-Z\s]+$", message = "Alphabets Only")
    @NotBlank(message = "Enter your First Name")
    private String firstName;
    @Pattern(regexp ="^[a-zA-Z\s]+$", message = "Alphabets Only")
    @NotBlank(message = "Enter your Last Name")
    private String lastName;

    @Email(regexp = "^[a-zA-Z0-9]+([._%+-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "Not Valid email format")
    @NotBlank(message = "Enter your Email")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[%&$|!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Must contain at least one number and one uppercase and\n" +
            "lowercase letter and one special character, and at least 8 or more characters")
    @NotBlank(message = "Enter your password")
    private String password;

    @NotBlank(message = "Re-Enter your password")
    private String re_type_password;

    private int enable;

    //link to class ConfirmationToken

    private String confirmationToken;

    public adminUserRegistrationDto(){
    }

    public adminUserRegistrationDto(String firstName, String lastName, String email, String password, int enable){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enable = enable;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRe_type_password() {
        return re_type_password;
    }

    public void setRe_type_password(String re_type_password) {
        this.re_type_password = re_type_password;
    }

    public int isEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    @Override
    public String toString() {
        return "adminUserRegistrationDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rpassword='" + re_type_password + '\'' +
                ", enable=" + enable +
                ", confirmationToken='" + confirmationToken + '\'' +
                '}';
    }
}
