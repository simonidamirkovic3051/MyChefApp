package rs.example.recipes.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.example.recipes.exception.DuplicateUsernameException;
import rs.example.recipes.exception.UserNotFoundException;
import rs.example.recipes.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.example.recipes.repository.UserRepository;
import rs.example.recipes.request.ChangePasswordRequest;
import rs.example.recipes.request.RegisterUserRequest;
import rs.example.recipes.request.UpdateUserRequest;

import java.util.Collections;
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findOne(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public User findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User update(UpdateUserRequest request) {
        User user = findOne(request.getId());
        user.setUsername(request.getUsername());
        return userRepository.save(user);
    }

    public User create(RegisterUserRequest request) {

        User user = null;
        try {
            findOne(request.getUsername());
            throw new DuplicateUsernameException(request.getUsername());
        } catch (UsernameNotFoundException e) {
            user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    public User changePassword(ChangePasswordRequest request) {
        User user = findOne(request.getId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findOne(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
