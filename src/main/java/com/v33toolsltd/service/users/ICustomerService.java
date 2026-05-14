package com.v33toolsltd.service.users;

import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface ICustomerService extends IService<Customer, Long> {
    List<Customer> getAll();
}

