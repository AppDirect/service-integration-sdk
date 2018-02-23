package com.appdirect.sdk.security.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleMapperTest {

    private RoleMapper roleMapper;

    @Before
    public void setUp() throws Exception {
        Map<String, List<String>> myMap = new HashMap<>();
        List<String> admin = asList("SUPERUSER", "SUPER_SUPPORT");
        List<String> viewer = Collections.singletonList("USER");
        myMap.put("admin", admin);
        myMap.put("viewer", viewer);
        roleMapper = new RoleMapper(myMap);
    }

    @Test
    public void testRoleMapAnonymousOnly() {
        List<String> roles = asList("BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertThat(myRoles).containsOnly("ANONYMOUS");
    }

    @Test
    public void testRoleMapAdmin() {
        List<String> roles = asList("SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT", "SUPER_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertThat(myRoles).containsOnly("ADMIN", "ANONYMOUS");
    }

    @Test
    public void testRoleMapUser() {
        List<String> roles = asList("USER", "BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertThat(myRoles).containsOnly("VIEWER", "ANONYMOUS");
    }

    @Test
    public void testRoleMapAdminAndViewer() {
        List<String> roles = asList("USER", "BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT", "SUPER_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertThat(myRoles).containsOnly("ADMIN", "VIEWER", "ANONYMOUS");
    }
}
