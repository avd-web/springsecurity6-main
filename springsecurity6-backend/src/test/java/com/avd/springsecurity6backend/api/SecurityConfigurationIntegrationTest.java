package com.avd.springsecurity6backend.api;

import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.auth.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityConfigurationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    @Test
    void testRegistrationLoginAndAccessAdminRoute() {
        // Registration
        String registerUrl = "http://localhost:" + port + "/api/v1/auth/register";
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe", "password", Role.ADMIN);
        ResponseEntity<Void> registrationResponse = restTemplate.postForEntity(registerUrl, registerRequest, Void.class);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());

//        // Login
//        String loginUrl = "http://localhost:" + port + "/api/v1/auth/authenticate";
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john.doe", "password");
//        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(loginUrl, authenticationRequest, Void.class);
//        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        // Extract JWT token from the response headers
        HttpHeaders headers = registrationResponse.getHeaders();
        String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);


        // Access Admin Route with JWT token
        String adminUrl = "http://localhost:" + port + "/api/v1/demo-controller";

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set(HttpHeaders.AUTHORIZATION, accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, authHeaders);

        ResponseEntity<String> adminResponse = restTemplate.exchange(adminUrl, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, adminResponse.getStatusCode());
        // Add more assertions based on your specific implementation

//        // Logout
//        String logoutUrl = "http://localhost:" + port + "/api/v1/auth/logout";
//        ResponseEntity<Void> logoutResponse = restTemplate.postForEntity(logoutUrl, null, Void.class);
//        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
    }
    }

