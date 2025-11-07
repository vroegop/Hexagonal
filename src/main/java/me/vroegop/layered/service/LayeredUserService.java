package me.vroegop.layered.service;

import jakarta.transaction.Transactional;
import me.vroegop.layered.persistence.LayeredUserEntity;
import me.vroegop.layered.persistence.LayeredUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class LayeredUserService {

    private final LayeredUserRepository layeredUserRepository;

    public LayeredUserService(LayeredUserRepository layeredUserRepository) {
        this.layeredUserRepository = layeredUserRepository;
    }

    public List<LayeredUserEntity> listUsers() {
        return layeredUserRepository.findAll();
    }

    public LayeredUserEntity getUser(Long id) {
        return layeredUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public LayeredUserEntity createUser(LayeredUserEntity input) {
        if (input.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New user must not have an id");
        }
        if (input.getUsername() == null || input.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if (layeredUserRepository.existsByUsername(input.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return layeredUserRepository.save(input);
    }

    public LayeredUserEntity updateUser(Long id, LayeredUserEntity patch) {
        LayeredUserEntity existing = layeredUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (patch.getUsername() != null && !patch.getUsername().equals(existing.getUsername())) {
            if (layeredUserRepository.existsByUsername(patch.getUsername())) {
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
        return layeredUserRepository.save(existing);
    }
}
