package com.example.CustomerAccountManagment.Customer_Database.CustomerService;

import com.example.CustomerAccountManagment.Customer_Database.CustomerEntity.Customer;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailPhoneNoDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.PhoneNoDuplicated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer getIdCustomer(Long id);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long id);

    Page<Customer> findByPage(int PageNo, int totalPage);

   String getProfileName();

   Customer checkDataDuplicated(Customer customer,String email, String phone_no) throws EmailPhoneNoDuplicated,
           EmailDuplicated, PhoneNoDuplicated;

   Customer findByEmail(String email);

   Customer findByPhoneNo(String phone_no);

   Customer checkCreateExisted(String email, String phone_no) throws EmailPhoneNoDuplicated,
    EmailDuplicated, PhoneNoDuplicated;
}
