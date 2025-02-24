package application.service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private JWTService jwtService;

    public AuthService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public Claims validateToken(String token) {
        return jwtService.validateToken(token);
    }

}
