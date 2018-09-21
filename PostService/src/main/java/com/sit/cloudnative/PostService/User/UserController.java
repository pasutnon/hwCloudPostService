
package com.sit.cloudnative.PostService.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    // Get All Notes
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllNotes() {
        List<User> user = userService.getAllUsers();
        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }

    // Create a new User
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User user_object = userService.create(user);
        return new ResponseEntity<User>(user_object, HttpStatus.OK);
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        // return userRepository.findById(userId).orElseThrow(() -> new
        // ResourceNotFoundException("User", "id", userId));
        User user = userService.getUserById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Update a Note

    // Delete a Note

}