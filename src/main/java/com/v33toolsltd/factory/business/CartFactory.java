package com.v33toolsltd.factory.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.domain.users.Customer;

import java.util.List;

public class CartFactory {

    private CartFactory() {}

    public static Cart createCart(Customer customer, List<CartItems> items) {
        // Using a basic null check as Customer is an object, not a primitive/string
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Valid Customer is required to create a cart");
        }

        Cart cart = new Cart.Builder()
                .setCustomer(customer)
                .build();

        if (items != null && !items.isEmpty()) {
            for (CartItems item : items) {
                // aggregate root handles the bidirectional mapping (item.setCart(cart))
                cart.addItem(item); 
            }
        }

        return cart;
    }
}