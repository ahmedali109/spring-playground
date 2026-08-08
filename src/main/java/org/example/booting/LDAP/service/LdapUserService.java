package org.example.booting.LDAP.service;

import org.example.booting.LDAP.DTO.Request.CreateUserRequest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

@Service
public class LdapUserService {

    private final LdapTemplate ldapTemplate;

    public LdapUserService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void createUser(CreateUserRequest request) {

        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("uid", request.username())
                .build();

        BasicAttributes attributes = new BasicAttributes();
        BasicAttribute objectClass = new BasicAttribute("objectClass");

        objectClass.add("top");
        objectClass.add("person");
        objectClass.add("organizationalPerson");
        objectClass.add("inetOrgPerson");

        attributes.put(objectClass);

        attributes.put("uid", request.username());

        attributes.put("cn", request.firstName() + " " + request.lastName());
        attributes.put("sn", request.lastName());
        attributes.put("givenName", request.firstName());
        attributes.put("mail", request.email());
        attributes.put("userPassword", request.password());
        ldapTemplate.bind(
                dn,
                null,
                attributes
        );
    }
}