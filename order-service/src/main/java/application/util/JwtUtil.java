package application.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtUtil {

    private static final String SECRET = "2f3a5257a53236dade3f5a40a162fcdb1f6e3092ef498f05539756dc52b720b5c1214d88da558225a3771598796b7bb7e32f4a0afa6c16acce19da4726d7bce3be9b0b1db7c185ff1fd12f846651a75b434197454f6da17109931176b0c96640ce19f26c66e4f58bf7eff5cf7c7f2c1a104f34538f5f09f13fb5eac0f0ea2bc7eb6f7eac3ab282f19c162c66912161848bad2e6a9097cc98785ce7d86fb3173c194cc3afdb4d3c8af5f3466a2c6537c6be4dac930c12880881f5ab38417e2c8e7d8b500392e4d776604849c1ac592374a864a33779e6f1b1156f3aeeb10128b8d7320fc6f89c86917108dd8a7cc838965d2e6997a238ea072b8552d93f2f98cc";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }
}
