package com.v33toolsltd.service.generic;

import com.v33toolsltd.domain.generic.Address;
import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.repository.generic.AddressRepository;
import com.v33toolsltd.repository.users.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    private final AddressRepository repository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AddressService(AddressRepository repository, CustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Address create(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }

        if (address.getCustomer() == null || address.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Address must be linked to a valid customer");
        }

        Customer managedCustomer = customerRepository.findById(address.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        Address managedAddress = new Address.Builder()
                .copy(address)
                .setCustomer(managedCustomer)
                .build();

        return repository.save(managedAddress);
    }

    @Override
    public Address read(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Address update(Address address) {
        if (address == null || address.getId() == null) {
            throw new IllegalArgumentException("Address or Address ID cannot be null");
        }

        if (!repository.existsById(address.getId())) {
            return null;
        }

        return repository.save(address);
    }

    @Override
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Address> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Address> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

}
