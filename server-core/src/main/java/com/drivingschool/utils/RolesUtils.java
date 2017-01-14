package com.drivingschool.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

public class RolesUtils {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_INSTRUCTOR = "ROLE_INSTRUCTOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";

    private static final String[] ROLES = {ROLE_USER, ROLE_INSTRUCTOR, ROLE_ADMIN, ROLE_SUPERADMIN};
    public static final List<String> ROLES_LIST = Arrays.asList(ROLES);

    public static String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((GrantedAuthority) auth.getAuthorities().toArray()[0]).getAuthority();
    }

}
