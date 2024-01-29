package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.auth.AuthenticationRequest;
import com.avd.springsecurity6backend.auth.AuthenticationResponse;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void register() {
        // Arrange
        final User user1 = TestUtil.createMockUser1();
        RegisterRequest request = new RegisterRequest();
        request.setFirstname(user1.getFirstname());
        request.setLastname(user1.getLastname());
        request.setUsername(user1.getUsername());
        request.setPassword(user1.getPassword());
        request.setRole(user1.getRole());

        String encodedPassword = "encodedPassword";

        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        User userWithEncodedPassword = new User();
        userWithEncodedPassword.setFirstname(request.getFirstname());
        userWithEncodedPassword.setLastname(request.getLastname());
        userWithEncodedPassword.setUsername(request.getUsername());
        userWithEncodedPassword.setPassword(encodedPassword);
        userWithEncodedPassword.setRole(request.getRole());

        jwtService.generateToken(userWithEncodedPassword);
        jwtService.generateRefreshToken(userWithEncodedPassword);

        when(jwtService.generateToken(userWithEncodedPassword)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(userWithEncodedPassword)).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testAuthenticate() {
        // Arrange
        final User user1 = TestUtil.createMockUser1();

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(user1.getUsername());
        request.setPassword(user1.getPassword());

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user1));
        when(jwtService.generateToken(user1)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user1)).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testRefreshToken() throws IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String authHeader = "Bearer jwtToken";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);

        User user = new User();
        user.setUsername("johndoe");
        user.setPassword("password");

        when(jwtService.extractUsername(authHeader.substring(7))).thenReturn(user.getUsername());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(authHeader.substring(7), user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("newJwtToken");

        // Create a ByteArrayOutputStream and a ServletOutputStream that writes to it
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);

        // Act
        authenticationService.refreshToken(request, response);

        // Assert
        String responseOutput = byteArrayOutputStream.toString();
        assertTrue(responseOutput.contains("newJwtToken"));
    }


}