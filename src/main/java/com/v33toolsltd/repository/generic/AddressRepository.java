package com.v33toolsltd.repository.generic;

import com.v33toolsltd.domain.generic.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByCustomerId(Long customerId);

}
