package com.v33toolsltd.domain.business;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    protected OrderItem() {}

    private OrderItem(Builder builder) {

        if (builder.product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (builder.quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    // ======================================================
    // ORDER CONTROLLED RELATIONSHIP
    // ======================================================
    void setOrder(Order order) {
        this.order = order;
    }

    // ======================================================
    // BUSINESS LOGIC
    // ======================================================
    public double getItemTotal() {
        if (product == null) {
            return 0;
        }

        // FIX: NO null check on double (this caused your error)
        return product.getPrice() * quantity;
    }

    // ======================================================
    // GETTERS
    // ======================================================
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // ======================================================
    // SETTERS (controlled)
    // ======================================================
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        this.quantity = quantity;
    }

    // ======================================================
    // BUILDER
    // ======================================================
    public static class Builder {
        private Product product;
        private int quantity;

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                '}';
    }
}