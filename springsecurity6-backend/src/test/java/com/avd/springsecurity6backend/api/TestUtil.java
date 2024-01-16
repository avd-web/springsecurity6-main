package com.avd.springsecurity6backend.api;

import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;

public class TestUtil {

    public static User createMockUser1() {
        //Arrange testable input-values for user1
        String TestEmail = "user1@mail.com";
        String TestFirstname = "user1Firstname";
        String TestLastname = "user1Lastname";
        String TestPassword = "password1";
        Role TestRole = Role.USER;
        //Arrange building User object
        return User.builder()
                .username(TestEmail)
                .firstname(TestFirstname)
                .lastname(TestLastname)
                .password(TestPassword)
                .role(TestRole)
                .build();
    }

    public static User createMockUser2() {
        //Arrange testable input-values for user2
        String TestEmail = "user2@mail.com";
        String TestFirstname = "user2Firstname";
        String TestLastname = "user2Lastname";
        String TestPassword = "password2";
        Role TestRole = Role.USER;
        //Arrange building User object
        return User.builder()
                .username(TestEmail)
                .firstname(TestFirstname)
                .lastname(TestLastname)
                .password(TestPassword)
                .role(TestRole)
                .build();
    }
}