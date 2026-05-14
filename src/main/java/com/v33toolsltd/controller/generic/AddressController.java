package com.v33toolsltd.controller.generic;


import com.v33toolsltd.domain.generic.Address;
import com.v33toolsltd.service.generic.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final IAddressService service;

    @Autowired
    public AddressController(IAddressService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Address create(@RequestBody Address address) {
        return service.create(address);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public Address read(@PathVariable Integer id) {
        return service.read(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public Address update(@RequestBody Address address) {
        return service.update(address);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public boolean delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<Address> getAll() {
        return service.getAll();
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<Address> getByCustomerId(@PathVariable Long customerId) {
        return service.findByCustomerId(customerId);
    }
}
