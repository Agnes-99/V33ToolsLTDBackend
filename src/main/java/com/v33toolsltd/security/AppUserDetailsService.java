package com.v33toolsltd.security;


import com.v33toolsltd.domain.users.Admin;
import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.domain.users.Manager;
import com.v33toolsltd.repository.users.AdminRepository;
import com.v33toolsltd.repository.users.CustomerRepository;
import com.v33toolsltd.repository.users.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public AppUserDetailsService(AdminRepository adminRepository,
                                 CustomerRepository customerRepository,
                                 ManagerRepository managerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Check Admin first
        Admin admin = adminRepository.findByEmailAddress(email).orElse(null);
        if(admin != null) {
            return new AppUserDetails(admin.getId(), admin.getEmailAddress(), admin.getPassword(), "ROLE_ADMIN");
        }

        // Check Customer
        Customer customer = customerRepository.findByEmailAddress(email).orElse(null);
        if(customer != null) {
            return new AppUserDetails(customer.getId(), customer.getEmailAddress(), customer.getPassword(), "ROLE_CUSTOMER");
        }

        // Check Manager
        Manager manager = managerRepository.findByEmailAddress(email).orElse(null);
        if(manager != null) {
            return new AppUserDetails(manager.getId(), manager.getEmailAddress(), manager.getPassword(), "ROLE_MANAGER");
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}