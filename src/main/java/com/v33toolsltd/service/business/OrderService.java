package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.domain.business.Order;
import com.v33toolsltd.domain.business.OrderItem;
import com.v33toolsltd.repository.business.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // ======================================================
    // CRUD
    // ======================================================

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order update(Order order) {
        return repository.save(order);
    }

    @Override
    public boolean delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return repository.findByCustomer_Id(customerId);
    }

    @Override
    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // ======================================================
    // CHECKOUT (FIXED)
    // ======================================================

    @Override
    @Transactional
    public Order checkout(Cart cart) {

        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order.Builder()
                .setCustomer(cart.getCustomer())
                .setOrderDate(LocalDate.now())
                .build();

        for (CartItems cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem.Builder()
                    .setProduct(cartItem.getProduct())
                    .setQuantity(cartItem.getQuantity())
                    .build();

            order.addOrderItem(orderItem);
        }

        Order saved = repository.save(order);

        // clear cart after checkout
        cart.getItems().clear();

        return saved;
    }
}