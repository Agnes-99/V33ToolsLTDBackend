package com.v33toolsltd.controller.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.business.ICartService;
import com.v33toolsltd.util.Helper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ICartService service;

    public CartController(ICartService service) {
        this.service = service;
    }

    /**
     * Security check helper to ensure the authenticated user matches the target customerId.
     */
    private boolean isUserAuthorized(Long customerId, Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof AppUserDetails)) return false;
        AppUserDetails user = (AppUserDetails) auth.getPrincipal();
        return user.getId().equals(customerId);
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Cart> create(@PathVariable Long customerId, Authentication auth) {
        if (!isUserAuthorized(customerId, auth)) return ResponseEntity.status(403).build();

        return ResponseEntity.ok(service.createForCustomer(customerId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Cart> getByCustomer(@PathVariable Long customerId, Authentication auth) {
        if (!isUserAuthorized(customerId, auth)) return ResponseEntity.status(403).build();
        
        return ResponseEntity.ok(service.getCartByCustomer(customerId));
    }

    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication auth
    ) {
        if (!isUserAuthorized(customerId, auth)) return ResponseEntity.status(403).build();
        if (!Helper.isValidQuantity(quantity)) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(service.addItem(customerId, productId, quantity));
    }

    @PutMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity,
            Authentication auth
    ) {
        if (!isUserAuthorized(customerId, auth)) return ResponseEntity.status(403).build();
        if (!Helper.isValidQuantity(quantity)) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(service.updateItemQuantity(customerId, productId, quantity));
    }

    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            Authentication auth
    ) {
        if (!isUserAuthorized(customerId, auth)) return ResponseEntity.status(403).build();
        
        return ResponseEntity.ok(service.removeItem(customerId, productId));
    }
}