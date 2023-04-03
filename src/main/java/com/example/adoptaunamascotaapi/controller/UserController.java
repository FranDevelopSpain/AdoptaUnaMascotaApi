package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.User;
import com.example.adoptaunamascotaapi.repository.UserRepository;
import com.example.adoptaunamascotaapi.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/login")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && PasswordUtil.hashPassword(rawPassword).equals(user.getPassword())) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @DeleteMapping("/")
    public void deleteUser(@RequestBody User user) {
        userRepository.delete(user);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
