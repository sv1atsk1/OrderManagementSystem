package by.viachaslau.api_gateway.application.filter;

import by.viachaslau.api_gateway.application.exception.ErrorMessages;
import by.viachaslau.api_gateway.application.exception.InvalidAuthHeaderException;
import by.viachaslau.api_gateway.application.exception.MissingAuthHeaderException;
import by.viachaslau.api_gateway.application.exception.UnauthorizedAccessException;
import by.viachaslau.api_gateway.application.util.JwtUtil;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.info("Missing authorization header for request: " + exchange.getRequest().getURI());
                    throw new MissingAuthHeaderException(ErrorMessages.MISSING_AUTH_HEADER.getMessage());
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    logger.info("Token extracted:" + authHeader);
                } else {
                    logger.info("Invalid authorization header format for request: " + exchange.getRequest().getURI());
                    throw new InvalidAuthHeaderException(ErrorMessages.INVALID_AUTH_HEADER_FORMAT.getMessage());
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                            .build();
                    logger.info("Token validated successfully for request: " + exchange.getRequest().getURI());
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());

                } catch (Exception e) {
                    logger.info("Invalid access for request: " + exchange.getRequest().getURI());
                    throw new UnauthorizedAccessException(ErrorMessages.UNAUTHORIZED_ACCESS.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
