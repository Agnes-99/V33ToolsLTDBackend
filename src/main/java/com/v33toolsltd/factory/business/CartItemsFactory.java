package com.v33toolsltd.factory.business;

import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.domain.business.Product;
import com.v33toolsltd.util.Helper;

public class CartItemsFactory {

    private CartItemsFactory() {}

    public static CartItems createCartItem(Product product, int quantity) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("A valid Product is required for a cart item");
        }

        if (!Helper.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        return new CartItems.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .build();
    }
}