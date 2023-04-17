package com.example.CustomerAccountManagment.adminUser_account.adminUserServicempl.TokenVerification;

import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import javax.persistence.*;
import java.sql.Timestamp;

//Database table information
@Getter
@Setter
@Entity
@Table(name = "confirmation_token")
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String token;
    private Timestamp expiredAt;

    //many token can refer to on admin user because they maybe forget to verify from the email,
    //and then need to register again.
    @ManyToOne
    @JoinColumn(name = "admin_user_id")
    private Admin_user adminUser;


    public ConfirmationToken(String token,
                             Admin_user adminUser) {
        this.token = token;
        this.adminUser = adminUser;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Admin_user getAdmin_user() {
        return adminUser;
    }

    public void setAdmin_user(Admin_user adminUser) {
        this.adminUser = adminUser;
    }


    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiredAt=" + expiredAt +
                ", admin_user=" + adminUser +
                '}';
    }
}
