package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.auth.AuthenticationResponse;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.Token;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthenticationServiceIntegrationTests {

    //TODO: Add other methods from AuthenticationService class
    //TODO: Change handwritten strings and duplicate code

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe", "password", Role.USER);
        User mockUser = User.builder().firstname(registerRequest.getFirstname()).lastname(registerRequest.getLastname()).username(registerRequest.getUsername()).password("encodedPassword") // Mocking encoded password
                .role(registerRequest.getRole()).build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("mockRefreshToken");

        // When
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        // Then
        assertEquals("mockAccessToken", authenticationResponse.getAccessToken());
        assertEquals("mockRefreshToken", authenticationResponse.getRefreshToken());

        // Verify that necessary methods were called
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
        verify(tokenRepository).save(any(Token.class));
    }

}


