package ecommerce.user.controller;



import ecommerce.user.dto.UserRequest;
import ecommerce.user.dto.UserResponse;
import ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ResponseEntity<>("User is created successfully.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable String id,
            @RequestBody UserRequest userRequest
    ) {
        boolean updatedUser = userService.updateUser(id, userRequest);
        if (updatedUser)
            return new ResponseEntity<>("User details updated successfully..", HttpStatus.OK);
        return new ResponseEntity<>("User details failed to update..", HttpStatus.BAD_REQUEST);
    }
}
