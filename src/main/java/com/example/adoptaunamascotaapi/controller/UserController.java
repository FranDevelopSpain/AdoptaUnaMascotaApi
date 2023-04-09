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

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/auth")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            System.out.println("Server hashedPassword: " + user.getPassword());

            if (PasswordUtil.checkPassword(rawPassword, user.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Usuario: " + user.getEmail());
        System.out.println("Contraseña sin Hash: " + user.getPassword());
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        System.out.println("Passsword Hash:" + hashedPassword);
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
        User adminUser = userRepository.findByEmail("admin@mail.com");
        if (adminUser != null && adminUser.getIsAdmin()) {
            if (adminUser.getPassword().equals("admin")) {
                System.out.println("Usuario: "+adminUser.getEmail() + "Contraseña: "+adminUser.getPassword());
                return ResponseEntity.ok(adminUser);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<User> updatePassword(@RequestParam String email, @RequestParam String newPassword) {
        User user = userRepository.findByEmail(email);
        System.out.println("Los datos son: "+user.getEmail() + " y " + user.getPassword());
        if (user != null) {
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            System.out.println("La contraseña anterior era: = " + hashedPassword);
            user.setPassword(hashedPassword);
            System.out.println("La contraseña nueva es: = " + newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
