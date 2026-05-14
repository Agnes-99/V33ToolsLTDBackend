package com.v33toolsltd.repository.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.repository.IRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends IRepository<Cart, Long> {
    // This correctly traverses Cart -> Customer -> Id
    Optional<Cart> findByCustomer_Id(Long customerId);
}