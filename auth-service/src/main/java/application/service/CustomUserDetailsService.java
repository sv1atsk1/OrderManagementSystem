package application.service;

import application.client.UserServiceClient;
import application.config.CustomUserDetails;
import application.dto.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;
    public CustomUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AuthRequest authRequest = userServiceClient.getUserByUsername(username);
        if (authRequest != null) {
            return new CustomUserDetails(authRequest);
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
