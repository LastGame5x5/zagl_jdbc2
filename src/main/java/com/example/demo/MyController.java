package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class MyController {

    @PostMapping("/user/insert")
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getEmail() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON");
        else {
            user.setDate(LocalDate.now());
            int rowsUpdated = DbFunctions.insertUser(user);
            if (rowsUpdated== 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with login " + user.getLogin() + " already exists");
            }
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/select/{login}")
    public ResponseEntity<?> getSelect(@PathVariable String login) {
        User user1 = DbFunctions.getUserByLogin(login);
        if (user1 != null) {
            return ResponseEntity.ok(user1);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
        }
    }
}
