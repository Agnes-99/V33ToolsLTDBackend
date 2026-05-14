package com.v33toolsltd.controller.user;


import com.v33toolsltd.domain.users.Manager;
import com.v33toolsltd.dto.users.LoginResponse;
import com.v33toolsltd.factory.users.ManagerFactory;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.service.users.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final IManagerService service;

    @Autowired
    public ManagerController(IManagerService service) {
        this.service = service;
    }

    private LoginResponse toDto(Manager m) {
        return new LoginResponse(
                m.getId(),
                m.getFirstName(),
                m.getLastName(),
                m.getPhoneNumber(),
                m.getEmailAddress(),
                m.getRole()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<LoginResponse> create(@RequestBody Manager manager) {
        Manager newManager = ManagerFactory.createManager(
                manager.getFirstName(),
                manager.getLastName(),
                manager.getPhoneNumber(),
                manager.getEmailAddress(),
                manager.getPassword()
        );

        Manager created = service.create(newManager);
        return ResponseEntity.ok(toDto(created));
    }

    @GetMapping("/read/{managerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoginResponse> read(@PathVariable Long managerId) {
        Manager manager = service.read(managerId);
        if (manager != null)
            return ResponseEntity.ok(toDto(manager));
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoginResponse> update(@RequestBody Manager manager, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        boolean isSelf = userDetails.getId().equals(manager.getId());
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isSelf && !isAdmin) {
            return ResponseEntity.status(403).build();
        }

        Manager updated = service.update(manager);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/delete/{managerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> delete(@PathVariable Long managerId, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        boolean isSelf = userDetails.getId().equals(managerId);
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isSelf && !isAdmin) {
            return ResponseEntity.status(403).build();
        }

        boolean deleted = service.delete(managerId);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoginResponse>> getAll() {
        List<Manager> managers = service.getAll();
        List<LoginResponse> dtos = managers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
