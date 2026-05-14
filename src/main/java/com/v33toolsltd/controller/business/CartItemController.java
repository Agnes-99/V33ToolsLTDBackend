package com.v33toolsltd.controller.business;

import com.v33toolsltd.domain.business.CartItems;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.business.ICartItemService;
import com.v33toolsltd.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final ICartItemService service;

    public CartItemController(ICartItemService service) {
        this.service = service;
    }

    private boolean isOwner(CartItems item, Authentication authentication) {
        if (item == null || item.getCart() == null || item.getCart().getCustomer() == null) {
            return false;
        }
        AppUserDetails details = (AppUserDetails) authentication.getPrincipal();
        return item.getCart().getCustomer().getId().equals(details.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItems> read(@PathVariable Long id, Authentication authentication) {
        CartItems item = service.read(id);

        if (item == null) return ResponseEntity.notFound().build();

        if (!isOwner(item, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<CartItems> updateQuantity(
            @PathVariable Long id,
            @RequestParam int quantity,
            Authentication authentication
    ) {
        if (!Helper.isValidQuantity(quantity)) return ResponseEntity.badRequest().build();

        CartItems item = service.read(id);
        if (item == null) return ResponseEntity.notFound().build();
        
        if (!isOwner(item, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(service.updateQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        CartItems item = service.read(id);
        
        if (item == null) return ResponseEntity.notFound().build();

        if (!isOwner(item, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItems>> findByCartId(@PathVariable Long cartId, Authentication authentication) {
        List<CartItems> items = service.findByCartId(cartId);

        // Security: If the list isn't empty, check ownership of the first item 
        // to verify this cart belongs to the requester.
        if (!items.isEmpty() && !isOwner(items.get(0), authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(items);
    }
}