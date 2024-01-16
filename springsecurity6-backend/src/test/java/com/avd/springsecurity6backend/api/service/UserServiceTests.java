package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void changePassword() {

        //Arrange test input for user1
        String TestEmail = "user@mail.com";
        String TestFirstname = "userFirstname";
        String TestLastname = "userLastname";
        String TestPassword = "password";
        Role TestRole = Role.USER;

        //Arrange building testUser1
        User user1 = User.builder()
                .username(TestEmail)
                .firstname(TestFirstname)
                .lastname(TestLastname)
                .password(TestPassword)
                .role(TestRole)
                .build();

        //Arrange password
        String currentPassword = "currentPassword";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        user1.setPassword(encodedPassword);
        //Arrange Principal with user1 credentials
        Principal principal = new UsernamePasswordAuthenticationToken(user1, null);
        //Arrange build ChangePasswordRequest object
        ChangePasswordRequest request = ChangePasswordRequest.builder().build();
        //Arrange current, new and confirmation password
        request.setCurrentPassword(currentPassword);
        request.setNewPassword(newPassword);
        request.setConfirmationPassword(newPassword);
        //Arrange password check and encoding new password
        when(passwordEncoder.matches(currentPassword, encodedPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        //Act
        userService.changePassword(request, principal);

        //Assert
        verify(passwordEncoder, times(1)).matches(currentPassword, encodedPassword);
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(repository, times(1)).save(user1);
        assertEquals(encodedPassword, user1.getPassword());
    }
}