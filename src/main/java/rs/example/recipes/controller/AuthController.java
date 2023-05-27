package rs.example.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import rs.example.recipes.model.User;
import rs.example.recipes.request.LoginRequest;
import rs.example.recipes.request.RegisterUserRequest;
import rs.example.recipes.response.LoginResponse;
import rs.example.recipes.service.UserService;
import rs.example.recipes.util.JwtUtil;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        User user = userService.create(registerUserRequest);
        return ResponseEntity.ok(user);
    }

}
