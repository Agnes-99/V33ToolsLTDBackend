package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.domain.business.Product;
import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.repository.business.CartRepository;
import com.v33toolsltd.repository.business.ProductRepository;
import com.v33toolsltd.repository.users.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Cart getCartByCustomer(Long customerId) {
        return cartRepository.findByCustomer_Id(customerId)
                .orElseGet(() -> createForCustomer(customerId));
    }

    @Override
    @Transactional
    public Cart addItem(Long customerId, Long productId, int quantity) {
        Cart cart = getCartByCustomer(customerId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems item = new CartItems.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .build();

        // The domain logic handles checking for existing products
        cart.addItem(item);

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long customerId, Long productId, int quantity) {
        Cart cart = getCartByCustomer(customerId);
        cart.setItemQuantity(productId, quantity);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart removeItem(Long customerId, Long productId) {
        Cart cart = getCartByCustomer(customerId);
        cart.removeItemByProductId(productId);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart createForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = new Cart.Builder()
                .setCustomer(customer)
                .build();

        return cartRepository.save(cart);
    }

    @Override public List<Cart> getAll() { return cartRepository.findAll(); }
    @Override public Cart create(Cart cart) { return cartRepository.save(cart); }
    @Override public Cart read(Long id) { return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found")); }
    @Override public Cart update(Cart cart) { return cartRepository.save(cart); }
    @Override public boolean delete(Long id) { cartRepository.deleteById(id); return true; }
}