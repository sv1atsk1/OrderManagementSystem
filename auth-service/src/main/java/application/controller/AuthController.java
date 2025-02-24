package application.controller;

import application.dto.AuthRequest;
import application.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        logger.info("Received authentication request for user: " + authRequest.getUsername());
        logger.info("Password provided: " + authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody AuthRequest authRequest) {
        authService.registerUser(authRequest);
        return "User registered successfully";
    }

    @GetMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.validateToken(token);
            return "Token is valid";
        } else {
            throw new RuntimeException("Invalid or missing Authorization header");
        }
    }
}