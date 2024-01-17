package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.auth.AuthenticationResponse;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void register() {
        // Arrange
        final User user1 = TestUtil.createMockUser1();
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("Test");
        request.setLastname("User");
        request.setUsername("testuser");
        request.setPassword("password");
        request.setRole(Role.USER);

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }
}