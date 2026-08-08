package org.example.booting.LDAP.controller;

import org.example.booting.LDAP.DTO.Request.CreateUserRequest;
import org.example.booting.LDAP.service.LdapUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ldap/users")
public class UserController {
    private final LdapUserService ldapUserService;

    public UserController(LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        ldapUserService.createUser(request);
        return ResponseEntity.ok("User created successfully");
    }
}
