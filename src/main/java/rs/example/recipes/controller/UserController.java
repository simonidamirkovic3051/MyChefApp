package rs.example.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.example.recipes.model.User;
import rs.example.recipes.request.ChangePasswordRequest;
import rs.example.recipes.request.UpdateUserRequest;
import rs.example.recipes.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody UpdateUserRequest request) {
        User user = userService.update(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        User user = userService.changePassword(request);
        return ResponseEntity.ok(user);
    }
}
