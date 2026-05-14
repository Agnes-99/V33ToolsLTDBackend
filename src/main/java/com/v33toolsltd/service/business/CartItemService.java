package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.repository.business.CartItemsRepository;
import com.v33toolsltd.repository.business.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemsRepository cartItemsRepository;
    private final CartRepository cartRepository;

    public CartItemService(CartItemsRepository cartItemsRepository,
                           CartRepository cartRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        CartItems item = cartItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        Cart cart = item.getCart();

        if (cart != null) {
            // Remove via domain logic to ensure bidirectional cleanup
            cart.removeItemByProductId(item.getProduct().getId());
            cartRepository.save(cart);
        } else {
            cartItemsRepository.delete(item);
        }
        return true;
    }

    @Override
    @Transactional
    public CartItems updateQuantity(Long cartItemId, int quantity) {
        CartItems item = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        item.setQuantity(quantity);
        return cartItemsRepository.save(item);
    }

    @Override public CartItems create(CartItems entity) { return cartItemsRepository.save(entity); }
    @Override public CartItems read(Long id) { return cartItemsRepository.findById(id).orElse(null); }
    @Override public CartItems update(CartItems entity) { return cartItemsRepository.save(entity); }
    @Override public List<CartItems> findByCartId(Long cartId) { return cartItemsRepository.findByCart_Id(cartId); }
    @Override public List<CartItems> findAll() { return cartItemsRepository.findAll(); }
    @Override public void deleteByCartId(Long cartId) { cartItemsRepository.deleteAll(findByCartId(cartId)); }
}