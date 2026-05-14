package com.v33toolsltd.service.generic;

import com.v33toolsltd.domain.generic.Address;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IAddressService extends IService<Address, Integer> {

    List<Address> getAll();
    List<Address> findByCustomerId(Long customerId);
}