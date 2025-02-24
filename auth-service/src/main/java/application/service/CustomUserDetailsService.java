package application.service;

import application.client.UserServiceClient;
import application.config.CustomUserDetails;
import application.dto.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;
    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    public CustomUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AuthRequest authRequest = userServiceClient.getUserByUsername(username);
        if (authRequest != null) {
            logger.info("User found: " + authRequest.getUsername());
            logger.info("Stored password (encoded): " + authRequest.getPassword());
            return new CustomUserDetails(authRequest);
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
