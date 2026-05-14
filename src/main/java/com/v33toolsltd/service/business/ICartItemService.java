package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface ICartItemService extends IService<CartItems, Long> {

    List<CartItems> findByCartId(Long cartId);

    List<CartItems> findAll();

    CartItems updateQuantity(Long cartItemId, int quantity);

    void deleteByCartId(Long cartId);
}