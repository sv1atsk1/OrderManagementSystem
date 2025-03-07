package application.client;

import application.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://AUTH-SERVICE:8084")
public interface AuthServiceClient {

    @GetMapping("/api/v1/auth/validate")
    UserDTO validateToken(@RequestParam("token") String token);

    @GetMapping("/api/v1/auth/user")
    UserDTO getUserByToken(@RequestHeader("Authorization") String token);
}
