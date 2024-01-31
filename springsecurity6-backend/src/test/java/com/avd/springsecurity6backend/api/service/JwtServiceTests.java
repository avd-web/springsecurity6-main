package com.avd.springsecurity6backend.api.service;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.user.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.avd.springsecurity6backend.user.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
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

    }

    @Test
    public void testExtractClaim() {

    }

    @Test
    public void testGenerateToken() {


        String token = jwtService.generateToken(mockUser);
        //ALSO ADD OVERLOADED GENERATE TOKEN FUNCTION?

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));

    }

    @Test
    public void testGenerateRefreshToken() {

    }

    @Test
    public void testIsTokenExpired() {

    }


//    @Test
//    public void testIsTokenValid() {
//        String username = "testUser";
//        String password = "testPassword";
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username(username)
//                .password(password)
//                .roles("USER")
//                .build();
//
//        String token = jwtService.generateToken(userDetails);
//        assertTrue(jwtService.isTokenValid(token, userDetails));
//    }

//    @Test
//    public void testIsTokenExpired() {
//        String username = "testUser";
//        String password = "testPassword";
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username(username)
//                .password(password)
//                .roles("USER")
//                .build();
//
//        String token = jwtService.generateToken(userDetails);
//        assertFalse(jwtService.isTokenExpired(token));
//    }
}