package com.example.adoptaunamascotaapi.controller;
import com.example.adoptaunamascotaapi.model.User;
import com.example.adoptaunamascotaapi.security.PasswordUtil;
import com.example.adoptaunamascotaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://10.0.2.2:8080")
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
    @GetMapping("/auth")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            System.out.println("Server hashedPassword: " + user.getPassword());

            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Raw password: " + user.getPassword());
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostConstruct
    public void createAdminIfNotExist() {
        String adminEmail = "admin@mail.com";
        User existingAdmin = userRepository.findByEmail(adminEmail);

        if (existingAdmin == null) {
            System.out.println("Creando usuario administrador.");
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setSurname("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setIsAdmin(true);
            String rawPassword = "admin";
            adminUser.setPassword(rawPassword);
            userRepository.save(adminUser);
            System.out.println("Usuario administrador creado con éxito.");
        } else {
            System.out.println("El usuario administrador ya existe.");
        }
    }

    @GetMapping("/admin/auth")
    public ResponseEntity<User> getAdminByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        User adminUser = userRepository.findByEmail("admin@mail.com");
        if (user != null && user.getIsAdmin()) {
            if (adminUser.getPassword().equals("admin")) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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
