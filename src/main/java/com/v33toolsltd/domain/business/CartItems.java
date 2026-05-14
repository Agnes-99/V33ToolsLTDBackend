package com.v33toolsltd.domain.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;

    @Column(nullable = false)
    private int quantity;

    protected CartItems() {}

    private CartItems(Builder builder) {
        if (builder.product == null) throw new IllegalArgumentException("Product cannot be null");
        if (builder.quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");

        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Cart getCart() { return cart; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        this.quantity = quantity;
    }

    public void incrementQuantity(int amount) {
        if (amount <= 0) return;
        this.quantity += amount;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getItemTotal() {
        return (product != null) ? product.getPrice() * quantity : 0.0;
    }

    // Builder
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

        public CartItems build() { return new CartItems(this); }
    }
}