package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.avd.springsecurity6backend.user.User;
import io.jsonwebtoken.Claims;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@RequiredArgsConstructor
public class JwtServiceTests {

    private User mockUser;
    private String username;

    @BeforeEach
    private void setupUser() {
        mockUser = TestUtil.createMockUser1();
        username = mockUser.getUsername();
    }

    @Autowired
    private JwtService jwtService;

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(mockUser);
        String extractedUsername = jwtService.extractUsername(token);

        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testExtractClaim() {
        String token = jwtService.generateToken(mockUser);
        String extractedUsername = jwtService.extractClaim(token, Claims::getSubject);

        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);

    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(mockUser);
        //Also add the overload testGenerateToken method?

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    public void testGenerateRefreshToken() {
        String refreshToken = jwtService.generateRefreshToken(mockUser);

        assertNotNull(refreshToken);
        assertEquals(username, jwtService.extractUsername(refreshToken));
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken(mockUser);
        boolean isValid = jwtService.isTokenValid(token, mockUser);

        assertTrue(isValid);
    }

//    //This is how to test a private method.
//    @Test
//    public void testIsTokenValid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String token = jwtService.generateToken(mockUser);
//
//        // Use reflection to invoke the private method
//        Method method = JwtService.class.getDeclaredMethod("isTokenExpired", String.class);
//        method.setAccessible(true);
//        boolean isExpired = (Boolean) method.invoke(jwtService, token);
//
//        assertFalse(isExpired);
//
//    }

}