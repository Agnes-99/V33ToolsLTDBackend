package com.v33toolsltd.service.users;

import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.repository.users.CustomerRepository;
import com.v33toolsltd.service.generic.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CustomerService(CustomerRepository repository, AddressService addressService) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Customer create(Customer customer) {
        customer = new Customer.Builder()
                .copy(customer)
                .setPassword(passwordEncoder.encode(customer.getPassword()))
                .build();
        return repository.save(customer);
    }

    @Override
    public Customer read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        return this.repository.save(customer);
    }

    @Override
    public boolean delete(Long id) {
        return this.repository.findById(id).map(customer -> {
            this.repository.delete(customer);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Customer> getAll() {
        return this.repository.findAll();
    }


}
