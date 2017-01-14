package com.drivingschool.utils;

public class PermissionUtils {

    public final static String HAS_ANY_ROLE = "hasRole('ROLE_USER') or hasRole('ROLE_INSTRUCTOR') " +
            "or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')";

    public final static String HAS_A_MANAGE_ROLE = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')";
}
