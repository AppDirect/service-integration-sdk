package com.appdirect.sdk.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "authentication")
@Data
public class RoleMapperConfiguration {
    private Map<String, List<String>> roles;
}
