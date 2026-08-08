package org.example.booting.LDAP.service;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

import javax.naming.directory.DirContext;

@Service
public class LdapAuthenticationService {

    private final LdapTemplate ldapTemplate;
    private final LdapContextSource contextSource;

    public LdapAuthenticationService(LdapTemplate ldapTemplate, LdapContextSource contextSource) {
        this.ldapTemplate = ldapTemplate;
        this.contextSource = contextSource;
    }

    public boolean authenticate(String username, String password) {
        String userDn = "uid=" + username + ",ou=users,dc=example,dc=com";
        try {
            DirContext context = contextSource.getContext(userDn, password);
            context.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}