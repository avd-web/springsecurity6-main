package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.avd.springsecurity6backend.user.User;
import io.jsonwebtoken.Claims;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTests {

    private User mockUser;
    private String username;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    public void setupUser() {
        //Arrange
        mockUser = TestUtil.createMockUser1();
        username = mockUser.getUsername();
    }

    @Test
    public void testExtractUsername() {
        //Act
        String token = jwtService.generateToken(mockUser);
        String extractedUsername = jwtService.extractUsername(token);
        //Assert
        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testExtractClaim() {
        //Act
        String token = jwtService.generateToken(mockUser);
        String extractedUsername = jwtService.extractClaim(token, Claims::getSubject);
        //Assert
        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testGenerateToken() {
        //Act
        String token = jwtService.generateToken(mockUser);
        //Assert
        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    public void testGenerateRefreshToken() {
        //Act
        String refreshToken = jwtService.generateRefreshToken(mockUser);
        //Assert
        assertNotNull(refreshToken);
        assertEquals(username, jwtService.extractUsername(refreshToken));
    }

    @Test
    public void testIsTokenValid() {
        //Act
        String token = jwtService.generateToken(mockUser);
        boolean isValid = jwtService.isTokenValid(token, mockUser);
        //Assert
        assertTrue(isValid);
    }

//    //This is how to test a PRIVATE method.
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