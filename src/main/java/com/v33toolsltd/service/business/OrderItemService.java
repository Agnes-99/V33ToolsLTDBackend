package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.OrderItem;
import com.v33toolsltd.repository.business.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public boolean delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("OrderItem not found");
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        return repository.findByOrder_Id(orderId);
    }

    @Override
    public List<OrderItem> findAll() {
        return repository.findAll();
    }
}