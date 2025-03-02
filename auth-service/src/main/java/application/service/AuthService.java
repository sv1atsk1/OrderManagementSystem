package application.service;

import application.client.UserServiceClient;
import application.dto.AuthRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final UserServiceClient userServiceClient;

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
