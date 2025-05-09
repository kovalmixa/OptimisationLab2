package edu.dnu.fpm.pz.db.Controllers;

import edu.dnu.fpm.pz.db.Entities.User;
import edu.dnu.fpm.pz.db.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        ResponseEntity<String> message = userService.addUser(user);
        return message;
    }
    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        return userService.findUsersWithPagination(offset, limit);
    }
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String emailPart,
                                  @RequestParam int page,
                                  @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findByEmailContaining(emailPart, pageable);
    }
    @GetMapping("findByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/!cache{id:[0-9]+}")
    public ResponseEntity<User> getUserByIdWithoutCache(@PathVariable Long id) {
        Optional<User> user = userService.getUserByIdWithoutCache(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generateUsers(@RequestParam int count) {
        userService.generateUsers(count);
        return ResponseEntity.ok("Users generated: " + count);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
