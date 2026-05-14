package com.v33toolsltd.domain.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.v33toolsltd.domain.users.Customer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "cart", uniqueConstraints = {@UniqueConstraint(columnNames = "customer_id")})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER  // CHANGED: Fixed the "Empty Cart" issue
    )
    @JsonManagedReference
    private List<CartItems> items = new ArrayList<>();

    protected Cart() {}

    private Cart(Builder builder) {
        if (builder.customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = builder.customer;

        if (builder.items != null) {
            builder.items.forEach(this::addItem);
        }
    }

    // Getters
    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<CartItems> getItems() { return items; }
    
    // Required for some JPA operations
    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public Long getCustomerId() {
        return customer != null ? customer.getId() : null;
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(CartItems::getItemTotal)
                .sum();
    }

    // Business Logic: ADD ITEM
    public void addItem(CartItems newItem) {
        validateItem(newItem);

        for (CartItems existing : items) {
            if (sameProduct(existing, newItem)) {
                existing.incrementQuantity(newItem.getQuantity());
                return;
            }
        }

        newItem.setCart(this);
        items.add(newItem);
    }

    // Business Logic: UPDATE QUANTITY
    public void setItemQuantity(Long productId, int quantity) {
        if (productId == null) throw new IllegalArgumentException("ProductId cannot be null");

        if (quantity <= 0) {
            removeItemByProductId(productId);
            return;
        }

        for (CartItems item : items) {
            if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                item.setQuantity(quantity);
                return;
            }
        }
        throw new IllegalArgumentException("Item not found in cart");
    }

    // Business Logic: REMOVE
    public void removeItemByProductId(Long productId) {
        if (productId == null) return;
        Iterator<CartItems> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItems item = iterator.next();
            if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                iterator.remove();
                item.setCart(null);
                return;
            }
        }
    }

    private void validateItem(CartItems item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (item.getProduct() == null || item.getProduct().getId() == null) {
            throw new IllegalArgumentException("Invalid product in cart item");
        }
        if (item.getQuantity() <= 0) throw new IllegalArgumentException("Quantity must be > 0");
    }

    private boolean sameProduct(CartItems a, CartItems b) {
        return a.getProduct() != null && b.getProduct() != null 
                && a.getProduct().getId().equals(b.getProduct().getId());
    }

    // Builder
    public static class Builder {
        private Customer customer;
        private List<CartItems> items;

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setItems(List<CartItems> items) {
            this.items = items;
            return this;
        }

        public Cart build() { return new Cart(this); }
    }
}