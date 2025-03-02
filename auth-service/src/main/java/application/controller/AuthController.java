package application.controller;

import application.dto.AuthRequest;
import application.exception.ErrorMessages;
import application.exception.InvalidAccessException;
import application.exception.InvalidAuthHeaderException;
import application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new InvalidAccessException(ErrorMessages.INVALID_ACCESS.getMessage());
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
            throw new InvalidAuthHeaderException(ErrorMessages.INVALID_AUTH_HEADER.getMessage());
        }
    }
}