package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.config.LogoutService;
import com.avd.springsecurity6backend.token.Token;
import com.avd.springsecurity6backend.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTests {

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Test
    public void testLogout() {
        // Arrange
        String authHeader = "Bearer jwtToken";
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        Token token = new Token();
        token.setToken(authHeader.substring(7));
        when(tokenRepository.findByToken(authHeader.substring(7))).thenReturn(Optional.of(token));
        // Act
        logoutService.logout(request, response, authentication);
        // Assert
        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
    }
}
