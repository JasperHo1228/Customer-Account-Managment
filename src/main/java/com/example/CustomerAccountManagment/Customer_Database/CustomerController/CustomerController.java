package com.example.CustomerAccountManagment.Customer_Database.CustomerController;

import com.example.CustomerAccountManagment.Customer_Database.CustomerEntity.Customer;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.EmailPhoneNoDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerExceptionhandler.PhoneNoDuplicated;
import com.example.CustomerAccountManagment.Customer_Database.CustomerService.CustomerService;
import com.example.CustomerAccountManagment.adminUser_account.adminUserRepository.Admin_userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/customerData")
public class CustomerController {
    private CustomerService customerService;
    private Customer customer;
    @Autowired
    private Admin_userRepository adminUserRepository;

    public CustomerController(CustomerService customerService, Admin_userRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
        this.customerService = customerService;
    }


    //control which kind of error should be output
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping(path = "customers")
    public String listCustomers(Model model) {
        return Pagination(1,model);
    }
    @GetMapping(path="customers/Page/{PageNo}")
    public String Pagination(@PathVariable(value="PageNo") int PageNo, Model model){
        int page_num = 10;
        Page<Customer> page = customerService.findByPage(PageNo,page_num);
        List<Customer> customers_data = page.getContent();
        model.addAttribute("curr_Page" ,PageNo);
        model.addAttribute("total_Page",page.getTotalPages());
        model.addAttribute("total_item",page.getTotalElements());
        model.addAttribute("customers",customers_data);
        model.addAttribute("name",customerService.getProfileName());
        return "customerData/customers";
    }

    @GetMapping(path="searchCustomers")
        public String searchCustomers(Model model){
        model.addAttribute("name",customerService.getProfileName());
        model.addAttribute("all_customers",customerService.getAllCustomers());
        return "customerData/search_customers";
    }

    //create Customer information page
    @GetMapping(path = "customers/new")
    public String createCustomer(Model model, Customer customer) {
        this.customer = customer;
        model.addAttribute("customer", customer);
        model.addAttribute("name",customerService.getProfileName());
        return "customerData/create_customers";
    }

    //add new Customer on Customer information page
    @PostMapping(path = "customers/new")
    public String saveCustomer(@Valid @ModelAttribute(value = "customer") Customer customer ,Model model){
           String email = customer.getEmail();
           String phoneNo = customer.getPhoneNo();

           try{
               Customer checkCreate = customerService.checkCreateExisted(email,phoneNo);
               customerService.saveCustomer(customer);
               model.addAttribute("success", "Customer has been created successfully!");
           }
           catch (EmailPhoneNoDuplicated e){
               model.addAttribute("error", "Email and Phone Number Existed!");
           }
           catch (EmailDuplicated e){
               model.addAttribute("error", "Email Existed");
           }
           catch (PhoneNoDuplicated e){
               model.addAttribute("error", "Phone Number Existed");
           }

         return "customerData/create_customers";
    }

    //show to exist customer data
    @GetMapping(path = "customers/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model){
        model.addAttribute("customer",customerService.getIdCustomer(id));
        model.addAttribute("name",customerService.getProfileName());
        return "customerData/update_customers";
    }

    //update
    @PutMapping(path = "customers/{id}")
    public String update_save_Customer(@Valid @PathVariable Long id,
                @ModelAttribute(value = "customer") Customer customer,Model model) {

                //get the existCustomer from database by id
                Customer existCustomer = customerService.getIdCustomer(id);
                existCustomer.setId(id);
                existCustomer.setFirstName(customer.getFirstName());
                existCustomer.setLastName(customer.getLastName());
                existCustomer.setEmail(customer.getEmail());
                existCustomer.setPhoneNo(customer.getPhoneNo());
                String email = existCustomer.getEmail();
                String phone_no = existCustomer.getPhoneNo();
                //avoid data duplicate
                try{
                     Customer check = customerService.checkDataDuplicated(existCustomer, email, phone_no);
                    //update
                    customerService.updateCustomer(existCustomer);
                    model.addAttribute("success", "Updated successfully!");
                }

                catch (EmailPhoneNoDuplicated e){
                    model.addAttribute("error", "Email and Phone Number Existed!");
                }
                catch (EmailDuplicated e){
                    model.addAttribute("error", "Email Existed");
                }
                catch (PhoneNoDuplicated e){
                    model.addAttribute("error", "Phone Number Existed");
                }

                    return "customerData/update_customers";
               }

    @GetMapping(path = "customers/{id}")
    public String deleteCustomer(@PathVariable Long id,Customer customer){
          //delete
          customerService.deleteCustomer(id);
          return "redirect:/customerData/customers";
    }
}
