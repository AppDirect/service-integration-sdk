package com.appdirect.sdk.security.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
public class RoleMapper {
    private static final String ANONYMOUS = "ANONYMOUS";

    private final Map<String, List<String>> roles;

    public RoleMapper(Map<String, List<String>> roles) {
        this.roles = roles;
    }

    public Set<String> roleMap(List<String> roles) {
        if (this.roles == null) {
            log.debug("Roles are {}", roles);
            return new HashSet<>(roles);
        }
        Set<String> mappedRoles = roles.stream().flatMap(
                role -> this.roles.entrySet().stream().map(entry -> {
                    if (entry.getValue().stream().anyMatch(r -> r.equals(role))) {
                        return entry.getKey();
                    }
                    return ANONYMOUS;
                }))
                .map(String::toUpperCase)
                .collect(toSet());

        log.debug("Roles {} are mapped to {}", roles, mappedRoles);
        return mappedRoles;
    }
}
