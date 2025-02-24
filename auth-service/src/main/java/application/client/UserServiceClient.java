package application.client;

import application.dto.AuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface UserServiceClient {
    @GetMapping("/users/username/{username}")
    AuthRequest getUserByUsername(@PathVariable("username") String username);

    @PostMapping("/users/register")
    void registerUser(@RequestBody AuthRequest authRequest);
}