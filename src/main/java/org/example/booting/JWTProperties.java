package org.example.booting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
public class JWTProperties {
    private String secret;
    private int accessExpiration;
    private int refreshExpiration;
}
