package com.v33toolsltd.repository.business;

import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.repository.IRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemsRepository extends IRepository<CartItems, Long> {
    // This correctly traverses CartItems -> Cart -> Id
    List<CartItems> findByCart_Id(Long cartId);
}