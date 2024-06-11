package com.appdirect.sdk.security.configuration.model;

import com.appdirect.sdk.security.exception.RoleMappingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@ConfigurationProperties(prefix = "authentication")
@Data
@Slf4j
public class RoleMapping {
    private Map<String, List<String>> roles;

    @PostConstruct
    public void verifyRoles() {
        if (roles == null) {
            log.warn("No roles are set to do mapping");
            return;
        }
        List<String> k = roles.keySet().stream().filter(s -> s.matches(".*[^a-zA-Z_].*")).collect(toList());
        if (!k.isEmpty()) {
            throw new RoleMappingException(k);
        }
    }
}
