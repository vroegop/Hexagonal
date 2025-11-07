package me.vroegop.layered.api;

import me.vroegop.layered.persistence.LayeredUserEntity;
import me.vroegop.layered.service.LayeredUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class LayeredUserController {

    private final LayeredUserService layeredUserService;

    public LayeredUserController(LayeredUserService layeredUserService) {
        this.layeredUserService = layeredUserService;
    }

    @GetMapping
    public List<LayeredUserEntity> list() {
        return layeredUserService.listUsers();
    }

    @GetMapping("/{id}")
    public LayeredUserEntity get(@PathVariable Long id) {
        return layeredUserService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<LayeredUserEntity> create(@RequestBody LayeredUserEntity body) {
        LayeredUserEntity created = layeredUserService.createUser(body);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public LayeredUserEntity update(@PathVariable Long id, @RequestBody LayeredUserEntity body) {
        return layeredUserService.updateUser(id, body);
    }
}
