package com.example.CustomerAccountManagment.adminUser_account.adminUserEntity;

import com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification.ConfirmationToken;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@EnableAutoConfiguration
@Table(name = "admin_user",uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}) })
public class Admin_user{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message = "Enter your First Name")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Enter your Last Name")
    private String lastName;
    @Column(name = "email")
    @NotBlank(message = "email must not be empty")
    @Email(regexp = "^[a-zA-Z0-9]+([._%+]?[a-zA-Z0-9]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "Not Valid format")
    private String email;
    @Column(name = "password")
    @NotBlank(message = "Enter your password")
    @Length(min = 8, message = "Password must include at least 8 characters")
    private String password;

    //Join admin_user table and role table together: admin_user_id & role_id
    //joinColumns = current entity to the join table
    //inverseJoinColumns = specify the columns that join the related entity to the join table
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_roles",
    joinColumns = @JoinColumn(name = "admin_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<Role> roles;
    @Column(name="activate_account")
    private int enable;
    //link to class ConfirmationToken
    //One admin user can have many token. Same concept ni ConfirmationToken class
    @OneToMany(mappedBy = "adminUser")
    private Collection<ConfirmationToken> confirmationToken;
    @Column(name="Verification_Expired_Time")
    private Timestamp timestamp;

    @Column(name="reset_password_token")
    private String resetPasswordToken;

    public Admin_user(){
    }

    public Admin_user(String firstName, String lastName, String email, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    public void isEnable(int enable){this.enable = enable;}
    public int getEnable(){
        return enable;
    }

    public Collection<ConfirmationToken> getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(Collection<ConfirmationToken> confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @Override
    public String toString() {
        return "Admin_user{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", enable=" + enable +
                ", confirmationToken=" + confirmationToken +
                ", timestamp=" + timestamp +
                ", resetPasswordToken='" + resetPasswordToken + '\'' +
                '}';
    }
}
