package com.example.CustomerAccountManagment.Customer_Database.CustomerServicempl;

import com.example.CustomerAccountManagment.Customer_Database.CustomerEntity.Customer;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailPhoneNoDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.PhoneNoDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerRepository.CustomerRepository;
import com.example.CustomerAccountManagment.Customer_Database.CustomerService.CustomerService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserEntity.Admin_user;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServicempl implements CustomerService {
    private CustomerRepository customerRepository;

    private Admin_userRepository adminUserRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getIdCustomer(Long id){
        return customerRepository.findById(id).get();
    }

    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> findByPage(int PageNo, int totalPage) {
        Pageable page = PageRequest.of(PageNo-1, totalPage);
        return customerRepository.findAll(page);
    }

    //get Admin User full name
    public String getProfileName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Admin_user adminUser = adminUserRepository.findByEmail(email);
        String firstName = adminUser.getFirstName();
        return "Hi, " + firstName+"!";
    }

    @Override
    public Customer checkDataDuplicated(Customer customer,String email, String phone_no) throws EmailPhoneNoDuplicated,
            EmailDuplicated, PhoneNoDuplicated{
        Customer existed_email = findByEmail(email);
        Customer existed_phone_no = findByPhoneNo(phone_no);
        Long updateCustomer = customer.getId();

        if(existed_email != null && existed_phone_no != null &&
           !existed_email.getId().equals(updateCustomer) && !existed_phone_no.getId().equals(updateCustomer)){
            throw new EmailPhoneNoDuplicated("Email and Phone Number Duplicated");
        }

       else if(existed_email != null  && !existed_email.getId().equals(updateCustomer)){
            throw new EmailDuplicated("Email Duplicated");
        }

       else if(existed_phone_no != null && !existed_phone_no.getId().equals(updateCustomer)){
            throw new PhoneNoDuplicated("Phone Number duplicated");
        }

        return customer;
    }



    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findByPhoneNo(String phone_no) {
        return customerRepository.findByPhoneNo(phone_no);
    }

    @Override
    public Customer checkCreateExisted(String email, String phone_no) throws EmailPhoneNoDuplicated, EmailDuplicated, PhoneNoDuplicated {
        Customer checkEmail = customerRepository.findByEmail(email);
        Customer checkPhone_no = customerRepository.findByPhoneNo(phone_no);
        if(checkEmail != null && checkPhone_no != null){
            throw new EmailPhoneNoDuplicated("Email and Phone Number Existed(Created Customer part)");
        }

        else if(checkEmail != null){
            throw new EmailDuplicated("Email Existed(Created Customer part)");
        }

        else if(checkPhone_no != null){
            throw new PhoneNoDuplicated("Phone Number Existed(Created Customer part)");
        }
        return null;
    }

}
