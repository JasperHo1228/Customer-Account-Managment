package com.example.CustomerAccountManagment.Customer_Database.CustomerEntity;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="customers")
@EnableAutoConfiguration
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name must not be empty")
    @Pattern(regexp = "^[A-Za-z\s]+$")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "last name must not be empty")
    @Pattern(regexp = "^[A-Za-z\s]+$")
    private String lastName;

    @Column(name="email")
    @NotBlank(message = "email must not be empty")
    @Email(regexp = "^[a-zA-Z0-9]+([._%+-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") //this is doing email validation
    private String email;

    @Column(name="phone_no")
    private String phoneNo;

    public Customer(){}

    public Customer(Long id, String firstName, String lastName, String email,String phoneNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
