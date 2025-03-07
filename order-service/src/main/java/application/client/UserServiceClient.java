package application.client;

import application.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8083", configuration = FeignClientInterceptor.class)
public interface UserServiceClient {

    @GetMapping("/api/v1/users/id/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
