package com.v33toolsltd.factory.business;

import com.v33toolsltd.domain.business.OrderItem;
import com.v33toolsltd.domain.business.Product;

public class OrderItemFactory {

    private OrderItemFactory() {}

    public static OrderItem createOrderItem(Product product, int quantity) {

        return new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .build();
    }
}