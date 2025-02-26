package application.service;

import application.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO userDTO);

    public UserDTO partialUpdateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}
