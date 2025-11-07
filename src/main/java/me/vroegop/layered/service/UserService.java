package me.vroegop.layered.service;

import jakarta.transaction.Transactional;
import me.vroegop.layered.persistence.UserEntity;
import me.vroegop.layered.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> listUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserEntity createUser(UserEntity input) {
        if (input.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New user must not have an id");
        }
        if (input.getUsername() == null || input.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return userRepository.save(input);
    }

    public UserEntity updateUser(Long id, UserEntity patch) {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (patch.getUsername() != null && !patch.getUsername().equals(existing.getUsername())) {
            if (userRepository.existsByUsername(patch.getUsername())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
            existing.setUsername(patch.getUsername());
        }
        if (patch.getEmail() != null) {
            existing.setEmail(patch.getEmail());
        }
        if (patch.getFullName() != null) {
            existing.setFullName(patch.getFullName());
        }
        // createdAt stays untouched; updatedAt handled by @PreUpdate
        return userRepository.save(existing);
    }
}
