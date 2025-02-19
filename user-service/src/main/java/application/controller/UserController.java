package application.controller;

import application.dto.UserDTO;
import application.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public UserDTO addNewUser(@RequestBody UserDTO userDTO) {
        return userServiceImpl.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }
}
