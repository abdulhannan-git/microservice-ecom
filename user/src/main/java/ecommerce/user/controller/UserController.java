package ecommerce.user.controller;


import ecommerce.user.dto.UserRequest;
import ecommerce.user.dto.UserResponse;
import ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

   // private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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
        //logger.info("user id from logs: {}", id);
        log.info("user id from logs: {}", id);
        log.trace("Trace: ");
        log.debug("Debug logs: ");
        log.warn("WARN level: ");
        log.error("Error: ");
        return userService.fetchUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {
        boolean updatedUser = userService.updateUser(id, userRequest);
        if (updatedUser) return new ResponseEntity<>("User details updated successfully..", HttpStatus.OK);
        return new ResponseEntity<>("User details failed to update..", HttpStatus.BAD_REQUEST);
    }
}
