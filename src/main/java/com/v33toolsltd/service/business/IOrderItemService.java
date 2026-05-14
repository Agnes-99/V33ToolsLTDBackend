package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.OrderItem;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IOrderItemService extends IService<OrderItem, Long> {

    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    List<OrderItem> findAll();
}