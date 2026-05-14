package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.Order;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IOrderService extends IService<Order, Long> {

    List<Order> findAll();

    List<Order> findOrdersByCustomerId(Long customerId);

    Order checkout(Cart cart);

    Order getOrderById(Long id);
}