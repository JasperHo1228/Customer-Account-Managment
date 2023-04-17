package com.example.CustomerAccountManagment.Customer_Database.CustomerRepository;

import com.example.CustomerAccountManagment.Customer_Database.CustomerEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);

    Customer findByPhoneNo(String phone_no);

}
