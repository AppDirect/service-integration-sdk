package com.appdirect.sdk.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RoleMapperTest {

    private RoleMapper roleMapper;

    @Before
    public void setUp() throws Exception {
        Map<String, List<String>> myMap = new HashMap<>();
        List<String> admin = Arrays.asList("superuser", "super support");
        List<String> viewer = Arrays.asList("user");
        myMap.put("admin", admin);
        myMap.put("viewer", viewer);
        roleMapper = new RoleMapper(myMap);
    }

    @Test
    public void testRoleMapAnonymousOnly() throws Exception {
        List<String> roles = Arrays.asList("BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertFalse(myRoles.contains("ADMIN"));
        assertFalse(myRoles.contains("VIEWER"));
        assertTrue(myRoles.contains("ANONYMOUS"));
    }

    @Test
    public void testRoleMapAdmin() throws Exception {
        List<String> roles = Arrays.asList("SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT", "SUPER_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertTrue(myRoles.contains("ADMIN"));
        assertFalse(myRoles.contains("VIEWER"));
        assertTrue(myRoles.contains("ANONYMOUS"));
    }

    @Test
    public void testRoleMapUser() throws Exception {
        List<String> roles = Arrays.asList("USER", "BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertFalse(myRoles.contains("ADMIN"));
        assertTrue(myRoles.contains("VIEWER"));
        assertTrue(myRoles.contains("ANONYMOUS"));
    }

    @Test
    public void testRoleMapAdminAndViewer() throws Exception {
        List<String> roles = Arrays.asList("USER", "BILLING_ADMIN", "SYS_ADMIN", "DEVELOPER", "CHANNEL_PRODUCT_SUPPORT", "SALES_SUPPORT", "SUPER_SUPPORT");
        Set<String> myRoles = roleMapper.roleMap(roles);
        assertTrue(myRoles.contains("ADMIN"));
        assertTrue(myRoles.contains("VIEWER"));
        assertTrue(myRoles.contains("ANONYMOUS"));
    }
}
