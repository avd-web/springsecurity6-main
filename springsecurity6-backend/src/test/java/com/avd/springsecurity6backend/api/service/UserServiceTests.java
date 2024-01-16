package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
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
class UserServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void UserService_ChangePassword_ReturnChangedPassword() {

        //Arrange user1 and password
        final User user1 = TestUtil.createMockUser1();
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
