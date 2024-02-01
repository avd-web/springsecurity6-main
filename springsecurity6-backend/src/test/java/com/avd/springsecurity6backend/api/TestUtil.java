package com.avd.springsecurity6backend.api;

import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestUtil {
    public static User createMockUser1() {
        //Arrange
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() {
        };
        //Arrange testable input-values for user1
        String TestEmail = "user1@mail.com";
        String TestFirstname = "user1Firstname";
        String TestLastname = "user1Lastname";
        String TestPassword = passwordEncoder.encode("password");
        Role TestRole = Role.USER;
        //Return User object via builder
        return User.builder().username(TestEmail).firstname(TestFirstname).lastname(TestLastname).password(TestPassword).role(TestRole).build();
    }

    public static User createMockAdmin1() {
        //Arrange
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() {
        };
        //Arrange testable input-values for user1
        String TestEmail = "admin1@mail.com";
        String TestFirstname = "admin1Firstname";
        String TestLastname = "admin1Lastname";
        String TestPassword = passwordEncoder.encode("password");
        Role TestRole = Role.ADMIN;
        //Return User object via builder
        return User.builder().username(TestEmail).firstname(TestFirstname).lastname(TestLastname).password(passwordEncoder.encode(TestPassword)).role(TestRole).build();
    }
}