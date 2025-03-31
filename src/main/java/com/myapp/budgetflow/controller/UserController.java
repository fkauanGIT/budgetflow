package com.myapp.budgetflow.controller;

import com.myapp.budgetflow.dto.UserDTO;
import com.myapp.budgetflow.model.user.User;
import com.myapp.budgetflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Criar novo usuário (aberto para todos)
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @Valid @RequestBody UserDTO userDTO) {

        User createdUser = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/../{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    // Listar todos os usuários (apenas ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.listAll());
    }


}