package me.vroegop.hexagonal.api;

import me.vroegop.layered.persistence.LayeredUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    public UserController() {
    }

    @GetMapping
    public ResponseEntity<List<LayeredUserEntity>> list() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LayeredUserEntity> get(@PathVariable Long id) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LayeredUserEntity> create(@RequestBody Object body) {
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LayeredUserEntity> update(@PathVariable Long id, @RequestBody Object body) {
        return ResponseEntity.notFound().build();
    }
}
