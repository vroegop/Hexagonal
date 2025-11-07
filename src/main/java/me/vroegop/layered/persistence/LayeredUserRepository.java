package me.vroegop.layered.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LayeredUserRepository extends JpaRepository<LayeredUserEntity, Long> {
    boolean existsByUsername(String username);
}
