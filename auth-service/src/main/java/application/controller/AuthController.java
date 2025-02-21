package application.controller;

import application.dto.AuthRequest;
import application.dto.UserDTO;
import application.service.AuthService;
import application.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final UserServiceImpl userServiceImpl;

    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    @PostMapping("/register")
    public UserDTO addNewUser(@RequestBody UserDTO userDTO) {
        logger.info("Received request to register user: {}", userDTO);
        return userServiceImpl.createUser(userDTO);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}