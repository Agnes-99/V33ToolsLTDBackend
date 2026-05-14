package com.v33toolsltd.controller.user;

import com.v33toolsltd.domain.users.Admin;
import com.v33toolsltd.dto.users.LoginResponse;
import com.v33toolsltd.factory.users.AdminFactory;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.users.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IAdminService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(IAdminService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    private LoginResponse toDto(Admin a) {
        return new LoginResponse(
                a.getId(),
                a.getFirstName(),
                a.getLastName(),
                a.getPhoneNumber(),
                a.getEmailAddress(),
                a.getRole()
        );
    }

    @PostMapping("/create")
    /* NOTE: Keep @PreAuthorize commented out for the VERY FIRST admin creation.
       Once you have one admin, uncomment it to protect the endpoint.
    */
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoginResponse> create(@RequestBody Admin admin) {
        // Encrypting the password exactly how it's done for Customers
        String encodedPassword = passwordEncoder.encode(admin.getPassword());

        Admin newAdmin = AdminFactory.createAdmin(
                admin.getFirstName(),
                admin.getLastName(),
                admin.getPhoneNumber(),
                admin.getEmailAddress(),
                encodedPassword
        );

        if (newAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        Admin created = service.create(newAdmin);
        return ResponseEntity.ok(toDto(created));
    }

    @GetMapping("/read/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoginResponse> read(@PathVariable Long adminId) {
        Admin admin = service.read(adminId);
        if (admin != null)
            return ResponseEntity.ok(toDto(admin));
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoginResponse> update(@RequestBody Admin admin, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        // Security check: Only the Admin themselves or a Super Admin can update
        boolean isSelf = userDetails.getId().equals(admin.getId());
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isSelf && !isAdmin) {
            return ResponseEntity.status(403).build();
        }

        // Optional: If updating password, remember to encode it here as well!
        Admin updated = service.update(admin);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/delete/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable Long adminId) {
        boolean deleted = service.delete(adminId);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoginResponse>> getAll() {
        List<Admin> admins = service.getAll();
        List<LoginResponse> dtos = admins.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}