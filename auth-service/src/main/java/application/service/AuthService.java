package application.service;

import application.client.UserServiceClient;
import application.dto.AuthRequest;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private JWTService jwtService;
    private final UserServiceClient userServiceClient;

    public AuthService(JWTService jwtService, UserServiceClient userServiceClient) {
        this.jwtService = jwtService;
        this.userServiceClient = userServiceClient;
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public Claims validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public void registerUser(AuthRequest authRequest) {
        userServiceClient.registerUser(authRequest);
    }
}
