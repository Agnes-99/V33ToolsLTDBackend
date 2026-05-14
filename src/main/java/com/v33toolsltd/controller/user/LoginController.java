package com.v33toolsltd.controller.user;


import com.v33toolsltd.domain.users.Admin;
import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.domain.users.Manager;
import com.v33toolsltd.dto.users.LoginRequest;
import com.v33toolsltd.dto.users.LoginResponse;
import com.v33toolsltd.security.AppUserDetails;
import com.v33toolsltd.security.AppUserDetailsService;
import com.v33toolsltd.security.jwt.JwtUtil;
import com.v33toolsltd.service.users.IAdminService;
import com.v33toolsltd.service.users.ICustomerService;
import com.v33toolsltd.service.users.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final ICustomerService customerService;
    private final IAdminService adminService;
    private final IManagerService managerService;

    @Autowired
    public LoginController(
            AuthenticationManager authenticationManager,
            AppUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            ICustomerService customerService,
            IAdminService adminService,
            IManagerService managerService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.customerService = customerService;
        this.adminService = adminService;
        this.managerService = managerService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt for email: " + request.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            AppUserDetails appUser = (AppUserDetails) userDetails;

            String token = jwtUtil.generateToken(userDetails);

            String role = appUser.getAuthorities().iterator().next().getAuthority();
            String firstName = null;
            String lastName = null;
            String phoneNumber = null;

            switch (role) {
                case "ROLE_CUSTOMER":
                    Customer customer = customerService.read(appUser.getId());
                    if (customer != null) {
                        firstName = customer.getFirstName();
                        lastName = customer.getLastName();
                        phoneNumber = customer.getPhoneNumber();
                    }
                    break;

                case "ROLE_ADMIN":
                    Admin admin = adminService.read(appUser.getId());
                    if (admin != null) {
                        firstName = admin.getFirstName();
                        lastName = admin.getLastName();
                        phoneNumber = admin.getPhoneNumber();
                    }
                    break;

                case "ROLE_MANAGER":
                    Manager manager = managerService.read(appUser.getId());
                    if (manager != null) {
                        firstName = manager.getFirstName();
                        lastName = manager.getLastName();
                        phoneNumber = manager.getPhoneNumber();
                    }
                    break;
            }

            LoginResponse response = new LoginResponse(
                    appUser.getId(),
                    firstName,
                    lastName,
                    phoneNumber,
                    appUser.getUsername(),
                    role
            );
            response.setToken(token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

}
