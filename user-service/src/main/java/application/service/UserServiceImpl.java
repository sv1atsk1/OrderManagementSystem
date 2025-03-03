package application.service;

import application.dto.UserDTO;
import application.entity.User;
import application.exception.ErrorMessages;
import application.exception.UserNotFoundException;
import application.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(@Valid @RequestBody UserDTO userDTO) {
        validateUserDTO(userDTO);
        User user = modelMapper.map(userDTO, User.class);
        return saveUser(userDTO, user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_ID_NULL.getMessage());
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.USERNAME_NULL_OR_BLANK.getMessage());
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(username));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO partialUpdateUser(Long id, @Valid @RequestBody UserDTO userDTO) {
        validateIdAndUserDTO(id,userDTO);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, @Valid @RequestBody UserDTO userDTO) {
        validateIdAndUserDTO(id, userDTO);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userDTO.getUsername() == null || userDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.USERNAME_NULL_OR_BLANK.getMessage());
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.EMAIL_NULL_OR_BLANK.getMessage());
        }
        modelMapper.map(userDTO, user);
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_ID_NULL.getMessage());
        }
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_DTO_NULL.getMessage());
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_NULL_OR_BLANK.getMessage());
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.USERNAME_NULL_OR_BLANK.getMessage());
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.EMAIL_NULL_OR_BLANK.getMessage());
        }
        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_EMAIL_FORMAT.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private void validateIdAndUserDTO(Long id, UserDTO userDTO) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessages.ID_NULL.getMessage());
        }
        if (userDTO == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_DTO_NULL.getMessage());
        }
    }

    private UserDTO saveUser(UserDTO userDTO, User user) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
