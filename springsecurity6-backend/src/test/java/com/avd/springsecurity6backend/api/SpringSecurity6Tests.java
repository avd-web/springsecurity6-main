package com.avd.springsecurity6backend.api;

import com.avd.springsecurity6backend.auth.AuthenticationResponse;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.UserRepository;
import com.avd.springsecurity6backend.user.User;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//TODO: Add exception handling to all tests?
//TODO: Try to remove @AUTOWIRED?
//TODO: Check dependency versions and H2 Database version (why is it not the latest?)
//TODO: What is/was the github dependency vulnerability (automatic) error, did it have anything to do with the H2 db version?

@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = YourWebSecurityConfigClass.class)
class SpringSecurity6Tests {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
//
//    @Autowired
//    private JwtService jwtService;

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

    void register() {
        // Arrange
        final com.avd.springsecurity6backend.user.User mockAdmin1 = TestUtil.createMockAdmin1();
        RegisterRequest request = new RegisterRequest();
        request.setFirstname(mockAdmin1.getFirstname());
        request.setLastname(mockAdmin1.getLastname());
        request.setUsername(mockAdmin1.getUsername());
        request.setPassword(mockAdmin1.getPassword());
        request.setRole(mockAdmin1.getRole());

        String encodedPassword = "encodedPassword";

        userRepository.save(any(com.avd.springsecurity6backend.user.User.class));
        passwordEncoder.encode(request.getPassword());

        User userWithEncodedPassword = new User();
        userWithEncodedPassword.setFirstname(request.getFirstname());
        userWithEncodedPassword.setLastname(request.getLastname());
        userWithEncodedPassword.setUsername(request.getUsername());
        userWithEncodedPassword.setPassword(encodedPassword);
        userWithEncodedPassword.setRole(request.getRole());

        jwtService.generateToken(userWithEncodedPassword);
        jwtService.generateRefreshToken(userWithEncodedPassword);

        // Act
        AuthenticationResponse response = authenticationService.register(request);

//        // Assert
//        assertEquals("jwtToken", response.getAccessToken());
//        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    void test1() throws Exception {
        register();
    }
}




//    @Test
//    void test1() throws Exception {
//
//        // Create a mock UserDetails instance
//        UserDetails userDetails = User.withUsername("testUser")
//                .password("testPassword")
//                .roles("ADMIN", "MANAGER")
//                .build();
//
//        // Generate a mock JWT token
//        String tokenValue = jwtService.generateToken(userDetails);
//
//        // Create Jwt object
//        Jwt jwt = new Jwt(tokenValue, Instant.now(), Instant.now().plusMillis(jwtExpiration),
//                Collections.singletonMap("alg", "none"),
//                Collections.singletonMap("sub", userDetails.getUsername()));
//
//        // Now you can use this token for testing
//
//        mockMvc.perform(get("/api/v1/demo-controller")
//                        .with(jwt().jwt())
//                )
//                .andExpect(status().isOk());
//    }

// Register a new user
//        this.mvc.perform(post("/api/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"admin@mail.com\", \"password\":\"admin\"}"))
//                .andExpect(status().isOk());

// Authenticate the new user
//        this.mvc.perform(post("/api/v1/auth/authenticate")
//                        .with(httpBasic("admin@mail.com", "admin"))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

//    @Test
//    @WithMockUser(username = "avd@mail.com", password = "1234")
//    public void testAuthentication() throws Exception {
//        mockMvc.perform(get("http://localhost:8080/api/v1/demo")
//                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
//                .andExpect(status().isOk());
//
//    }

//    @Test
//    @WithUserDetails
//    @WithMockUser(firstname = "admin", lastname = "admin", email = "admin@mail.com", password = "admin", role = "ADMIN")
//    public void testAuthentication2() throws Exception {
//        mockMvc.perform(get("http://localhost:8080/api/v1/auth/register")
//                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
//                .andExpect(status().isOk());
//
//    }

