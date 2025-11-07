package me.vroegop.layered.api;

import me.vroegop.layered.persistence.UserEntity;
import me.vroegop.layered.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> list() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public UserEntity get(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity body) {
        UserEntity created = userService.createUser(body);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Long id, @RequestBody UserEntity body) {
        return userService.updateUser(id, body);
    }
}
