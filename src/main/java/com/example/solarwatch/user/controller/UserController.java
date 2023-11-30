package com.example.solarwatch.user.controller;

import com.example.solarwatch.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.solarwatch.user.model.User;



@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User userById = userService.getUserById(id);
        if (userById == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with requested id");

        }
        return ResponseEntity.status(HttpStatus.OK).body(userById);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name) {
        User userByName = userService.getUserByName(name);
        if (userByName == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with requested name");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userByName);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id) {
        boolean updatePerformed = userService.updateUser(user, id);
        if (updatePerformed) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with requested id");
    }
}
