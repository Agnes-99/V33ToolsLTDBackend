package com.v33toolsltd.domain.business;

import com.v33toolsltd.domain.users.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDate orderDate;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {}

    private Order(Builder builder) {

        if (builder.customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        this.customer = builder.customer;
        this.orderDate = builder.orderDate != null
                ? builder.orderDate
                : LocalDate.now();

        if (builder.orderItems != null) {
            builder.orderItems.forEach(this::addOrderItem);
        }
    }

    // ======================================================
    // DOMAIN BEHAVIOUR (Order is IMMUTABLE after creation)
    // ======================================================

    public void addOrderItem(OrderItem item) {
        validateItem(item);

        item.setOrder(this);
        orderItems.add(item);
    }

    public void removeOrderItemByProductId(Long productId) {

        if (productId == null) return;

        orderItems.removeIf(item -> {
            boolean match = item.getProduct() != null &&
                    productId.equals(item.getProduct().getId());

            if (match) {
                item.setOrder(null);
            }

            return match;
        });
    }

    // ======================================================
    // TOTAL (computed safely)
    // ======================================================
    public double getTotalAmount() {
        return orderItems.stream()
                .mapToDouble(OrderItem::getItemTotal)
                .sum();
    }

    // ======================================================
    // VALIDATION
    // ======================================================
    private void validateItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("OrderItem cannot be null");
        }

        if (item.getProduct() == null || item.getProduct().getId() == null) {
            throw new IllegalArgumentException("Invalid product in order item");
        }

        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
    }

    // ======================================================
    // GETTERS
    // ======================================================
    public Long getId() { return id; }

    public Customer getCustomer() { return customer; }

    public LocalDate getOrderDate() { return orderDate; }

    public List<OrderItem> getOrderItems() { return orderItems; }

    public Long getCustomerId() {
        return customer != null ? customer.getId() : null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + getCustomerId() +
                ", orderDate=" + orderDate +
                ", items=" + orderItems.size() +
                ", total=" + getTotalAmount() +
                '}';
    }

    // ======================================================
    // BUILDER
    // ======================================================
    public static class Builder {
        private Customer customer;
        private LocalDate orderDate;
        private List<OrderItem> orderItems;

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}