package com.v33toolsltd.factory.business;

import com.v33toolsltd.domain.business.Order;
import com.v33toolsltd.domain.business.OrderItem;
import com.v33toolsltd.domain.users.Customer;

import java.time.LocalDate;
import java.util.List;

public class OrderFactory {

    private OrderFactory() {}

    public static Order createOrder(Customer customer,
                                    List<OrderItem> items,
                                    LocalDate date) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Order order = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(date)
                .build();

        if (items != null) {
            for (OrderItem item : items) {
                order.addOrderItem(item);
            }
        }

        return order;
    }
}