package org.example.booting.LDAP.controller;

import lombok.RequiredArgsConstructor;
import org.example.booting.LDAP.DTO.Request.LoginRequest;
import org.example.booting.LDAP.service.LdapAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ldap/auth")
@RequiredArgsConstructor
public class LdapAuthController {
    private final LdapAuthenticationService ldapAuthenticationService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        boolean authenticated = ldapAuthenticationService.authenticate( request.username(), request.password());
        if (!authenticated) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid username or password");
        }
        return ResponseEntity.ok("LDAP authentication successful");
    }
}