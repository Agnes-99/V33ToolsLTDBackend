package com.v33toolsltd.controller.business;

import com.v33toolsltd.domain.business.Cart;
import com.v33toolsltd.domain.business.Order;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.business.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService service;

    public OrderController(IOrderService service) {
        this.service = service;
    }

    // ======================================================
    // CHECKOUT (USES EXISTING SERVICE METHOD)
    // ======================================================
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> checkout(@RequestBody Cart cart,
                                         Authentication authentication) {

        AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        // security check: cart must belong to logged-in user
        if (cart.getCustomerId() == null ||
            !user.getId().equals(cart.getCustomerId())) {
            return ResponseEntity.status(403).build();
        }

        Order order = service.checkout(cart);
        return ResponseEntity.ok(order);
    }

    // ======================================================
    // READ ORDER
    // ======================================================
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Order> read(@PathVariable Long orderId,
                                      Authentication authentication) {

        Order order = service.getOrderById(orderId);

        AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        if (!isOwnerOrAdmin(user, authentication, order.getCustomerId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(order);
    }

    // ======================================================
    // DELETE ORDER
    // ======================================================
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long orderId,
                                       Authentication authentication) {

        Order order = service.getOrderById(orderId);

        AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        if (!isOwnerOrAdmin(user, authentication, order.getCustomerId())) {
            return ResponseEntity.status(403).build();
        }

        service.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    // ======================================================
    // ADMIN/MANAGER ONLY
    // ======================================================
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ======================================================
    // HELPER METHOD
    // ======================================================
    private boolean isOwnerOrAdmin(AppUserDetails user,
                                   Authentication auth,
                                   Long customerId) {

        return user.getId().equals(customerId)
                || auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN") ||
                        a.getAuthority().equals("ROLE_MANAGER"));
    }
}