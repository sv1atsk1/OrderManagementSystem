package by.viachaslau.api_gateway.application.util;

import by.viachaslau.api_gateway.application.exception.ErrorMessages;
import by.viachaslau.api_gateway.application.exception.ExpiredJwtTokenException;
import by.viachaslau.api_gateway.application.exception.InvalidJwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
public class JwtUtil {

    public static final String SECRET = "";

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException(ErrorMessages.EXPIRED_JWT_TOKEN.getMessage());
        } catch (JwtException e) {
            throw new InvalidJwtTokenException(ErrorMessages.INVALID_JWT_TOKEN.getMessage());
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
