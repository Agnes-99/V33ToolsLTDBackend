package com.v33toolsltd.controller.user;


import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.dto.users.LoginResponse;
import com.v33toolsltd.factory.users.CustomerFactory;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.users.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerService service;

    @Autowired
    public CustomerController(ICustomerService service) {
        this.service = service;
    }

    private LoginResponse toDto(Customer c) {
        return new LoginResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getPhoneNumber(),
                c.getEmailAddress(),
                c.getRole()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<LoginResponse> create(@RequestBody Customer customer) {
        Customer newCustomer = CustomerFactory.createCustomer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmailAddress(),
                customer.getPassword()
        );

        Customer created = service.create(newCustomer);
        return ResponseEntity.ok(toDto(created));
    }

    @GetMapping("/read/{customerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoginResponse> read(@PathVariable Long customerId) {
        Customer customer = service.read(customerId);
        return (customer != null)
                ? ResponseEntity.ok(toDto(customer))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoginResponse> update(@RequestBody Customer customer, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        if (!userDetails.getId().equals(customer.getId()) &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }

        Customer updated = service.update(customer);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> delete(@PathVariable Long customerId, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        if (!userDetails.getId().equals(customerId) &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }

        boolean deleted = service.delete(customerId);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoginResponse>> getAll() {
        List<LoginResponse> dtos = service.getAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
