package edu.dnu.fpm.pz.db.Services;

import com.github.javafaker.Faker;
import edu.dnu.fpm.pz.db.Entities.User;
import edu.dnu.fpm.pz.db.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcurrentMapCacheManager cacheManager;

    private final Faker faker = new Faker();

    public void generateUsers(int count) {
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPasswordHash(faker.internet().password());
            user.setBio(faker.lorem().sentence());
            userRepository.save(user);
        }
    }

    private Cache getUserCache() {
        return cacheManager.getCache("User");
    }

    public User getUserById(Long id) {
        Cache.ValueWrapper wrapper = getUserCache().get(id);
        if (wrapper != null) {
            return (User) wrapper.get();
        }

        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> getUserCache().put(id, u));
        return user.orElse(null);
    }

    public Optional<User> findById(Long id) {
        User cached = getUserById(id);
        return Optional.ofNullable(cached);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUsersWithPagination(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return userRepository.findAll(pageable).getContent();
    }

    public List<User> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable);
    }

    public ResponseEntity<String> addUser(User user) {
//        if (userRepository.existsByEmail(user.getEmail())) {
//            return ResponseEntity.badRequest().body("Email already in use");
//        }

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    public ResponseEntity<String> updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userUpdated = userOptional.get();
            userUpdated.setEmail(user.getEmail());
            userUpdated.setUsername(user.getUsername());
            userUpdated.setBio(user.getBio());
//            userUpdated.setProfilePicture(user.getProfilePicture());
            userUpdated.setPasswordHash(user.getPasswordHash());
            userUpdated.setLastLogin(user.getLastLogin());

            userRepository.save(userUpdated);
            getUserCache().put(id, userUpdated);

            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public ResponseEntity<String> deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            getUserCache().evict(id);
        }
        return ResponseEntity.ok("User deleted successfully");
    }

    public Optional<User> getUserByIdWithoutCache(Long id) {
        User user = userRepository.findById(id).get();
        return Optional.ofNullable(user);
    }
}
