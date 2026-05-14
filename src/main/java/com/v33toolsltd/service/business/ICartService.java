package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface ICartService extends IService<Cart, Long> {

    List<Cart> getAll();

    Cart getCartByCustomer(Long customerId);

    Cart createForCustomer(Long customerId);

    Cart addItem(Long customerId, Long productId, int quantity);

    Cart updateItemQuantity(Long customerId, Long productId, int quantity);

    Cart removeItem(Long customerId, Long productId);
}